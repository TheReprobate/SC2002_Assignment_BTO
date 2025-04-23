package btosystem.cont.hdbmanager;

import java.time.LocalDate;
import java.util.List;

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

// missing - view enquiry/ view applications/ view team... + filters
// missing - delete project
public class HdbManagerCurrentProjectController extends HdbManagerProjectController {
    private static final String[] MENU = {"View Project Details", "Approve Officer Registration", "Reject Officer Registration", "Edit Project", "Delete Project", "Exit"};
    private static final String[] EDIT_FIELDS = {"Neighborhood", "Flat Count", "Visibility", "Exit"};
    private List<Project> projects;

    public HdbManagerCurrentProjectController(HdbManager user, HdbManagerServiceManager serviceManager) {
        super(user, serviceManager);
    }

    @Override
    protected boolean load() throws Exception {
        projects = serviceManager.getProjectService().getCurrentProject(user);
        if(projects == null || projects.size() <= 0) {
            throw new Exception("No current project found, join a team. ");
        }
        return true;
    }

    @Override
    protected String display() {
        return serviceManager.getGenericService().displayProject(projects) + ListToStringFormatter.toString(MENU);
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

    private void viewProject() throws Exception {
        Project project = getProject();
        super.viewProject(project);
    }

    private void approveRegistration() throws Exception {
        Project project = getProject();
        OfficerRegistration registration = getRegistration(project);
        String input = InputHandler.getStringInput("Confirm to approve registration [Y/N]: ", RegexPatterns.YES_NO);
        if (!(input.equals("Y") || input.equals("y"))) {
            return;
        }
        ProjectTeam team = serviceManager.getTeamService().getProjectTeam(project);
        serviceManager.getTeamService().approveRegistration(user, team, registration);
        System.out.println("Registration Approved!");
    }

    private void rejectRegistration() throws Exception {
        Project project = getProject();
        OfficerRegistration registration = getRegistration(project);
        String input = InputHandler.getStringInput("Confirm to reject registration [Y/N]: ", RegexPatterns.YES_NO);
        if (!(input.equals("Y") || input.equals("y"))) {
            return;
        }
        ProjectTeam team = serviceManager.getTeamService().getProjectTeam(project);
        serviceManager.getTeamService().rejectRegistration(user, team, registration);
        System.out.println("Registration Rejected!");
    }


    private void editProject() throws Exception {
        Project project = getProject();
        System.out.println(ListToStringFormatter.toString(EDIT_FIELDS));
        int index = InputHandler.getIntIndexInput("Select a field: ");
        switch(index) {
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
                String input = InputHandler.getStringInput("Turn visibility on? [Y=on/N=off]: ", RegexPatterns.YES_NO);
                if(!(input.equals("Y") || input.equals("y"))){
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
    private OfficerRegistration getRegistration(Project project) throws Exception {
        List<OfficerRegistration> registrations = serviceManager.getTeamService().getRegistrations(project, RegistrationStatus.PENDING);
        if(registrations == null || registrations.size() <= 0) {
            throw new Exception("No officer registrations found in this project. ");
        }
        System.out.println(serviceManager.getGenericService().displayRegistration(registrations));
        int index = InputHandler.getIntIndexInput("Select a registration: ");
        OfficerRegistration registration = serviceManager.getGenericService()
                                                        .getRegistration(registrations, index);
        return registration;
    }

    private void deleteProject() throws Exception {
        Project project = getProject();
        String input = InputHandler.getStringInput("Confirm to delete project [Y/N]: ", RegexPatterns.YES_NO);
        if(!(input.equals("Y") || input.equals("y"))){
            return;
        }
        serviceManager.getProjectService().deleteProject(user, project);
    }

    private Project getProject() throws Exception {
        int index = InputHandler.getIntIndexInput("Select a project: ");
        Project project = serviceManager.getGenericService().getProject(projects, index);
        return project;
    }
}
