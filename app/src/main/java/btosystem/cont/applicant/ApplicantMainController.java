package btosystem.cont.applicant;

import btosystem.classes.Applicant;
import btosystem.service.ApplicantServiceManager;
import btosystem.utils.ListToStringFormatter;

public class ApplicantMainController extends ApplicantController {
    private static final String[] MENU = {"Project Service", "Application Service", "Enquiry Service", "Exit"};
    private ApplicantBtoApplicationController applicationController;
    private ApplicantEnquiryController enquiryController;
    private ApplicantProjectController projectController;
    
    public ApplicantMainController(Applicant user, ApplicantServiceManager serviceManager) {
        super(user, serviceManager);
        this.applicationController = new ApplicantBtoApplicationController(user, serviceManager);
        this.enquiryController = new ApplicantEnquiryController(user, serviceManager);
        this.projectController = new ApplicantProjectController(user, serviceManager);
    }

    @Override
    protected boolean load() throws Exception {
        return true;
    }

    @Override
    protected String display(){
        return ListToStringFormatter.toString(MENU);
    }

    @Override
    protected int process(int input) throws Exception {
        switch(input) {
            case 0: projectController.execute(); return 0;
            case 1: applicationController.execute(); return 0;
            case 2: enquiryController.execute(); return 0;
            case 3: return -1;
            default: throw new Exception("Please enter a valid input. "); 
        }
    }
}
