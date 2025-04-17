package btosystem.cont.applicant;

import btosystem.utils.ListToStringFormatter;

public class ApplicantMainController extends ApplicantController {
    private static final String[] MENU = {"Project Service", "Application Service", "Enquiry Service", "Exit"};
    private ApplicantBtoApplicationController applicationController;
    private ApplicantEnquiryController enquiryController;
    private ApplicantProjectController projectController;
    
    public ApplicantMainController(ApplicantBtoApplicationController applicationController,
            ApplicantEnquiryController enquiryController, ApplicantProjectController projectController) {
        this.applicationController = applicationController;
        this.enquiryController = enquiryController;
        this.projectController = projectController;
    }

    @Override
    protected int process(int input) throws Exception {
        switch(input) {
            case 0: projectController.execute(); return 0;
            case 1: applicationController.execute(); return 0;
            case 2: enquiryController.execute(); return 0;
            default: System.out.println("Please enter a valid input. "); return 0;
        }
    }
    @Override
    protected String getMenu() {
        return ListToStringFormatter.toString(MENU);
    }
}
