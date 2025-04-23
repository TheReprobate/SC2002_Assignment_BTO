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

    Project retrieveProject(Enquiry enquiry);

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

    int addEnquiry(List<Enquiry> enquiries, Enquiry enquiry);

    boolean hasReplied(Enquiry enquiry);

    List<Enquiry> filterEnquiries(List<Enquiry> enquiries, boolean replied);

    /**
     * Sets project to empty
     *
     * @param enquiry The enquiry to edit
     */
    void removeProject(Enquiry enquiry) throws Exception;
}
