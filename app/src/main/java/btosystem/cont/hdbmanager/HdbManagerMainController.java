package btosystem.cont.hdbmanager;

import btosystem.classes.HdbManager;
import btosystem.service.HdbManagerServiceManager;
import btosystem.utils.ListToStringFormatter;

/**
 * The main controller class specific to the {@link HdbManager} role.
 * Implements the main control loop and performs delegation of
 * operations to the other controllers in the family
 */
public class HdbManagerMainController extends HdbManagerController {
    private static final String[] MENU = {
        "System Project Service", "Current Project Service",
        "Application Service", "Enquiry Service", "Exit"};

    private HdbManagerBtoApplicationController applicationController;
    private HdbManagerEnquiryController enquiryController;
    private HdbManagerSystemProjectController systemProjectController;
    private HdbManagerCurrentProjectController currentProjectController;

    /**
     * Constructor for the main {@link HdbManager} controller.
     * Instantiates the other sub-controllers in the family for its own use
     *
     * @param user reference to a {@link HdbManager} object
     * @param serviceManager reference to a {@link HdbManagerServiceManager}
     *                       instance for use by sub-controllers
     */
    public HdbManagerMainController(HdbManager user, HdbManagerServiceManager serviceManager) {
        super(user, serviceManager);
        this.applicationController =
                new HdbManagerBtoApplicationController(user, serviceManager);
        this.enquiryController =
                new HdbManagerEnquiryController(user, serviceManager);
        this.systemProjectController =
                new HdbManagerSystemProjectController(user, serviceManager);
        this.currentProjectController =
                new HdbManagerCurrentProjectController(user, serviceManager);
    }
    
    @Override
    protected boolean load() throws Exception {
        return true;
    }

    @Override
    protected String display() {
        return "\n[HDB MANAGER DASHBOARD]" + ListToStringFormatter.toString(MENU);
    }

    @Override
    protected int process(int input) throws Exception {
        switch (input) {
          case 0:
              systemProjectController.execute();
              return 0;
          case 1:
              currentProjectController.execute();
              return 0;
          case 2:
              applicationController.execute();
              return 0;
          case 3:
              enquiryController.execute();
              return 0;
          case 4:
              return -1;
          default:
              throw new Exception("Please enter a valid input. ");
        }
    }
}
