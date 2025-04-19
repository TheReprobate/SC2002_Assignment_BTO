package btosystem.service.hdbmanager;

import java.util.List;

import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class HdbManagerEnquiryService extends Service {

    public HdbManagerEnquiryService(DataManager dataManager, OperationsManager operationsManager) {
        super(dataManager, operationsManager);
    }

    public List<Enquiry> getEnquiries(Project project) {
        return operationsManager.getProjectManager().retrieveEnquiries(project);
    }

    public List<Enquiry> getEnquiries(Project project, boolean replied) {
        return operationsManager.getEnquiryManager().filterEnquiries(getEnquiries(project), replied);
    }

    public List<Enquiry> getRepliableEnquiries(HdbManager user) {
        ProjectTeam currentTeam = operationsManager.getUserManager().retrieveCurrentTeam(user);
        Project currentProject = operationsManager.getProjectTeamManager().retrieveAssignedProject(currentTeam);
        List<Enquiry> projectEnquiries = operationsManager.getProjectManager().retrieveEnquiries(currentProject);
        return projectEnquiries;
    }

    public void replyEnquiry(HdbManager user, Enquiry enquiry, String reply) throws Exception{
        ProjectTeam team = operationsManager.getUserManager().retrieveCurrentTeam(user);
        Project currentProj = operationsManager.getProjectTeamManager().retrieveAssignedProject(team);
        Project enquiryProj = operationsManager.getEnquiryManager().retrieveProject(enquiry);
        if(!currentProj.equals(enquiryProj)) {
            throw new Exception("Access Denied. No permission to reply to this enquiry. ");
        }
        operationsManager.getEnquiryManager().replyEnquiry(enquiry, reply);
    }
}
