package btosystem.cont.hdbmanager;

import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.service.HdbManagerServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;
import java.util.List;

/**
 * High-level controller class specific to the {@link HdbManager} role
 * handling enquiry related functionality.
 */
public class HdbManagerEnquiryController extends HdbManagerController {
    private static final String[] MENU = {"Reply Enquiry", "Exit"};
    private Project project;
    private List<Enquiry> enquiries;

    /**
     * Constructor for the {@link HdbManager} Enquiry controller.
     *
     * @param user reference to a {@link HdbManager} object
     * @param serviceManager reference to a {@link HdbManagerServiceManager}
     */
    public HdbManagerEnquiryController(HdbManager user, HdbManagerServiceManager serviceManager) {
        super(user, serviceManager);
    }

    /**
     * Retrieves the project's un-replied enquiries via service call.
     */
    @Override
    protected boolean load() throws Exception {
        project = serviceManager.getProjectService().getCurrentProject(user);
        if (project == null) {
            System.out.println("No current project found. ");
            return false;
        }
        enquiries = serviceManager.getEnquiryService().getEnquiries(project, false);
        if (enquiries.size() <= 0) {
            System.out.println("No enquiries found. ");
            return false;
        }
        return true;
    }
    
    @Override
    protected String display() {
        return serviceManager.getGenericService().displayEnquiry(enquiries)
                + ListToStringFormatter.toString(MENU);
    }

    @Override
    protected int process(int input) throws Exception {
        switch (input) {
          case 0:
              replyEnquiry();
              return 0;
          case 1:
              return -1;
          default:
              throw new Exception("Please enter a valid input. ");
        }
    }

    /**
     * Invokes service classes to perform the operations required
     * to reply to an enquiry.
     *
     * @throws Exception propagated errors from service calls
     */
    private void replyEnquiry() throws Exception {
        Enquiry enquiry = getEnquiry();
        String reply = InputHandler.getStringInput("Input reply: ");
        serviceManager.getEnquiryService().replyEnquiry(user, enquiry, reply);
        System.out.println("Enquiry reply successful!");
    }

    /**
     * Requests user to pick an enquiry and invoke service class to return
     * enquiry object.
     *
     * @throws Exception propagated errors from service calls
     */
    private Enquiry getEnquiry() throws Exception {
        int index = InputHandler.getIntIndexInput("Select an enquiry: ");
        Enquiry enquiry = serviceManager.getGenericService().getEnquiry(enquiries, index);
        return enquiry;
    }
}
