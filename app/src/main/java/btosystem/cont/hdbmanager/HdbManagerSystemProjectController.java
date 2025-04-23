package btosystem.cont.hdbmanager;

import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.classes.enums.Neighborhood;
import btosystem.service.HdbManagerServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;
import btosystem.utils.RegexPatterns;
import java.time.LocalDate;
import java.util.List;

/**
 * High-level controller class specific to the {@link HdbManager} role.
 * for functionality related to all projects in the system
 */

public class HdbManagerSystemProjectController extends HdbManagerProjectController {
    private static final String[] MENU = {
        "View Project Details", "View Created Projects",
        "Join Team", "Create Project", "Exit"
    };
    private List<Project> projects;

    /**
     * Constructor for the {@link HdbManager} Project (system-wide) controller.
     *
     * @param user reference to a {@link HdbManager} object
     * @param serviceManager reference to a {@link HdbManagerServiceManager}
     */

    public HdbManagerSystemProjectController(
            HdbManager user, HdbManagerServiceManager serviceManager) {
        super(user, serviceManager);
    }

    @Override
    protected boolean load() throws Exception {
        projects = serviceManager.getProjectService().getProject();
        if (projects == null) {
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
              viewCreatedProjects();
              return 0;
          case 2:
              joinTeam();
              return 0;
          case 3:
              createProject();
              return 0;
          case 4:
              return -1;
          default: throw new Exception("Please enter a valid input. ");
        }
    }

    /**
     * Invokes service class to retrieve projects created by current manager
     * and displays them.
     *
     * @throws Exception propagated errors from service calls
     */
    private void viewCreatedProjects() throws Exception {
        List<Project> createdProjects = serviceManager.getProjectService().getCreatedProject(user);
        if (createdProjects.size() <= 0) {
            System.out.println("No created projects found. ");
        }
        System.out.println(serviceManager.getGenericService().displayProject(createdProjects));
    }

    /**
     * Invokes service classes to perform operations for creating
     * a project, including gathering user input.
     *
     * @throws Exception propagated errors from service calls
     */
    private void createProject() throws Exception {
        String name = InputHandler.getStringInput("Input project name: ");
        if (serviceManager.getProjectService().projectExist(name)) {
            return;
        }
        Neighborhood neighborhood = getInputNeighborhood();
        LocalDate start = getInputTime("start");
        LocalDate end = getInputTime("end");
        if (!serviceManager.getProjectService().hasValidTime(start, end)) {
            return;
        }
        serviceManager.getProjectService().createProject(user, name, neighborhood, start, end);
        System.out.println("Project creation successful!");
    }

    /**
     * Invokes service classes to perform operations for joining a team.
     *
     * @throws Exception propagated errors from service calls
     */
    private void joinTeam() throws Exception {
        Project project = getProject();
        String input = InputHandler.getStringInput(
                "Confirm to join team [Y/N]: ", RegexPatterns.YES_NO);
        if (!(input.equals("Y") || input.equals("y"))) {
            return;
        }
        serviceManager.getTeamService().joinTeam(user, project);
        System.out.println("User has joined the team!");
    }

    /**
     * Pick a project to be viewed.
     *
     * @throws Exception propagated errors from service calls or user not involved in any projects
     */

    private void viewProject() throws Exception {
        Project project = getProject();
        super.viewProject(project);
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

    // private void deleteProject() {}; // not implemented
}
