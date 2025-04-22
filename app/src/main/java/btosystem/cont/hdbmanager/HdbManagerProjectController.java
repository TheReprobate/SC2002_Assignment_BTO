package btosystem.cont.hdbmanager;

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * High-level controller class specific to the {@link HdbManager} role
 * handling single-project related functionality.
 */
public abstract class HdbManagerProjectController extends HdbManagerController {
    private static final String[] PROJECT_MENU = {
            "View Enquiries", "View Applications", "View Project Team", "Exit"
    };

    /**
     * Constructor for the {@link HdbManager} Project controller.
     *
     * @param user reference to a {@link HdbManager} object
     * @param serviceManager reference to a {@link HdbManagerServiceManager}
     */
    public HdbManagerProjectController(HdbManager user, HdbManagerServiceManager serviceManager) {
        super(user, serviceManager);
    }

    private static final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Requests user to select neighbourhood from a list.
     *
     * @return the user's selected neighbourhood
     * @throws Exception invalid input from user
     */
    protected Neighborhood getInputNeighborhood() throws Exception {
        Neighborhood[] neighborhoods = Neighborhood.values();
        System.out.println(ListToStringFormatter.toString(neighborhoods));
        int index = InputHandler.getIntIndexInput("Select a neighborhood: ");
        if (index < 0 || index >= neighborhoods.length) {
            throw new Exception("Input out of bounds. ");
        }
        return neighborhoods[index];
    }

    /**
     * Requests user to select flat type from a list.
     *
     * @return the user's selected flat type
     * @throws Exception invalid input from user
     */
    protected FlatType getInputFlat() throws Exception {
        FlatType[] flatTypes = FlatType.values();
        System.out.println(ListToStringFormatter.toString(flatTypes));
        int index = InputHandler.getIntIndexInput("Select a flat: ");
        if (index < 0 || index >= flatTypes.length) {
            throw new Exception("Input out of bounds. ");
        }
        return flatTypes[index];
    }

    /**
     * Requests user to input a date.
     *
     * @param type which field the date is for, e.g., start date
     * @return the user's entered Date as {@link LocalDate}
     */
    protected LocalDate getInputTime(String type) throws Exception {
        String prompt = String.format("Input %s date (dd/MM/yyy): ", type);
        String input = InputHandler.getStringInput(prompt, RegexPatterns.DATE);
        LocalDate date = LocalDate.parse(input, dateFormatter);
        return date;
    }

    /**
     * Displays project for user to view more details, such as enquiries or applications,
     * by invoking the relevant service methods.
     *
     * @param project the selected project
     * @throws Exception propagated errors from service calls or invalid input from user
     */
    protected void viewProject(Project project) throws Exception {
        System.out.println(serviceManager.getGenericService().displayProject(project));
        System.out.println(ListToStringFormatter.toString(PROJECT_MENU));
        int input = InputHandler.getIntIndexInput("Select an option: ");
        switch (input) {
            case 0:
                List<Enquiry> enquiries =
                        serviceManager.getEnquiryService().getEnquiries(project);

                if (enquiries.size() <= 0) {
                    System.out.println("No enquiries found. ");
                    break;
                }
                System.out.println(serviceManager.getGenericService().displayEnquiry(enquiries));
                break;
            case 1:
                List<BtoApplication> applications =
                        serviceManager.getApplicationService().getApplications(project);

                if (applications.size() <= 0) {
                    System.out.println("No enquiries found. ");
                    break;
                }
                System.out.println(
                        serviceManager.getGenericService().displayApplication(applications)
                );
                break;
            case 2:
                ProjectTeam team = serviceManager.getTeamService().getProjectTeam(project);
                System.out.println(serviceManager.getGenericService().displayTeam(team));
                break;
            case 3:
                break;
            default:
                throw new Exception("Option does not exist. ");
        }
    }
}

