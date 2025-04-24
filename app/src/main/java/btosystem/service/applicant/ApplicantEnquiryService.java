package btosystem.service.applicant;

import btosystem.classes.Applicant;
import btosystem.classes.Enquiry;
import btosystem.classes.Project;
import btosystem.operations.interfaces.BtoApplicationOperations;
import btosystem.operations.interfaces.EnquiryOperations;
import btosystem.operations.interfaces.OfficerRegistrationOperations;
import btosystem.operations.interfaces.ProjectOperations;
import btosystem.operations.interfaces.ProjectTeamOperations;
import btosystem.operations.interfaces.UserOperations;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;
import java.util.List;

/**
 * Service class handling enquiry-related operations for applicants. Provides
 * functionality for creating, editing, and deleting enquiries.
 */
public class ApplicantEnquiryService extends Service {

    /**
     * Constructs a new ApplicantEnquiryService with required dependencies.
     *
     * @param dataManager Data management operations
     * @param applicationOperations BTO application operations
     * @param enquiryOperations Enquiry operations
     * @param registrationOperations Officer registration operations
     * @param projectTeamOperations Project team operations
     * @param userOperations User operations
     * @param projectOperations Project operations
     */
    public ApplicantEnquiryService(
            DataManager dataManager,
            BtoApplicationOperations applicationOperations,
            EnquiryOperations enquiryOperations,
            OfficerRegistrationOperations registrationOperations,
            ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations,
            ProjectOperations projectOperations) {

        super(dataManager, applicationOperations, enquiryOperations,
                registrationOperations, projectTeamOperations,
                userOperations, projectOperations);
    }

    /**
     * Retrieves all enquiries made by the specified applicant.
     *
     * @param user The applicant whose enquiries to retrieve
     * @return List of enquiries made by the applicant
     */
    public List<Enquiry> getPersonalEnquiries(Applicant user) {
        return userManager.retrieveEnquiries(user);
    }

    /**
     * Creates a new enquiry for the specified project.
     *
     * @param user The applicant creating the enquiry
     * @param project The project the enquiry is about
     * @param content The content of the enquiry
     * @throws Exception If the enquiry cannot be created
     */
    public void createEnquiry(Applicant user, Project project, String content)
            throws Exception {
        Enquiry enquiry = enquiryManager.createEnquiry(project, user, content);
        List<Enquiry> projectEnquiryRef = projectManager.retrieveEnquiries(project);
        List<Enquiry> applicantEnquiryRef = getPersonalEnquiries(user);
        enquiryManager.addEnquiry(projectEnquiryRef, enquiry);
        enquiryManager.addEnquiry(applicantEnquiryRef, enquiry);
    }

    /**
     * Edits an existing enquiry.
     *
     * @param user The applicant editing the enquiry
     * @param enquiry The enquiry to edit
     * @param content The new content for the enquiry
     * @throws Exception If project is closed/lacks permission/replied to
     */
    public void editEnquiry(Applicant user, Enquiry enquiry, String content)
            throws Exception {
        Project enquiryProject = enquiryManager.retrieveProject(enquiry);
        if (!projectManager.isOpen(enquiryProject)) {
            throw new Exception("Project is not open");
        }
        if (!hasPermission(user, enquiry)) {
            throw new Exception("No permission to edit this enquiry.");
        }
        if (!isEditable(enquiry)) {
            throw new Exception("Enquiry has reply unable to process.");
        }
        enquiryManager.editEnquiry(enquiry, content);
    }

    /**
     * Deletes an existing enquiry.
     *
     * @param user The applicant deleting the enquiry
     * @param enquiry The enquiry to delete
     * @throws Exception If project is closed/lacks permission/replied to
     */
    public void deleteEnquiry(Applicant user, Enquiry enquiry) throws Exception {
        Project enquiryProject = enquiryManager.retrieveProject(enquiry);
        if (!projectManager.isOpen(enquiryProject)) {
            throw new Exception("Project is not open");
        }
        if (!hasPermission(user, enquiry)) {
            throw new Exception("No permission to delete this enquiry.");
        }
        if (!isEditable(enquiry)) {
            throw new Exception("Enquiry has reply unable to process.");
        }
        Project project = enquiryManager.retrieveProject(enquiry);
        List<Enquiry> projectEnquiryRef = projectManager.retrieveEnquiries(project);
        List<Enquiry> applicantEnquiryRef = getPersonalEnquiries(user);
        enquiryManager.deleteEnquiry(projectEnquiryRef, enquiry);
        enquiryManager.deleteEnquiry(applicantEnquiryRef, enquiry);
    }

    /**
     * Checks if an enquiry can be edited or deleted.
     *
     * @param enquiry The enquiry to check
     * @return true if the enquiry has no reply and can be edited, false otherwise
     * @throws Exception If there is an error checking the enquiry status
     */
    public boolean isEditable(Enquiry enquiry) throws Exception {
        return !enquiryManager.hasReplied(enquiry);
    }

    /**
     * Checks if the applicant has permission to modify the enquiry.
     *
     * @param user The applicant to check
     * @param enquiry The enquiry to check
     * @return true if the applicant owns the enquiry, false otherwise
     */
    private boolean hasPermission(Applicant user, Enquiry enquiry) {
        Applicant enquiryApplicant = enquiryManager.retrieveApplicant(enquiry);
        return enquiryApplicant.equals(user);
    }
}
