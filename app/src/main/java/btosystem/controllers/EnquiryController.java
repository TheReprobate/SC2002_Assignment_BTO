package btosystem.controllers;

import btosystem.classes.Applicant;
import btosystem.classes.Enquiry;
import btosystem.classes.Project;
import btosystem.controllers.interfaces.EnquiryOperations;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for handling Enquiry operations. Implements the EnquiryOperations
 * interface.
 */
public class EnquiryController implements EnquiryOperations {

    /**
     * Creates a new enquiry for a project by an applicant.
     *
     * @param project Project related to the enquiry
     * @param applicant Applicant who submitted the enquiry
     * @param content Content of the enquiry
     * @return Newly created Enquiry object
     */
    @Override
    public Enquiry createEnquiry(Project project, Applicant applicant, String content) {
        return new Enquiry(project, applicant, content);
    }

    /**
     * Retrieves an enquiry based on its index in the list.
     *
     * @param enquiries List of enquiries
     * @param index Index of the enquiry to retrieve
     * @return Enquiry object if valid index, else null
     * @throws Exception 
     */
    @Override
    public Enquiry retrieveEnquiry(List<Enquiry> enquiries, int index) throws Exception {
        if (index >= 0 && index < enquiries.size()) {
            return enquiries.get(index);
        }
        throw new Exception("Index out of bounds. ");
    }

    /**
     * Deletes an enquiry if it has not been replied to.
     *
     * @param enquiries List of enquiries
     * @param enquiry Enquiry to delete
     * @return 1 if deleted successfully, else 0
     */
    @Override
    public int deleteEnquiry(List<Enquiry> enquiries, Enquiry enquiry) throws Exception {
        if (!enquiry.hasReplied()) {
            return enquiries.remove(enquiry) ? 1 : 0;
        }
        throw new Exception("Enquiry has a reply, unable to delete. ");
    }

    /**
     * Adds a reply to an enquiry.
     *
     * @param user User replying to the enquiry
     * @param enquiry Enquiry to be replied to
     * @param reply Reply content
     * @return 1 if reply was successful, else 0
     */
    @Override
    public int replyEnquiry(Enquiry enquiry, String reply) throws Exception {
        if (!enquiry.hasReplied()) {
            enquiry.setReply(reply);
            enquiry.setReplied(true);
            enquiry.setRepliedAt(LocalDateTime.now());
            return 1;
        }
        throw new Exception("Enquiry has a reply, unable to reply. ");
    }

    /**
     * Edits the content of an enquiry if it hasn't been replied to yet.
     *
     * @param enquiry Enquiry to edit
     * @param content New content
     * @return 1 if edited successfully, else 0
     */
    @Override
    public int editEnquiry(Enquiry enquiry, String content) throws Exception {
        if (!enquiry.hasReplied()) {
            enquiry.setContent(content);
            return 1;
        }
        throw new Exception("Enquiry has a reply, unable to edit. ");
    }

    /**
     * Converts a list of enquiries to a formatted string.
     *
     * @param data List of enquiries
     * @return String representation of all enquiries
     */
    @Override
    public String toString(List<Enquiry> data) {
        StringBuilder sb = new StringBuilder();

        // Format: No. | Project Name | Applicant Name | Enquiry | Created At | Replied
        String format = "%-4s %-25s %-15s %-60s %-12s %-10s%n";
        sb.append(String.format(format,
                "No.",
                "Project Name",
                "Applicant",
                "Enquiry",
                "Created At",
                "Replied"));

        int count = 1;
        for (Enquiry e : data) {
            // Format the date part only from the LocalDateTime
            String createdAtDate = e.getCreatedAt().toLocalDate().toString();

            sb.append(String.format(format,
                    "[" + count++ + "]",
                    e.getProject().getName(),
                    e.getApplicant().getName(),
                    e.getContent(),
                    createdAtDate,
                    e.hasReplied() ? "Yes" : "No"));
        }

        return sb.toString();
    }

    /**
     * Converts a single enquiry to a formatted string.
     *
     * @param data Enquiry object
     * @return String representation of the enquiry
     */
    @Override
    public String toString(Enquiry data) {
        return "Enquiry: " + data.getContent()
                + "\nReplied: " + data.hasReplied()
                + "\nReply: " + (data.getReply() == null ? "N/A" : data.getReply())
                + "\nCreated at: " + data.getCreatedAt()
                + "\nReplied at: " + (data.getRepliedAt() == null ? "N/A" : data.getRepliedAt());
    }

    @Override
    public Project retrieveProject(Enquiry enquiry) {
        return enquiry.getProject();
    }

    @Override
    public Applicant retrieveApplicant(Enquiry enquiry) {
        return enquiry.getApplicant();
    }

    @Override
    public boolean hasReplied(Enquiry enquiry) {
        return enquiry.hasReplied();
    }
    
    @Override
    public int addEnquiry(List<Enquiry> enquiries, Enquiry enquiry) {
        enquiries.add(enquiry);
        return 1;
    }

    @Override
    public List<Enquiry> filterEnquiries(List<Enquiry> enquiries, boolean replied) {
        return enquiries.stream().filter(e -> e.hasReplied() == replied).collect(Collectors.toList());
    }

    /**
     * Sets project to empty
     *
     * @param enquiry The enquiry to edit
     */
    @Override
    public void removeProject(Enquiry enquiry) throws Exception {
        enquiry.setProject(null);
    }
}
