package btosystem.controllers;

import btosystem.classes.Applicant;
import btosystem.classes.Enquiry;
import btosystem.classes.Project;
import btosystem.controllers.interfaces.EnquiryOperations;

import java.time.LocalDateTime;
import java.util.List;

public class EnquiryController implements EnquiryOperations {

    @Override
    public Enquiry createEnquiry(Project project, Applicant applicant, String content) {
        return new Enquiry(project, applicant, content);
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
    public int replyEnquiry(Enquiry enquiry, String reply) {
        if (!enquiry.hasReplied()) {
            enquiry.setReply(reply);
            enquiry.setReplied(true);
            enquiry.setRepliedAt(LocalDateTime.now());
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
        instance.setReplied(false);
        instance.setRepliedAt(null);
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
        return enquiries.stream().filter(e -> e.hasReplied() == replied).toList();
    }
}
