package btosystem.controllers;

import java.util.List;
import java.util.stream.Collectors;

import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;

import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
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
     * @param overwrite To force HdbManager to be overwritten, even if manager already exists
     * @return 1 if successful, throws relevant error messages if not
     * @throws Exception Exception thrown if team or manager does not exist, or manager already exists and overwrite is false
     */
    @Override
    public int assignProjectManager(ProjectTeam team, HdbManager manager, boolean overwrite) throws Exception{
        if(team == null) {
            // Team object does not exist
            throw new Exception("Team object does not exist.");
        }
        if(manager == null) {
            // Manager object does not exist
            throw new Exception("Manager object does not exist.");
        }
        if(team.getManager() != null) {
            // Manager already exists
            if(!overwrite) {
                throw new Exception("Manager already exists. Overwrite?");
            }
        }
        // Successful
        team.setManager(manager);
        return 1;
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
     * @return 1 if successful, throws relevant error messages if not
     * @throws Exception Exception thrown if team or officer does not exist, or officer duplicate is found
     */
    @Override
    public int assignProjectOfficer(ProjectTeam team, HdbOfficer officer) throws Exception{
        if(team == null) {
            // Team object does not exist.
            throw new Exception("Team object does not exist.");
        }
        if(officer == null) {
            // Officer does not exist
            throw new Exception("Officer object does not exist.");
        }
        if(team.getOfficers().contains(officer)) {
            // Registration already exists
            throw new Exception("Officer already part of team.");
        }
        // Successful
        team.assignOfficer(officer);
        return 1;
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
     * @return 1 if successful, throws relevant error messages if not
     * @throws Exception Exception thrown if team or registration does not exist, or registration duplicate is found
     */
    @Override
    public int addRegistration(ProjectTeam team, OfficerRegistration registration) throws Exception {
        if(team == null) {
            // Team object does not exist.
            throw new Exception("Team object does not exist.");
        }
        if(registration == null) {
            // Registration object does not exist
            throw new Exception("Registration object does not exist.");
        }
        if(team.getOfficerRegistrations().contains(registration)) {
            // Registration already exists
            throw new Exception("Registration already exists.");
        }
        // Successful
        team.addOfficerRegistration(registration);
        return 1;
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

        String managerName;
        try{
            managerName = data.getManager().getName();
        }
        catch(Exception e) {
            managerName = "No manager assigned to project team.";
        }

        String officers;
        try {
            officers = data.getOfficers()
                            .stream()
                            .map(officer -> officer.getName())
                            .collect(Collectors.joining("\n"));
            if(officers.isBlank()) {
                throw new Exception();
            }
        }
        catch(Exception e) {
            officers = "No officer assigned to project team.";
        }

        String registrations;
        try {
            registrations = data.getOfficerRegistrations()
                            .stream()
                            .map(registration -> registration.getOfficer().getName())
                            .collect(Collectors.joining("\n"));
            if(registrations.isBlank()) {
                throw new Exception();
            }
        }
        catch(Exception e) {
            registrations = "No Officer Registrations applied to project team.";
        } 

        return 
        "Project Name           : \n" + projName        + "\n\n" +
        "Manager                : \n" + managerName     + "\n\n" +
        "Officers               : \n" + officers        + "\n\n" +
        "Pending registrations  : \n" + registrations   + "\n";
    }
}
