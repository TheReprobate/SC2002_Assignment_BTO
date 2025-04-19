package btosystem.cont.applicant;

import java.util.List;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.Project;
import btosystem.classes.enums.FlatType;
import btosystem.service.ApplicantServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;
import btosystem.utils.RegexPatterns;

public class ApplicantBtoApplicationController extends ApplicantController{
    private static final String[] MENU = {"Create Application", "Withdrawal Application", "Exit"};
    private BtoApplication application;
    
    public ApplicantBtoApplicationController(Applicant user, ApplicantServiceManager serviceManager) {
        super(user, serviceManager);
    }

    @Override
    protected boolean load() throws Exception {
        application = serviceManager.getApplicationService().getApplication(user);
        if(application == null) {
            System.out.println("No active application");
        }
        return true;
    }

    @Override
    protected String display() {
        return serviceManager.getGenericService().displayApplication(application) + ListToStringFormatter.toString(MENU);
    }

    @Override
    protected int process(int input) throws Exception {
        switch(input) {
            case 0: createApplication(); return 0;
            case 1: withdrawApplication(); return 0;
            case 2: return -1;
            default: throw new Exception("Please enter a valid input. ");
        }
    }

    private void createApplication() throws Exception {
        if(application != null) {
            throw new Exception("Active application found. ");
        }
        List<Project> projects = serviceManager.getProjectService().getVisibleProjects();
        System.out.println(serviceManager.getGenericService().displayProject(projects));
        int projectIndex = InputHandler.getIntIndexInput("Select a project to apply for: ");
        Project project = serviceManager.getGenericService().getProject(projects, projectIndex);
        List<FlatType> flatTypes = serviceManager.getApplicationService().getAvailableFlatTypes(user, project);
        if(flatTypes.size() <= 0) {
            throw new Exception("No flat types available for you. ");
        }
        System.out.println(ListToStringFormatter.toString(flatTypes));
        int flatIndex = InputHandler.getIntIndexInput("Select a flat to apply for: ");
        FlatType flatType = flatTypes.get(flatIndex);
        serviceManager.getApplicationService().createApplication(user, project, flatType);
        System.out.println("Application creation success!");
    }

    private void withdrawApplication() throws Exception {
        if(application == null) {
            throw new Exception("No existing application found. ");
        }
        String input = InputHandler.getStringInput("Confirm to withdraw application? [Y/N] ", RegexPatterns.YES_NO);
        if(!(input.equals("Y") || input.equals("y"))){
            System.out.println("Application withdrawal cancelled");
            return;
        }
        serviceManager.getApplicationService().withdrawApplication(user);
        System.out.println("Application withdrawal success!");
    }
}
