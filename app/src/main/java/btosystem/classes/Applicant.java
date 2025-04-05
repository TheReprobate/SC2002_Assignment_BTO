package btosystem.classes;

import java.util.ArrayList;
import java.util.List;

public class Applicant extends User {
    private BtoApplication activeApplication;
    private List<Enquiry> enquiries;

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
