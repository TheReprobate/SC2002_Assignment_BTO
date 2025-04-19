package btosystem.cont.hdbmanager;

import java.util.List;

import btosystem.classes.BtoApplication;
import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.classes.enums.ApplicationStatus;
import btosystem.service.HdbManagerServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;

public class HdbManagerBtoApplicationController extends HdbManagerController{
    private static final String[] MENU = {"Approve Application", "Reject Application", "Exit"};
    private Project project;
    private List<BtoApplication> applications;

    public HdbManagerBtoApplicationController(HdbManager user, HdbManagerServiceManager serviceManager) {
        super(user, serviceManager);
    }

    @Override
    protected boolean load() throws Exception {
        project = serviceManager.getProjectService().getCurrentProject(user);
        if(project == null) {
            System.out.println("No current project found. ");
            return false;
        }
        applications = serviceManager.getApplicationService().getApplications(project, ApplicationStatus.PENDING);
        if(applications.size() <= 0) {
            System.out.println("No applications found. ");
            return false;
        }
        return true;
    }

    @Override
    protected String display() {
        return serviceManager.getGenericService().displayApplication(applications) + ListToStringFormatter.toString(MENU);
    }

    @Override
    protected int process(int input) throws Exception {
        switch(input) {
            case 0: approveApplication();return 0;
            case 1: rejectApplication();return 0;
            case 2: return -1;
            default: throw new Exception("Please enter a valid input. ");
        }
    }


    private void approveApplication() throws Exception {
        BtoApplication application = getApplication();
        serviceManager.getApplicationService().approveApplication(user, application);
        System.out.println("Approve application successful!");
    }

    private void rejectApplication() throws Exception {
        BtoApplication application = getApplication();
        serviceManager.getApplicationService().rejectApplication(user, application);
        System.out.println("Reject application successful!");
    }

    private BtoApplication getApplication() throws Exception {
        int index = InputHandler.getIntIndexInput("Select an application: ");
        BtoApplication application = serviceManager.getGenericService().getApplication(applications, index);
        return application;
    }
    
}
