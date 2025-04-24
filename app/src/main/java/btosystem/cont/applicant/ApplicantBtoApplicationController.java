package btosystem.cont.applicant;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.Project;
import btosystem.classes.enums.FlatType;
import btosystem.service.ApplicantServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;
import btosystem.utils.RegexPatterns;
import java.util.List;

/**
 * High-level controller class specific to the {@link Applicant} role
 * handling BTO application related functionality.
 */

public class ApplicantBtoApplicationController extends ApplicantController {
    private static final String[] MENU = {
        "Create Application", "Withdraw Application", "Exit"
    };
    private BtoApplication application;

    /**
     * Constructor for the {@link Applicant} BTOApplications controller.
     *
     * @param user reference to a {@link Applicant} object
     * @param serviceManager reference to a {@link ApplicantServiceManager}
     */

    public ApplicantBtoApplicationController(Applicant user, 
                                            ApplicantServiceManager serviceManager) {
        super(user, serviceManager);
    }

    @Override
    protected boolean load() throws Exception {
        application = serviceManager.getApplicationService().getApplication(user);
        return true;
    }

    @Override
    protected String display() {
        if (application == null) {
            return "You currently do not have an active application.\n"
                    + ListToStringFormatter.toString(MENU);
        }
        else {
            return "Current Application: \n"
                    + serviceManager.getGenericService().displayApplication(application)
                    + "\n"
                    + ListToStringFormatter.toString(MENU);
        }
    }

    @Override
    protected int process(int input) throws Exception {
        switch (input) {
          case 0:
              createApplication();
              return 0;
          case 1:
              withdrawApplication();
              return 0;
          case 2:
              return -1;
          default:
              throw new Exception("Please enter a valid input. ");
        }
    }

    /**
     * Invokes service classes to perform the operations required in application process.
     *
     * @throws Exception when no flat-types are available or propagated errors from service calls
     */

    private void createApplication() throws Exception {
        if (application != null) {
            throw new Exception("Active application found. ");
        }
        List<Project> projects = serviceManager.getProjectService().getVisibleProjects();
        System.out.println(serviceManager.getGenericService().displayProject(projects));
        int projectIndex = InputHandler.getIntIndexInput("Select a project to apply for: ");
        Project project = serviceManager.getGenericService().getProject(projects, projectIndex);
        List<FlatType> flatTypes = serviceManager.getApplicationService()
                                                .getAvailableFlatTypes(user, project);
        if (flatTypes.size() <= 0) {
            throw new Exception("No flat types available for you. ");
        }
        System.out.println(ListToStringFormatter.toString(flatTypes));
        int flatIndex = InputHandler.getIntIndexInput("Select a flat to apply for: ");
        FlatType flatType = flatTypes.get(flatIndex);
        serviceManager.getApplicationService().createApplication(user, project, flatType);
        System.out.println("Application created successfully!");
    }

    /**
     * Invokes service classes to perform the operations required
     * for withdrawal of an application.
     *
     * @throws Exception when no flat-types are available or propagated errors from service calls
     */

    private void withdrawApplication() throws Exception {
        if (application == null) {
            throw new Exception("No existing application found. ");
        }
        String input = InputHandler.getStringInput(
                "Confirm to withdraw application? [Y/N] ", RegexPatterns.YES_NO
        );
        if (!(input.equals("Y") || input.equals("y"))) {
            System.out.println("Application withdrawal cancelled!");
            return;
        }
        serviceManager.getApplicationService().withdrawApplication(user);
        System.out.println("Application withdrawal success!");
    }
}
