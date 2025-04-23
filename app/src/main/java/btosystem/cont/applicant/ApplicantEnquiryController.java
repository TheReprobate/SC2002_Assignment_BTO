package btosystem.cont.applicant;

import java.util.List;

import btosystem.classes.Applicant;
import btosystem.classes.Enquiry;
import btosystem.classes.Project;
import btosystem.service.ApplicantServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;
import btosystem.utils.RegexPatterns;

// missing filters
public class ApplicantEnquiryController extends ApplicantController{
    private static final String[] MENU = {"View Enquiry Details", "Create Enquiry", "Edit Enquiry", "Delete Enquiry", "Exit"};
    private List<Enquiry> enquiries;
    
    public ApplicantEnquiryController(Applicant user, ApplicantServiceManager serviceManager) {
        super(user, serviceManager);
    }

    @Override
    protected boolean load() throws Exception{
        enquiries = serviceManager.getEnquiryService().getPersonalEnquiries(user);
        if(enquiries.size() <= 0) {
            System.out.println("No personal enquiries found. ");
        }
        return true;
    }

    @Override
    protected String display(){
        return serviceManager.getGenericService().displayEnquiry(enquiries) + ListToStringFormatter.toString(MENU);
    }

    @Override
    protected int process(int input) throws Exception {
        switch(input) {
            case 0: viewEnquiry(); return 0;
            case 1: createEnquiry(); return 0;
            case 2: editEnquiry(); return 0;
            case 3: deleteEnquiry(); return 0;
            case 4: return -1;
            default: throw new Exception("Please enter a valid input. ");
        }
    }

    private void viewEnquiry() throws Exception {
        Enquiry enquiry = getEnquiry();
        System.out.println(serviceManager.getGenericService().displayEnquiry(enquiry));
    }

    private void createEnquiry() throws Exception {
        List<Project> projects = serviceManager.getProjectService().getVisibleProjects();
        if(projects == null || projects.size() <= 0){
            throw new Exception("No projects found. ");
        }
        System.out.println(serviceManager.getGenericService().displayProject(projects));
        int projectIndex = InputHandler.getIntIndexInput("Select a project to enquire for: ");
        Project project = serviceManager.getGenericService().getProject(projects, projectIndex);
        String input = InputHandler.getStringInput("Input message: ");
        serviceManager.getEnquiryService().createEnquiry(user, project, input);
        System.out.println("Enquiry create success!");
    }
    
    private void editEnquiry() throws Exception {
        Enquiry enquiry = getEnquiry();
        if(!serviceManager.getEnquiryService().isEditable(enquiry)) {
            throw new Exception("Enquiry has reply unable to process.");
        }
        // System.out.println(serviceManager.getGenericService().displayEnquiry(enquiry));
        String input = InputHandler.getStringInput("Input new message: ");
        serviceManager.getEnquiryService().editEnquiry(user, enquiry, input);
        System.out.println("Enquiry edit success!");
    }

    private void deleteEnquiry() throws Exception {
        Enquiry enquiry = getEnquiry();
        if(!serviceManager.getEnquiryService().isEditable(enquiry)) {
            throw new Exception("Enquiry has reply unable to process.");
        }
        System.out.println(serviceManager.getGenericService().displayEnquiry(enquiry));
        String input = InputHandler.getStringInput("Confirm to delete enquiry [Y/N]: ", RegexPatterns.YES_NO);
        if(!(input.equals("Y") || input.equals("y"))){
            System.out.println("Enquiry deletion cancelled");
            return;
        }
        serviceManager.getEnquiryService().deleteEnquiry(user, enquiry);
        System.out.println("Enquiry delete success!");
    }

    private Enquiry getEnquiry() throws Exception {
        if(enquiries.size() <= 0) {
            throw new Exception("No personal enquiries found. ");
        }
        int index = InputHandler.getIntIndexInput("Select an enquiry: ");
        Enquiry enquiry = serviceManager.getGenericService().getEnquiry(enquiries, index);
        return enquiry;
    }
}
