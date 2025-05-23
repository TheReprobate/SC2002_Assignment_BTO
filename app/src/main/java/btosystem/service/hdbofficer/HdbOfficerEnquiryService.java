package btosystem.service.hdbofficer;

import btosystem.classes.Enquiry;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.operations.interfaces.BtoApplicationOperations;
import btosystem.operations.interfaces.EnquiryOperations;
import btosystem.operations.interfaces.OfficerRegistrationOperations;
import btosystem.operations.interfaces.ProjectOperations;
import btosystem.operations.interfaces.ProjectTeamOperations;
import btosystem.operations.interfaces.UserOperations;
import btosystem.service.applicant.ApplicantEnquiryService;
import btosystem.utils.DataManager;
import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * Service class handling enquiry-related operations for HDB officers. Extends
 * ApplicantEnquiryService to provide additional officer-specific functionality.
 */
public class HdbOfficerEnquiryService extends ApplicantEnquiryService {
    /**     
     * Constructs a new HdbOfficerEnquiryService with required dependencies.
     *
     * @param dataManager Data management operations
     * @param applicationOperations BTO application operations
     * @param enquiryOperations Enquiry operations
     * @param registrationOperations Officer registration operations
     * @param projectTeamOperations Project team operations
     * @param userOperations User operations
     * @param projectOperations Project operations
     */
    public HdbOfficerEnquiryService(
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
     * Retrieves all enquiries for a specific project.
     *
     * @param project The project to retrieve enquiries for
     * @return List of enquiries for the project
     */
    public List<Enquiry> getEnquiries(Project project) {
        return projectManager.retrieveEnquiries(project);
    }

    /**
     * Retrieves enquiries for a project filtered by reply status.
     *
     * @param project The project to retrieve enquiries for
     * @param replied Whether to filter for replied or unreplied enquiries
     * @return Filtered list of enquiries
     */
    public List<Enquiry> getEnquiries(Project project, boolean replied) {
        return enquiryManager.filterEnquiries(getEnquiries(project), replied);
    }

    /**
     * Retrieves enquiries that the officer can reply to.
     *
     * @param user The officer requesting the enquiries
     * @return List of reply-able enquiries
     */
    public List<Enquiry> getRepliableEnquiries(HdbOfficer user) {   //35
        return getViewableProjectEnquiries(user);
    }

    /**
     * Replies to an enquiry after checking permissions.
     *
     * @param user The officer replying to the enquiry
     * @param enquiry The enquiry to reply to
     * @param reply The reply content
     * @throws Exception If project is not open or officer lacks permission
     */
    public void replyEnquiry(HdbOfficer user, Enquiry enquiry, String reply) throws Exception {
        Project enquiryProject = enquiryManager.retrieveProject(enquiry);
        if (!projectManager.isOpen(enquiryProject)) {
            throw new Exception("Project is not open");
        }
        ProjectTeam team = getCurrentTeam(user);
        if (team == null) {
            throw new Exception("Currently not in a team.");
        }
        Project currentProj = projectTeamManager.retrieveAssignedProject(team);
        Project enquiryProj = enquiryManager.retrieveProject(enquiry);
        if (!currentProj.equals(enquiryProj)) {
            throw new AccessDeniedException(
                    "Access Denied. No permission to reply to this enquiry.");
        }
        enquiryManager.replyEnquiry(enquiry, reply);
    }

    /**
     * Retrieves a list of enquiries for the project that the given HDB officer's current
     * team is assigned to and is currently open. This is a helper method used to determine
     * which enquiries an officer has the authority to view and potentially reply to.
     *
     * @param user The {@link HdbOfficer} object requesting the viewable enquiries.
     * @return A {@code List} of {@link Enquiry} 
     *          objects associated with the officer's current project.
     */
    private List<Enquiry> getViewableProjectEnquiries(HdbOfficer user) {
        ProjectTeam currentTeam = getCurrentTeam(user);
        Project currentProject = projectTeamManager.retrieveAssignedProject(currentTeam);
        List<Enquiry> projectEnquiries = projectManager.retrieveEnquiries(currentProject);
        return projectEnquiries;
    }

    /**
     * Retrieves the current project team that the given HDB officer is actively working on.
     * A team is considered the current team if it is assigned to a project that is currently open
     * for application. If the officer is not currently assigned to any open project, 
     * this method returns {@code null}.
     *
     * @param user The {@link HdbOfficer} object for whom to retrieve the current team.
     * @return The {@link ProjectTeam} object of the officer's current team, 
     *          or {@code null} if none.
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
