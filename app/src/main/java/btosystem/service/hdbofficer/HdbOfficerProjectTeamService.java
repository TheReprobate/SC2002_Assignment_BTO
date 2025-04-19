package btosystem.service.hdbofficer;

import java.util.List;

import btosystem.classes.BtoApplication;
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
    
    public void createRegistration(HdbOfficer user, Project project) throws Exception {
        ProjectTeam team = operationsManager.getProjectManager().retrieveProjectTeam(project);
        List<OfficerRegistration> registrations = operationsManager.getProjectTeamManager().retrieveOfficerRegistrations(team);
        if(operationsManager.getRegistrationManager().hasApplied(registrations, user)){
            throw new Exception("Already has pending application for this team. ");
        }
        BtoApplication application = operationsManager.getUserManager().retrieveApplication(user);
        ProjectTeam currentTeam = operationsManager.getUserManager().retrieveCurrentTeam(user);
        if(currentTeam != null) {
            Project currentProject = operationsManager.getProjectTeamManager().retrieveAssignedProject(currentTeam);
            if(operationsManager.getApplicationManager().retrieveProject(application).equals(currentProject)){
                throw new Exception("Already has existing application in project, unable to apply for project. ");
            }
            Project appliedProject = operationsManager.getProjectTeamManager().retrieveAssignedProject(team);
            if(operationsManager.getProjectManager().hasTimeOverlap(currentProject, appliedProject)) {
                throw new Exception("Unable to register for project due to time overlap. ");
            }
        }
        OfficerRegistration registration = operationsManager.getRegistrationManager().createRegistration(team, user);
        List<OfficerRegistration> teamRegistrationRef = operationsManager.getProjectTeamManager().retrieveOfficerRegistrations(team);
        operationsManager.getRegistrationManager().addRegistration(teamRegistrationRef, registration);
    }
}
