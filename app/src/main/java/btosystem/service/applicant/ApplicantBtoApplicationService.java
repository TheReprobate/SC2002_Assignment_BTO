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
        return getOperationsManager().getUserManager().retrieveApplication(user);
    }

    public List<FlatType> getAvailableFlatTypes(Applicant user, Project project) {
        List<FlatType> eligibleFlatTypes = getOperationsManager().getApplicationManager().getEligibleFlatTypes(user);
        List<FlatType> availableFlatTypes = getOperationsManager().getProjectManager().getAvailableFlatTypes(project);
        eligibleFlatTypes.removeIf(f -> !availableFlatTypes.contains(f));
        return eligibleFlatTypes;
    }
    
    public void createApplication(Applicant user, Project project, FlatType flatType) throws Exception {
        List<BtoApplication> projectApplicationRef = getOperationsManager().getProjectManager().retrieveApplications(project);
        BtoApplication application = getOperationsManager().getUserManager().retrieveApplication(user);
        if(!getOperationsManager().getProjectManager().isOpen(project)) { 
            throw new Exception("Project is unavailable. ");
        }
        if(getOperationsManager().getApplicationManager().hasApplied(projectApplicationRef, user)){
            throw new Exception("Applicant has applied for this project before, unable to reapply. ");
        }
        if(application != null) {
            throw new Exception("Applicant has an existing application. ");
        }
        
        if(!getOperationsManager().getProjectManager().unitHasSlots(project, flatType)) {
            throw new Exception("There is no slots available for your requirements.  ");
        }
        application = getOperationsManager().getApplicationManager().createApplication(project, user, flatType);
        getOperationsManager().getApplicationManager().addApplication(projectApplicationRef, application);
        getOperationsManager().getUserManager().setApplication(user, application);
    }

    public void withdrawApplication(Applicant user) throws Exception {
        BtoApplication application = getOperationsManager().getUserManager().retrieveApplication(user);
        if(application == null) {
            throw new Exception("Applicant does not have an existing application. ");
        }
        getOperationsManager().getApplicationManager().withdrawApplication(application);
        getOperationsManager().getUserManager().removeApplication(user);
    }

}
