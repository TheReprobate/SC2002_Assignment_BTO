package btosystem.service;

import java.util.List;
import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
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
        return operationsManager.getEnquiryManager().toString(enquiries);
    }

    public String displayEnquiry(Enquiry enquiry) {
        if(enquiry == null){
            return "";
        }
        return operationsManager.getEnquiryManager().toString(enquiry);
    }

    public Enquiry getEnquiry(List<Enquiry> enquiries, int index) throws Exception {
        return operationsManager.getEnquiryManager().retrieveEnquiry(enquiries, index);
    }

    public String displayProject(Project project) {
        if(project == null){
            return "";
        }
        return operationsManager.getProjectManager().toString(project);
    }
    
    public String displayProject(List<Project> projects) {
        if(projects == null){
            return "";
        }
        return operationsManager.getProjectManager().toString(projects);
    }

    public Project getProject(List<Project> projects, int index) throws Exception{
        return operationsManager.getProjectManager().retrieveProject(projects, index);
    }

    public String displayApplication(BtoApplication application) {
        if(application == null){
            return "";
        }
        return operationsManager.getApplicationManager().toString(application);
    }

    public String displayApplication(List<BtoApplication> applications) {
        return operationsManager.getApplicationManager().toString(applications);
    }

    public String displayTeam(ProjectTeam team) {
        return operationsManager.getProjectTeamManager().toString(team);
    }

    public String displayRegistration(List<OfficerRegistration> registrations) {
        return operationsManager.getRegistrationManager().toString(registrations);
    }

    public BtoApplication getApplication(List<BtoApplication> applications, int index) {
        return operationsManager.getApplicationManager().retrieveApplication(applications, index);
    }

    public OfficerRegistration getRegistration(List<OfficerRegistration> registrations, int index) throws Exception {
        return operationsManager.getRegistrationManager().retrieveOfficerRegistration(registrations, index);
    }
}
