package btosystem.service.applicant;

import java.util.List;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.Project;
import btosystem.classes.enums.FlatType;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class ApplicantBtoApplicationService extends Service {

    public ApplicantBtoApplicationService(DataManager dataManager, OperationsManager operationsManager) {
        super(dataManager, operationsManager);
    }

    public BtoApplication getApplication(Applicant user) {
        return operationsManager.getUserManager().retrieveApplication(user);
    }

    public List<FlatType> getAvailableFlatTypes(Applicant user, Project project) {
        List<FlatType> eligibleFlatTypes = operationsManager.getApplicationManager().getEligibleFlatTypes(user);
        List<FlatType> availableFlatTypes = operationsManager.getProjectManager().getAvailableFlatTypes(project);
        eligibleFlatTypes.removeIf(f -> !availableFlatTypes.contains(f));
        return eligibleFlatTypes;
    }
    
    public void createApplication(Applicant user, Project project, FlatType flatType) throws Exception {
        List<BtoApplication> projectApplicationRef = operationsManager.getProjectManager().retrieveApplications(project);
        BtoApplication application = operationsManager.getUserManager().retrieveApplication(user);
        if(!operationsManager.getProjectManager().isOpen(project)) { 
            throw new Exception("Project is unavailable. ");
        }
        if(operationsManager.getApplicationManager().hasApplied(projectApplicationRef, user)){
            throw new Exception("Applicant has applied for this project before, unable to reapply. ");
        }
        if(application != null) {
            throw new Exception("Applicant has an existing application. ");
        }
        
        if(!operationsManager.getProjectManager().unitHasSlots(project, flatType)) {
            throw new Exception("There is no slots available for your requirements.  ");
        }
        application = operationsManager.getApplicationManager().createApplication(project, user, flatType);
        operationsManager.getApplicationManager().addApplication(projectApplicationRef, application);
        operationsManager.getUserManager().setApplication(user, application);
    }

    public void withdrawApplication(Applicant user) throws Exception {
        BtoApplication application = operationsManager.getUserManager().retrieveApplication(user);
        if(application == null) {
            throw new Exception("Applicant does not have an existing application. ");
        }
        operationsManager.getApplicationManager().withdrawApplication(application);
        operationsManager.getUserManager().removeApplication(user);
    }

}
