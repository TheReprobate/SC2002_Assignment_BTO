package btosystem.cont.hdbofficer;

import btosystem.classes.HdbOfficer;
import btosystem.service.HdbOfficerServiceManager;
import btosystem.utils.ListToStringFormatter;

public class HdbOfficerMainController extends HdbOfficerController{
    private static final String[] MENU = {"Application Service", "Enquiry Service", "Project Service", "Exit"};
    private HdbOfficerBtoApplicationController applicationController;
    private HdbOfficerEnquiryController enquiryController;
    private HdbOfficerProjectController projectController;
    public HdbOfficerMainController(HdbOfficer user, HdbOfficerServiceManager serviceManager) {
        super(user);
        this.applicationController = new HdbOfficerBtoApplicationController(user, serviceManager);
        this.enquiryController = new HdbOfficerEnquiryController(user, serviceManager);
        //this.projectController = new HdbOfficerProjectController();
    }

    @Override
    protected boolean load() throws Exception {
        return true;
    }

    @Override
    protected String display() {
        return ListToStringFormatter.toString(MENU);
    }

    @Override
    protected int process(int input) throws Exception {
        switch(input) {
            case 0: applicationController.execute(); return 0;
            case 1: enquiryController.execute(); return 0;
            //case 2: projectController.execute(); return 0;
            case 3: return -1;
            default: System.out.println("Please enter a valid input. "); return 0;
        }
    }
}
