package btosystem.service.hdbofficer;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.User;
import btosystem.classes.enums.FlatType;
import btosystem.operations.interfaces.BtoApplicationOperations;
import btosystem.operations.interfaces.EnquiryOperations;
import btosystem.operations.interfaces.OfficerRegistrationOperations;
import btosystem.operations.interfaces.ProjectOperations;
import btosystem.operations.interfaces.ProjectTeamOperations;
import btosystem.operations.interfaces.UserOperations;
import btosystem.service.applicant.ApplicantBtoApplicationService;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;
import java.util.List;

/**
 * Service class extending {@link ApplicantBtoApplicationService} to handle
 * BTO application related operations specifically for HDB officers. This
 * class provides functionalities such as creating, retrieving, and processing
 * BTO applications, with necessary permission checks for HDB officers.
 */
public class HdbOfficerBtoApplicationService extends ApplicantBtoApplicationService {
    /**
     * Constructs a new {@code HdbOfficerBtoApplicationService} with the
     * necessary dependencies for data management and operation handling.
     *
     * @param dataManager Data management operations.
     * @param applicationOperations BTO application specific operations.
     * @param enquiryOperations Enquiry related operations.
     * @param registrationOperations Officer registration operations.
     * @param projectTeamOperations Project team management operations.
     * @param userOperations User related operations.
     * @param projectOperations Project specific operations.
     */
    public HdbOfficerBtoApplicationService(
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
            projectOperations
        );
    }

    /**
     * Overrides the parent class's {@code createApplication} method to include
     * a permission check to ensure that HDB officers are not allowed to apply
     * for projects they are managing. If the officer has access to the project,
     * an exception is thrown; otherwise, the application creation proceeds.
     *
     * @param user The {@link Applicant} (cast from {@link HdbOfficer}) 
     *              attempting to create the application.
     * @param project The {@link Project} for which the application is being created.
     * @param flatType The {@link FlatType} chosen for the application.
     * @throws Exception If the HDB officer has access to the project or if any
     *                  other error occurs during application creation.
     */
    @Override
    public void createApplication(Applicant user, 
                                Project project, FlatType flatType) throws Exception {
        if (hasProjectAccess((HdbOfficer) user, project)) {
            throw new Exception("Access Denied. Not allowed to apply for this project. ");
        }
        super.createApplication(user, project, flatType);
    }

    /**
     * Retrieves all BTO applications associated with a specific project.
     *
     * @param project The {@link Project} to retrieve applications for.
     * @return A {@code List} of {@link BtoApplication} objects for the given project.
     */
    public List<BtoApplication> getApplications(Project project) {
        return projectManager.retrieveApplications(project);
    }

    /**
     * Retrieves a specific BTO application based on the applicant's NRIC.
     *
     * @param nric The NRIC of the applicant whose application is to be retrieved.
     * @return The {@link BtoApplication} object associated with the given NRIC.
     * @throws Exception If no applicant is found with the given NRIC or if the
     *                  applicant does not have an existing application.
     */
    public BtoApplication getApplication(String nric) throws Exception {
        Applicant applicant = getApplicant(nric);
        BtoApplication application = userManager.retrieveApplication(applicant);
        if (application == null) {
            throw new Exception(
                "Access Denied.Applicant does not have existing application. ");
        }
        return application;
    }

    /**
     * Processes a BTO application by verifying access permissions, application
     * status, applicant eligibility for the chosen flat type, and availability
     * of units. If all checks pass, the application is processed, and the unit
     * count for the selected flat type in the project is decreased.
     *
     * @param application The {@link BtoApplication} to be processed.
     * @param officer The {@link HdbOfficer} processing the application.
     * @throws Exception If the officer does not have access to the application,
     *                  if the application is not approved for processing, if the
     *                  applicant is not eligible for the chosen flat type, or if
     *                  the selected flat type has no available units.
     */
    public void processApplication(BtoApplication application, 
                                HdbOfficer officer) throws Exception {
        Applicant applicant = applicationManager.retrieveApplicant(application);    
        FlatType flatType = application.getFlatType();
        if (!hasApplicationAccess(officer, application)) {
            throw new Exception("Access Denied. Not allowed to access this application. ");
        }
        if (!applicationManager.isReadyToProcess(application)) {
            throw new Exception("Application is not approved. ");
        }
        List<FlatType> allowedflatTypes = applicationManager.getEligibleFlatTypes(applicant);
        if (!allowedflatTypes.contains(flatType)) {
            throw new Exception("Not allowed to choose this flat type. ");
        }
        Project applicationProject = applicationManager.retrieveProject(application);
        if (!projectManager.unitHasSlots(applicationProject, flatType)) {
            throw new Exception("Flat type does don't have slots. ");
        }
        applicationManager.processApplication(application, officer);
        projectManager.decreaseUnitCount(applicationProject, flatType);
    }

    /**
     * Checks if the given HDB officer has access to the BTO application. Access
     * is granted if the officer is part of the project team managing the project
     * to which the application belongs.
     *
     * @param user The {@link HdbOfficer} attempting to access the application.
     * @param application The {@link BtoApplication} being accessed.
     * @return {@code true} if the officer has access to the application, {@code false} otherwise.
     * @throws Exception If an error occurs while retrieving the project 
     *                  associated with the application.
     */
    private boolean hasApplicationAccess(HdbOfficer user, 
                                        BtoApplication application) throws Exception {
        Project applicationProject = applicationManager.retrieveProject(application);
        return hasProjectAccess(user, applicationProject);
    }

    /**
     * Checks if the given HDB officer has access to a specific project. Access
     * is granted if the officer's current project team is assigned to the given project.
     *
     * @param user The {@link HdbOfficer} to check access for.
     * @param project The {@link Project} to check access to.
     * @return {@code true} if the officer has access to the project, {@code false} otherwise.
     */
    private boolean hasProjectAccess(HdbOfficer user, Project project) {
        ProjectTeam currentTeam = getCurrentTeam(user);
        if (currentTeam == null) {
            return false;
        }
        Project projectInCharge = projectTeamManager.retrieveAssignedProject(currentTeam);
        return projectInCharge.equals(project);
    }

    /**
     * Retrieves an {@link Applicant} object based on the provided NRIC.
     *
     * @param nric The NRIC of the applicant to retrieve.
     * @return The {@link Applicant} object associated with the given NRIC.
     * @throws Exception If no user is found with the given NRIC or if the user
     *                   is not an instance of {@link Applicant}.
     */
    private Applicant getApplicant(String nric) throws Exception {
        User user = userManager.retrieveUser(dataManager.getUsers(), nric);
        if (!(user instanceof Applicant)) {
            throw new Exception("User is not an applicant. ");
        }
        return (Applicant) user;
    }

    /**
     * Retrieves the current {@link ProjectTeam} that the given {@link HdbOfficer}
     * is actively working on. A team is considered the current team if it is
     * assigned to a project that is currently open for application. If the officer
     * is not currently assigned to any open project, this method returns {@code null}.
     *
     * @param user The {@link HdbOfficer} for whom to retrieve the current team.
     * @return The {@link ProjectTeam} object of the officer's current team, or
     *          {@code null} if the officer is not assigned to any open project.
     */
    private ProjectTeam getCurrentTeam(HdbOfficer user) {
        List<ProjectTeam> teams = userManager.retrieveTeams(user);
        for (ProjectTeam t : teams) {
            Project p = projectTeamManager.retrieveAssignedProject(t);
            if (projectManager.isOpen(p)) {
                return t;
            }
        }
        return null;
    }
}
