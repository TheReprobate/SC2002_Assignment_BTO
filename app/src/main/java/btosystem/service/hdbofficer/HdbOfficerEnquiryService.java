package btosystem.service.hdbofficer;

import java.nio.file.AccessDeniedException;
import java.util.List;

import btosystem.classes.Enquiry;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.service.applicant.ApplicantEnquiryService;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class HdbOfficerEnquiryService extends ApplicantEnquiryService {

    public HdbOfficerEnquiryService(DataManager dataManager, OperationsManager operationsManager) {
        super(dataManager, operationsManager);
    }

    public List<Enquiry> getEnquiries(Project project) {
        return operationsManager.getProjectManager().retrieveEnquiries(project);
    }

    public List<Enquiry> getEnquiries(Project project, boolean replied) {
        return operationsManager.getEnquiryManager().filterEnquiries(getEnquiries(project), replied);
    }

    public List<Enquiry> getRepliableEnquiries(HdbOfficer user) {
        return getViewableProjectEnquiries(user);
    }

    public void replyEnquiry(HdbOfficer user, Enquiry enquiry, String reply) throws Exception {
        Project enquiryProject = operationsManager.getEnquiryManager().retrieveProject(enquiry);
        if(!operationsManager.getProjectManager().isOpen(enquiryProject)) {
            throw new Exception("Project is not open");
        }
        ProjectTeam team = operationsManager.getUserManager().retrieveCurrentTeam(user);
        Project currentProj = operationsManager.getProjectTeamManager().retrieveAssignedProject(team);
        Project enquiryProj = operationsManager.getEnquiryManager().retrieveProject(enquiry);
        if(!currentProj.equals(enquiryProj)) {
            throw new AccessDeniedException("Access Denied. No permission to reply to this enquiry. ");
        }
        operationsManager.getEnquiryManager().replyEnquiry(enquiry, reply);
    }

    private List<Enquiry> getViewableProjectEnquiries(HdbOfficer user) {
        ProjectTeam currentTeam = operationsManager.getUserManager().retrieveCurrentTeam(user);
        Project currentProject = operationsManager.getProjectTeamManager().retrieveAssignedProject(currentTeam);
        List<Enquiry> projectEnquiries = operationsManager.getProjectManager().retrieveEnquiries(currentProject);
        return projectEnquiries;
    }
    
}
