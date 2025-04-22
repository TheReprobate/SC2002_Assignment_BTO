package btosystem.service.applicant;

import java.util.List;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.Project;
import btosystem.classes.enums.FlatType;
import btosystem.controllers.interfaces.BtoApplicationOperations;
import btosystem.controllers.interfaces.EnquiryOperations;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;
import btosystem.controllers.interfaces.ProjectOperations;
import btosystem.controllers.interfaces.ProjectTeamOperations;
import btosystem.controllers.interfaces.UserOperations;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class ApplicantBtoApplicationService extends Service {

    public ApplicantBtoApplicationService(DataManager dataManager, BtoApplicationOperations applicationManager, EnquiryOperations enquiryManager,
            OfficerRegistrationOperations registrationOperations, ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations, ProjectOperations projectOperations) {
        super(dataManager, applicationManager, enquiryManager, registrationOperations, projectTeamOperations, userOperations, projectOperations);
    }

    public BtoApplication getApplication(Applicant user) {
        return userManager.retrieveApplication(user);
    }

    public List<FlatType> getAvailableFlatTypes(Applicant user, Project project) {
        List<FlatType> eligibleFlatTypes = applicationManager.getEligibleFlatTypes(user);
        List<FlatType> availableFlatTypes = projectManager.getAvailableFlatTypes(project);
        eligibleFlatTypes.removeIf(f -> !availableFlatTypes.contains(f));
        return eligibleFlatTypes;
    }
    
    public void createApplication(Applicant user, Project project, FlatType flatType) throws Exception {
        List<BtoApplication> projectApplicationRef = projectManager.retrieveApplications(project);
        BtoApplication application = userManager.retrieveApplication(user);
        if (!projectManager.isOpen(project)) { 
            throw new Exception("Project is unavailable. ");
        }
        if (applicationManager.hasApplied(projectApplicationRef, user)) {
            throw new Exception("Applicant has applied for this project before, unable to reapply. ");
        }
        if (application != null) {
            throw new Exception("Applicant has an existing application. ");
        }
        
        if (!projectManager.unitHasSlots(project, flatType)) {
            throw new Exception("There is no slots available for your requirements.  ");
        }
        application = applicationManager.createApplication(project, user, flatType);
        applicationManager.addApplication(projectApplicationRef, application);
        userManager.setApplication(user, application);
    }

    public void withdrawApplication(Applicant user) throws Exception {
        BtoApplication application = userManager.retrieveApplication(user);
        if (application == null) {
            throw new Exception("Applicant does not have an existing application. ");
        }
        applicationManager.withdrawApplication(application);
        userManager.removeApplication(user);
    }

}
