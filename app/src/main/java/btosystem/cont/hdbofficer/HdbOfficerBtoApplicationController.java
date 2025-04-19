package btosystem.cont.hdbofficer;

import java.util.regex.Pattern;

import btosystem.classes.BtoApplication;
import btosystem.classes.HdbOfficer;
import btosystem.service.HdbOfficerServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;
import btosystem.utils.RegexPatterns;

public class HdbOfficerBtoApplicationController extends HdbOfficerController{
    private static final String[] MENU = {"Process Application", "Exit"};

    public HdbOfficerBtoApplicationController(HdbOfficer user, HdbOfficerServiceManager serviceManager) {
        super(user, serviceManager);
    }

    @Override
    protected boolean load() throws Exception {
        return true;
    }

    @Override
    protected String display() {
        return ListToStringFormatter.toString(MENU);
    }

    @Override
    protected int process(int input) throws Exception {
        switch(input) {
            case 0: processApplication();return 0;
            case 1: return -1;
            default: throw new Exception("Please enter a valid input. ");
        }
    }

    private void processApplication() throws Exception {
        String nric = InputHandler.getStringInput("Input Applicant's NRIC: ", RegexPatterns.NRIC);
        BtoApplication application = serviceManager.getApplicationService().getApplication(nric);
        System.out.println(serviceManager.getGenericService().displayApplication(application));
        String input = InputHandler.getStringInput("Confirm to process application [Y/N]: ", RegexPatterns.YES_NO);
        if(!(input.equals("Y") || input.equals("y"))){
            System.out.println("Process application cancelled");
            return;
        }
        serviceManager.getApplicationService().processApplication(application, user);
        System.out.println("Process application successful!");
    }
    
}
