package btosystem.clients.applicant;

import btosystem.classes.Applicant;
import btosystem.classes.Enquiry;
import btosystem.classes.Project;
import btosystem.service.ApplicantServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;
import btosystem.utils.RegexPatterns;
import java.util.List;

// missing filters

/**
 * High-level controller class specific to the {@link Applicant} role
 * handling enquiries related functionality.
 */
public class ApplicantEnquiryController extends ApplicantController {
    private static final String[] MENU = {"View Enquiry Details", "Create Enquiry", "Edit Enquiry", "Delete Enquiry", "Exit"};
    private List<Enquiry> enquiries;

    /**
     * Constructor for the {@link Applicant} Enquiry controller.
     *
     * @param user reference to a {@link Applicant} object
     * @param serviceManager reference to a {@link ApplicantServiceManager}
     */
    public ApplicantEnquiryController(Applicant user, ApplicantServiceManager serviceManager) {
        super(user, serviceManager);
    }

    /**
     * Retrieves the user's enquiries via service call.
     */
    @Override
    protected boolean load() throws Exception {
        enquiries = serviceManager.getEnquiryService().getPersonalEnquiries(user);
        if (enquiries.size() <= 0) {
            System.out.println("No personal enquiries found. ");
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
              viewEnquiry();
              return 0;
          case 1:
              createEnquiry();
              return 0;
          case 2:
              editEnquiry();
              return 0;
          case 3:
              deleteEnquiry();
              return 0;
          case 4:
              return -1;
          default:
              throw new Exception("Please enter a valid input. ");
        }
    }

    /**
     * Invokes service classes to perform the operations required
     * to view a specific enquiry.
     *
     * @throws Exception propagated errors from service calls
     */
    private void viewEnquiry() throws Exception {
        Enquiry enquiry = getEnquiry();
        System.out.println(serviceManager.getGenericService().displayEnquiry(enquiry));
    }

    /**
     * Invokes service classes to perform the operations required
     * to create an enquiry.
     *
     * @throws Exception propagated errors from service calls
     */
    private void createEnquiry() throws Exception {
        List<Project> projects = serviceManager.getProjectService().getVisibleProjects();
        if(projects == null || projects.size() <= 0){
            throw new Exception("No projects found. ");
        }
        System.out.println(serviceManager.getGenericService().displayProject(projects));
        int projectIndex = InputHandler.getIntIndexInput("Select a project to enquire for: ");
        Project project = serviceManager.getGenericService().getProject(projects, projectIndex);
        String input = InputHandler.getStringInput("Input message: ");
        serviceManager.getEnquiryService().createEnquiry(user, project, input);
        System.out.println("Enquiry create success!");
    }

    /**
     * Invokes service classes to perform the operations required
     * to edit an enquiry.
     *
     * @throws Exception propagated errors from service calls or editing not allowed
     */
    private void editEnquiry() throws Exception {
        Enquiry enquiry = getEnquiry();
        if (!serviceManager.getEnquiryService().isEditable(enquiry)) {
            throw new Exception("Enquiry has a reply, unable to process.");
        }
        // System.out.println(serviceManager.getGenericService().displayEnquiry(enquiry));
        String input = InputHandler.getStringInput("Input new message: ");
        serviceManager.getEnquiryService().editEnquiry(user, enquiry, input);
        System.out.println("Enquiry edit success!");
    }

    /**
     * Invokes service classes to perform the operations required
     * to delete an enquiry.
     *
     * @throws Exception propagated errors from service calls
     */
    private void deleteEnquiry() throws Exception {
        Enquiry enquiry = getEnquiry();
        if (!serviceManager.getEnquiryService().isEditable(enquiry)) {
            throw new Exception("Enquiry has a reply, unable to process.");
        }
        System.out.println(serviceManager.getGenericService().displayEnquiry(enquiry));
        String input = InputHandler.getStringInput(
                "Confirm to delete enquiry [Y/N]: ",
                RegexPatterns.YES_NO
        );
        if (!(input.equals("Y") || input.equals("y"))) {
            System.out.println("Enquiry deletion cancelled");
            return;
        }
        serviceManager.getEnquiryService().deleteEnquiry(user, enquiry);
        System.out.println("Enquiry delete success!");
    }

    /**
     * Requests user to pick an enquiry and invoke service class to return
     * enquiry object.
     *
     * @throws Exception propagated errors from service calls or user has no enquiry records
     */
    private Enquiry getEnquiry() throws Exception {
        if (enquiries.size() <= 0) {
            throw new Exception("No personal enquiries found. ");
        }
        int index = InputHandler.getIntIndexInput("Select an enquiry: ");
        Enquiry enquiry = serviceManager.getGenericService().getEnquiry(enquiries, index);
        return enquiry;
    }
}
