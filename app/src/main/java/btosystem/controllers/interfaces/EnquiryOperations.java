package btosystem.controllers.interfaces;

import btosystem.classes.Applicant;
import btosystem.classes.Enquiry;
import btosystem.classes.Project;
import java.util.List;

/**
 * Interface defining operations related to enquiry management. Extends
 * ListToString for enquiry list formatting and CleanupOperations for enquiry
 * cleanup.
 */
public interface EnquiryOperations
        extends ListToString<Enquiry> {

    /**
     * Creates a new enquiry for a project.
     *
     * @param project The project the enquiry is about
     * @param applicant The applicant making the enquiry
     * @param content The content of the enquiry
     * @return The newly created Enquiry object
     */
    Enquiry createEnquiry(Project project, Applicant applicant, String content);

    /**
     * Retrieves an enquiry from a list by its index.
     *
     * @param enquiries The list of enquiries to search
     * @param index The index of the enquiry to retrieve
     * @return The Enquiry object if found, null otherwise
     * @throws Exception 
     */
    Enquiry retrieveEnquiry(List<Enquiry> enquiries, int index) throws Exception;

    /**
     * Retrieves the project associated with an enquiry.
     *
     * @param enquiry The enquiry to examine
     * @return The Project associated with the enquiry
     */
    Project retrieveProject(Enquiry enquiry);

    /**
     * Retrieves the applicant who made the enquiry.
     *
     * @param enquiry The enquiry to examine
     * @return The Applicant who made the enquiry
     */
    Applicant retrieveApplicant(Enquiry enquiry);

    /**
     * Deletes an enquiry from a list if it hasn't been replied to.
     *
     * @param enquiries The list containing the enquiry
     * @param enquiry The enquiry to delete
     * @return 1 if deletion was successful, 0 otherwise
     * @throws Exception 
     */
    int deleteEnquiry(List<Enquiry> enquiries, Enquiry enquiry) throws Exception;

    /**
     * Adds a reply to an enquiry.
     *
     * @param enquiry The enquiry to reply to
     * @param reply The reply content
     * @return 1 if reply was successful, 0 otherwise
     * @throws Exception 
     */
    int replyEnquiry(Enquiry enquiry, String reply) throws Exception;

    /**
     * Edits the content of an enquiry if it hasn't been replied to.
     *
     * @param enquiry The enquiry to edit
     * @param content The new content
     * @return 1 if edit was successful, 0 otherwise
     * @throws Exception 
     */
    int editEnquiry(Enquiry enquiry, String content) throws Exception;

    /**
     * Adds an enquiry to a list of enquiries.
     *
     * @param enquiries The list to add to
     * @param enquiry The enquiry to add
     * @return 1 if addition was successful, 0 otherwise
     */
    int addEnquiry(List<Enquiry> enquiries, Enquiry enquiry);

    /**
     * Checks if an enquiry has been replied to.
     *
     * @param enquiry The enquiry to check
     * @return true if the enquiry has been replied to, false otherwise
     */
    boolean hasReplied(Enquiry enquiry);

    /**
     * Filters enquiries by their reply status.
     *
     * @param enquiries The list of enquiries to filter
     * @param replied Whether to filter for replied or unreplied enquiries
     * @return The filtered list of enquiries
     */
    List<Enquiry> filterEnquiries(List<Enquiry> enquiries, boolean replied);

    /**
     * Sets project to empty
     *
     * @param enquiry The enquiry to edit
     */
    void removeProject(Enquiry enquiry) throws Exception;
}
