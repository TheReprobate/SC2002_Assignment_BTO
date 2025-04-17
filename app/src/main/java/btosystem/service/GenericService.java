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
        return getOperationsManager().getEnquiryManager().toString(enquiries);
    }

    public String displayEnquiry(Enquiry enquiry) {
        return getOperationsManager().getEnquiryManager().toString(enquiry);
    }

    public Enquiry getEnquiry(List<Enquiry> enquiries, int index) throws Exception {
        return getOperationsManager().getEnquiryManager().retrieveEnquiry(enquiries, index);
    }

    public String displayProject(Project project) {
        return getOperationsManager().getProjectManager().toString(project);
    }
    
    public String displayProject(List<Project> projects) {
        return getOperationsManager().getProjectManager().toString(projects);
    }

    public Project getProject(List<Project> projects, int index) throws Exception{
        return getOperationsManager().getProjectManager().retrieveProject(projects, index);
    }

    public String displayApplication(BtoApplication application) {
        return getOperationsManager().getApplicationManager().toString(application);
    }
    
}
