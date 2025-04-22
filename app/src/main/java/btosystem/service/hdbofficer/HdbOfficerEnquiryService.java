package btosystem.service.hdbofficer;

import java.nio.file.AccessDeniedException;
import java.util.List;

import btosystem.classes.Enquiry;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.controllers.interfaces.BtoApplicationOperations;
import btosystem.controllers.interfaces.EnquiryOperations;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;
import btosystem.controllers.interfaces.ProjectOperations;
import btosystem.controllers.interfaces.ProjectTeamOperations;
import btosystem.controllers.interfaces.UserOperations;
import btosystem.service.applicant.ApplicantEnquiryService;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class HdbOfficerEnquiryService extends ApplicantEnquiryService {

    public HdbOfficerEnquiryService(DataManager dataManager, BtoApplicationOperations applicationManager, EnquiryOperations enquiryManager,
            OfficerRegistrationOperations registrationOperations, ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations, ProjectOperations projectOperations) {
        super(dataManager, applicationManager, enquiryManager, registrationOperations, projectTeamOperations, userOperations, projectOperations);
    }

    public List<Enquiry> getEnquiries(Project project) {
        return projectManager.retrieveEnquiries(project);
    }

    public List<Enquiry> getEnquiries(Project project, boolean replied) {
        return enquiryManager.filterEnquiries(getEnquiries(project), replied);
    }

    public List<Enquiry> getRepliableEnquiries(HdbOfficer user) {
        return getViewableProjectEnquiries(user);
    }

    public void replyEnquiry(HdbOfficer user, Enquiry enquiry, String reply) throws Exception {
        Project enquiryProject = enquiryManager.retrieveProject(enquiry);
        if (!projectManager.isOpen(enquiryProject)) {
            throw new Exception("Project is not open");
        }
        ProjectTeam team = userManager.retrieveCurrentTeam(user);
        Project currentProj = projectTeamManager.retrieveAssignedProject(team);
        Project enquiryProj = enquiryManager.retrieveProject(enquiry);
        if (!currentProj.equals(enquiryProj)) {
            throw new AccessDeniedException("Access Denied. No permission to reply to this enquiry. ");
        }
        enquiryManager.replyEnquiry(enquiry, reply);
    }

    private List<Enquiry> getViewableProjectEnquiries(HdbOfficer user) {
        ProjectTeam currentTeam = userManager.retrieveCurrentTeam(user);
        Project currentProject = projectTeamManager.retrieveAssignedProject(currentTeam);
        List<Enquiry> projectEnquiries = projectManager.retrieveEnquiries(currentProject);
        return projectEnquiries;
    }
    
}
