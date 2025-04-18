package btosystem.cont.hdbofficer;

import java.util.List;

import btosystem.classes.Enquiry;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.service.HdbOfficerServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;

public class HdbOfficerEnquiryController extends HdbOfficerController{
    private static final String[] MENU = {"Reply Enquiry", "Exit"};
    private HdbOfficerServiceManager serviceManager;
    private Project project;
    private List<Enquiry> enquiries;
    public HdbOfficerEnquiryController(HdbOfficer user, HdbOfficerServiceManager serviceManager) {
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
        enquiries = serviceManager.getProjectService().getEnquiries(project, false);
        if(enquiries.size() <= 0) {
            System.out.println("No enquiries found. ");
            return false;
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
        serviceManager.getEnquiryService().replyEnquiry(getUser(), enquiry, reply);
        System.out.println("Enquiry reply successful!");
    }

    private Enquiry getEnquiry() throws Exception {
        int index = InputHandler.getIntIndexInput("Select an enquiry: ");
        Enquiry enquiry = serviceManager.getGenericService().getEnquiry(enquiries, index);
        return enquiry;
    }
}
