package btosystem;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;

import btosystem.controllers.EnquiryController;
import btosystem.controllers.ProjectController;
import btosystem.controllers.UserController;
import btosystem.controllers.OfficerRegistrationController;
import btosystem.controllers.ProjectTeamController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The main application class serving as the entry point for the program.
 */
public class App {

    /**
     * The main method serving as the program entry point.
     *
     * @param args Command-line arguments passed to the application
     */
    
     public static void main(String[] args) {
        final ProjectController projectController = new ProjectController();
        final ProjectTeamController projectTeamController = new ProjectTeamController();
        final OfficerRegistrationController officerRegistrationController = new OfficerRegistrationController();
        final EnquiryController enquiryController = new EnquiryController();

        final HdbManager hdbManager = new HdbManager("S9810294C", "Trump", 54, true);

        final HdbOfficer officerAmk = new HdbOfficer(
                "S1111111A", "Officer Tan", 30, false);
        final HdbOfficer officerBishan = new HdbOfficer(
                "S2222222B", "Officer Lim", 35, true);
        final HdbOfficer officerJurong = new HdbOfficer(
                "S3333333C", "Officer Wong", 40, false);

        final Applicant applicant1 = new Applicant(
                "S4444444D", "John Doe", 25, false);
        final Applicant applicant2 = new Applicant(
                "S5555555E", "Jane Smith", 30, true);
        final Applicant applicant3 = new Applicant(
                "S6666666F", "Bob Johnson", 45, false);

        // Create some test projects
        final Project projectAngmokio = projectController.createProject(
                "AMK Residences", Neighborhood.ANG_MO_KIO,
                LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 2), hdbManager);
        final Project projectBishan = projectController.createProject(
                "Bishan Vista", Neighborhood.BISHAN,
                LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 2), hdbManager);
        final Project projectJurong = projectController.createProject(
                "Jurong Heights", Neighborhood.JURONG,
                LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 2), hdbManager);
        

        // List to store all projects
        List<Project> projects = new ArrayList<>();
        projects.add(projectAngmokio);
        projects.add(projectBishan);
        projects.add(projectJurong);
        
        // Just to make my life easier (pt 1)
        ProjectTeam teamAmk = null, teamBishan = null, teamJurong = null;
        List<ProjectTeam> projTeams = new ArrayList<>();
        projTeams.add(teamAmk);
        projTeams.add(teamBishan);
        projTeams.add(teamJurong);

        // Just to make my life easier (pt 2)
        List<HdbOfficer> officers = new ArrayList<>();
        officers.add(officerAmk);
        officers.add(officerBishan);
        officers.add(officerJurong);

        for(int i = 0; i < projTeams.size(); i++) {
                // Set up project teams
                System.out.println(i);
                try {
                        // Initialise one ProjectTeam
                        projTeams.set(i, projectTeamController.createProjectTeam(projects.get(i)));

                        try {
                                projectTeamController.assignProjectManager(projTeams.get(i), hdbManager, false);
                        }
                        catch (Exception e) {
                                if(e.getMessage().equals("Manager already exists. Overwrite?")) {
                                        // Prompt for overwrite y/n input
                                        // If yes,
                                        char rawInput = 'y';
                                        if(rawInput == 'y') {
                                                projectTeamController.assignProjectManager(projTeams.get(i), hdbManager, true);
                                        }
                                        else {
                                                // do something else
                                        }
                                }
                                System.out.println("Error: " + e.getMessage());
                        }

                        // Create Registration object
                        OfficerRegistration r1 = officerRegistrationController.createRegistration(projTeams.get(i), officers.get(i));

                        // Add registration to projectTeam
                        int success = projectTeamController.addRegistration(projTeams.get(i), r1);
                        // honestly idk if the success variables are necessary, but ig good to have?
                        if(success != 0) {
                                // Manager sets that registration to approved
                                int success2 = officerRegistrationController.approveRegistration(r1);
                                if(success2 != 0) {
                                        // projectTeamController actually adds officer to team
                                        int success3 = projectTeamController.assignProjectOfficer(projTeams.get(i), officers.get(i));
                                }
                        }

                        // Project Controller probably takes over here
                        projects.get(i).setProjectTeam(projTeams.get(i));
                        officers.get(i).setCurrentTeam(projTeams.get(i));
                }
                catch(Exception e) {
                        System.out.println("Error: " + e.getMessage());
                }
        }

        // Test: Retrieve project by name
        Project retrievedProject = projectController.retrieveProject(
                projects, "Project Alpha");
        if (retrievedProject != null) {
            System.out.println("Retrieved Project by Name: "
                    + retrievedProject.getName());
        }

        // Test: Retrieve project by index
        Project retrievedProjectByIndex = projectController.retrieveProject(
                projects, 1);
        System.out.println("Retrieved Project by Index: "
                + retrievedProjectByIndex.getName());

        // Test: Filter projects by neighborhood
        List<Project> northProjects = projectController.filterProject(
                projects, Neighborhood.ANG_MO_KIO);
        System.out.println("Filtered Projects in ANG MO KIO: ");
        northProjects.forEach(project -> System.out.println(project.getName()));

        // Test: Filter projects by visibility (true)
        List<Project> visibleProjects = projectController.filterProject(
                projects, true);
        System.out.println("Filtered Visible Projects: ");
        visibleProjects.forEach(project -> System.out.println(project.getName()));

        // Test: Update unit count for a project
        projectController.updateUnitCount(
                projectAngmokio, FlatType.THREE_ROOM, 50);
        System.out.println("Updated unit count for "
                + projectAngmokio.getName() + ": " + projectAngmokio.getUnits());

        // Test: Edit project neighborhood
        projectController.editProject(projectAngmokio, Neighborhood.JURONG);
        System.out.println("Updated Neighborhood for "
                + projectAngmokio.getName() + ": " + projectAngmokio.getNeighborhood());

        // Test: Edit project open and close times
        LocalDate newOpenTime = LocalDate.of(2025, 4, 10);
        LocalDate newCloseTime = LocalDate.of(2025, 5, 11);
        projectController.editProject(projectBishan, newOpenTime, newCloseTime);
        System.out.println("Updated Open and Close Times for "
                + projectBishan.getName() + ": " + projectBishan.getOpenTime()
                + " - " + projectBishan.getCloseTime());

        // Test: Delete a project
        int deleteResult = projectController.deleteProject(
                projects, projectJurong);
        System.out.println("Delete result for Project NTU: " + deleteResult);

        // Test: Check if project exists by name
        boolean exists = projectController.projectExist(
                projects, "Project Bishan");
        System.out.println("Does 'Project Bishan' exist? " + exists);

        // Test: Get ProjectTeam
        ProjectTeam projectTeam = projectController.retrieveProjectTeam(
                projectJurong);
        System.out.println("Project Team for "
                + projectJurong.getName() + ": " + projectTeam);

        // Test: Get Enquiries
        List<Enquiry> enquiries = projectController.retrieveEnquiries(
                projectJurong);
        System.out.println("Enquiries for "
                + projectJurong.getName() + ": " + enquiries.size());

        // Test: Get Applications
        List<BtoApplication> applications = projectController.retrieveApplications(
                projectJurong);
        System.out.println("Applications for "
                + projectJurong.getName() + ": " + applications.size());

        // Print all projects
        System.out.println("\nAll Projects:");
        System.out.println(projectController.toString(projects));

        // Print single project
        System.out.println("\nSingle project:");
        System.out.println(projectController.toString(projectAngmokio));

        // Test: Applicant able submit enquiry project she not applied
        Enquiry amkEnquiry1 = enquiryController.createEnquiry(
                projectAngmokio, applicant1, "What are the amenities in AMK Hub?");
        projectAngmokio.getEnquiries().add(amkEnquiry1);
        applicant1.getEnquiries().add(amkEnquiry1);
        System.out.println("Applicant submitted enquiry to project not applied: "
                + amkEnquiry1.getContent());

        // Test: Applicant able submit enquiry project she applied
        BtoApplication application = new BtoApplication(
                projectBishan, applicant1, FlatType.TWO_ROOM);
        applicant1.setActiveApplication(application);
        projectBishan.getBtoApplications().add(application);

        Enquiry bishanEnquiry1 = enquiryController.createEnquiry(
                projectBishan, applicant1, "Can I change my flat selection?");
        projectBishan.getEnquiries().add(bishanEnquiry1);
        applicant1.getEnquiries().add(bishanEnquiry1);
        System.out.println("\nApplicant submitted enquiry to project she applied: "
                + bishanEnquiry1.getContent());

        // Test: Applicant able view enquiries
        System.out.println("\nApplicant's enquiries:");
        System.out.println(enquiryController.toString(applicant1.getEnquiries()));

        // Test: Applicant able edit enquiry
        enquiryController.editEnquiry(
                amkEnquiry1, "Revised: What recreational facilities in AMK Hub?");
        System.out.println("\nAfter editing enquiry: " + amkEnquiry1.getContent());

        // Test: Applicant not able edit after replied
        enquiryController.replyEnquiry(
                officerAmk, amkEnquiry1, "Includes swimming pool and gym");
        int editAttempt = enquiryController.editEnquiry(
                amkEnquiry1, "Trying to edit after reply");
        System.out.println("\nAttempt to edit after reply (should fail): "
                + editAttempt);

        // Test: Applicant not able delete after replied
        int deleteAttempt = enquiryController.deleteEnquiry(
                projectAngmokio.getEnquiries(), amkEnquiry1);
        System.out.println("Attempt to delete after reply (should fail): "
                + deleteAttempt);

        // Test: Officer able view project he handled
        System.out.println("\nOfficer viewing enquiries for project he handles:");
        System.out.println(enquiryController.toString(projectAngmokio.getEnquiries()));

        // Test: Officer not able view project not handled
        System.out.println("\nOfficer trying to view project he doesn't handle:");
        System.out.println("Officer assigned to: "
                + officerAmk.getCurrentTeam().getProject().getName());
        System.out.println("Bishan project enquiries count: "
                + projectBishan.getEnquiries().size());

        // Test: Officer able reply enquiry he handled
        int replySuccess = enquiryController.replyEnquiry(
                officerAmk, amkEnquiry1, "Facilities include...");
        System.out.println("\nOfficer reply to his project enquiry (should succeed): "
                + replySuccess);

        // Test: Officer not able reply enquiry he handled
        int replyFail = enquiryController.replyEnquiry(
                officerAmk, bishanEnquiry1, "Unauthorized reply");
        System.out.println("Officer reply to other project enquiry (should fail): "
                + replyFail);

        // Test: Manager able view project he handled
        System.out.println("\nManager viewing AMK project enquiries:");
        System.out.println(enquiryController.toString(projectAngmokio.getEnquiries()));

        // Test: Manager able view project he not handled
        System.out.println("\nManager viewing Bishan project enquiries:");
        System.out.println(enquiryController.toString(projectBishan.getEnquiries()));

        // Test: Manager able reply enquiry he handled
        Enquiry amkEnquiry2 = enquiryController.createEnquiry(
                projectAngmokio, applicant2, "Manager test enquiry");
        projectAngmokio.getEnquiries().add(amkEnquiry2);
        int managerReply = enquiryController.replyEnquiry(
                hdbManager, amkEnquiry2, "Manager's reply");
        System.out.println("\nManager reply to AMK enquiry (should succeed): "
                + managerReply);

        /*
        // Test: Manager not able reply enquiry he handled
        System.out.println("\nIn current system, manager can reply to all enquiries");

        // Must be all run in sequence to show every/most cases
        TestProjTeamXOfficerReg test = new TestProjTeamXOfficerReg();
        test.testProjectTeamControllerInitialise();

        test.testProjectTeamControllerHdbManager();

        // Registration first, else all test cases for officer will fail
        test.testProjectTeamControllerOfficerRegistration();
        test.testProjectTeamControllerHdbOfficer();

        test.toStringTeams();
        try {
                test.cleanupProjectTeam();
        }
        catch(Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        test.toStringTeams();
        */
    }
}
