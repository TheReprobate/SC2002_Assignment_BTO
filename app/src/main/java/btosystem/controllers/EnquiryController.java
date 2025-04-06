package btosystem.controllers;

import java.util.List;

import btosystem.classes.Applicant;
import btosystem.classes.Enquiry;
import btosystem.classes.Project;
import btosystem.controllers.interfaces.EnquiryOperations;

public class EnquiryController implements EnquiryOperations {

    public Enquiry createEnquiry(Project project, Applicant applicant, String content) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Enquiry retrieveEnquiry(List<Enquiry> enquiries, int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int deleteEnquiry(Enquiry enquiry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int replyEnquiry(Enquiry enquiry, String reply) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int editEnquiry(Enquiry enquiry, String content) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String toString(List<Enquiry> data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String toString(Enquiry data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void cleanup(Enquiry instance) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}