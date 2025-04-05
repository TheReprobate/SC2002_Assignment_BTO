package btosystem.classes;

public class Enquiry {
    private String content;
    private String reply;
    private boolean replied;
    private Applicant applicant;
    private Project project;

    public Enquiry(Project project, Applicant applicant, String content) {
        this.content = content;
        this.project = project;
        this.applicant = applicant;
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

    public void setContent(String content) {
        this.content = content;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public void setReplied(boolean replied) {
        this.replied = replied;
    }
}
