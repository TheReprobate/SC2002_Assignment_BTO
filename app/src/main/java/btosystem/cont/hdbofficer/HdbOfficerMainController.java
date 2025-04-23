package btosystem.cont.hdbofficer;

import btosystem.classes.HdbOfficer;
import btosystem.service.HdbOfficerServiceManager;
import btosystem.utils.ListToStringFormatter;

/**
 * The main controller class specific to the {@link HdbOfficer} role.
 * Implements the main control loop and performs delegation of
 * operations to the other controllers in the family
 */
public class HdbOfficerMainController extends HdbOfficerController {
    private static final String[] MENU = {
        "Application Service", "Enquiry Service", "Project Service", "Exit"
    };
    private HdbOfficerBtoApplicationController applicationController;
    private HdbOfficerEnquiryController enquiryController;
    private HdbOfficerProjectController projectController;

    /**
     * Constructor for the main {@link HdbOfficer} controller.
     * Instantiates the other sub-controllers in the family for its own use
     *
     * @param user reference to a {@link HdbOfficer} object
     * @param serviceManager reference to a {@link HdbOfficerServiceManager}
     *                       instance for use by sub-controllers
     */
    public HdbOfficerMainController(HdbOfficer user, HdbOfficerServiceManager serviceManager) {
        super(user, serviceManager);
        this.applicationController = new HdbOfficerBtoApplicationController(user, serviceManager);
        this.enquiryController = new HdbOfficerEnquiryController(user, serviceManager);
        this.projectController = new HdbOfficerProjectController(user, serviceManager);
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
        switch (input) {
          case 0:
              applicationController.execute();
              return 0;
          case 1:
              enquiryController.execute();
              return 0;
          case 2:
              projectController.execute();
              return 0;
          case 3: 
              return -1;
          default:
              System.out.println("Please enter a valid input. ");
              return 0;
        }
    }
}
