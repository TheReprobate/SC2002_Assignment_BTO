package btosystem.service.hdbofficer;

import java.util.List;

import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.Neighborhood;
import btosystem.service.applicant.ApplicantProjectService;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class HdbOfficerProjectService extends ApplicantProjectService {

    public HdbOfficerProjectService(DataManager dataManager, OperationsManager operationsManager) {
        super(dataManager, operationsManager);
    }

    public Project getCurrentProject(HdbOfficer user) throws Exception {
        ProjectTeam currentTeam = getOperationsManager().getUserManager().retrieveCurrentTeam(user);
        Project projectInCharge = getOperationsManager().getProjectTeamManager().retrieveAssignedProject(currentTeam);
        return projectInCharge;
    }

    public List<BtoApplication> getApplications(Project project) {
        return getOperationsManager().getProjectManager().retrieveApplications(project);
    }

    public List<Enquiry> getEnquiries(Project project) {
        return getOperationsManager().getProjectManager().retrieveEnquiries(project);
    }

    public List<Enquiry> getEnquiries(Project project, boolean replied) {
        return getOperationsManager().getEnquiryManager().filterEnquiries(getEnquiries(project), replied);
    }
}
