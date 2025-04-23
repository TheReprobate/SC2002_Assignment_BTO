package btosystem.cont.applicant;

import btosystem.classes.Applicant;
import btosystem.classes.Project;
import btosystem.service.ApplicantServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;
import java.util.List;

// missing - filters
/**
 * High-level controller class specific to the {@link Applicant} role
 * handling Project related functionality.
 */
public class ApplicantProjectController extends ApplicantController {
    private static final String[] MENU = {
        "View Project Details", "Exit"
    };
    private List<Project> projects;

    /**
     * Constructor for the {@link Applicant} Project controller.
     *
     * @param user reference to a {@link Applicant} object
     * @param serviceManager reference to a {@link ApplicantServiceManager}
     */
    public ApplicantProjectController(Applicant user, ApplicantServiceManager serviceManager) {
        super(user, serviceManager);
    }

    @Override
    protected boolean load() throws Exception {
        projects = serviceManager.getProjectService().getVisibleProjects();
        if (projects.size() <= 0) {
            System.out.println("No projects found. ");
            return false;
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
              return -1;
          default:
              throw new Exception("Please enter a valid input. ");
        }
    }

    /**
     * Invokes service class to display a project.
     *
     * @throws Exception propagated errors from service calls
     */
    private void viewProject() throws Exception {
        Project project = getProject();
        System.out.println(serviceManager.getGenericService().displayProject(project));
    }

    /**
     * Requests user to pick a project and invoke service class to return
     * project object.
     *
     * @throws Exception propagated errors from service calls or user not involved in any projects
     */

    private Project getProject() throws Exception {
        if (projects.size() <= 0) {
            throw new Exception("No related projects found. ");
        }
        int index = InputHandler.getIntIndexInput("Select a project: ");
        return serviceManager.getGenericService().getProject(projects, index);
    }
}
