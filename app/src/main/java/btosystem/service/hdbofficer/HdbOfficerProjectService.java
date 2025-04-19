package btosystem.service.hdbofficer;

import java.time.LocalDate;
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
    public List<Project> getProjects() {
        return dataManager.getProjects();
    }
    public List<Project> getProjects(LocalDate start, LocalDate end) {
        return operationsManager.getProjectManager().filterProject(getProjects(), start, end);
    }

    public Project getCurrentProject(HdbOfficer user) throws Exception {
        ProjectTeam currentTeam = operationsManager.getUserManager().retrieveCurrentTeam(user);
        Project projectInCharge = operationsManager.getProjectTeamManager().retrieveAssignedProject(currentTeam);
        return projectInCharge;
    }
}
