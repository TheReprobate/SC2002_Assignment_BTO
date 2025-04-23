package btosystem.service;

import java.util.List;
import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.controllers.interfaces.BtoApplicationOperations;
import btosystem.controllers.interfaces.EnquiryOperations;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;
import btosystem.controllers.interfaces.ProjectOperations;
import btosystem.controllers.interfaces.ProjectTeamOperations;
import btosystem.controllers.interfaces.UserOperations;
import btosystem.utils.DataManager;

public class GenericService extends Service{

    public GenericService(DataManager dataManager, BtoApplicationOperations applicationManager, EnquiryOperations enquiryManager,
            OfficerRegistrationOperations registrationOperations, ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations, ProjectOperations projectOperations) {
        super(dataManager, applicationManager, enquiryManager, registrationOperations, projectTeamOperations, userOperations, projectOperations);
    }

    public String displayEnquiry(List<Enquiry> enquiries) {
        if(enquiries == null){
            return "";
        }
        return enquiryManager.toString(enquiries);
    }

    public String displayEnquiry(Enquiry enquiry) {
        if(enquiry == null){
            return "";
        }
        Project project = enquiryManager.retrieveProject(enquiry);
        if (project == null) {
            return "Project no longer exist" + enquiryManager.toString(enquiry);
        }
        return projectManager.toString(project) + enquiryManager.toString(enquiry);
    }

    public Enquiry getEnquiry(List<Enquiry> enquiries, int index) throws Exception {
        return enquiryManager.retrieveEnquiry(enquiries, index);
    }

    public String displayProject(Project project) {
        if(project == null){
            return "";
        }
        return projectManager.toString(project);
    }
    
    public String displayProject(List<Project> projects) {
        if(projects == null){
            return "";
        }
        return projectManager.toString(projects);
    }

    public Project getProject(List<Project> projects, int index) throws Exception{
        return projectManager.retrieveProject(projects, index);
    }

    public String displayApplication(BtoApplication application) {
        if(application == null){
            return "";
        }
        Project project = applicationManager.retrieveProject(application);
        if (project == null) {
            return "Project no longer exist" + applicationManager.toString(application);
        }
        return projectManager.toString(project) + userManager.toString(applicationManager.retrieveApplicant(application)) + applicationManager.toString(application);
    }

    public String displayApplication(List<BtoApplication> applications) {
        return applicationManager.toString(applications);
    }

    public String displayTeam(ProjectTeam team) {
        return projectTeamManager.toString(team);
    }

    public String displayRegistration(List<OfficerRegistration> registrations) {
        return registrationManager.toString(registrations);
    }

    public BtoApplication getApplication(List<BtoApplication> applications, int index) {
        return applicationManager.retrieveApplication(applications, index);
    }

    public OfficerRegistration getRegistration(List<OfficerRegistration> registrations, int index) throws Exception {
        return registrationManager.retrieveOfficerRegistration(registrations, index);
    }
}
