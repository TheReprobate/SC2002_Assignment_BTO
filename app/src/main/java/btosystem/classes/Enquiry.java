package btosystem.classes;

import java.time.LocalDateTime;

/**
 * Represents an enquiry made by an applicant about a project. Contains
 * information about the enquiry content, reply status, and timestamps for
 * creation and reply.
 */
public class Enquiry {

    private String content;
    private String reply;
    private boolean replied;
    private Applicant applicant;
    private Project project;
    private LocalDateTime createdAt;
    private LocalDateTime repliedAt;

    /**
     * Constructs a new Enquiry with the given project, applicant, and content.
     *
     * @param project The project the enquiry is about
     * @param applicant The applicant making the enquiry
     * @param content The content of the enquiry
     */
    public Enquiry(Project project, Applicant applicant, String content) {
        this.content = content;
        this.project = project;
        this.applicant = applicant;
        this.createdAt = LocalDateTime.now();
        this.replied = false;
    }

    /**
     * Gets the content of the enquiry.
     *
     * @return The enquiry content
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the reply to the enquiry.
     *
     * @return The reply content, or null if not replied
     */
    public String getReply() {
        return reply;
    }

    /**
     * Checks if the enquiry has been replied to.
     *
     * @return true if the enquiry has been replied to, false otherwise
     */
    public boolean hasReplied() {
        return replied;
    }

    /**
     * Gets the applicant who made the enquiry.
     *
     * @return The applicant object
     */
    public Applicant getApplicant() {
        return applicant;
    }

    /**
     * Gets the project the enquiry is about.
     *
     * @return The project object
     */
    public Project getProject() {
        return project;
    }

    /**
     * Gets the timestamp when the enquiry was created.
     *
     * @return The creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the timestamp when the enquiry was replied to.
     *
     * @return The reply timestamp, or null if not replied
     */
    public LocalDateTime getRepliedAt() {
        return repliedAt;
    }

    /**
     * Sets the content of the enquiry.
     *
     * @param content The new content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Sets the reply content for the enquiry.
     *
     * @param reply The reply content to set
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    /**
     * Sets the replied status of the enquiry.
     *
     * @param replied true if the enquiry has been replied to, false otherwise
     */
    public void setReplied(boolean replied) {
        this.replied = replied;
    }

    /**
     * Sets the timestamp when the enquiry was replied to.
     *
     * @param repliedAt The reply timestamp to set
     */
    public void setRepliedAt(LocalDateTime repliedAt) {
        this.repliedAt = repliedAt;
    }
}
