package btosystem.cont.applicant;

import java.util.List;

import btosystem.classes.Project;
import btosystem.cont.Controller;
import btosystem.utils.ApplicantServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;

public class ApplicantProjectController extends Controller{
    private static final String[] MENU = {"View Projects", "Exit"};
    private ApplicantServiceManager serviceManager;

    public ApplicantProjectController(ApplicantServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }
    @Override
    protected int process(int input) throws Exception {
        switch(input) {
            case 0: viewProjects(); return 0;
            default: System.out.println("Please enter a valid input. "); return 0;
        }
    }
    @Override
    protected String getMenu() {
        return ListToStringFormatter.toString(MENU);
    }
    private void viewProjects() throws Exception{
        List<Project> projects = serviceManager.getProjectService().getVisibleProjects();
        System.out.println(serviceManager.getGenericService().displayProject(projects));
        int index = InputHandler.getIntIndexInput("Select a project to view its details: ");
        Project project = serviceManager.getGenericService().getProject(projects, index);
        System.out.println(serviceManager.getGenericService().displayProject(project));
    }
}
