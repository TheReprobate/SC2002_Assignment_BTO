package btosystem.cont.hdbofficer;

import btosystem.classes.BtoApplication;
import btosystem.classes.HdbOfficer;
import btosystem.service.HdbOfficerServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;
import btosystem.utils.RegexPatterns;

/**
 * High-level controller class specific to the {@link HdbOfficer} role
 * handling BTO application related functionality.
 */
public class HdbOfficerBtoApplicationController extends HdbOfficerController{
    private static final String[] MENU = {
            "Process Application", "Exit"
    };

    /**
     * Constructor for the {@link HdbOfficer} BTOApplications controller.
     *
     * @param user reference to a {@link HdbOfficer} object
     * @param serviceManager reference to a {@link HdbOfficerServiceManager}
     */
    public HdbOfficerBtoApplicationController(
            HdbOfficer user, HdbOfficerServiceManager serviceManager) {
        super(user, serviceManager);
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
                processApplication();
                return 0;
            case 1: return -1;
            default: throw new Exception("Please enter a valid input. ");
        }
    }

    /**
     * Invokes service classes to perform the operations required
     * to process an application.
     *
     * @throws Exception propagated errors from service calls
     */
    private void processApplication() throws Exception {
        String nric = InputHandler.getStringInput("Input Applicant's NRIC: ", RegexPatterns.NRIC);
        BtoApplication application = serviceManager.getApplicationService().getApplication(nric);
        System.out.println(serviceManager.getGenericService().displayApplication(application));
        String input = InputHandler.getStringInput("Confirm to process application [Y/N]: ", RegexPatterns.YES_NO);
        if (!(input.equals("Y") || input.equals("y"))) {
            System.out.println("Process application cancelled");
            return;
        }
        serviceManager.getApplicationService().processApplication(application, user);
        System.out.println("Process application successful!");
    }
    
}
