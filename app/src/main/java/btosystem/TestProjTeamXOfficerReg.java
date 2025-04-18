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

public class TestProjTeamXOfficerReg {
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

    public TestProjTeamXOfficerReg() {
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
    
    // Testing ProjectTeamController methods
    public void testProjectTeamControllerInitialise() {
        System.out.println("Testing Project Team Controller");
        System.out.println("Testing: ProjectTeam createProjectTeam(Project proj)");
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
        System.out.println("Testing: int assignProjectManager(ProjectTeam team, HdbManager manager, boolean overwrite)");
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

        System.out.println("Testing: boolean isInTeam(ProjectTeam team, HdbManager manager)");
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
        System.out.println("Testing: int assignProjectOfficer(ProjectTeam team, HdbOfficer officer)");
        // Expecting success, registration accepted in projectteams
        try {
            int success = projectTeamController.assignProjectOfficer(t1, o1);
            if(success ==  1) System.out.println("Successfully assigned o1 to t1");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Expecting failure, duplicate
        try {
            int success = projectTeamController.assignProjectOfficer(t1, o1);
            if(success ==  1) System.out.println("Successfully assigned o1 to t1");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Expecting failure, no registration approval
        try {
            int success = projectTeamController.assignProjectOfficer(t1, o3);
            if(success ==  1) System.out.println("Successfully assigned o3 to t1");
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

        System.out.println("Testing: boolean isInTeam(ProjectTeam team, HdbOfficer officer)");
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
            r1 = officerRegistrationController.createRegistration(t1, o1);
            
            // Adding of Registrations to Project Team (Pt 2 of 2)
            int success = projectTeamController.addRegistration(t1, r1);
            int success2 = officerRegistrationController.approveRegistration(r1);

            if(success ==  1) System.out.println("Successfully added o1 Registration to t1");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Expecting success, not a duplicate
        try {
            // Creation of registration object (Pt 1 of 2)
            r2 = officerRegistrationController.createRegistration(t3, o2);
            
            // Adding of Registrations to Project Team (Pt 2 of 2)
            int success = projectTeamController.addRegistration(t3, r2);
            int success2 = officerRegistrationController.approveRegistration(r2);

            if(success ==  1) System.out.println("Successfully added o2 Registration to t3");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Expecting failure, duplicate registration
        try {
            // Creation of registration object (Pt 1 of 2)
            r2 = officerRegistrationController.createRegistration(t3, o1);
            
            // Adding of Registrations to Project Team (Pt 2 of 2)
            int success = projectTeamController.addRegistration(t3, r2);
            int success2 = officerRegistrationController.approveRegistration(r2);

            if(success ==  1) System.out.println("Successfully added o1 Registration to t3");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Expecting failure, o2 already part of t1
        try {
            // forcefully inserting officer first
            t1.assignOfficer(o2);

            // Creation of registration object (Pt 1 of 2)
            r3 = officerRegistrationController.createRegistration(t1, o2);
            
            // Adding of Registrations to Project Team (Pt 2 of 2)
            int success = projectTeamController.addRegistration(t3, r3);
            int success2 = officerRegistrationController.approveRegistration(r3);

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
        try {
            // Testing retrieveOfficerRegistration(List<OfficerRegistration> registrations, int index)
            System.out.println(officerRegistrationController
                .toString(officerRegistrationController.retrieveOfficerRegistration(listOfficerRegistration, 0)));
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        /* ---------------------------------- End OfficerRegistration --------------------------------- */
    }

    public void toStringTeams() {
        System.out.printf("\nTesting toString\n");
        System.out.println(projectTeamController.toString(t1));
    }

    public void cleanupProjectTeam() throws Exception {
        officerRegistrationController.cleanup(t1.getOfficerRegistrations());

        projectTeamController.cleanup(t1);
        t1 = null;
    }
}