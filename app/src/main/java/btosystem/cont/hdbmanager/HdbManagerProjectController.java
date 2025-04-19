package btosystem.cont.hdbmanager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;
import btosystem.service.HdbManagerServiceManager;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;
import btosystem.utils.RegexPatterns;

public abstract class HdbManagerProjectController extends HdbManagerController {
    private static final String[] PROJECT_MENU = {"View Enquiries", "View Applications", "View Project Team", "Exit"};

    public HdbManagerProjectController(HdbManager user, HdbManagerServiceManager serviceManager) {
        super(user, serviceManager);
    }

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    protected Neighborhood getInputNeighborhood() throws Exception {
        Neighborhood[] neighborhoods = Neighborhood.values();
        System.out.println(ListToStringFormatter.toString(neighborhoods));
        int index = InputHandler.getIntIndexInput("Select a neighborhood: ");
        if (index < 0 || index >= neighborhoods.length) {
            throw new Exception("Input out of bounds. ");
        }
        return neighborhoods[index];
    }

    protected FlatType getInputFlat() throws Exception {
        FlatType[] neighborhoods = FlatType.values();
        System.out.println(ListToStringFormatter.toString(neighborhoods));
        int index = InputHandler.getIntIndexInput("Select a flat: ");
        if (index < 0 || index >= neighborhoods.length) {
            throw new Exception("Input out of bounds. ");
        }
        return neighborhoods[index];
    }

    protected LocalDate getInputTime(String type) throws Exception {
        String prompt = String.format("Input %s date (dd/MM/yyy): ", type);
        String input = InputHandler.getStringInput(prompt, RegexPatterns.DATE);
        LocalDate date = LocalDate.parse(input, dateFormatter);
        return date;
    }

    protected void viewProject(Project project) throws Exception {
        System.out.println(serviceManager.getGenericService().displayProject(project));
        System.out.println(ListToStringFormatter.toString(PROJECT_MENU));
        int input = InputHandler.getIntIndexInput("Select an option: ");
        switch(input) {
            case 0:
                List<Enquiry> enquiries = serviceManager.getEnquiryService().getEnquiries(project);
                if(enquiries.size() <= 0) {
                    System.out.println("No enquiries found. ");
                    break;
                }
                System.out.println(serviceManager.getGenericService().displayEnquiry(enquiries));
                break;
            case 1:
                List<BtoApplication> applications = serviceManager.getApplicationService().getApplications(project);
                if(applications.size() <= 0) {
                    System.out.println("No enquiries found. ");
                    break;
                }
                System.out.println(serviceManager.getGenericService().displayApplication(applications));
                break;
            case 2:
                ProjectTeam team = serviceManager.getTeamService().getProjectTeam(project);
                System.out.println(serviceManager.getGenericService().displayTeam(team));
            case 3:
                break;
            default:
                throw new Exception("Option does not exist. ");
        }
    }
}

