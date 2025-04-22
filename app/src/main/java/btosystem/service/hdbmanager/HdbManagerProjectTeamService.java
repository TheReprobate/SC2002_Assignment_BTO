package btosystem.service.hdbmanager;

import java.util.List;

import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.RegistrationStatus;
import btosystem.controllers.interfaces.BtoApplicationOperations;
import btosystem.controllers.interfaces.EnquiryOperations;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;
import btosystem.controllers.interfaces.ProjectOperations;
import btosystem.controllers.interfaces.ProjectTeamOperations;
import btosystem.controllers.interfaces.UserOperations;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class HdbManagerProjectTeamService extends Service {

    public HdbManagerProjectTeamService (DataManager dataManager, BtoApplicationOperations applicationManager, EnquiryOperations enquiryManager,
            OfficerRegistrationOperations registrationOperations, ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations, ProjectOperations projectOperations) {
        super(dataManager, applicationManager, enquiryManager, registrationOperations, projectTeamOperations, userOperations, projectOperations);
    }

    public ProjectTeam getProjectTeam(Project project) {
        return projectManager.retrieveProjectTeam(project);
    }

    public List<OfficerRegistration> getRegistrations(Project project) {
        ProjectTeam team = projectManager.retrieveProjectTeam(project);
        List<OfficerRegistration> registrations = projectTeamManager.retrieveOfficerRegistrations(team);
        return registrations;
    }

    public List<OfficerRegistration> getRegistrations(Project project, RegistrationStatus status) {
        return registrationManager.filterRegistrations(getRegistrations(project), status);
    } 

    // do we invalidate all his previous registartions when approved?????????????????????????
    public void approveRegistration(HdbManager user, ProjectTeam team, OfficerRegistration registration) throws Exception {
        Project project = projectTeamManager.retrieveAssignedProject(team);
        if (!hasProjectAccess(user, project)) {
            throw new Exception("Access Denied. Not allowed to access this project. ");
        }
        HdbOfficer officer = registrationManager.retrieveAppliedOfficer(registration);
        if (userManager.retrieveCurrentTeam(officer) != null) {
            throw new Exception("Officer is currently assigned to a team. ");
        }
        if (projectTeamManager.hasMaxOfficers(team)) {
            throw new Exception("Maximum possible officers in team.  ");
        }
        registrationManager.approveRegistration(registration);
        projectTeamManager.assignProject(team, officer);
        userManager.setTeam(team, officer);
    }

    public void rejectRegistration(HdbManager user, ProjectTeam team, OfficerRegistration registration) throws Exception {
        Project project = projectTeamManager.retrieveAssignedProject(team);
        if (!hasProjectAccess(user, project)) {
            throw new Exception("Access Denied. Not allowed to access this project. ");
        }
        registrationManager.rejectRegistration(registration);
    }

    public void joinTeam(HdbManager user, Project project) throws Exception {
        ProjectTeam team = projectManager.retrieveProjectTeam(project);
        if (userManager.retrieveCurrentTeam(user) != null) {
            throw new Exception("User is currently assigned to a team. ");
        }
        if (projectTeamManager.hasManager(team)) {
            throw new Exception("Existing manager in team. ");
        }
        projectTeamManager.assignProject(team, user);
        userManager.setTeam(team, user);
    }

    private boolean hasProjectAccess(HdbManager user, Project project) {
        ProjectTeam currentTeam = userManager.retrieveCurrentTeam(user);
        Project projectInCharge = projectTeamManager.retrieveAssignedProject(currentTeam);
        return projectInCharge.equals(project);
    }
}
