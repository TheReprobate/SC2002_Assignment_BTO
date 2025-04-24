package btosystem.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an applicant for Build-To-Order (BTO) flats.
 * An applicant is a type of user who can submit BTO applications and make enquiries.
 */
public class Applicant extends User {
    private BtoApplication activeApplication;
    private List<Enquiry> enquiries;

    /**
     * Constructs an {@code Applicant} object with the specified NRIC, name, age, and marital status.
     *
     * @param nric    The NRIC of the applicant.
     * @param name    The name of the applicant.
     * @param age     The age of the applicant.
     * @param married {@code true} if the applicant is married, {@code false} otherwise.
     */
    public Applicant(String nric, String name, int age, boolean married) {
        super(nric, name, age, married);
        enquiries = new ArrayList<Enquiry>();
    }

    public BtoApplication getActiveApplication() {
        return activeApplication;
    }

    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    public void setActiveApplication(BtoApplication activeApplication) {
        this.activeApplication = activeApplication;
    }
}
