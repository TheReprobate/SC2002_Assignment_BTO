package btosystem.cont.applicant;

import java.util.List;
import java.util.regex.Pattern;

import btosystem.classes.BtoApplication;
import btosystem.classes.Project;
import btosystem.utils.ApplicantServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;

public class ApplicantBtoApplicationController extends ApplicantController{
    private static final String[] MENU = {"View Current Application", "Create Application", "Withdrawal Application", "Exit"};
    private ApplicantServiceManager serviceManager;
    
    public ApplicantBtoApplicationController(ApplicantServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @Override
    protected int process(int input) throws Exception {
        switch(input) {
            case 0: viewApplication(); return 0;
            case 1: createApplication(); return 0;
            case 2: withdrawApplication(); return 0;
            case 3: return -1;
            default: System.out.println("Please enter a valid input. "); return 0;
        }
    }

    private void viewApplication(){
        BtoApplication application = serviceManager.getApplicationService().getApplication(getUser());
        System.out.println(serviceManager.getGenericService().displayApplication(application));
    }

    private void createApplication() throws Exception {
        List<Project> projects = serviceManager.getProjectService().getVisibleProjects();
        System.out.println(serviceManager.getGenericService().displayProject(projects));
        int projectIndex = InputHandler.getIntIndexInput("Select a project to apply for: ");
        Project project = serviceManager.getGenericService().getProject(projects, projectIndex);
        serviceManager.getApplicationService().createApplication(getUser(), project);
    }

    private void withdrawApplication() throws Exception {
        viewApplication();
        String input = InputHandler.getStringInput("Confirm to withdraw application (Y/N)", Pattern.compile("^[YyNn]$"));
        if(!(input == "Y" || input == "y")){
            return;
        }
        serviceManager.getApplicationService().withdrawApplication(getUser());
    }
    @Override
    protected String getMenu() {
        return ListToStringFormatter.toString(MENU);
    }
}
