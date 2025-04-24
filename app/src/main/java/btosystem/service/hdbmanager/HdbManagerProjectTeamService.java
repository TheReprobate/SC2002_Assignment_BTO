package btosystem.service.hdbmanager;

import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.RegistrationStatus;
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
 * Service class providing functionalities for HDB managers to manage project teams
 * and officer registrations within those teams.
 */
public class HdbManagerProjectTeamService extends Service {

    /**
     * Constructs a new HdbManagerProjectTeamService with the necessary dependencies.
     *
     * @param dataManager Data management operations.
     * @param applicationOperations BTO application related operations.
     * @param enquiryOperations Enquiry related operations.
     * @param registrationOperations Officer registration related operations.
     * @param projectTeamOperations Project team related operations.
     * @param userOperations User related operations.
     * @param projectOperations Project related operations.
     */
    public HdbManagerProjectTeamService (
            DataManager dataManager,
            BtoApplicationOperations applicationOperations,
            EnquiryOperations enquiryOperations,
            OfficerRegistrationOperations registrationOperations,
            ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations,
            ProjectOperations projectOperations)
    {
        super(dataManager,
            applicationOperations,
            enquiryOperations,
            registrationOperations, 
            projectTeamOperations, 
            userOperations, 
            projectOperations);
    }

    /**
     * Retrieves the project team associated with a given project.
     *
     * @param project The project to retrieve the team for.
     * @return The project team assigned to the project, or null if none.
     */
    public ProjectTeam getProjectTeam(Project project) {
        return projectManager.retrieveProjectTeam(project);
    }

    /**
     * Retrieves all officer registrations for a specific project team.
     *
     * @param project The project to retrieve registrations for.
     * @return A list of officer registrations for the project.
     */
    public List<OfficerRegistration> getRegistrations(Project project) {
        ProjectTeam team = projectManager.retrieveProjectTeam(project);
        List<OfficerRegistration> registrations = projectTeamManager
                                                .retrieveOfficerRegistrations(team);
        return registrations;
    }

    /**
     * Retrieves officer registrations for a project filtered by a specific status.
     *
     * @param project The project to retrieve registrations for.
     * @param status The registration status to filter by.
     * @return A list of officer registrations for the project with the specified status.
     */
    public List<OfficerRegistration> getRegistrations(Project project, 
                                                    RegistrationStatus status) {
        return registrationManager.filterRegistrations(getRegistrations(project), status);
    }

    /**
     * Approves an officer registration to join a project team.
     * This method checks for project access, time overlaps with other assignments,
     * and the maximum number of officers allowed in the team before approving.
     * Upon approval, the officer is assigned to the project and the team.
     *
     * @param user The HDB manager approving the registration.
     * @param team The project team the officer is joining.
     * @param registration The officer registration to be approved.
     * @throws Exception If the manager does not have access to the project,
     * if the officer has a time overlap with another project,
     * or if the project team has reached its maximum capacity.
     */
    public void approveRegistration(HdbManager user, 
                                    ProjectTeam team, 
                                    OfficerRegistration registration) throws Exception {
        Project project = projectTeamManager.retrieveAssignedProject(team);
        if (!hasProjectAccess(user, project)) {
            throw new Exception("Access Denied. Not allowed to access this project. ");
        }
        HdbOfficer officer = registrationManager.retrieveAppliedOfficer(registration);
        List<ProjectTeam> officerTeam = userManager.retrieveTeams(officer);
        for(ProjectTeam t: officerTeam) {
            Project p = projectTeamManager.retrieveAssignedProject(t);
            if(projectManager.hasTimeOverlap(project, p)) {
                throw new Exception("Unable to process, time overlap detected. ");
            }
        }
        if(projectTeamManager.hasMaxOfficers(team)) {
            throw new Exception("Maximum possible officers in team.  ");
        }
        registrationManager.approveRegistration(registration);
        projectTeamManager.assignProject(team, officer);
        projectTeamManager.addProjectTeam(userManager.retrieveTeams(officer), team);
    }

    /**
     * Rejects an officer registration to join a project team.
     * This method checks if the manager has the authority to access the project
     * before rejecting the registration.
     *
     * @param user The HDB manager rejecting the registration.
     * @param team The project team the officer applied to.
     * @param registration The officer registration to be rejected.
     * @throws Exception If the manager does not have access to the project.
     */
    public void rejectRegistration(HdbManager user, 
                                    ProjectTeam team, 
                                    OfficerRegistration registration) throws Exception {
        Project project = projectTeamManager.retrieveAssignedProject(team);
        if (!hasProjectAccess(user, project)) {
            throw new Exception("Access Denied. Not allowed to access this project. ");
        }
        registrationManager.rejectRegistration(registration);
    }

    /**
     * Allows an HDB manager to join a project team for a specific project.
     * This method ensures that the project does not already have a manager
     * and that the manager does not have any time overlaps with other projects.
     *
     * @param user The HDB manager joining the team.
     * @param project The project to join.
     * @throws Exception If the project already has a manager or if the manager
     * has a time overlap with another assigned project.
     */
    public void joinTeam(HdbManager user, Project project) throws Exception {
        ProjectTeam projectTeam = projectManager.retrieveProjectTeam(project);
        if (projectTeamManager.hasManager(projectTeam)) {
            throw new Exception("Project already has a manager. ");
        }
        List<ProjectTeam> userTeams = userManager.retrieveTeams(user);
        for (ProjectTeam t : userTeams){
            Project p = projectTeamManager.retrieveAssignedProject(t);
            if (projectManager.hasTimeOverlap(project, p)){
                throw new Exception("Unable to join team, time overlapped with other projects. ");
            }
        }
        projectTeamManager.assignProject(projectTeam, user);
        projectTeamManager.addProjectTeam(userTeams, projectTeam);
    }

    /**
     * Checks if a given HDB manager has access to a specific project.
     * Access is granted if the manager is part of a team assigned to that project.
     *
     * @param user The HDB manager to check access for.
     * @param project The project to check access to.
     * @return True if the manager has access to the project, false otherwise.
     * @throws Exception If there is an issue retrieving the manager's teams or
     * the project assigned to those teams.
     */
    private boolean hasProjectAccess(HdbManager user, Project project) throws Exception {
        List<ProjectTeam> teams = userManager.retrieveTeams(user);
        for (ProjectTeam t: teams) {
            Project p = projectTeamManager.retrieveAssignedProject(t);
            if (p.equals(project)) {
                return true;
            }
        }
        return false;
    }
}
