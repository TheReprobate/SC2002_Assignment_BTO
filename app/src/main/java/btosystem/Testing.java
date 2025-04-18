package btosystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import btosystem.classes.Applicant;
import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.Neighborhood;
import btosystem.controllers.OfficerRegistrationController;
import btosystem.controllers.ProjectController;
import btosystem.controllers.ProjectTeamController;

public class Testing {
    // Initialise
    ProjectController projectController = new ProjectController();
    ProjectTeamController projectTeamController = new ProjectTeamController();
    OfficerRegistrationController officerRegistrationController = new OfficerRegistrationController();

    // List to store all projects
    List<Project> projects = new ArrayList<>();

    HdbManager m1 = new HdbManager("S1111111A", "m1", 1, true);
    HdbManager m2 = new HdbManager("S2222222B", "m2", 2, false);
    HdbManager m3 = new HdbManager("S3333333C", "m3", 3, true);

    HdbOfficer o1 = new HdbOfficer("S4444444D", "o1", 4, false);
    HdbOfficer o2 = new HdbOfficer("S5555555E", "o2", 5, true);
    HdbOfficer o3 = new HdbOfficer("S6666666F", "o3", 6, false);

    Applicant a1 = new Applicant("S7777777G", "a1", 7, true);
    Applicant a2 = new Applicant("S8888888H", "a2", 8, false);
    Applicant a3 = new Applicant("S9999999I", "a3", 9, true);

    // Testing Project Team
    ProjectTeam t1 = null;
    ProjectTeam t2 = null;
    ProjectTeam t3 = null;
    ProjectTeam t4 = null;

    // Null objects for testing of exceptions
    Project pN = null;
    ProjectTeam tN = null;
    HdbManager mN = null;
    HdbOfficer oN = null;
    Applicant aN = null;

    public Testing() {
        // Create some test projects
        Project proj1 = projectController
        .createProject(
            "Project Ang Mo Kio", 
            Neighborhood.ANG_MO_KIO, 
            LocalDate.of(2025, 4, 1), 
            LocalDate.of(2025, 5, 2), 
            m1);
        Project proj2 = projectController
        .createProject(
            "Project Bishan", 
            Neighborhood.BISHAN, 
            LocalDate.of(2025, 4, 1), 
            LocalDate.of(2025, 5, 2), 
            m2);
        Project proj3 = projectController
        .createProject(
            "Project NTU", 
            Neighborhood.JURONG, 
            LocalDate.of(2025, 4, 1), 
            LocalDate.of(2025, 5, 2), 
            m3);

        projects.add(proj1);
        projects.add(proj2);
        projects.add(proj3);
    }
    
    
    // Testing ProjectController functions
    public void testProjectController() {
        /*
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
        projectController.updateUnitCount(project1, FlatType.THREE_ROOM, 50);
        System.out.println("Updated unit count for " + project1.getName() + ": " + project1.getUnits());

        // Test: Edit project neighborhood
        projectController.editProject(project1, Neighborhood.JURONG);
        System.out.println("Updated Neighborhood for " + project1.getName() + ": " + project1.getNeighborhood());

        // Test: Edit project open and close times
        LocalDate newOpenTime = LocalDate.of(2025, 4, 10);
        LocalDate newCloseTime = LocalDate.of(2025, 5, 11);
        projectController.editProject(project2, newOpenTime, newCloseTime);
        System.out.println("Updated Open and Close Times for " + project2.getName() + ": "
                + project2.getOpenTime() + " - " + project2.getCloseTime());

        // Test: Delete a project
        int deleteResult = projectController.deleteProject(projects, project3);
        System.out.println("Delete result for Project NTU: " + deleteResult);

        // Test: Check if project exists by name
        boolean exists = projectController.projectExist(projects, "Project Bishan");
        System.out.println("Does 'Project Bishan' exist? " + exists);

        // Test: Get ProjectTeam
        ProjectTeam projectTeam = projectController.retrieveProjectTeam(project1);
        System.out.println("Project Team for " + project1.getName() + ": " + projectTeam);

        // Test: Get Enquiries
        List<Enquiry> enquiries = projectController.retrieveEnquiries(project1);
        System.out.println("Enquiries for " + project1.getName() + ": " + enquiries.size());

        // Test: Get Applications
        List<BtoApplication> applications = projectController.retrieveApplications(project1);
        System.out.println("Applications for " + project1.getName() + ": " + applications.size());

        // Print all projects
        System.out.println("\nAll Projects:");
        System.out.println(projectController.toString(projects));
        */
    }
    
    // Testing ProjectTeamController methods
    public void testProjectTeamControllerInitialise() {
        System.out.println("Testing Project Team Controller");
        System.out.println("Testing: ProjectTeam createProjectTeam(Project proj) throws Exception");
        // Standard case
        try {
            t1 = projectTeamController.createProjectTeam(projects.get(0));
            projects.get(0).setProjectTeam(t1);
            System.out.println("Standard case 1 pass");

            t2 = projectTeamController.createProjectTeam(projects.get(1));
            projects.get(0).setProjectTeam(t2);
            System.out.println("Standard case 2 pass");

            t3 = projectTeamController.createProjectTeam(projects.get(2));
            projects.get(0).setProjectTeam(t3);
            System.out.println("Standard case 3 pass");
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }

        // Duplicate case
        try {
            t4 = projectTeamController.createProjectTeam(projects.get(0));
            System.out.println("Duplicate case pass");
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }

        // Null case
        try {
            t1 = projectTeamController.createProjectTeam(null);
            System.out.println("Null case pass");
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void testProjectTeamControllerHdbManager() {
        /* -------------------------------------- For HdbManager -------------------------------------- */
        System.out.println("For HdbManager");
        System.out.println("Testing: int assignProjectManager(ProjectTeam team, HdbManager manager, boolean overwrite) throws Exception");
        // Testing: int assignProjectManager(ProjectTeam team, HdbManager manager, boolean overwrite) throws Exception
        // Expecting success, no manager assigned yet
        try {
            int success = projectTeamController.assignProjectManager(t1, m1, false);
            if(success ==  1) System.out.println("Successfully assigned m1 to t1");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Expecting failure, manager already exists
        try {
            int success = projectTeamController.assignProjectManager(t1, m2, false);
            if(success ==  1) System.out.println("Successfully assigned m2 to t1");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Expecting success, overwrite true
        try {
            int success = projectTeamController.assignProjectManager(t1, m3, true);
            if(success ==  1) System.out.println("Successfully assigned m3 to t1");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Null Case 1: Expecting failure, tN does not exist
        try {
            int success = projectTeamController.assignProjectManager(tN, m1, false);
            if(success ==  1) System.out.println("Successfully assigned m1 to tN");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Null Case 2: Expecting failure, mN does not exist
        try {
            int success = projectTeamController.assignProjectManager(t1, mN, false);
            if(success ==  1) System.out.println("Successfully assigned mN to t1");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Testing: boolean isInTeam(ProjectTeam team, HdbManager manager) throws Exception");
        // Testing: boolean isInTeam(ProjectTeam team, HdbManager manager) throws Exception
        // Expecting success, m3 is the current manager
        try {
            if(projectTeamController.isInTeam(t1, m3)) System.out.println("m3 is in t1");
            else System.out.println("m3 is not in t1");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Expecting failure, m1 is not the current manager
        try {
            if(projectTeamController.isInTeam(t1, m1)) System.out.println("m1 is in t1");
            else System.out.println("m1 is not in t1");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Null Case 1: Expecting failure, tN does not exist
        try {
            if(projectTeamController.isInTeam(tN, m3)) System.out.println("m3 is in tN");
            else System.out.println("m3 is not in tN");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Null Case 2: Expecting failure, mN does not exist
        try {
            if(projectTeamController.isInTeam(t1, mN)) System.out.println("mN is in t1");
            else System.out.println("mN is not in t1");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        /* -------------------------------------- End HdbManager -------------------------------------- */
    }

    public void testProjectTeamControllerHdbOfficer() {
        System.out.printf("\nFor HdbOfficer\n");
        /* -------------------------------------- For HdbOfficer -------------------------------------- */
        // Testing: int assignProjectOfficer(ProjectTeam team, HdbOfficer officer) throws Exception
        System.out.println("Testing: int assignProjectOfficer(ProjectTeam team, HdbOfficer officer) throws Exception");
        // Expecting success, first officer to be added
        try {
            int success = projectTeamController.assignProjectOfficer(t1, o1);
            if(success ==  1) System.out.println("Successfully assigned o1 to t1");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Expecting success, no duplicate
        try {
            int success = projectTeamController.assignProjectOfficer(t1, o2);
            if(success ==  1) System.out.println("Successfully assigned o2 to t1");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Expecting failure, first officer dupe
        try {
            int success = projectTeamController.assignProjectOfficer(t1, o1);
            if(success ==  1) System.out.println("Successfully assigned o1 to t1");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Null Case 1: Expecting failure, tN does not exist
        try {
            int success = projectTeamController.assignProjectOfficer(tN, o3);
            if(success ==  1) System.out.println("Successfully assigned o3 to tN");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Null Case 2: Expecting failure, oN does not exist
        try {
            int success = projectTeamController.assignProjectOfficer(t1, oN);
            if(success ==  1) System.out.println("Successfully assigned oN to t1");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("boolean isInTeam(ProjectTeam team, HdbOfficer officer) throws Exception");
        // Testing: boolean isInTeam(ProjectTeam team, HdbOfficer officer) throws Exception
        // Expecting success, o1 is part of project team
        try {
            if(projectTeamController.isInTeam(t1, o1)) System.out.println("o1 is in t1");
            else System.out.println("o1 is not in t1");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        // Expecting failure, o3 is not part of project team
        try {
            if(projectTeamController.isInTeam(t1, o3)) System.out.println("o3 is in Project Team");
            else System.out.println("o3 is not in t1");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Null Case 1: Expecting failure, tN does not exist
        try {
            if(projectTeamController.isInTeam(tN, o1)) System.out.println("o1 is in tN");
            else System.out.println("o1 is not in tN");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Null Case 2: Expecting failure, oN does not exist
        try {
            if(projectTeamController.isInTeam(t1, oN)) System.out.println("oN is in t1");
            else System.out.println("oN is not in t1");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        /* -------------------------------------- End HdbOfficer -------------------------------------- */
    }

    // Testing ProjectTeamController + OfficerRegistrationController methods
    public void testProjectTeamControllerOfficerRegistration() {
        /* ---------------------------------- For OfficerRegistration --------------------------------- */
        System.out.printf("\nFor OfficerRegistration\n");
        OfficerRegistration r1 = null, r2 = null, r3 = null;
        
        // Expecting success, first registration
        try {
            // Creation of registration object (Pt 1 of 2)
            r1 = officerRegistrationController.createRegistration(t3, o1);
            
            // Adding of Registrations to Project Team (Pt 2 of 2)
            int success = projectTeamController.addRegistration(t3, r1);
            if(success ==  1) System.out.println("Successfully added o1 Registration to t3");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Expecting failure, duplicate registration
        try {
            // Creation of registration object (Pt 1 of 2)
            r2 = officerRegistrationController.createRegistration(t3, o1);
            
            // Adding of Registrations to Project Team (Pt 2 of 2)
            int success = projectTeamController.addRegistration(t3, r1);
            if(success ==  1) System.out.println("Successfully added o1 Registration to t3");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Expecting failure, o2 already part of t1
        try {
            // Creation of registration object (Pt 1 of 2)
            r3 = officerRegistrationController.createRegistration(t1, o2);
            
            // Adding of Registrations to Project Team (Pt 2 of 2)
            int success = projectTeamController.addRegistration(t1, r3);
            if(success ==  1) System.out.println("Successfully added o2 Registration to t1");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Testing retrieveOfficerRegistrations
        List<OfficerRegistration> listOfficerRegistration = projectTeamController.retrieveOfficerRegistrations(t3);
        // Testing toString(List<OfficerRegistration>)
        System.out.println(officerRegistrationController.toString(listOfficerRegistration));

        try {
            // Testing approveRegistration(OfficerRegistration)
            officerRegistrationController.approveRegistration(listOfficerRegistration.get(0));
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        // Testing toString(OfficerRegistration)
        System.out.println(officerRegistrationController.toString(listOfficerRegistration.get(0)));

        try {
            // Testing rejectRegistration(OfficerRegistration)
            officerRegistrationController.rejectRegistration(listOfficerRegistration.get(0));
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(officerRegistrationController.toString(listOfficerRegistration.get(0)));

        /* ---------------------------------- End OfficerRegistration --------------------------------- */
        
        // System.out.printf("\nTesting toString\n");
        // System.out.println(projectTeamController.toString(t1));
    }
}