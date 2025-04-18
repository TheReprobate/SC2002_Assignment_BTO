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

    public String displayProjectEnquiries(HdbOfficer user) {
        List<Enquiry> enquiries = getViewableProjectEnquiries(user);
        return getOperationsManager().getEnquiryManager().toString(enquiries);
    }

    public List<Enquiry> getRepliableEnquiries(HdbOfficer user) {
        return getViewableProjectEnquiries(user);
    }

    public void replyEnquiry(HdbOfficer user, Enquiry enquiry, String reply) throws Exception {
        Project enquiryProject = getOperationsManager().getEnquiryManager().retrieveProject(enquiry);
        if(!getOperationsManager().getProjectManager().isOpen(enquiryProject)) {
            throw new Exception("Project is not open");
        }
        ProjectTeam team = getOperationsManager().getUserManager().retrieveCurrentTeam(user);
        Project currentProj = getOperationsManager().getProjectTeamManager().retrieveAssignedProject(team);
        Project enquiryProj = getOperationsManager().getEnquiryManager().retrieveProject(enquiry);
        if(!currentProj.equals(enquiryProj)) {
            throw new AccessDeniedException("Access Denied. No permission to reply to this enquiry. ");
        }
        getOperationsManager().getEnquiryManager().replyEnquiry(enquiry, reply);
    }

    private List<Enquiry> getViewableProjectEnquiries(HdbOfficer user) {
        ProjectTeam currentTeam = getOperationsManager().getUserManager().retrieveCurrentTeam(user);
        Project currentProject = getOperationsManager().getProjectTeamManager().retrieveAssignedProject(currentTeam);
        List<Enquiry> projectEnquiries = getOperationsManager().getProjectManager().retrieveEnquiries(currentProject);
        return projectEnquiries;
    }
    
}
