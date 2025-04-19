package btosystem.cont.hdbmanager;

import java.time.LocalDate;
import java.util.List;

import btosystem.classes.Enquiry;
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
    private static final String[] MENU = {"View Project Details", "Approve Officer Registration", "Reject Officer Registration", "Edit Project", "Exit"};
    private static final String[] EDIT_FIELDS = {"Time", "Neighborhood", "Flat Count", "Exit"};
    private Project project;
    private List<OfficerRegistration> registrations;

    public HdbManagerCurrentProjectController(HdbManager user, HdbManagerServiceManager serviceManager) {
        super(user, serviceManager);
    }

    @Override
    protected boolean load() throws Exception {
        project = serviceManager.getProjectService().getCurrentProject(user);
        if(project == null) {
            System.out.println("No current project found, join a team. ");
            return false;
        }
        registrations = serviceManager.getTeamService().getRegistrations(project, RegistrationStatus.PENDING);
        return true;
    }

    @Override
    protected String display() {
        return serviceManager.getGenericService().displayProject(project) + ListToStringFormatter.toString(MENU);
    }

    @Override
    protected int process(int input) throws Exception {
        switch(input) {
            case 0: viewProject(); return 0;
            case 1: approveRegistration(); return 0;
            case 2: rejectRegistration(); return 0;
            case 3: editProject(); return 0;
            case 4: return -1;
            default: throw new Exception("Please enter a valid input. ");
        }
    }

    private void viewProject() throws Exception {
        super.viewProject(project);
    }

    private void approveRegistration() throws Exception {
        OfficerRegistration registration = getRegistration();
        String input = InputHandler.getStringInput("Confirm to approve registration [Y/N]: ", RegexPatterns.YES_NO);
        if(!(input.equals("Y") || input.equals("y"))){
            return;
        }
        ProjectTeam team = serviceManager.getTeamService().getProjectTeam(project);
        serviceManager.getTeamService().approveRegistration(user, team, registration);
        System.out.println("Registration Approved!");
    }

    private void rejectRegistration() throws Exception {
        OfficerRegistration registration = getRegistration();
        String input = InputHandler.getStringInput("Confirm to reject registration [Y/N]: ", RegexPatterns.YES_NO);
        if(!(input.equals("Y") || input.equals("y"))){
            return;
        }
        ProjectTeam team = serviceManager.getTeamService().getProjectTeam(project);
        serviceManager.getTeamService().rejectRegistration(user, team, registration);
        System.out.println("Registration Rejected!");
    }


    private void editProject() throws Exception {
        if(project == null) {
            throw new Exception("No current project found. ");
        }
        Project project = serviceManager.getProjectService().getCurrentProject(user);
        System.out.println(ListToStringFormatter.toString(EDIT_FIELDS));
        int index = InputHandler.getIntIndexInput("Select a field: ");
        switch(index) {
            case 0: 
                LocalDate start = getInputTime("open");
                LocalDate end = getInputTime("close");
                if(!serviceManager.getProjectService().hasValidTime(start, end)){
                    return;
                }
                serviceManager.getProjectService().editProject(project, start, end);
                System.out.println("Time edit successful");
                break;
            case 1: 
                Neighborhood neighborhood = getInputNeighborhood();
                serviceManager.getProjectService().editProject(project, neighborhood);
                System.out.println("Neighborhood edit successful");
                break;
            case 2:      
                FlatType flat = getInputFlat();
                int count = InputHandler.getIntInput("Input flat count: ");
                serviceManager.getProjectService().editProject(project, flat, count);
                System.out.println("Flat count edit successful");
                break;
            case 3:
                break;
            default:
                throw new Exception("Option does not exist. ");
        }
    }
    private OfficerRegistration getRegistration() throws Exception {
        System.out.println(serviceManager.getGenericService().displayRegistration(registrations));
        int index = InputHandler.getIntIndexInput("Select a registration: ");
        OfficerRegistration registration = serviceManager.getGenericService().getRegistration(registrations, index);
        return registration;
    }
}
