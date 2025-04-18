package btosystem.service.hdbofficer;

import java.util.List;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.User;
import btosystem.classes.enums.FlatType;
import btosystem.service.applicant.ApplicantBtoApplicationService;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class HdbOfficerBtoApplicationService extends ApplicantBtoApplicationService{

    public HdbOfficerBtoApplicationService(DataManager dataManager, OperationsManager operationsManager) {
        super(dataManager, operationsManager);
    }

    @Override
    public void createApplication(Applicant user, Project project, FlatType flatType) throws Exception {
        if(hasProjectAccess((HdbOfficer) user, project)){
            throw new Exception("Access Denied. Not allowed to apply for this project. ");
        }
        super.createApplication(user, project, flatType);
    }

    public BtoApplication getApplication(String nric) throws Exception {
        Applicant applicant = getApplicant(nric);
        BtoApplication application = getOperationsManager().getUserManager().retrieveApplication(applicant);
        if(application == null) {
            throw new Exception("Access Denied.Applicant does not have existing application. ");
        }
        return application;
    }

    public void processApplication(BtoApplication application, HdbOfficer officer) throws Exception {
        Applicant applicant = getOperationsManager().getApplicationManager().retrieveApplicant(application);
        if(!hasApplicationAccess(officer, application)){
            throw new Exception("Access Denied. Not allowed to process own application. ");
        }        
        FlatType flatType = application.getFlatType();
        if(!hasApplicationAccess(officer, application)){
            throw new Exception("Access Denied. Not allowed to access this application. ");
        }
        if(!getOperationsManager().getApplicationManager().isReadyToProcess(application)) {
            throw new Exception("Application is not approved. ");
        }
        List<FlatType> allowedflatTypes = getOperationsManager().getApplicationManager().getEligibleFlatTypes(applicant);
        if(!allowedflatTypes.contains(flatType)) {
            throw new Exception("Not allowed to choose this flat type. ");
        }
        Project applicationProject = getOperationsManager().getApplicationManager().retrieveProject(application);
        if(!getOperationsManager().getProjectManager().unitHasSlots(applicationProject, flatType)) {
            throw new Exception("Flat type does don't have slots. ");
        }
        getOperationsManager().getApplicationManager().processApplication(application, officer);
        getOperationsManager().getProjectManager().decreaseUnitCount(applicationProject, flatType);
    }
    private boolean hasApplicationAccess(HdbOfficer user, BtoApplication application) {
        Project applicationProject = getOperationsManager().getApplicationManager().retrieveProject(application);
        return hasProjectAccess(user, applicationProject);
    }
    private boolean hasProjectAccess(HdbOfficer user, Project project) {
        ProjectTeam currentTeam = getOperationsManager().getUserManager().retrieveCurrentTeam(user);
        Project projectInCharge = getOperationsManager().getProjectTeamManager().retrieveAssignedProject(currentTeam);
        return projectInCharge.equals(project);
    }
    private Applicant getApplicant(String nric) throws Exception {
        User user = getOperationsManager().getUserManager().retrieveUser(getDataManager().getUsers(), nric);
        if(!(user instanceof Applicant)){
            throw new Exception("User is not an applicant. ");
        }
        return (Applicant) user;
    }
}
