package btosystem.cont.hdbmanager;

import java.time.LocalDate;
import java.util.List;

import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.classes.enums.Neighborhood;
import btosystem.service.HdbManagerServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;
import btosystem.utils.RegexPatterns;

public class HdbManagerSystemProjectController extends HdbManagerProjectController {
    private static final String[] MENU = {"View Project Details", "View Created Projects", "Join Team", "Create Project", "Exit"};
    private List<Project> projects;

    public HdbManagerSystemProjectController(HdbManager user, HdbManagerServiceManager serviceManager) {
        super(user, serviceManager);
    }

    @Override
    protected boolean load() throws Exception {
        projects = serviceManager.getProjectService().getProject();
        if(projects == null || projects.size() <= 0) {
            System.out.println("No projects found. ");
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
            case 2: joinTeam(); return 0;
            case 3: createProject(); return 0;
            case 4: return -1;
            default: throw new Exception("Please enter a valid input. ");
        }
    }
    
    private void viewCreatedProjects() throws Exception {
        if(projects == null || projects.size() <= 0) {
            throw new Exception("No projects found. ");
        }
        List<Project> createdProjects = serviceManager.getProjectService().getCreatedProject(user);
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
        serviceManager.getProjectService().createProject(user, name, neighborhood, start, end);
        System.out.println("Project creation successful!");
    }

    private void joinTeam() throws Exception {
        if(projects == null || projects.size() <= 0) {
            throw new Exception("No projects found. ");
        }
        Project project = getProject();
        String input = InputHandler.getStringInput("Confirm to join team [Y/N]: ", RegexPatterns.YES_NO);
        if(!(input.equals("Y") || input.equals("y"))){
            return;
        }
        serviceManager.getTeamService().joinTeam(user, project);
        System.out.println("User has joined the team!");
    }

    private void viewProject() throws Exception {
        if(projects == null || projects.size() <= 0) {
            throw new Exception("No projects found. ");
        }
        Project project = getProject();
        super.viewProject(project);
    }

    private Project getProject() throws Exception {
        int index = InputHandler.getIntIndexInput("Select a project: ");
        Project project = serviceManager.getGenericService().getProject(projects, index);
        return project;
    }
}
