package btosystem.service.hdbofficer;

import java.util.List;

import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class HdbOfficerProjectTeamService extends Service {

    public HdbOfficerProjectTeamService (DataManager dataManager, OperationsManager operationsManager) {
        super(dataManager, operationsManager);
    }
    
    public void createRegistration(HdbOfficer user, ProjectTeam team) throws Exception {
        ProjectTeam currentTeam = getOperationsManager().getUserManager().retrieveCurrentTeam(user);
        Project currentProject = getOperationsManager().getProjectTeamManager().retrieveAssignedProject(currentTeam);
        Project appliedProject = getOperationsManager().getProjectTeamManager().retrieveAssignedProject(team);
        if(getOperationsManager().getProjectManager().hasTimeOverlap(currentProject, appliedProject)) {
            throw new Exception("Unable to register for project due to time overlap. ");
        }
        List<OfficerRegistration> registrations = getOperationsManager().getProjectTeamManager().retrieveOfficerRegistrations(currentTeam);
        if(getOperationsManager().getRegistrationManager().hasApplied(registrations, user)){
            throw new Exception("Already has pending application for this team. ");
        }
        OfficerRegistration registration = getOperationsManager().getRegistrationManager().createRegistration(appliedProject, user);
        List<OfficerRegistration> teamRegistrationRef = getOperationsManager().getProjectTeamManager().retrieveOfficerRegistrations(currentTeam);
        getOperationsManager().getRegistrationManager().addRegistration(teamRegistrationRef, registration);
    }
}
