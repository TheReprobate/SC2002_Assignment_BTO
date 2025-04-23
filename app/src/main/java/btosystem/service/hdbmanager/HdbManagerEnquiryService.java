package btosystem.service.hdbmanager;

import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.controllers.interfaces.BtoApplicationOperations;
import btosystem.controllers.interfaces.EnquiryOperations;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;
import btosystem.controllers.interfaces.ProjectOperations;
import btosystem.controllers.interfaces.ProjectTeamOperations;
import btosystem.controllers.interfaces.UserOperations;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import java.util.List;

public class HdbManagerEnquiryService extends Service {

    public HdbManagerEnquiryService(DataManager dataManager, 
                                    BtoApplicationOperations applicationManager, 
                                    EnquiryOperations enquiryManager,
                                    OfficerRegistrationOperations registrationOperations, 
                                    ProjectTeamOperations projectTeamOperations,
                                    UserOperations userOperations, 
                                    ProjectOperations projectOperations) {
        super(dataManager, 
            applicationManager, 
            enquiryManager, 
            registrationOperations, 
            projectTeamOperations, 
            userOperations, 
            projectOperations);
    }

    public List<Enquiry> getEnquiries(Project project) {
        return projectManager.retrieveEnquiries(project);
    }

    public List<Enquiry> getEnquiries(Project project, boolean replied) {
        return enquiryManager.filterEnquiries(getEnquiries(project), replied);
    }

    public List<Enquiry> getRepliableEnquiries(HdbManager user) {
        ProjectTeam currentTeam = userManager.retrieveCurrentTeam(user);
        Project currentProject = projectTeamManager.retrieveAssignedProject(currentTeam);
        List<Enquiry> projectEnquiries = projectManager.retrieveEnquiries(currentProject);
        return projectEnquiries;
    }

    public void replyEnquiry(HdbManager user, Enquiry enquiry, String reply) throws Exception {
        ProjectTeam team = userManager.retrieveCurrentTeam(user);
        Project currentProj = projectTeamManager.retrieveAssignedProject(team);
        Project enquiryProj = enquiryManager.retrieveProject(enquiry);
        if (!currentProj.equals(enquiryProj)) {
            throw new Exception("Access Denied. No permission to reply to this enquiry. ");
        }
        enquiryManager.replyEnquiry(enquiry, reply);
    }
}
