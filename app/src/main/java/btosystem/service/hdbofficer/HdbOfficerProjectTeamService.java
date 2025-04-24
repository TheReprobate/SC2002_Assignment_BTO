package btosystem.service.hdbofficer;

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
import java.util.List;

/**
 * Service class for handling project team related operations for HDB officers.
 * This class provides functionalities such as creating officer registrations for project teams.
 */
public class HdbOfficerProjectTeamService extends Service {

    /**
     * Constructs a new {@code HdbOfficerProjectTeamService} with the necessary dependencies.
     *
     * @param dataManager             Data management operations.
     * @param applicationOperations BTO application operations.
     * @param enquiryOperations     Enquiry operations.
     * @param registrationOperations Officer registration operations.
     * @param projectTeamOperations Project team operations.
     * @param userOperations        User operations.
     * @param projectOperations     Project operations.
     */
    public HdbOfficerProjectTeamService (
            DataManager dataManager,
             BtoApplicationOperations applicationOperations,
             EnquiryOperations enquiryOperations,
             OfficerRegistrationOperations registrationOperations,
             ProjectTeamOperations projectTeamOperations,
             UserOperations userOperations,
             ProjectOperations projectOperations) {

        super(dataManager,
                applicationOperations,
                enquiryOperations,
                registrationOperations,
                projectTeamOperations,
                userOperations,
                projectOperations);
    }

    /**
     * Creates a new officer registration for a specific project.
     * This method checks for existing registrations, ongoing applications in other open projects,
     * and potential time overlaps with currently assigned projects before creating the registration.
     *
     * @param user    The {@link HdbOfficer} creating the registration.
     * @param project The {@link Project} for which the officer is registering.
     * @throws Exception If the officer has a pending application for the team,
     * already has an existing application in another open project,
     * or if there is a time overlap with their currently assigned projects.
     */
    public void createRegistration(HdbOfficer user, Project project) throws Exception {
        ProjectTeam team = projectManager.retrieveProjectTeam(project);
        List<OfficerRegistration> registrations = projectTeamManager.retrieveOfficerRegistrations(team);
        if (registrationManager.hasApplied(registrations, user)) {
            throw new Exception("Already has pending application for this team. ");
        }
        BtoApplication application = userManager.retrieveApplication(user);
        ProjectTeam currentTeam = getCurrentTeam(user);
        if (currentTeam != null) {
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

    /**
     * Retrieves the current project team that the given HDB officer is actively working on.
     * A team is considered the current team if it is assigned to a project that is currently open
     * for application. If the officer is not currently assigned to any open project, this method returns {@code null}.
     *
     * @param user The {@link HdbOfficer} object for whom to retrieve the current team.
     * @return The {@link ProjectTeam} object of the officer's current team, or {@code null} if none.
     */
    private ProjectTeam getCurrentTeam(HdbOfficer user){
        List<ProjectTeam> teams = userManager.retrieveTeams(user);
        for (ProjectTeam t: teams) {
            Project p = projectTeamManager.retrieveAssignedProject(t);
            if (projectManager.isOpen(p)) {
                return t;
            }
        }
        return null;
    }
}
