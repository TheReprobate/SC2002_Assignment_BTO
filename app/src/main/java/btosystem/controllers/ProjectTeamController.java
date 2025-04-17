package btosystem.controllers;

import java.util.List;
import java.util.stream.Collectors;

import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;

import btosystem.classes.HdbOfficer;
import btosystem.classes.HdbManager;
import btosystem.classes.OfficerRegistration;

import btosystem.controllers.interfaces.ProjectTeamOperations;

/**
 * Controller for ProjectTeam class that implements ProjectTeamOperations interface.
 */
public class ProjectTeamController implements ProjectTeamOperations{

    /**
     * Constructor for creating ProjectTeam.
     *
     * @param proj Project that this ProjectTeam is assigned to
     * @return ProjectTeam object created
     */
    @Override
    public ProjectTeam createProjectTeam(Project proj) {
        ProjectTeam projTeam = new ProjectTeam(proj);
        return projTeam;
    }

    /* -------------------------------------- For HdbManager -------------------------------------- */
    /**
     * Assigns the HdbManager into the ProjectTeam
     *
     * @param team ProjectTeam to assign HdbManager to
     * @param manager HdbManager to be assigned
     * @return 1 if successful, -1 if issues with team, -2 if issues with manager
     */
    @Override
    public int assignProject(ProjectTeam team, HdbManager manager) {
        try {
            team.setManager(manager);
            return 1;
        }
        catch(Exception e){
            return -1;
        }
    }

    /**
     * Checks if HdbManager is part of this ProjectTeam
     *
     * @param team ProjectTeam to check
     * @param manager HdbManager to look for
     * @return true if successful, false if unsucessful
     */
    @Override
    public boolean isInTeam(ProjectTeam team, HdbManager manager) {
        return team.getManager().equals(manager);
    }
    /* -------------------------------------- End HdbManager -------------------------------------- */

    /* -------------------------------------- For HdbOfficer -------------------------------------- */
    /**
     * Assigns the HdbOfficer into the ProjectTeam
     *
     * @param team ProjectTeam to assign HdbOfficer to
     * @param officer HdbOfficer to be assigned
     * @return 1 if successful, -1 if issues with team, -2 if issues with officer
     */
    @Override
    public int assignProject(ProjectTeam team, HdbOfficer officer) {
        try {
            team.assignOfficer(officer);
            return 1;
        }
        catch(Exception e){
            return -1;
        }
    }

    /**
     * Checks if HdbOfficer is part of this ProjectTeam
     *
     * @param team ProjectTeam to check
     * @param officer Officer to look for
     * @return true if successful, false if unsucessful
     */
    @Override
    public boolean isInTeam(ProjectTeam team, HdbOfficer officer) {
        List<HdbOfficer> officerList = team.getOfficers();
        return officerList.contains(officer);
    }
    /* -------------------------------------- End HdbOfficer -------------------------------------- */
    
    // For OfficerRegistration
    /* ---------------------------------- For OfficerRegistration --------------------------------- */
    /**
     * Adds OfficerRegistration to this ProjectTeam
     *
     * @param team ProjectTeam to check
     * @param registration OfficerRegistration to look for
     * @return 1 if successful, -1 if issues with team, -2 if issues with registration
     */
    @Override
    public int addRegistration(ProjectTeam team, OfficerRegistration registration) {
        try {
            team.addOfficerRegistration(registration);
            return 1;
        }
        catch(Exception e){
            return -1;
        }
    }
    
    /**
     * Retrieves List of OfficerRegistration associated with this ProjectTeam
     *
     * @param team ProjectTeam to retrieve Officer Registrations List
     * @return List of Officer Registrations associated with ProjectTeam
     */
    @Override
    public List<OfficerRegistration> retrieveOfficerRegistrations(ProjectTeam team) {
        return team.getOfficerRegistrations();
    }
    /* ---------------------------------- End OfficerRegistration --------------------------------- */

    /**
     * Compiles ProjectTeam data into a single string for output
     *
     * @param data ProjectTeam data to compile
     * @return Formatted string with all relevant ProjectTeam data
     */
    @Override
    public String toString(ProjectTeam data) {
        String projName = data.getProject().getName();
        String managerName = data.getManager().getName();

        String officers = data.getOfficers()
                                .stream()
                                .map(officer -> officer.getName())
                                .collect(Collectors.joining("\n"));

        String registrations = data.getOfficerRegistrations()
                                .stream()
                                .map(registration -> registration.getOfficer().getName())
                                .collect(Collectors.joining("\n"));

        return 
        "Project Name           : \n" + projName        + "\n" +
        "Manager                : \n" + managerName     + "\n" +
        "Officers               : \n" + officers        + "\n" +
        "Pending registrations  : \n" + registrations   + "\n";
    }
}
