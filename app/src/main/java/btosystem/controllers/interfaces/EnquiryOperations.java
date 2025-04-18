package btosystem.controllers.interfaces;

import btosystem.classes.Applicant;
import btosystem.classes.Enquiry;
import btosystem.classes.Project;

import java.util.List;

public interface EnquiryOperations
        extends ListToString<Enquiry>, CleanupOperations<Enquiry> {
    Enquiry createEnquiry(Project project, Applicant applicant, String content);

    Enquiry retrieveEnquiry(List<Enquiry> enquiries, int index);

    Project retrieveProject(Enquiry enquiry);

    Applicant retrieveApplicant(Enquiry enquiry);

    int deleteEnquiry(List<Enquiry> enquiries, Enquiry enquiry);

    int replyEnquiry(Enquiry enquiry, String reply);

    int editEnquiry(Enquiry enquiry, String content);

    int addEnquiry(List<Enquiry> enquiries, Enquiry enquiry);

    boolean hasReplied(Enquiry enquiry);

    List<Enquiry> filterEnquiries(List<Enquiry> enquiries, boolean replied);
}
