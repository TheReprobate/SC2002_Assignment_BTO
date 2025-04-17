package btosystem.cont.hdbmanager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

import btosystem.classes.Project;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;
import btosystem.utils.HdbManagerServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;

public class HdbManagerProjectController extends HdbManagerController {
    private static final String[] MENU = {"View Projects", "View Created Projects", "View Current Project", "Create Project", "Edit Project", "Exit"};
    private static final String[] EDIT_FIELDS = {"Time", "Neighborhood", "Flat Count"};
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Pattern dateRegex = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$");
    private HdbManagerServiceManager serviceManager;

    public HdbManagerProjectController(HdbManagerServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @Override
    protected int process(int input) throws Exception {
        switch(input) {
            case 0: viewProjects(); return 0;
            case 1: viewCreatedProjects(); return 0;
            case 2: viewCurrentProject(); return 0;
            case 3: createProject(); return 0;
            case 4: editProject(); return 0;
            default: return -1;
        }
    }

    @Override
    protected String getMenu() {
        return ListToStringFormatter.toString(MENU);
    }
    
    private void viewProjects() throws Exception {
        List<Project> projects = serviceManager.getProjectService().getProject();
        Project project = getProject(projects);
        System.out.println(serviceManager.getGenericService().displayProject(project));
    }
    
    private void viewCreatedProjects() throws Exception {
        List<Project> projects = serviceManager.getProjectService().getCreatedProject(getUser());
        Project project = getProject(projects);
        System.out.println(serviceManager.getGenericService().displayProject(project));
    }

    private void viewCurrentProject() {
        Project project = serviceManager.getProjectService().getCurrentProject(getUser());
        System.out.println(serviceManager.getGenericService().displayProject(project));
    }

    private void createProject() throws Exception {
        String name = InputHandler.getStringInput("Input project name: ");
        if(serviceManager.getProjectService().projectExist(name)){
            return;
        }
        Neighborhood neighborhood = getInputNeighborhood();
        LocalDateTime start = getInputTime("start");
        LocalDateTime end = getInputTime("end");
        if(!serviceManager.getProjectService().hasValidTime(start, end)){
            return;
        }
        serviceManager.getProjectService().createProject(getUser(), name, neighborhood, start, end);
    }

    private void editProject() throws Exception {
        viewCurrentProject();
        Project project = serviceManager.getProjectService().getCurrentProject(getUser());
        System.out.println(ListToStringFormatter.toString(EDIT_FIELDS));
        int index = InputHandler.getIntIndexInput("Select a field: ");
        switch(index) {
            case 0: 
                LocalDateTime start = getInputTime("open");
                LocalDateTime end = getInputTime("close");
                if(!serviceManager.getProjectService().hasValidTime(start, end)){
                    return;
                }
                serviceManager.getProjectService().editProject(project, start, end);
            case 1: 
                Neighborhood neighborhood = getInputNeighborhood();
                serviceManager.getProjectService().editProject(project, neighborhood);
            case 2:      
                FlatType flat = getInputFlat();
                int count = InputHandler.getIntInput("Input flat count: ");
                serviceManager.getProjectService().editProject(project, flat, count);
        }

    }

    private Project getProject(List<Project> projects) throws Exception {
        System.out.println(serviceManager.getGenericService().displayProject(projects));
        int index = InputHandler.getIntIndexInput("Select a project: ");
        Project project = serviceManager.getGenericService().getProject(projects, index);
        return project;
    }

    private Neighborhood getInputNeighborhood() throws Exception {
        Neighborhood[] neighborhoods = Neighborhood.values();
        System.out.println(ListToStringFormatter.toString(neighborhoods));
        int index = InputHandler.getIntIndexInput("Select a neighborhood: ");
        if (index < 0 || index >= neighborhoods.length) {
            throw new Exception("Input out of bounds. ");
        }
        return neighborhoods[index];
    }

    private FlatType getInputFlat() throws Exception {
        FlatType[] neighborhoods = FlatType.values();
        System.out.println(ListToStringFormatter.toString(neighborhoods));
        int index = InputHandler.getIntIndexInput("Select a flat: ");
        if (index < 0 || index >= neighborhoods.length) {
            throw new Exception("Input out of bounds. ");
        }
        return neighborhoods[index];
    }

    private LocalDateTime getInputTime(String type) throws Exception {
        String prompt = String.format("Input %s date: ", type);
        String input = InputHandler.getStringInput(prompt, dateRegex);
        LocalDateTime date = LocalDateTime.parse(input, dateFormatter);
        return date;
    }
}

