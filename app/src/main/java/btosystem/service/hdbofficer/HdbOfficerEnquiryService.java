package btosystem.service.hdbofficer;

import btosystem.classes.Enquiry;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.controllers.interfaces.BtoApplicationOperations;
import btosystem.controllers.interfaces.EnquiryOperations;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;
import btosystem.controllers.interfaces.ProjectOperations;
import btosystem.controllers.interfaces.ProjectTeamOperations;
import btosystem.controllers.interfaces.UserOperations;
import btosystem.service.applicant.ApplicantEnquiryService;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;
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
     * @param applicationManager BTO application operations
     * @param enquiryManager Enquiry operations
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
        if(team == null) {
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

    private List<Enquiry> getViewableProjectEnquiries(HdbOfficer user) {
        ProjectTeam currentTeam = getCurrentTeam(user);
        Project currentProject = projectTeamManager.retrieveAssignedProject(currentTeam);
        List<Enquiry> projectEnquiries = projectManager.retrieveEnquiries(currentProject);
        return projectEnquiries;
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
