package btosystem.service.hdbofficer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.Neighborhood;
import btosystem.controllers.interfaces.BtoApplicationOperations;
import btosystem.controllers.interfaces.EnquiryOperations;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;
import btosystem.controllers.interfaces.ProjectOperations;
import btosystem.controllers.interfaces.ProjectTeamOperations;
import btosystem.controllers.interfaces.UserOperations;
import btosystem.service.applicant.ApplicantProjectService;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class HdbOfficerProjectService extends ApplicantProjectService {

    public HdbOfficerProjectService(DataManager dataManager, BtoApplicationOperations applicationOperations, EnquiryOperations enquiryOperations,
            OfficerRegistrationOperations registrationOperations, ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations, ProjectOperations projectOperations) {
        super(dataManager, applicationOperations, enquiryOperations, registrationOperations, projectTeamOperations, userOperations, projectOperations);
    }
    public List<Project> getProjects() {
        return dataManager.getProjects();
    }
    public List<Project> getProjects(LocalDate start, LocalDate end) {
        return projectManager.filterProject(getProjects(), start, end);
    }

    public List<Project> getWorkingProject(HdbOfficer user) {
        List<Project> projects = new ArrayList<>();
        List<ProjectTeam> teams = userManager.retrieveTeams(user);
        for(ProjectTeam t: teams) {
            Project p = projectTeamManager.retrieveAssignedProject(t);
            projects.add(p);
        }
        return projects;
    }

    public Project getCurrentProject(HdbOfficer user) {
        ProjectTeam currentTeam = getCurrentTeam(user);
        if(currentTeam == null) {
            return null;
        }
        Project projectInCharge = projectTeamManager.retrieveAssignedProject(currentTeam);
        return projectInCharge;
    }

    private ProjectTeam getCurrentTeam(HdbOfficer user) {
        List<ProjectTeam> teams = userManager.retrieveTeams(user);
        for(ProjectTeam t: teams) {
            Project p = projectTeamManager.retrieveAssignedProject(t);
            if(projectManager.isOpen(p)) {
                return t;
            }
        }
        return null;
    }
}
