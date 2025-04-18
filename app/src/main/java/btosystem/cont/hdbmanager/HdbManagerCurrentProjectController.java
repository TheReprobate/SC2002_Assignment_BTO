package btosystem.cont.hdbmanager;

import java.time.LocalDate;

import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;
import btosystem.service.HdbManagerServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;

// missing - view enquiry/ view applications/ view team... + filters
// missing - delete project
public class HdbManagerCurrentProjectController extends HdbManagerProjectController {
    private static final String[] MENU = {"Edit Project", "Exit"};
    private static final String[] EDIT_FIELDS = {"Time", "Neighborhood", "Flat Count", "Exit"};
    private HdbManagerServiceManager serviceManager;
    private Project project;

    public HdbManagerCurrentProjectController(HdbManager user, HdbManagerServiceManager serviceManager) {
        super(user);
        this.serviceManager = serviceManager;
    }

    @Override
    protected boolean load() throws Exception {
        project = serviceManager.getProjectService().getCurrentProject(getUser());
        if(project == null) {
            System.out.println("No current project found. ");
            return false;
        }
        return true;
    }

    @Override
    protected String display() {
        return serviceManager.getGenericService().displayProject(project) + ListToStringFormatter.toString(MENU);
    }

    @Override
    protected int process(int input) throws Exception {
        switch(input) {
            case 0: editProject(); return 0;
            case 1: return -1;
            default: throw new Exception("Please enter a valid input. ");
        }
    }


    private void editProject() throws Exception {
        if(project == null) {
            throw new Exception("No current project found. ");
        }
        Project project = serviceManager.getProjectService().getCurrentProject(getUser());
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
    
}
