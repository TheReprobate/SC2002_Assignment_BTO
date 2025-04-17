package btosystem;

/**
* The main application class serving as the entry point for the program.
*/
import btosystem.classes.*;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;
import btosystem.controllers.OfficerRegistrationController;
import btosystem.controllers.ProjectController;
import btosystem.controllers.ProjectTeamController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class App {
    /**
    * The main method serving as the program entry point.
    *
    * @param args Command-line arguments passed to the application 
    */
    
    public static void main(String[] args) {
        ProjectController projectController = new ProjectController();
        ProjectTeamController projectTeamController = new ProjectTeamController();
        OfficerRegistrationController officerRegistrationController = new OfficerRegistrationController();

        HdbManager m1 = new HdbManager("S1111111A", "m1", 1, true);
        HdbManager m2 = new HdbManager("S2222222B", "m2", 2, false);
        HdbManager m3 = new HdbManager("S3333333C", "m3", 3, true);

        HdbOfficer o1 = new HdbOfficer("S4444444D", "o1", 4, false);
        HdbOfficer o2 = new HdbOfficer("S5555555E", "o2", 5, true);
        HdbOfficer o3 = new HdbOfficer("S6666666F", "o3", 6, false);

        Applicant a1 = new Applicant("S7777777G", "a1", 7, true);
        Applicant a2 = new Applicant("S8888888H", "a2", 8, false);
        Applicant a3 = new Applicant("S9999999I", "a3", 9, true);

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

        // List to store all projects
        List<Project> projects = new ArrayList<>();
        projects.add(proj1);
        projects.add(proj2);
        projects.add(proj3);

        // Testing Project Team
        ProjectTeam t1 = projectTeamController.createProjectTeam(projects.get(0));
        ProjectTeam t2 = projectTeamController.createProjectTeam(projects.get(1));
        ProjectTeam t3 = projectTeamController.createProjectTeam(projects.get(2));

        OfficerRegistration r1;

        // Assigning project manager
        try {
            projectTeamController.assignProjectManager(t1, m1, false);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            projectTeamController.assignProjectManager(t1, m2, false);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            r1 = officerRegistrationController.createRegistration(t1, o1);
            projectTeamController.addRegistration(t1, r1);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        

        System.out.println(projectTeamController.toString(t1));

        /* -------------------------------------- For HdbManager -------------------------------------- */
        //int assignProject(ProjectTeam team, HdbManager manager);

        //boolean isInTeam(ProjectTeam team, HdbManager manager);
        /* -------------------------------------- End HdbManager -------------------------------------- */

        /* -------------------------------------- For HdbOfficer -------------------------------------- */
        //int assignProject(ProjectTeam team, HdbOfficer officer);

        //boolean isInTeam(ProjectTeam team, HdbOfficer officer);
        /* -------------------------------------- End HdbOfficer -------------------------------------- */

        /* ---------------------------------- For OfficerRegistration --------------------------------- */
        //int addRegistration(ProjectTeam team, OfficerRegistration registration);

        //List<OfficerRegistration> retrieveOfficerRegistrations(ProjectTeam team);
        /* ---------------------------------- End OfficerRegistration --------------------------------- */

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
}
