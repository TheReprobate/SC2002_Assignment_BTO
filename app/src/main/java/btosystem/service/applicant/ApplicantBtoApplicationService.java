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
    
    public void createApplication(Applicant user, Project project) throws Exception {
        BtoApplication application = getOperationsManager().getUserManager().retrieveApplication(user);
        if(application != null) {
            throw new Exception("Applicant has an existing application. ");
        }
        if(getOperationsManager().getProjectManager().isOpen(project)) { 
            throw new Exception("Project is unavailable. ");
        }
        List<FlatType> flatOptions = getOperationsManager().getUserManager().getAllowedFlatTypes(user);
        for(FlatType f: flatOptions) {
            if(getOperationsManager().getProjectManager().unitHasSlots(project, f));{
                application = getOperationsManager().getApplicationManager().createApplication(project, user);
                List<BtoApplication> projectApplicationRef = getOperationsManager().getProjectManager().retrieveApplications(project);
                getOperationsManager().getApplicationManager().addApplication(projectApplicationRef, application);
            }
        }
        throw new Exception("There is no slots available for your requirements.  ");
    }

    public void withdrawApplication(Applicant user) throws Exception {
        BtoApplication application = getOperationsManager().getUserManager().retrieveApplication(user);
        if(application == null) {
            throw new Exception("Applicant does not have an existing application. ");
        }
        getOperationsManager().getApplicationManager().withdrawApplication(application);
    }

}
