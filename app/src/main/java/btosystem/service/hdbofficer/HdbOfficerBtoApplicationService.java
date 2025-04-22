package btosystem.service.hdbofficer;

import java.util.List;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.User;
import btosystem.classes.enums.FlatType;
import btosystem.controllers.interfaces.BtoApplicationOperations;
import btosystem.controllers.interfaces.EnquiryOperations;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;
import btosystem.controllers.interfaces.ProjectOperations;
import btosystem.controllers.interfaces.ProjectTeamOperations;
import btosystem.controllers.interfaces.UserOperations;
import btosystem.service.applicant.ApplicantBtoApplicationService;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class HdbOfficerBtoApplicationService extends ApplicantBtoApplicationService{

    public HdbOfficerBtoApplicationService(DataManager dataManager, BtoApplicationOperations applicationManager, EnquiryOperations enquiryManager,
            OfficerRegistrationOperations registrationOperations, ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations, ProjectOperations projectOperations) {
        super(dataManager, applicationManager, enquiryManager, registrationOperations, projectTeamOperations, userOperations, projectOperations);
    }

    @Override
    public void createApplication(Applicant user, Project project, FlatType flatType) throws Exception {
        if (hasProjectAccess((HdbOfficer) user, project)) {
            throw new Exception("Access Denied. Not allowed to apply for this project. ");
        }
        super.createApplication(user, project, flatType);
    }

    public List<BtoApplication> getApplications(Project project) {
        return projectManager.retrieveApplications(project);
    }

    public BtoApplication getApplication(String nric) throws Exception {
        Applicant applicant = getApplicant(nric);
        BtoApplication application = userManager.retrieveApplication(applicant);
        if (application == null) {
            throw new Exception("Access Denied.Applicant does not have existing application. ");
        }
        return application;
    }

    public void processApplication(BtoApplication application, HdbOfficer officer) throws Exception {
        Applicant applicant = applicationManager.retrieveApplicant(application);
        if (!hasApplicationAccess(officer, application)) {
            throw new Exception("Access Denied. Not allowed to process own application. ");
        }        
        FlatType flatType = application.getFlatType();
        if (!hasApplicationAccess(officer, application)) {
            throw new Exception("Access Denied. Not allowed to access this application. ");
        }
        if (!applicationManager.isReadyToProcess(application)) {
            throw new Exception("Application is not approved. ");
        }
        List<FlatType> allowedflatTypes = applicationManager.getEligibleFlatTypes(applicant);
        if (!allowedflatTypes.contains(flatType)) {
            throw new Exception("Not allowed to choose this flat type. ");
        }
        Project applicationProject = applicationManager.retrieveProject(application);
        if (!projectManager.unitHasSlots(applicationProject, flatType)) {
            throw new Exception("Flat type does don't have slots. ");
        }
        applicationManager.processApplication(application, officer);
        projectManager.decreaseUnitCount(applicationProject, flatType);
    }

    private boolean hasApplicationAccess(HdbOfficer user, BtoApplication application) {
        Project applicationProject = applicationManager.retrieveProject(application);
        return hasProjectAccess(user, applicationProject);
    }
    private boolean hasProjectAccess(HdbOfficer user, Project project) {
        ProjectTeam currentTeam = userManager.retrieveCurrentTeam(user);
        Project projectInCharge = projectTeamManager.retrieveAssignedProject(currentTeam);
        return projectInCharge.equals(project);
    }
    private Applicant getApplicant(String nric) throws Exception {
        User user = userManager.retrieveUser(dataManager.getUsers(), nric);
        if (!(user instanceof Applicant)) {
            throw new Exception("User is not an applicant. ");
        }
        return (Applicant) user;
    }
}
