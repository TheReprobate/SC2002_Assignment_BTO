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
    
    public String displayProjectEnquiries(Project project) {
        List<Enquiry> projectEnquiries = getOperationsManager().getProjectManager().retrieveEnquiries(project);
        return getOperationsManager().getEnquiryManager().toString(projectEnquiries);
    }

    public List<Enquiry> getRepliableEnquiries(HdbManager user) {
        ProjectTeam currentTeam = getOperationsManager().getUserManager().retrieveCurrentTeam(user);
        Project currentProject = getOperationsManager().getProjectTeamManager().retrieveAssignedProject(currentTeam);
        List<Enquiry> projectEnquiries = getOperationsManager().getProjectManager().retrieveEnquiries(currentProject);
        return projectEnquiries;
    }

    public void replyEnquiry(HdbManager user, Enquiry enquiry, String reply) throws Exception{
        ProjectTeam team = getOperationsManager().getUserManager().retrieveCurrentTeam(user);
        Project currentProj = getOperationsManager().getProjectTeamManager().retrieveAssignedProject(team);
        Project enquiryProj = getOperationsManager().getEnquiryManager().retrieveProject(enquiry);
        if(!currentProj.equals(enquiryProj)) {
            throw new Exception("No permission to reply to this enquiry. ");
        }
        getOperationsManager().getEnquiryManager().replyEnquiry(enquiry, reply);
    }
}
