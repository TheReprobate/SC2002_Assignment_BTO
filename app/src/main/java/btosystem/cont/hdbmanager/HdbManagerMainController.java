package btosystem.cont.hdbmanager;

import btosystem.classes.HdbManager;
import btosystem.service.HdbManagerServiceManager;
import btosystem.utils.ListToStringFormatter;

public class HdbManagerMainController extends HdbManagerController {
    private static final String[] MENU = {"System Project Service", "Current Project Service", "Application Service", "Enquiry Service", "Exit"};
    private HdbManagerBtoApplicationController applicationController;
    private HdbManagerEnquiryController enquiryController;
    private HdbManagerSystemProjectController systemProjectController;
    private HdbManagerCurrentProjectController currentProjectController;
    
    public HdbManagerMainController(HdbManager user, HdbManagerServiceManager serviceManager) {
        super(user);
        this.applicationController = new HdbManagerBtoApplicationController(user, serviceManager);
        this.enquiryController = new HdbManagerEnquiryController(user, serviceManager);
        this.systemProjectController = new HdbManagerSystemProjectController(user, serviceManager);
        this.currentProjectController = new HdbManagerCurrentProjectController(user, serviceManager);
    }
    
    @Override
    protected boolean load() throws Exception {
        applicationController.setUser(getUser());
        enquiryController.setUser(getUser());
        systemProjectController.setUser(getUser());
        currentProjectController.setUser(getUser());
        return true;
    }

    @Override
    protected String display() {
        return ListToStringFormatter.toString(MENU);
    }

    @Override
    protected int process(int input) throws Exception {
        switch(input) {
            case 0: systemProjectController.execute(); return 0;
            case 1: currentProjectController.execute(); return 0;
            case 2: applicationController.execute(); return 0;
            case 3: enquiryController.execute(); return 0;
            case 4: return -1;
            default: throw new Exception("Please enter a valid input. ");
        }
    }
}
