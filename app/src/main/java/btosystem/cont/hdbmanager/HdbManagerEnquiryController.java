package btosystem.cont.hdbmanager;

import java.util.ArrayList;
import java.util.List;

import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.service.HdbManagerServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;

public class HdbManagerEnquiryController extends HdbManagerController {
    private static final String[] MENU = {"Reply Enquiry", "Exit"};
    private List<Project> projects;
    private List<Enquiry> enquiries;
    public HdbManagerEnquiryController(HdbManager user, HdbManagerServiceManager serviceManager) {
        super(user, serviceManager);
    }

    @Override
    protected boolean load() throws Exception {
        projects = serviceManager.getProjectService().getCurrentProject(user);
        if(projects == null || projects.size() <= 0) {
            throw new Exception("No current project found, join a team. ");
        }
        enquiries = new ArrayList<>();
        for(Project p: projects) {
            enquiries.addAll(serviceManager.getEnquiryService().getEnquiries(p, false));
        }
        if(enquiries.size() <= 0) {
            throw new Exception("No repliable enquiries found. ");
        }
        return true;
    }
    
    @Override
    protected String display() {
        return serviceManager.getGenericService().displayEnquiry(enquiries) + ListToStringFormatter.toString(MENU);
    }

    @Override
    protected int process(int input) throws Exception {
        switch(input) {
            case 0: replyEnquiry();return 0;
            case 1: return -1;
            default: throw new Exception("Please enter a valid input. ");
        }
    }

    private void replyEnquiry() throws Exception {
        Enquiry enquiry = getEnquiry();
        String reply = InputHandler.getStringInput("Input reply: ");
        serviceManager.getEnquiryService().replyEnquiry(user, enquiry, reply);
        System.out.println("Enquiry reply successful!");
    }

    private Enquiry getEnquiry() throws Exception {
        int index = InputHandler.getIntIndexInput("Select an enquiry: ");
        Enquiry enquiry = serviceManager.getGenericService().getEnquiry(enquiries, index);
        return enquiry;
    }
}
