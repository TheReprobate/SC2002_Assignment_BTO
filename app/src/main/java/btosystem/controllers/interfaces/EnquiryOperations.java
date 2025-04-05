package btosystem.controllers.interfaces;

import btosystem.classes.Applicant;
import btosystem.classes.Enquiry;
import btosystem.classes.Project;
import java.util.List;

public interface EnquiryOperations
        extends ListToStringParser<Enquiry>, ToStringParser<Enquiry>, CleanupOperations<Enquiry> {
    Enquiry createEnquiry(Project project, Applicant applicant, String content);

    List<Enquiry> retrieveEnquiries(Applicant applicant);

    List<Enquiry> retrievEnquiries(Project project);

    Enquiry retrieveEnquiry(List<Enquiry> enquiries, int index);

    int deleteEnquiry(Enquiry enquiry);

    int replyEnquiry(Enquiry enquiry, String reply);

    int editEnquiry(Enquiry enquiry, String content);
}
