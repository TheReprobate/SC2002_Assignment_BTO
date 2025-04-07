package btosystem.controllers;

import btosystem.classes.*;
import btosystem.controllers.interfaces.EnquiryOperations;

import java.util.Date;
import java.util.List;

public class EnquiryController implements EnquiryOperations {

    // Create an enquiry and attach it to both project and applicant
    @Override
    public Enquiry createEnquiry(Project project, Applicant applicant, String content) {
        Enquiry enquiry = new Enquiry(project, applicant, content + " [Created: " + new Date() + "]");
        applicant.getEnquiries().add(enquiry);
        project.getEnquiries().add(enquiry);
        return enquiry;
    }

    // Retrieve an enquiry by index from a list
    @Override
    public Enquiry retrieveEnquiry(List<Enquiry> enquiries, int index) {
        if (index >= 0 && index < enquiries.size()) {
            return enquiries.get(index);
        }
        return null;
    }

    // Delete an enquiry only if it has not been replied
    @Override
    public int deleteEnquiry(List<Enquiry> enquiries, Enquiry enquiry) {
        if (!enquiry.hasReplied()) {
            enquiries.remove(enquiry); // from view list
            enquiry.getProject().getEnquiries().remove(enquiry); // from project
            enquiry.getApplicant().getEnquiries().remove(enquiry); // from applicant
            return 1; // Success
        }
        return 0; // Fail (cannot delete if already replied)
    }

    // Officers and Managers can only reply if authorized
    public int replyEnquiry(User user, Enquiry enquiry, String reply) {
        if (enquiry.hasReplied()) {
            return 0; // Already replied
        }

        Project project = enquiry.getProject();
        if (user instanceof HdbOfficer) {
            HdbOfficer officer = (HdbOfficer) user;
            if (project.getProjectTeam() != null && project.getProjectTeam().getOfficers().contains(officer)) {
                enquiry.setReply(reply + " [Replied: " + new Date() + "]");
                enquiry.setReplied(true);
                return 1;
            }
            return 0; // officer not in project team (changed from -1 to 0)
        } else if (user instanceof HdbManager) {
            HdbManager manager = (HdbManager) user;
            if (project.getCreatedBy() == manager) {
                enquiry.setReply(reply + " [Replied: " + new Date() + "]");
                enquiry.setReplied(true);
                return 1;
            }
            return 0; // manager not assigned to this project (changed from -1 to 0)
        }

        // If we get here, user is neither an Officer nor a Manager
        return 0; // unauthorized user type 
    }

    // Edit only allowed if not yet replied
    @Override
    public int editEnquiry(Enquiry enquiry, String content) {
        if (!enquiry.hasReplied()) {
            enquiry.setContent(content + " [Edited: " + new Date() + "]");
            return 1; // Success
        }
        return 0; // Fail
    }

    // Utility cleanup (clear enquiry content)
    @Override
    public void cleanup(Enquiry instance) {
        instance.setContent(null);
        instance.setReply(null);
        instance.setReplied(false);
    }

    // a single enquiry
    @Override
    public String toString(Enquiry data) {
        return "Enquiry: " + data.getContent() + "\nReply: " + (data.getReply() == null ? "Pending" : data.getReply());
    }

    // a list of enquiries
    @Override
    public String toString(List<Enquiry> data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            sb.append("[").append(i).append("] ").append(toString(data.get(i))).append("\n");
        }
        return sb.toString();
    }
}
