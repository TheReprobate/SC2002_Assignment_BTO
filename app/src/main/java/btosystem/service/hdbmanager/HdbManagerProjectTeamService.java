package btosystem.service.hdbmanager;

import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class HdbManagerProjectTeamService extends Service {

    public HdbManagerProjectTeamService (DataManager dataManager,
            OperationsManager operationsManager) {
        super(dataManager, operationsManager);
    }

    // do we invalidate all his previous registartions when approved?????????????????????????
    public void approveRegistration(HdbManager user, ProjectTeam team, OfficerRegistration registration) throws Exception {
        Project project = getOperationsManager().getProjectTeamManager().retrieveAssignedProject(team);
        if(!hasProjectAccess(user, project)){
            throw new Exception("Access Denied. Not allowed to access this project. ");
        }
        HdbOfficer officer = getOperationsManager().getRegistrationManager().retrieveAppliedOfficer(registration);
        if(getOperationsManager().getUserManager().retrieveCurrentTeam(officer) != null) {
            throw new Exception("Officer is currently assigned to a team. ");
        }
        if(getOperationsManager().getProjectTeamManager().hasMaxOfficers(team)) {
            throw new Exception("Maximum possible officers in team.  ");
        }
        getOperationsManager().getRegistrationManager().approveRegistration(registration);
        getOperationsManager().getProjectTeamManager().assignProject(team, officer);
    }

    public void rejectRegistration(HdbManager user, ProjectTeam team, OfficerRegistration registration) throws Exception {
        Project project = getOperationsManager().getProjectTeamManager().retrieveAssignedProject(team);
        if(!hasProjectAccess(user, project)){
            throw new Exception("Access Denied. Not allowed to access this project. ");
        }
        getOperationsManager().getRegistrationManager().rejectRegistration(registration);
    }

    public void joinTeam(HdbManager user, ProjectTeam team) throws Exception {
        if(getOperationsManager().getUserManager().retrieveCurrentTeam(user) != null){
            throw new Exception("User is currently assigned to a team. ");
        }
        if(getOperationsManager().getProjectTeamManager().hasManager(team)) {
            throw new Exception("Existing manager in team. ");
        }
        getOperationsManager().getProjectTeamManager().assignProject(team, user);
    }

    private boolean hasProjectAccess(HdbManager user, Project project) {
        ProjectTeam currentTeam = getOperationsManager().getUserManager().retrieveCurrentTeam(user);
        Project projectInCharge = getOperationsManager().getProjectTeamManager().retrieveAssignedProject(currentTeam);
        return projectInCharge.equals(project);
    }
}
