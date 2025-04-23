package btosystem.cont.hdbofficer;

import java.time.LocalDate;
import java.util.List;

import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.service.HdbOfficerServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;
import btosystem.utils.RegexPatterns;

public class HdbOfficerProjectController extends HdbOfficerController{
    private static final String[] MENU = {"Register for project", "Exit"};
    private List<Project> projects;

    public HdbOfficerProjectController(HdbOfficer user, HdbOfficerServiceManager serviceManager) {
        super(user, serviceManager);
    }

    @Override
    protected boolean load() throws Exception {
        projects = serviceManager.getProjectService().getProjects(null, LocalDate.now());
        if(projects == null || projects.size() <= 0) {
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
            case 0: registerProject();return 0;
            case 1: return -1;
            default: throw new Exception("Please enter a valid input. ");
        }
    }

    private void registerProject() throws Exception {
        Project project = getProject();
        String input = InputHandler.getStringInput("Confirm to register for project? [Y/N]: ", RegexPatterns.YES_NO);
        if(!(input.equals("Y") || input.equals("y"))){
            System.out.println("Project registration cancelled.");
            return;
        }
        serviceManager.getTeamService().createRegistration(user, project);
        System.out.println("Registration Success!");
    }

    private Project getProject() throws Exception {
        int index = InputHandler.getIntIndexInput("Select a project: ");
        Project project = serviceManager.getGenericService().getProject(projects, index);
        return project;
    }
    
}
