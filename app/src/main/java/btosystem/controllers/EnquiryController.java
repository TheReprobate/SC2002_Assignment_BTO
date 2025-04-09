package btosystem.controllers;

import btosystem.classes.Applicant;
import btosystem.classes.Enquiry;
import btosystem.classes.Project;
import btosystem.classes.User;
import btosystem.controllers.interfaces.EnquiryOperations;

import java.util.List;

public class EnquiryController implements EnquiryOperations {

    @Override
    public Enquiry createEnquiry(Project project, Applicant applicant, String content) {
        Enquiry enquiry = new Enquiry(project, applicant, content);
        applicant.getEnquiries().add(enquiry);
        project.getEnquiries().add(enquiry);
        return enquiry;
    }

    @Override
    public Enquiry retrieveEnquiry(List<Enquiry> enquiries, int index) {
        if (index >= 0 && index < enquiries.size()) {
            return enquiries.get(index);
        }
        return null;
    }

    @Override
    public int deleteEnquiry(List<Enquiry> enquiries, Enquiry enquiry) {
        if (!enquiry.hasReplied()) {
            return enquiries.remove(enquiry) ? 1 : 0;
        }
        return 0;
    }

    @Override
    public int replyEnquiry(User user, Enquiry enquiry, String reply) {
        if (!enquiry.hasReplied()) {
            enquiry.setReply(reply);
            return 1;
        }
        return 0;
    }

    @Override
    public int editEnquiry(Enquiry enquiry, String content) {
        if (!enquiry.hasReplied()) {
            enquiry.setContent(content);
            return 1;
        }
        return 0;
    }

    @Override
    public void cleanup(Enquiry instance) {
        instance.setContent(null);
        instance.setReply(null);
    }

    @Override
    public String toString(List<Enquiry> data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            sb.append(i).append(". ").append(toString(data.get(i))).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString(Enquiry data) {
        return "Enquiry: " + data.getContent()
                + "\nReplied: " + data.hasReplied()
                + "\nReply: " + (data.getReply() == null ? "N/A" : data.getReply())
                + "\nCreated at: " + data.getCreatedAt()
                + "\nReplied at: " + (data.getRepliedAt() == null ? "N/A" : data.getRepliedAt());
    }
}
