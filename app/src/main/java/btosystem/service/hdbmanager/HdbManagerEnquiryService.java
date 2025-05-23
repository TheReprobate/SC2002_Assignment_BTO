package btosystem.service.hdbmanager;

import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
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
 * Service class handling enquiry-related operations for HDB managers. Provides
 * functionality for viewing and replying to enquiries.
 */
public class HdbManagerEnquiryService extends Service {

    /**
     * Constructs a new HdbManagerEnquiryService with required dependencies.
     *
     * @param dataManager Data management operations
     * @param applicationOperations BTO application operations
     * @param enquiryOperations Enquiry operations
     * @param registrationOperations Officer registration operations
     * @param projectTeamOperations Project team operations
     * @param userOperations User operations
     * @param projectOperations Project operations
     */
    public HdbManagerEnquiryService(DataManager dataManager,
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
     * Retrieves enquiries that the manager can reply to.
     *
     * @param user The manager requesting the enquiries
     * @return List of reply-able enquiries
     */
    public List<Enquiry> getRepliableEnquiries(HdbManager user) throws Exception {
        ProjectTeam currentTeam = getCurrentTeam(user);
        Project currentProject = projectTeamManager.retrieveAssignedProject(currentTeam);
        return projectManager.retrieveEnquiries(currentProject);
    }

    /**
     * Replies to an enquiry after checking permissions.
     *
     * @param user The manager replying to the enquiry
     * @param enquiry The enquiry to reply to
     * @param reply The reply content
     * @throws Exception If manager lacks permission to reply
     */
    public void replyEnquiry(HdbManager user, Enquiry enquiry, String reply) throws Exception {
        ProjectTeam team = getCurrentTeam(user);
        Project currentProj = projectTeamManager.retrieveAssignedProject(team);
        Project enquiryProj = enquiryManager.retrieveProject(enquiry);
        if (!currentProj.equals(enquiryProj)) {
            throw new Exception("Access Denied. No permission to reply to this enquiry.");
        }
        enquiryManager.replyEnquiry(enquiry, reply);
    }

    /**
     * Retrieves the current project team the given HDB manager is assigned to
     * for an open project.
     *
     * @param user The HDB manager to retrieve the current team for.
     * @return The current project team of the manager.
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
}
