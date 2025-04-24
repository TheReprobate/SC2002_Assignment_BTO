package btosystem.service.hdbofficer;

import java.util.List;

import btosystem.classes.BtoApplication;
import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.operations.interfaces.BtoApplicationOperations;
import btosystem.operations.interfaces.EnquiryOperations;
import btosystem.operations.interfaces.OfficerRegistrationOperations;
import btosystem.operations.interfaces.ProjectOperations;
import btosystem.operations.interfaces.ProjectTeamOperations;
import btosystem.operations.interfaces.UserOperations;
import btosystem.service.Service;
import btosystem.utils.DataManager;

public class HdbOfficerProjectTeamService extends Service {

    public HdbOfficerProjectTeamService (DataManager dataManager, BtoApplicationOperations applicationOperations, EnquiryOperations enquiryOperations,
    OfficerRegistrationOperations registrationOperations, ProjectTeamOperations projectTeamOperations,
    UserOperations userOperations, ProjectOperations projectOperations ) {
        super(dataManager, applicationOperations, enquiryOperations, registrationOperations, projectTeamOperations, userOperations, projectOperations);
    }
    
    public void createRegistration(HdbOfficer user, Project project) throws Exception {
        ProjectTeam team = projectManager.retrieveProjectTeam(project);
        List<OfficerRegistration> registrations = projectTeamManager.retrieveOfficerRegistrations(team);
        if (registrationManager.hasApplied(registrations, user)) {
            throw new Exception("Already has pending application for this team. ");
        }
        BtoApplication application = userManager.retrieveApplication(user);
        ProjectTeam currentTeam = getCurrentTeam(user);
        if(currentTeam != null) {
            Project currentProject = projectTeamManager.retrieveAssignedProject(currentTeam);
            if (application != null && applicationManager.retrieveProject(application).equals(currentProject)) {
                throw new Exception("Already has existing application in project, unable to apply for project. ");
            }
            Project appliedProject = projectTeamManager.retrieveAssignedProject(team);
            if (projectManager.hasTimeOverlap(currentProject, appliedProject)) {
                throw new Exception("Unable to register for project due to time overlap. ");
            }
        }
        OfficerRegistration registration = registrationManager.createRegistration(team, user);
        List<OfficerRegistration> teamRegistrationRef = projectTeamManager.retrieveOfficerRegistrations(team);
        registrationManager.addRegistration(teamRegistrationRef, registration);
    }

    private ProjectTeam getCurrentTeam(HdbOfficer user){
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
