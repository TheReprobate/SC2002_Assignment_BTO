package btosystem.cont.applicant;

import java.util.List;

import btosystem.classes.Applicant;
import btosystem.classes.Project;
import btosystem.service.ApplicantServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;

// missing - filters
public class ApplicantProjectController extends ApplicantController{
    private static final String[] MENU = {"View Project Details", "Exit"};
    private ApplicantServiceManager serviceManager;
    private List<Project> projects;

    public ApplicantProjectController(Applicant user, ApplicantServiceManager serviceManager) {
        super(user);
        this.serviceManager = serviceManager;
    }

    @Override
    protected boolean load() throws Exception{
        projects = serviceManager.getProjectService().getVisibleProjects();
        if(projects.size() <= 0) {
            System.out.println("No projects found. ");
            return false;
        }
        return true;
    }

    @Override
    protected String display(){
        return serviceManager.getGenericService().displayProject(projects) + ListToStringFormatter.toString(MENU);
    }

    @Override
    protected int process(int input) throws Exception {
        switch(input) {
            case 0: viewProject(); return 0;
            case 1: return -1;
            default: throw new Exception("Please enter a valid input. ");
        }
    }

    private void viewProject() throws Exception {
        Project project = getProject();
        System.out.println(serviceManager.getGenericService().displayProject(project));
    }

    private Project getProject() throws Exception {
        if(projects.size() <= 0) {
            throw new Exception("No personal enquiries recorded. ");
        }
        int index = InputHandler.getIntIndexInput("Select a project: ");
        return serviceManager.getGenericService().getProject(projects, index);
    }
}
