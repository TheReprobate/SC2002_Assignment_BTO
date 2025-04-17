package btosystem.cont.hdbmanager;

import btosystem.utils.ListToStringFormatter;

public class HdbManagerMainController extends HdbManagerController {
    private static final String[] MENU = {"Project Service", "Application Service", "Enquiry Service", "Exit"};
    private HdbManagerBtoApplicationController applicationController;
    private HdbManagerEnquiryController enquiryController;
    private HdbManagerProjectController projectController;
    
    public HdbManagerMainController(HdbManagerBtoApplicationController applicationController,
            HdbManagerEnquiryController enquiryController, HdbManagerProjectController projectController) {
        this.applicationController = applicationController;
        this.enquiryController = enquiryController;
        this.projectController = projectController;
    }
    @Override
    protected String getMenu() {
        return ListToStringFormatter.toString(MENU);
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
}
