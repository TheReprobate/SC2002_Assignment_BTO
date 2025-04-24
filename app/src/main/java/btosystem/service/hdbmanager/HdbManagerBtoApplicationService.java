package btosystem.service.hdbmanager;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.ApplicationStatus;
import btosystem.classes.enums.FlatType;
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
 * Service class providing functionalities for HDB managers to handle BTO
 * applications. This includes retrieving, approving, and rejecting applications
 * related to projects they are managing.
 */
public class HdbManagerBtoApplicationService extends Service {

    /**
     * Constructs a new HdbManagerBtoApplicationService with the necessary
     * dependencies.
     *
     * @param dataManager Data management operations.
     * @param applicationOperations BTO application specific operations.
     * @param enquiryOperations Enquiry related operations.
     * @param registrationOperations Officer registration operations.
     * @param projectTeamOperations Project team management operations.
     * @param userOperations User-related operations.
     * @param projectOperations Project-specific operations.
     */
    public HdbManagerBtoApplicationService(
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
     * Retrieves all BTO applications associated with a specific project.
     *
     * @param project The project to retrieve applications for.
     * @return A list of {@link BtoApplication} for the given project.
     */
    public List<BtoApplication> getApplications(Project project) {
        return projectManager.retrieveApplications(project);
    }

    /**
     * Retrieves BTO applications for a specific project that match a given
     * application status.
     *
     * @param project The project to filter applications from.
     * @param status The {@link ApplicationStatus} to filter by.
     * @return A list of {@link BtoApplication} for the project with the specified status.
     */
    public List<BtoApplication> getApplications(Project project, ApplicationStatus status) {
        return applicationManager.filterApplications(getApplications(project), status);
    }

    /**
     * Approves a BTO application via a HDB manager. This method
     * verifies the manager's access to the application and ensures the
     * application is in a pending state and that there are available units
     * of the requested flat type in the project.
     *
     * @param user The {@link HdbManager} performing the approval.
     * @param application The {@link BtoApplication} to be approved.
     * @throws Exception If the manager does not have access, the application
     *                  is not pending, or there are no available units of the requested type.
     */
    public void approveApplication(HdbManager user, 
                                    BtoApplication application) throws Exception {
        if (!hasApplicationAccess(user, application)) {
            throw new Exception(
                "Access Denied. Not allowed to access this application. ");
        }
        if (!applicationManager.isPending(application)) {
            throw new Exception("Application has been processed. ");
        }
        Project project = applicationManager.retrieveProject(application);
        FlatType flatType = applicationManager.retrieveFlatType(application);
        if (!projectManager.unitHasSlots(project, flatType)) {
            throw new Exception(
                "No slots for unit type, unable to approve applicaton. "
            );
        } 
        applicationManager.approveApplication(application);
    }

    /**
     * Rejects a BTO application via a HDB manager. This method
     * verifies the manager's access to the application and ensures the
     * application is in a pending state before rejecting it.
     *
     * @param user The {@link HdbManager} performing the rejection.
     * @param application The {@link BtoApplication} to be rejected.
     * @throws Exception If the manager does not have access or the application is not pending.
     */
    public void rejectApplication(HdbManager user, BtoApplication application) throws Exception {
        if (!hasApplicationAccess(user, application)) {
            throw new Exception(
                "Access Denied. Not allowed to access this application. ");
        }
        if (!applicationManager.isPending(application)) {
            throw new Exception("Application has been processed. ");
        }
        applicationManager.rejectApplication(application);
    }

    /**
     * Checks if the given HDB manager has access to a specific BTO application.
     * Access is granted if the manager is part of a project team assigned to
     * the project associated with the application.
     *
     * @param user The {@link HdbManager} to check access for.
     * @param application The {@link BtoApplication} to check access to.
     * @return {@code true} if the manager has access, {@code false} otherwise.
     * @throws Exception If there is an error retrieving project or team information.
     */
    private boolean hasApplicationAccess(
            HdbManager user,
            BtoApplication application
    ) throws Exception {
        Project applicationProject = applicationManager.retrieveProject(application);
        return hasProjectAccess(user, applicationProject);
    }

    /**
     * Checks if the given HDB manager has access to a specific project.
     * Access is granted if the manager is part of a project team assigned to
     * that project.
     *
     * @param user The {@link HdbManager} to check access for.
     * @param project The {@link Project} to check access to.
     * @return {@code true} if the manager has access, {@code false} otherwise.
     * @throws Exception If there is an error retrieving team information.
     */
    private boolean hasProjectAccess(HdbManager user, Project project) throws Exception {
        List<ProjectTeam> teams = userManager.retrieveTeams(user);
        for (ProjectTeam t : teams) {
            Project p = projectTeamManager.retrieveAssignedProject(t);
            if (p.equals(project)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the current project team that the HDB manager is actively
     * working on. This is determined by finding a team the manager is part of
     * that is currently assigned to an open project.
     *
     * @param user The {@link HdbManager} to retrieve the current team for.
     * @return The {@link ProjectTeam} the manager is currently active in.
     * @throws Exception If the manager is not currently assigned to an open project team.
     */
    private ProjectTeam getCurrentTeam(HdbManager user) throws Exception {
        List<ProjectTeam> teams = userManager.retrieveTeams(user);
        for (ProjectTeam t : teams) {
            Project p = projectTeamManager.retrieveAssignedProject(t);
            if (projectManager.isOpen(p)) {
                return t;
            }
        }
        throw new Exception("Currently not in a team.");
    }

    /**
     * Generates a report containing details of all BTO applications for a
     * given project. The report includes information about the applicant and
     * the application itself.
     *
     * @param project The {@link Project} to generate the report for.
     * @return A string containing the formatted report of all applications for the project.
     */
    public String generateReport(Project project) {
        StringBuilder sb = new StringBuilder();
        List<BtoApplication> applications = projectManager.retrieveApplications(project);
        for (BtoApplication a : applications) {
            Applicant applicant = applicationManager.retrieveApplicant(a);
            sb.append(userManager.toString(applicant))
                .append(applicationManager
                .toString(a)).append('\n');
        }
        return sb.toString();
    }
}
