package btosystem;

/**
 * The main application class serving as the entry point for the program.
 */
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

import btosystem.classes.enums.FlatType;
import btosystem.controllers.ProjectController;

public class App {

    /**
     * The main method serving as the program entry point.
     *
     * @param args Command-line arguments passed to the application
     */
    public static void main(String[] args) {
        ProjectController projectController = new ProjectController();
        EnquiryController enquiryController = new EnquiryController();

        HdbManager hdbManager = new HdbManager("S9810294C", "Trump", 54, true);
        HdbOfficer officerAMK = new HdbOfficer("S1111111A", "Officer Tan", 30, false);
        HdbOfficer officerBishan = new HdbOfficer("S2222222B", "Officer Lim", 35, true);
        HdbOfficer officerJurong = new HdbOfficer("S3333333C", "Officer Wong", 40, false);
        Applicant applicant1 = new Applicant("S4444444D", "John Doe", 25, false);
        Applicant applicant2 = new Applicant("S5555555E", "Jane Smith", 30, true);
        Applicant applicant3 = new Applicant("S6666666F", "Bob Johnson", 45, false);

        // Create some test projects
        Project projectAMK = projectController.createProject("AMK Residences", Neighborhood.ANG_MO_KIO,
                LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 2), hdbManager);
        Project projectBishan = projectController.createProject("Bishan Vista", Neighborhood.BISHAN,
                LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 2), hdbManager);
        Project projectJurong = projectController.createProject("Jurong Heights", Neighborhood.JURONG,
                LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 2), hdbManager);

        // Set up project teams
        ProjectTeam teamAMK = new ProjectTeam(projectAMK);
        teamAMK.setManager(hdbManager);
        teamAMK.getOfficers().add(officerAMK);
        projectAMK.setProjectTeam(teamAMK);
        officerAMK.setCurrentTeam(teamAMK);

        ProjectTeam teamBishan = new ProjectTeam(projectBishan);
        teamBishan.setManager(hdbManager);
        teamBishan.getOfficers().add(officerBishan);
        projectBishan.setProjectTeam(teamBishan);
        officerBishan.setCurrentTeam(teamBishan);

        ProjectTeam teamJurong = new ProjectTeam(projectJurong);
        teamJurong.setManager(hdbManager);
        teamJurong.getOfficers().add(officerJurong);
        projectJurong.setProjectTeam(teamJurong);
        officerJurong.setCurrentTeam(teamJurong);

        // List to store all projects
        List<Project> projects = new ArrayList<>();
        projects.add(projectAMK);
        projects.add(projectBishan);
        projects.add(projectJurong);

        // Test: Retrieve project by name
        Project retrievedProject = projectController.retrieveProject(projects, "Project Alpha");
        if (retrievedProject != null) {
            System.out.println("Retrieved Project by Name: " + retrievedProject.getName());
        }

        // Test: Retrieve project by index
        Project retrievedProjectByIndex = projectController.retrieveProject(projects, 1);
        System.out.println("Retrieved Project by Index: " + retrievedProjectByIndex.getName());

        // Test: Filter projects by neighborhood
        List<Project> northProjects = projectController.filterProject(projects, Neighborhood.ANG_MO_KIO);
        System.out.println("Filtered Projects in ANG MO KIO: ");
        northProjects.forEach(project -> System.out.println(project.getName()));

        // Test: Filter projects by visibility (true)
        List<Project> visibleProjects = projectController.filterProject(projects, true);
        System.out.println("Filtered Visible Projects: ");
        visibleProjects.forEach(project -> System.out.println(project.getName()));

        // Test: Update unit count for a project
        projectController.updateUnitCount(projectAMK, FlatType.THREE_ROOM, 50);
        System.out.println("Updated unit count for " + projectAMK.getName() + ": " + projectAMK.getUnits());

        // Test: Edit project neighborhood
        projectController.editProject(projectAMK, Neighborhood.JURONG);
        System.out.println("Updated Neighborhood for " + projectAMK.getName() + ": " + projectAMK.getNeighborhood());

        // Test: Edit project open and close times
        LocalDate newOpenTime = LocalDate.of(2025, 4, 10);
        LocalDate newCloseTime = LocalDate.of(2025, 5, 11);
        projectController.editProject(projectBishan, newOpenTime, newCloseTime);
        System.out.println("Updated Open and Close Times for " + projectBishan.getName() + ": "
                + projectBishan.getOpenTime() + " - " + projectBishan.getCloseTime());

        // Test: Delete a project
        int deleteResult = projectController.deleteProject(projects, projectJurong);
        System.out.println("Delete result for Project NTU: " + deleteResult);

        // Test: Check if project exists by name
        boolean exists = projectController.projectExist(projects, "Project Bishan");
        System.out.println("Does 'Project Bishan' exist? " + exists);

        // Test: Get ProjectTeam
        ProjectTeam projectTeam = projectController.retrieveProjectTeam(projectJurong);
        System.out.println("Project Team for " + projectJurong.getName() + ": " + projectTeam);

        // Test: Get Enquiries
        List<Enquiry> enquiries = projectController.retrieveEnquiries(projectJurong);
        System.out.println("Enquiries for " + projectJurong.getName() + ": " + enquiries.size());

        // Test: Get Applications
        List<BtoApplication> applications = projectController.retrieveApplications(projectJurong);
        System.out.println("Applications for " + projectJurong.getName() + ": " + applications.size());

        // Print all projects
        System.out.println("\nAll Projects:");
        System.out.println(projectController.toString(projects));

        // Print single  projects
        System.out.println("\nSingle project:");
        System.out.println(projectController.toString(projectAMK));

        // Test: Applicant able submit enquiry project she not applied
        Enquiry amkEnquiry1 = enquiryController.createEnquiry(projectAMK, applicant1,
                "What are the amenities in AMK Hub?");
        projectAMK.getEnquiries().add(amkEnquiry1);
        applicant1.getEnquiries().add(amkEnquiry1);
        System.out.println("Applicant submitted enquiry to project not applied: "
                + amkEnquiry1.getContent());

        // Test: Applicant able submit enquiry project she applied
        BtoApplication application = new BtoApplication(projectBishan, applicant1, FlatType.TWO_ROOM);
        applicant1.setActiveApplication(application);
        projectBishan.getBtoApplications().add(application);

        Enquiry bishanEnquiry1 = enquiryController.createEnquiry(projectBishan, applicant1,
                "Can I change my flat selection?");
        projectBishan.getEnquiries().add(bishanEnquiry1);
        applicant1.getEnquiries().add(bishanEnquiry1);
        System.out.println("\nApplicant submitted enquiry to project she applied: "
                + bishanEnquiry1.getContent());

        // Test: Applicant able view enquiries
        System.out.println("\nApplicant's enquiries:");
        System.out.println(enquiryController.toString(applicant1.getEnquiries()));

        // Test: Applicant able edit enquiry
        enquiryController.editEnquiry(amkEnquiry1, "Revised: What recreational facilities in AMK Hub?");
        System.out.println("\nAfter editing enquiry: " + amkEnquiry1.getContent());

        // Test: Applicant not able edit after replied
        enquiryController.replyEnquiry(officerAMK, amkEnquiry1, "Includes swimming pool and gym");
        int editAttempt = enquiryController.editEnquiry(amkEnquiry1, "Trying to edit after reply");
        System.out.println("\nAttempt to edit after reply (should fail): " + editAttempt);

        // Test: Applicant not able delete after replied
        int deleteAttempt = enquiryController.deleteEnquiry(projectAMK.getEnquiries(), amkEnquiry1);
        System.out.println("Attempt to delete after reply (should fail): " + deleteAttempt);

        // Test: Officer able view project he handled
        System.out.println("\nOfficer viewing enquiries for project he handles:");
        System.out.println(enquiryController.toString(projectAMK.getEnquiries()));

        // Test: Officer not able view project not handled
        System.out.println("\nOfficer trying to view project he doesn't handle:");
        System.out.println("Officer assigned to: " + officerAMK.getCurrentTeam().getProject().getName());
        System.out.println("Bishan project enquiries count: " + projectBishan.getEnquiries().size());

        // Test: Officer able reply enquiry he handled
        int replySuccess = enquiryController.replyEnquiry(officerAMK, amkEnquiry1, "Facilities include...");
        System.out.println("\nOfficer reply to his project enquiry (should succeed): " + replySuccess);

        // Test: Officer not able reply enquiry he handled
        int replyFail = enquiryController.replyEnquiry(officerAMK, bishanEnquiry1, "Unauthorized reply");
        System.out.println("Officer reply to other project enquiry (should fail): " + replyFail);

        // Test: Manager able view project he handled
        System.out.println("\nManager viewing AMK project enquiries:");
        System.out.println(enquiryController.toString(projectAMK.getEnquiries()));

        // Test: Manager able view project he not handled
        System.out.println("\nManager viewing Bishan project enquiries:");
        System.out.println(enquiryController.toString(projectBishan.getEnquiries()));

        // Test: Manager able reply enquiry he handled
        Enquiry amkEnquiry2 = enquiryController.createEnquiry(projectAMK, applicant2,
                "Manager test enquiry");
        projectAMK.getEnquiries().add(amkEnquiry2);
        int managerReply = enquiryController.replyEnquiry(hdbManager, amkEnquiry2, "Manager's reply");
        System.out.println("\nManager reply to AMK enquiry (should succeed): " + managerReply);

        // Test: Manager not able reply enquiry he handled
        // Note: In current setup, manager can reply to all enquiries
        System.out.println("\nIn current system, manager can reply to all enquiries");
    }
}
