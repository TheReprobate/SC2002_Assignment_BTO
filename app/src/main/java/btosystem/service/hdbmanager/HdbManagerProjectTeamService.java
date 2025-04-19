package btosystem.service.hdbmanager;

import java.util.List;

import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.RegistrationStatus;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class HdbManagerProjectTeamService extends Service {

    public HdbManagerProjectTeamService (DataManager dataManager,
            OperationsManager operationsManager) {
        super(dataManager, operationsManager);
    }

    public ProjectTeam getProjectTeam(Project project) {
        return operationsManager.getProjectManager().retrieveProjectTeam(project);
    }

    public List<OfficerRegistration> getRegistrations(Project project) {
        ProjectTeam team = operationsManager.getProjectManager().retrieveProjectTeam(project);
        List<OfficerRegistration> registrations = operationsManager.getProjectTeamManager().retrieveOfficerRegistrations(team);
        return registrations;
    }

    public List<OfficerRegistration> getRegistrations(Project project, RegistrationStatus status) {
        return operationsManager.getRegistrationManager().filterRegistrations(getRegistrations(project), status);
    } 

    // do we invalidate all his previous registartions when approved?????????????????????????
    public void approveRegistration(HdbManager user, ProjectTeam team, OfficerRegistration registration) throws Exception {
        Project project = operationsManager.getProjectTeamManager().retrieveAssignedProject(team);
        if(!hasProjectAccess(user, project)){
            throw new Exception("Access Denied. Not allowed to access this project. ");
        }
        HdbOfficer officer = operationsManager.getRegistrationManager().retrieveAppliedOfficer(registration);
        if(operationsManager.getUserManager().retrieveCurrentTeam(officer) != null) {
            throw new Exception("Officer is currently assigned to a team. ");
        }
        if(operationsManager.getProjectTeamManager().hasMaxOfficers(team)) {
            throw new Exception("Maximum possible officers in team.  ");
        }
        operationsManager.getRegistrationManager().approveRegistration(registration);
        operationsManager.getProjectTeamManager().assignProject(team, officer);
        operationsManager.getUserManager().setTeam(team, officer);
    }

    public void rejectRegistration(HdbManager user, ProjectTeam team, OfficerRegistration registration) throws Exception {
        Project project = operationsManager.getProjectTeamManager().retrieveAssignedProject(team);
        if(!hasProjectAccess(user, project)){
            throw new Exception("Access Denied. Not allowed to access this project. ");
        }
        operationsManager.getRegistrationManager().rejectRegistration(registration);
    }

    public void joinTeam(HdbManager user, Project project) throws Exception {
        ProjectTeam team = operationsManager.getProjectManager().retrieveProjectTeam(project);
        if(operationsManager.getUserManager().retrieveCurrentTeam(user) != null){
            throw new Exception("User is currently assigned to a team. ");
        }
        if(operationsManager.getProjectTeamManager().hasManager(team)) {
            throw new Exception("Existing manager in team. ");
        }
        operationsManager.getProjectTeamManager().assignProject(team, user);
        operationsManager.getUserManager().setTeam(team, user);
    }

    private boolean hasProjectAccess(HdbManager user, Project project) {
        ProjectTeam currentTeam = operationsManager.getUserManager().retrieveCurrentTeam(user);
        Project projectInCharge = operationsManager.getProjectTeamManager().retrieveAssignedProject(currentTeam);
        return projectInCharge.equals(project);
    }
}
