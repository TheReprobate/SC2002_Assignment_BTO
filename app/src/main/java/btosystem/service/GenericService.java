package btosystem.service;

import java.util.List;
import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.Project;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class GenericService extends Service{

    public GenericService(DataManager dataManager, OperationsManager operationsManager) {
        super(dataManager, operationsManager);
    }

    public String displayEnquiry(List<Enquiry> enquiries) {
        if(enquiries == null){
            return "";
        }
        return getOperationsManager().getEnquiryManager().toString(enquiries);
    }

    public String displayEnquiry(Enquiry enquiry) {
        if(enquiry == null){
            return "";
        }
        return getOperationsManager().getEnquiryManager().toString(enquiry);
    }

    public Enquiry getEnquiry(List<Enquiry> enquiries, int index) throws Exception {
        return getOperationsManager().getEnquiryManager().retrieveEnquiry(enquiries, index);
    }

    public String displayProject(Project project) {
        if(project == null){
            return "";
        }
        return getOperationsManager().getProjectManager().toString(project);
    }
    
    public String displayProject(List<Project> projects) {
        if(projects == null){
            return "";
        }
        return getOperationsManager().getProjectManager().toString(projects);
    }

    public Project getProject(List<Project> projects, int index) throws Exception{
        return getOperationsManager().getProjectManager().retrieveProject(projects, index);
    }

    public String displayApplication(BtoApplication application) {
        if(application == null){
            return "";
        }
        return getOperationsManager().getApplicationManager().toString(application);
    }

    public String displayApplication(List<BtoApplication> applications) {
        return getOperationsManager().getApplicationManager().toString(applications);
    }

    public BtoApplication getApplication(List<BtoApplication> applications, int index) {
        return getOperationsManager().getApplicationManager().retrieveApplication(applications, index);
    }
    
}
