package btosystem.cont.hdbmanager;

import btosystem.classes.BtoApplication;
import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.classes.enums.ApplicationStatus;
import btosystem.service.HdbManagerServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;
import java.util.List;

/**
 * High-level controller class specific to the {@link HdbManager} role
 * handling BTO application related functionality.
 */
public class HdbManagerBtoApplicationController extends HdbManagerController {
    private static final String[] MENU = {
        "Approve Application", "Reject Application", "Exit"
    };
    private Project project;
    private List<BtoApplication> applications;

    /**
     * Constructor for the {@link HdbManager} BTOApplications controller.
     *
     * @param user reference to a {@link HdbManager} object
     * @param serviceManager reference to a {@link HdbManagerServiceManager}
     */

    public HdbManagerBtoApplicationController(
            HdbManager user,
            HdbManagerServiceManager serviceManager) {
        super(user, serviceManager);
    }

    @Override
    protected boolean load() throws Exception {
        project = serviceManager.getProjectService().getCurrentProject(user);
        if (project == null) {
            System.out.println("No current project found. ");
            return false;
        }
        applications = serviceManager.getApplicationService().getApplications(project, ApplicationStatus.PENDING);
        if (applications.size() <= 0) {
            System.out.println("No applications found. ");
            return false;
        }
        return true;
    }

    @Override
    protected String display() {
        return serviceManager.getGenericService().displayApplication(applications)
                + ListToStringFormatter.toString(MENU);
    }

    @Override
    protected int process(int input) throws Exception {
        switch (input) {
          case 0:
              approveApplication();
              return 0;
          case 1:
              rejectApplication();
              return 0;
          case 2:
              return -1;
          default:
              throw new Exception("Please enter a valid input. ");
        }
    }

    /**
     * Invokes service classes to perform the operations required
     * to approve an application.
     *
     * @throws Exception propagated errors from service calls
     */
    private void approveApplication() throws Exception {
        BtoApplication application = getApplication();
        serviceManager.getApplicationService().approveApplication(user, application);
        System.out.println("Approve application successful!");
    }

    /**
     * Invokes service classes to perform the operations required
     * to reject an application.
     *
     * @throws Exception propagated errors from service calls
     */
    private void rejectApplication() throws Exception {
        BtoApplication application = getApplication();
        serviceManager.getApplicationService().rejectApplication(user, application);
        System.out.println("Reject application successful!");
    }

    /**
     * Requests user to select an application and invoke service class to return
     * application object.
     *
     * @throws Exception propagated errors from service calls
     */
    private BtoApplication getApplication() throws Exception {
        int index = InputHandler.getIntIndexInput("Select an application: ");
        BtoApplication application = serviceManager.getGenericService().getApplication(applications, index);
        return application;
    }
    
}
