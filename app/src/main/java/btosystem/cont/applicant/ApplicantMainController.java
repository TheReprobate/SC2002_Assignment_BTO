package btosystem.cont.applicant;

import btosystem.classes.Applicant;
import btosystem.service.ApplicantServiceManager;
import btosystem.utils.ListToStringFormatter;

/**
 * The main controller class specific to the {@link Applicant} role.
 * Implements the main control loop and performs delegation of
 * operations to the other controllers in the family
 */
public class ApplicantMainController extends ApplicantController {
    private static final String[] MENU = {
        "Project Service", "Application Service", "Enquiry Service", "Exit"
    };
    private ApplicantBtoApplicationController applicationController;
    private ApplicantEnquiryController enquiryController;
    private ApplicantProjectController projectController;

    /**
     * Constructor for the main {@link Applicant} controller.
     * Instantiates the other sub-controllers in the family for its own use
     *
     * @param user reference to a {@link Applicant} object
     * @param serviceManager reference to a {@link ApplicantServiceManager}
     *                       instance for use by sub-controllers
     */
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
    protected String display() {
        return "\nAPPLICATION DASHBOARD" + ListToStringFormatter.toString(MENU);
    }

    @Override
    protected int process(int input) throws Exception {
        return switch (input) {
          case 0 -> {
              projectController.execute();
              yield 0;
          }
          case 1 -> {
              applicationController.execute();
              yield 0;
          }
          case 2 -> {
              enquiryController.execute();
              yield 0;
          }
          case 3 -> -1;
          default -> throw new Exception("Please enter a valid input. ");
        };
    }
}
