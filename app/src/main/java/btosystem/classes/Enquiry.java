package btosystem.classes;

import java.time.LocalDateTime;

public class Enquiry {

    private String content;
    private String reply;
    private boolean replied;
    private Applicant applicant;
    private Project project;
    private LocalDateTime createdAt;
    private LocalDateTime repliedAt;

    public Enquiry(Project project, Applicant applicant, String content) {
        this.content = content;
        this.project = project;
        this.applicant = applicant;
        this.createdAt = LocalDateTime.now();
        this.replied = false;
    }

    public String getContent() {
        return content;
    }

    public String getReply() {
        return reply;
    }

    public boolean hasReplied() {
        return replied;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Project getProject() {
        return project;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getRepliedAt() {
        return repliedAt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public void setReplied(boolean replied) {
        this.replied = replied;
    }

    public void setRepliedAt(LocalDateTime repliedAt) {
        this.repliedAt = repliedAt;
    }
}
