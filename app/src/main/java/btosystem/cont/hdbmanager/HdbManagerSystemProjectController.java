package btosystem.cont.hdbmanager;

import java.time.LocalDate;
import java.util.List;

import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.classes.enums.Neighborhood;
import btosystem.service.HdbManagerServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;

public class HdbManagerSystemProjectController extends HdbManagerProjectController {
    private static final String[] MENU = {"View Project Details", "View Created Projects", "Create Project", "Exit"};
    private static final String[] PROJECT_MENU = {"View Enquiries", "View Applications", "View Project Team", "Exit"};
    private HdbManagerServiceManager serviceManager;
    private List<Project> projects;

    public HdbManagerSystemProjectController(HdbManager user, HdbManagerServiceManager serviceManager) {
        super(user);
        this.serviceManager = serviceManager;
    }

    @Override
    protected boolean load() throws Exception {
        projects = serviceManager.getProjectService().getProject();
        if(projects == null) {
            throw new Exception("No projects found. ");
        }
        return true;
    }

    @Override
    protected String display() {
        return serviceManager.getGenericService().displayProject(projects) + ListToStringFormatter.toString(MENU);
    }

    @Override
    protected int process(int input) throws Exception {
        switch(input) {
            case 0: viewProject(); return 0;
            case 1: viewCreatedProjects(); return 0;
            case 2: createProject(); return 0;
            case 3: return -1;
            default: throw new Exception("Please enter a valid input. ");
        }
    }

    private void viewProject() throws Exception {
        Project project = getProject();
        System.out.println(serviceManager.getGenericService().displayProject(project));
        System.out.println(ListToStringFormatter.toString(PROJECT_MENU));
        int input = InputHandler.getIntIndexInput("Select an option: ");
        switch(input) {
            case 0:
                List<Enquiry> enquiries = serviceManager.getProjectService().getEnquiries(project);
                if(enquiries.size() <= 0) {
                    System.out.println("No enquiries found. ");
                    break;
                }
                System.out.println(serviceManager.getGenericService().displayEnquiry(enquiries));
                break;
            case 1:
                List<BtoApplication> applications = serviceManager.getProjectService().getApplications(project);
                if(applications.size() <= 0) {
                    System.out.println("No enquiries found. ");
                    break;
                }
                System.out.println(serviceManager.getGenericService().displayApplication(applications));
                break;
            case 2:
                // not implemented
            case 3:
                break;
            default:
                throw new Exception("Option does not exist. ");
        }
    }
    
    private void viewCreatedProjects() throws Exception {
        List<Project> createdProjects = serviceManager.getProjectService().getCreatedProject(getUser());
        if(createdProjects.size() <= 0) {
            System.out.println("No created projects found. ");
        }
        System.out.println(serviceManager.getGenericService().displayProject(createdProjects));
    }

    private void createProject() throws Exception {
        String name = InputHandler.getStringInput("Input project name: ");
        if(serviceManager.getProjectService().projectExist(name)){
            return;
        }
        Neighborhood neighborhood = getInputNeighborhood();
        LocalDate start = getInputTime("start");
        LocalDate end = getInputTime("end");
        if(!serviceManager.getProjectService().hasValidTime(start, end)){
            return;
        }
        serviceManager.getProjectService().createProject(getUser(), name, neighborhood, start, end);
        System.out.println("Project creation successful!");
    }

    private Project getProject() throws Exception {
        int index = InputHandler.getIntIndexInput("Select a project: ");
        Project project = serviceManager.getGenericService().getProject(projects, index);
        return project;
    }

    private void deleteProject() {}; // not implemented
}
