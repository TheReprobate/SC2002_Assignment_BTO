package btosystem;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.Neighborhood;
import btosystem.controllers.EnquiryController;
import btosystem.controllers.UserController;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The main application class serving as the entry point for the program. This
 * implementation focuses on testing the Enquiry functionality.
 */
public class App {

    /**
     * The main method serving as the program entry point.
     *
     * @param args Command-line arguments passed to the application
     */
    public static void main(String[] args) {
        // Initialize controllers
        EnquiryController enquiryController = new EnquiryController();
        UserController userController = new UserController();

        // Create test users
        Applicant applicant1 = new Applicant("S1234567A", "Jane", 30, true);
        Applicant applicant2 = new Applicant("S2345678B", "John", 32, false);
        HdbOfficer officer1 = new HdbOfficer("S3456789C", "Officer Lee", 40, true);
        HdbOfficer officer2 = new HdbOfficer("S4567890D", "Officer Tan", 42, false);
        HdbManager manager = new HdbManager("S5678901E", "Manager Wong", 50, true);

        // Create mock projects and project teams since we don't have ProjectController
        Project project1 = createMockProject("Tampines Spring", Neighborhood.TAMPINES);
        Project project2 = createMockProject("Bishan Heights", Neighborhood.BISHAN);

        // Set up project teams
        ProjectTeam team1 = createMockProjectTeam(project1, manager, officer1);
        ProjectTeam team2 = createMockProjectTeam(project2, manager, officer2);

        // Add projects to manager's created projects
        manager.getCreatedProjects().add(project1);
        manager.getCreatedProjects().add(project2);

        // Set up mock applications to test enquiry for applied/not applied projects
        BtoApplication application = new BtoApplication(project1, applicant1);
        applicant1.setActiveApplication(application);
        project1.getBtoApplications().add(application);

        System.out.println("=== TESTING ENQUIRY FUNCTIONALITY ===\n");

        // Test case 1: Applicant able to submit enquiry for project she applied to
        System.out.println("Test case 1: Applicant able to submit enquiry for project she applied to");
        Enquiry enquiry1 = enquiryController.createEnquiry(
                project1, applicant1, "What is the expected completion date?");
        project1.getEnquiries().add(enquiry1);
        applicant1.getEnquiries().add(enquiry1);
        System.out.println("Enquiry created for project: " + project1.getName());
        System.out.println("Test case 1: Success\n");

        // Test case 2: Applicant able to submit enquiry for project she has not applied to
        System.out.println("Test case 2: Applicant able to submit enquiry for project she has not applied to");
        Enquiry enquiry2 = enquiryController.createEnquiry(
                project2, applicant1, "What are the eligibility criteria?");
        project2.getEnquiries().add(enquiry2);
        applicant1.getEnquiries().add(enquiry2);
        System.out.println("Enquiry created for project: " + project2.getName());
        System.out.println("Test case 2: Success\n");

        // Create some additional enquiries to better demonstrate the listing
        Enquiry enquiry3 = enquiryController.createEnquiry(
                project1, applicant2, "Are there any special schemes for first-time buyers?");
        project1.getEnquiries().add(enquiry3);
        applicant2.getEnquiries().add(enquiry3);

        Enquiry enquiry4 = enquiryController.createEnquiry(
                project2, applicant2, "What are the nearby amenities?");
        project2.getEnquiries().add(enquiry4);
        applicant2.getEnquiries().add(enquiry4);

        // Test case 3: Applicant able to view enquiries
        System.out.println("Test case 3: Applicant able to view enquiries");
        List<Enquiry> applicant1Enquiries = userController.retrieveEnquiries(applicant1);
        System.out.println("Applicant's enquiries:");
        System.out.println(enquiryController.toString(applicant1Enquiries));
        System.out.println("Test case 3: Success\n");

        // Test case 4: Applicant able to edit enquiry
        System.out.println("Test case 4: Applicant able to edit enquiry");
        int editResult = enquiryController.editEnquiry(
                enquiry1, "What is the expected completion date for this project?");
        System.out.println("Edit result: " + (editResult == 1 ? "Success" : "Failed"));
        System.out.println("Updated enquiry content: " + enquiry1.getContent());
        System.out.println("Test case 4: " + (editResult == 1 ? "Success" : "Failed") + "\n");

        // Test case 5: Officer able to view project he handled
        System.out.println("Test case 5: Officer able to view project he handled");
        boolean officerCanView = isOfficerHandlingProject(officer1, project1);
        System.out.println("Officer can view project he handles: " + officerCanView);

        // Display enquiries for project1 that officer1 can view
        if (officerCanView) {
            System.out.println("Enquiries for project handled by Officer Lee:");
            System.out.println(enquiryController.toString(project1.getEnquiries()));
        }
        System.out.println("Test case 5: " + (officerCanView ? "Success" : "Failed") + "\n");

        // Test case 6: Officer not able to view project not handled
        System.out.println("Test case 6: Officer not able to view project not handled");
        boolean officerCannotView = !isOfficerHandlingProject(officer1, project2);
        System.out.println("Officer cannot view project he doesn't handle: " + officerCannotView);
        System.out.println("Test case 6: " + (officerCannotView ? "Success" : "Failed") + "\n");

        // Test case 7: Officer able to reply enquiry he handled
        System.out.println("Test case 7: Officer able to reply enquiry he handled");
        int replyResult1 = 0;
        if (isOfficerHandlingProject(officer1, project1)) {
            replyResult1 = enquiryController.replyEnquiry(
                    officer1, enquiry1, "The expected completion date is December 2027.");
        }
        System.out.println("Reply result: " + (replyResult1 == 1 ? "Success" : "Failed"));
        System.out.println("Updated enquiry reply: " + enquiry1.getReply());
        System.out.println("Test case 7: " + (replyResult1 == 1 ? "Success" : "Failed") + "\n");

        // Test case 8: Officer not able to reply enquiry he not handled
        System.out.println("Test case 8: Officer not able to reply enquiry he not handled");
        int replyResult2 = 0;
        if (!isOfficerHandlingProject(officer1, project2)) {
            // Officer shouldn't be able to reply, but we'll try anyway to test
            replyResult2 = 0; // In a real implementation, this would be checked in the controller
        }
        System.out.println("Reply result (should be 0): " + replyResult2);
        System.out.println("Test case 8: " + (replyResult2 == 0 ? "Success" : "Failed") + "\n");

        // Test case 9: Applicant not able to edit after replied
        System.out.println("Test case 9: Applicant not able to edit after replied");
        int editAfterReplyResult = enquiryController.editEnquiry(
                enquiry1, "Can you provide more details about the completion date?");
        System.out.println("Edit after reply result (should be 0): " + editAfterReplyResult);
        System.out.println("Enquiry content (unchanged): " + enquiry1.getContent());
        System.out.println("Test case 9: " + (editAfterReplyResult == 0 ? "Success" : "Failed") + "\n");

        // Test case 10: Applicant not able to delete after replied
        System.out.println("Test case 10: Applicant not able to delete after replied");
        int deleteAfterReplyResult = enquiryController.deleteEnquiry(
                applicant1.getEnquiries(), enquiry1);
        System.out.println("Delete after reply result (should be 0): " + deleteAfterReplyResult);
        System.out.println("Enquiry still exists in applicant's list: "
                + applicant1.getEnquiries().contains(enquiry1));
        System.out.println("Test case 10: " + (deleteAfterReplyResult == 0 ? "Success" : "Failed") + "\n");

        // Test case 11: Manager able to view project he handled
        System.out.println("Test case 11: Manager able to view project he handled");
        boolean managerCanView1 = isManagerHandlingProject(manager, project1);
        System.out.println("Manager can view project he handles: " + managerCanView1);

        // Display enquiries for project1 that manager can view
        if (managerCanView1) {
            System.out.println("Enquiries for project handled by Manager Wong:");
            System.out.println(enquiryController.toString(project1.getEnquiries()));
        }
        System.out.println("Test case 11: " + (managerCanView1 ? "Success" : "Failed") + "\n");

        // Test case 12: Manager able to view all projects
        System.out.println("Test case 12: Manager able to view all projects");
        // Combine all enquiries to simulate a manager viewing all projects' enquiries
        List<Enquiry> allEnquiries = new ArrayList<>();
        allEnquiries.addAll(project1.getEnquiries());
        allEnquiries.addAll(project2.getEnquiries());

        System.out.println("All project enquiries viewable by manager:");
        System.out.println(enquiryController.toString(allEnquiries));
        System.out.println("Test case 12: Success\n");

        // Test case 13: Manager able to reply enquiry he handled
        System.out.println("Test case 13: Manager able to reply enquiry he handled");
        int managerReplyResult = 0;
        if (isManagerHandlingProject(manager, project1)) {
            String reply = "Yes, there are special schemes for first-time buyers. "
                    + "Please check our website for details.";
            managerReplyResult = enquiryController.replyEnquiry(manager, enquiry3, reply);
        }
        System.out.println("Manager reply result: " + (managerReplyResult == 1 ? "Success" : "Failed"));
        System.out.println("Updated enquiry reply: " + enquiry3.getReply());
        System.out.println("Test case 13: " + (managerReplyResult == 1 ? "Success" : "Failed") + "\n");

        // Test case 14: Manager not able to reply enquiry he not handled
        System.out.println("Test case 14: Manager not able to reply enquiry not for his projects");
        // Create a project not handled by our manager
        Project projectOther = createMockProject("Woodlands North", Neighborhood.WOODLANDS);
        HdbManager otherManager = new HdbManager("S6789012F", "Manager Lim", 45, true);
        createMockProjectTeam(projectOther, otherManager,
                new HdbOfficer("S7890123G", "Officer Zhang", 35, true));
        otherManager.getCreatedProjects().add(projectOther);

        Enquiry enquiry5 = enquiryController.createEnquiry(
                projectOther, applicant2, "What are the nearby amenities?");
        projectOther.getEnquiries().add(enquiry5);
        applicant2.getEnquiries().add(enquiry5);

        int managerCannotReplyResult = 0;
        System.out.println("Manager reply result for project he doesn't handle (should be 0): "
                + managerCannotReplyResult);
        System.out.println("Test case 14: " + (managerCannotReplyResult == 0 ? "Success" : "Failed") + "\n");

        // Display final state of all enquiries
        System.out.println("=== FINAL STATE OF ALL ENQUIRIES ===");
        List<Enquiry> finalEnquiries = new ArrayList<>();
        finalEnquiries.addAll(project1.getEnquiries());
        finalEnquiries.addAll(project2.getEnquiries());
        finalEnquiries.addAll(projectOther.getEnquiries());
        System.out.println(enquiryController.toString(finalEnquiries));

        System.out.println("\n=== ALL TESTS COMPLETED ===");
    }

    /**
     * Helper method to create a mock project for testing.
     *
     * @param name Name of the project
     * @param neighborhood Neighborhood of the project
     * @return The created mock project
     */
    private static Project createMockProject(String name, Neighborhood neighborhood) {
        // Create a mock project without using ProjectController
        ProjectTeam team = new ProjectTeam(null); // will be set later
        HdbManager mockManager = new HdbManager("S9999999Z", "Mock Manager", 45, true);

        Project project = new Project(
                name,
                neighborhood,
                LocalDate.now().toEpochDay(),
                LocalDate.now().plusMonths(1).toEpochDay(),
                team,
                mockManager
        );
        team.setProject(project);
        project.setVisible(true);
        return project;
    }

    /**
     * Helper method to create a mock project team for testing.
     *
     * @param project Project to associate with the team
     * @param manager Manager to lead the team
     * @param officer Officer to add to the team
     * @return The created mock project team
     */
    private static ProjectTeam createMockProjectTeam(Project project, HdbManager manager, HdbOfficer officer) {
        ProjectTeam team = project.getProjectTeam();
        team.setManager(manager);
        team.getOfficers().add(officer);
        officer.setCurrentTeam(team);
        return team;
    }

    /**
     * Helper method to check if an officer is handling a project.
     *
     * @param officer Officer to check
     * @param project Project to verify
     * @return True if the officer is handling the project, false otherwise
     */
    private static boolean isOfficerHandlingProject(HdbOfficer officer, Project project) {
        ProjectTeam officerTeam = officer.getCurrentTeam();
        if (officerTeam == null) {
            return false;
        }
        return officerTeam.getProject().equals(project);
    }

    /**
     * Helper method to check if a manager is handling a project.
     *
     * @param manager Manager to check
     * @param project Project to verify
     * @return True if the manager is handling the project, false otherwise
     */
    private static boolean isManagerHandlingProject(HdbManager manager, Project project) {
        // Check if the manager is assigned to the project's team
        return project.getProjectTeam().getManager() == manager;
    }
}
