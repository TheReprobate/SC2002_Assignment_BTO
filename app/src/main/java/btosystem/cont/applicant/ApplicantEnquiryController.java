package btosystem.cont.applicant;

import java.util.List;
import java.util.regex.Pattern;

import btosystem.classes.Enquiry;
import btosystem.classes.Project;
import btosystem.utils.ApplicantServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;

public class ApplicantEnquiryController extends ApplicantController{
    private static final String[] MENU = {"View Personal Enquiries", "Create Enquiry", "Edit Enquiry", "Delete Enquiry", "Exit"};
    private ApplicantServiceManager serviceManager;
    
    public ApplicantEnquiryController(ApplicantServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @Override
    protected int process(int input) throws Exception {
        switch(input) {
            case 0: viewEnquiries(); return 0;
            case 1: createEnquiry(); return 0;
            case 2: editEnquiry(); return 0;
            case 3: deleteEnquiry(); return 0;
            default: return -1;
        }
    }
    @Override
    protected String getMenu() {
        return ListToStringFormatter.toString(MENU);
    }

    private void viewEnquiries() throws Exception {
        Enquiry enquiry = getEnquiry();
        System.out.println(serviceManager.getGenericService().displayEnquiry(enquiry));
    }

    private void createEnquiry() throws Exception {
        List<Project> projects = serviceManager.getProjectService().getVisibleProjects();
        System.out.println(serviceManager.getGenericService().displayProject(projects));
        int projectIndex = InputHandler.getIntIndexInput("Select a project to enquire for: ");
        Project project = serviceManager.getGenericService().getProject(projects, projectIndex);
        String input = InputHandler.getStringInput("Input message: ");
        serviceManager.getEnquiryService().createEnquiry(getUser(), project, input);
    }
    
    private void editEnquiry() throws Exception {
        Enquiry enquiry = getEnquiry();
        if(!serviceManager.getEnquiryService().isEditable(enquiry)) {
            throw new Exception("Enquiry has reply unable to process.");
        }
        System.out.println(serviceManager.getGenericService().displayEnquiry(enquiry));
        String input = InputHandler.getStringInput("Input new message: ");
        serviceManager.getEnquiryService().editEnquiry(getUser(), enquiry, input);
    }

    private void deleteEnquiry() throws Exception {
        Enquiry enquiry = getEnquiry();
        if(!serviceManager.getEnquiryService().isEditable(enquiry)) {
            throw new Exception("Enquiry has reply unable to process.");
        }
        System.out.println(serviceManager.getGenericService().displayEnquiry(enquiry));
        String input = InputHandler.getStringInput("Confirm to delete enquiry", Pattern.compile("^[YyNn]$"));
        if(!(input == "Y" || input == "y")){
            return;
        }
        serviceManager.getEnquiryService().deleteEnquiry(getUser(), enquiry);
    }

    private Enquiry getEnquiry() throws Exception {
        List<Enquiry> personalEnquiries = serviceManager.getEnquiryService().getPersonalEnquiries(getUser());
        System.out.println(serviceManager.getGenericService().displayEnquiry(personalEnquiries));
        int index = InputHandler.getIntIndexInput("Select an enquiry: ");
        Enquiry enquiry = serviceManager.getGenericService().getEnquiry(personalEnquiries, index);
        return enquiry;
    }
}
