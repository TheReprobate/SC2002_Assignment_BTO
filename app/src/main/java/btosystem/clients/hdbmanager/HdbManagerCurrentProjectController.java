package btosystem.clients.hdbmanager;

import btosystem.classes.HdbManager;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;
import btosystem.classes.enums.RegistrationStatus;
import btosystem.service.HdbManagerServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;
import btosystem.utils.RegexPatterns;
import java.util.List;

/**
 * High-level controller class specific to the {@link HdbManager} role
 * handling functionality related to the HDB Manager's current project.
 * This class extends {@link HdbManagerProjectController} and provides
 * actions such as viewing project details, approving/rejecting officer
 * registrations, editing project information, and deleting the current project.
 */
public class HdbManagerCurrentProjectController extends HdbManagerProjectController {
    private static final String[] MENU = {"View Project Details", 
                                          "Approve Officer Registration", 
                                          "Reject Officer Registration", 
                                          "Edit Project", "Delete Project", "Exit"};
    private static final String[] EDIT_FIELDS = {"Neighborhood", 
                                                 "Flat Count", "Visibility", "Exit"};
    private List<Project> projects;

    /**
     * Constructor for the {@link HdbManagerCurrentProjectController}.
     *
     * @param user           reference to the logged-in {@link HdbManager} user.
     * @param serviceManager reference to the {@link HdbManagerServiceManager}
     *                      for accessing business logic.
     */
    public HdbManagerCurrentProjectController(HdbManager user, 
                                            HdbManagerServiceManager serviceManager) {
        super(user, serviceManager);
    }

    @Override
    protected boolean load() throws Exception {
        projects = serviceManager.getProjectService().getCurrentProject(user);
        if (projects == null || projects.size() <= 0) {
            throw new Exception("No current project found, join a team. ");
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
              approveRegistration(); 
              return 0;
          case 2: 
              rejectRegistration(); 
              return 0;
          case 3: 
              editProject(); 
              return 0;
          case 4: 
              deleteProject();
              return -1;
          case 5: 
              return -1;
          default: 
              throw new Exception("Please enter a valid input. ");
        }
    }

    /**
     * Allows the HDB Manager to view the details of a selected project.
     * This method retrieves the project based on user input and then calls
     * the parent class's {@link #viewProject(Project)} method to display
     * further options related to the project.
     *
     * @throws Exception if there's an error in getting the project or during the viewing process.
     */
    private void viewProject() throws Exception {
        Project project = getProject();
        super.viewProject(project);
    }

    /**
     * Allows the HDB Manager to approve a pending officer registration for the
     * current project. This method prompts the user to select a registration,
     * confirms the approval, and then updates the registration status through
     * the service layer.
     *
     * @throws Exception if there are no pending registrations, if the user
     *                  provides invalid input, 
     *                  or if an error occurs during the approval process.
     */
    private void approveRegistration() throws Exception {
        Project project = getProject();
        OfficerRegistration registration = getRegistration(project);
        String input = InputHandler
                    .getStringInput("Confirm to approve registration [Y/N]: ", 
                    RegexPatterns.YES_NO);
        if (!(input.equals("Y") || input.equals("y"))) {
            return;
        }
        ProjectTeam team = serviceManager.getTeamService().getProjectTeam(project);
        serviceManager.getTeamService().approveRegistration(user, team, registration);
        System.out.println("Registration Approved!");
    }

    /**
     * Allows the HDB Manager to reject a pending officer registration for the
     * current project. This method prompts the user to select a registration,
     * confirms the rejection, and then updates the registration status through
     * the service layer.
     *
     * @throws Exception if there are no pending registrations, if the user
     *                  provides invalid input, 
     *                  or if an error occurs during the rejection process.
     */
    private void rejectRegistration() throws Exception {
        Project project = getProject();
        OfficerRegistration registration = getRegistration(project);
        String input = InputHandler
                    .getStringInput("Confirm to reject registration [Y/N]: ", 
                    RegexPatterns.YES_NO);
        if (!(input.equals("Y") || input.equals("y"))) {
            return;
        }
        ProjectTeam team = serviceManager.getTeamService().getProjectTeam(project);
        serviceManager.getTeamService().rejectRegistration(user, team, registration);
        System.out.println("Registration Rejected!");
    }

    /**
     * Allows the HDB Manager to edit certain details of the current project,
     * such as the neighborhood, flat count for a specific flat type, or the
     * project's visibility status.
     *
     * @throws Exception if the user provides invalid input or if an error
     *                  occurs during the project editing process.
     */
    private void editProject() throws Exception {
        Project project = getProject();
        System.out.println(ListToStringFormatter.toString(EDIT_FIELDS));
        int index = InputHandler.getIntIndexInput("Select a field: ");
        switch (index) {
          case 0: 
              Neighborhood neighborhood = getInputNeighborhood();
              serviceManager.getProjectService().editProject(project, neighborhood);
              System.out.println("Neighborhood edit successful");
              break;
          case 1:      
              FlatType flat = getInputFlat();
              int count = InputHandler.getIntInput("Input flat count: ");
              serviceManager.getProjectService().editProject(project, flat, count);
              System.out.println("Flat count edit successful");
              break;
          case 2:
              String input = InputHandler.getStringInput(
                  "Turn visibility on? [Y=on/N=off]: ", RegexPatterns.YES_NO);
              if (!(input.equals("Y") || input.equals("y"))) {
                  serviceManager.getProjectService().editProject(project, false);
                  break;
              }
              serviceManager.getProjectService().editProject(project, true);
              break;
          case 3:
              break;
          default:
              throw new Exception("Option does not exist. ");
        }
    }

    /**
     * Retrieves a specific pending officer registration for the given project
     * based on user selection.
     *
     * @param project the {@link Project} for which to retrieve registrations.
     * @return the selected {@link OfficerRegistration} object.
     * @throws Exception if no pending registrations are found or if the user
     *                   provides an invalid selection.
     */
    private OfficerRegistration getRegistration(Project project) throws Exception {
        List<OfficerRegistration> registrations = serviceManager.getTeamService()
                                                                .getRegistrations(project, 
                                                                RegistrationStatus.PENDING);
        if (registrations == null || registrations.size() <= 0) {
            throw new Exception("No officer registrations found in this project. ");
        }
        System.out.println(serviceManager.getGenericService().displayRegistration(registrations));
        int index = InputHandler.getIntIndexInput("Select a registration: ");
        OfficerRegistration registration = serviceManager.getGenericService()
                                                        .getRegistration(registrations, index);
        return registration;
    }

    /**
     * Allows the HDB Manager to delete the current project. This method prompts
     * for confirmation before proceeding with the deletion through the service layer.
     *
     * @throws Exception if the user provides invalid input or if an error occurs
     *                      during the project deletion process.
     */
    private void deleteProject() throws Exception {
        Project project = getProject();
        String input = InputHandler.getStringInput(
                        "Confirm to delete project [Y/N]: ", RegexPatterns.YES_NO);
        if (!(input.equals("Y") || input.equals("y"))) {
            return;
        }
        serviceManager.getProjectService().deleteProject(user, project);
    }

    /**
     * Retrieves a specific project from the list of current projects based on
     * user selection.
     *
     * @return the selected {@link Project} object.
     * @throws Exception if the user provides an invalid selection.
     */
    private Project getProject() throws Exception {
        int index = InputHandler.getIntIndexInput("Select a project: ");
        Project project = serviceManager.getGenericService().getProject(projects, index);
        return project;
    }
}
