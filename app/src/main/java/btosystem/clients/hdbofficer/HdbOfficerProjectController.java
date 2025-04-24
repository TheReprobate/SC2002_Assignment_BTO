package btosystem.clients.hdbofficer;

import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.service.HdbOfficerServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;
import btosystem.utils.RegexPatterns;
import java.time.LocalDate;
import java.util.List;

/**
 * High-level controller class specific to the {@link HdbOfficer} role
 * handling Project related functionality.
 */

public class HdbOfficerProjectController extends HdbOfficerController {
    private static final String[] MENU = {
        "View project", "View Participating Project", "Register for project", "Exit"
    };
    private List<Project> projects;

    /**
     * Constructor for the {@link HdbOfficer} Project controller.
     *
     * @param user reference to a {@link HdbOfficer} object
     * @param serviceManager reference to a {@link HdbOfficerServiceManager}
     */
    public HdbOfficerProjectController(HdbOfficer user, HdbOfficerServiceManager serviceManager) {
        super(user, serviceManager);
    }

    /**
     * Retrieves the project's that have started from today.
     */
    @Override
    protected boolean load() throws Exception {
        projects = serviceManager.getProjectService().getProjects(null, LocalDate.now());
        if (projects == null || projects.size() <= 0) {
            throw new Exception("No projects found. ");
        }
        return true;
    }

    @Override
    protected String display() {
        return serviceManager.getGenericService().displayProject(projects)
                + ListToStringFormatter.toString(MENU);
    }

    @Override
    protected int process(int input) throws Exception {
        switch (input) {
          case 0:
              viewProject();
              return 0;
          case 1:
              viewCurrentProjects();
              return 0;
          case 2:
              registerProject();
              return 0;
          case 3: 
              return -1;
          default: 
              throw new Exception("Please enter a valid input. ");
        }
    }
    
    /**
     * Invokes service classes to perform the operations required
     * to view more details about a project.
     *
     * @throws Exception propagated errors from service calls
     */
    private void viewProject() throws Exception {
        Project project = getProject();
        System.out.println(serviceManager.getGenericService().displayProject(project));
    }

    /**
     * Invokes service classes to perform the operations required
     * to register for a project.
     *
     * @throws Exception propagated errors from service calls
     */
    private void registerProject() throws Exception {
        Project project = getProject();
        String input = InputHandler.getStringInput(
            "Confirm to register for project? [Y/N]: ", RegexPatterns.YES_NO);
        if (!(input.equals("Y") || input.equals("y"))) {
            System.out.println("Project registration cancelled.");
            return;
        }
        serviceManager.getTeamService().createRegistration(user, project);
        System.out.println("Registration Success!");
    }

    private void viewCurrentProjects() throws Exception {
        List<Project> projects = serviceManager.getProjectService().getWorkingProject(user);
        if (projects.size() <= 0 || projects == null) {
            throw new Exception("No participating projects. ");
        }
        System.out.println(serviceManager.getGenericService().displayProject(projects));
    }

    /**
     * Requests user to pick a project and invoke service class to return
     * project object.
     *
     * @throws Exception propagated errors from service calls
     */
    private Project getProject() throws Exception {
        int index = InputHandler.getIntIndexInput("Select a project: ");
        Project project = serviceManager.getGenericService().getProject(projects, index);
        return project;
    }
    
}
