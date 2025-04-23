package btosystem.controllers;

import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.RegistrationStatus;
import btosystem.controllers.interfaces.ProjectTeamOperations;
import java.util.List;

/**
 * Controller for ProjectTeam class that implements ProjectTeamOperations interface.
 */
public class ProjectTeamController implements ProjectTeamOperations {
    private static final int MAX_OFFICER = 10;
    /**
     * Constructor for creating ProjectTeam.
     *
     * @param proj Project that this ProjectTeam is assigned to
     * @return ProjectTeam object created
     * @throws Exception Exception thrown if ProjectTeam already exists or project does not exist
     */

    @Override
    public ProjectTeam createProjectTeam(Project proj) throws Exception {
        if (proj == null) {
            // Project object does not exist
            throw new Exception("Project object does not exist.");
        }
        // if (proj.getProjectTeam() != null) {
        //     // ProjectTeam object already exists
        //     throw new Exception("ProjectTeam object already exists.");
        // }
        ProjectTeam projTeam = new ProjectTeam(proj);
        return projTeam;
    }

    /**
     * Assigns the HdbManager into the ProjectTeam.
     *
     * @param team ProjectTeam to assign HdbManager to
     * @param manager HdbManager to be assigned
     * @return 1 if successful, throws relevant error messages if not
     * @throws Exception Exception thrown if team or manager obejcts does not exist
     */

    @Override
    public int assignProject(ProjectTeam team, HdbManager manager) throws Exception {
        if (team == null) {
            // Team object does not exist
            throw new Exception("Team object does not exist.");
        }
        if (manager == null) {
            // Manager object does not exist
            throw new Exception("Manager object does not exist.");
        }
        if (team.getManager() != null) {
            // Manager already exists
            // if (!overwrite) {
            //     throw new Exception("Manager already exists. Overwrite?");
            // }
            throw new Exception("Manager already exists. ");
        }
        // Successful
        team.setManager(manager);
        return 1;
    }

    /**
     * Assigns the HdbOfficer into the ProjectTeam.
     *
     * @param team ProjectTeam to assign HdbOfficer to
     * @param officer HdbOfficer to be assigned
     * @return 1 if successful, throws relevant error messages if not
     * @throws Exception Exception thrown if team or officer does not exist, 
     *                      officer duplicate is found,
     *                      officer has not applied for project,
     *                      or officer registration status is not accepted
     */

    @Override
    public int assignProject(ProjectTeam team, HdbOfficer officer) throws Exception {
        if (team == null) {
            // Team object does not exist.
            throw new Exception("Team object does not exist.");
        }
        if (officer == null) {
            // Officer does not exist
            throw new Exception("Officer object does not exist.");
        }
        if (team.getOfficers().stream()
                    .anyMatch(o -> o.equals(officer))) {
            // Officer already in Project Team
            throw new Exception("Officer already in Project Team.");
        }
        if (!team.getOfficerRegistrations().stream()
                    .anyMatch(registration -> registration.getOfficer().equals(officer))) {
            // No registration, cannot assign
            throw new Exception("Officer has not applied for Project.");
        }

        OfficerRegistration matchedRegistration = null;

        for (OfficerRegistration reg : team.getOfficerRegistrations()) {
            if (reg.getOfficer().equals(officer)) {
                matchedRegistration = reg;
                break;
            }
        }

        if (matchedRegistration == null) {
            // No registration, cannot assign
            throw new Exception("Officer has not applied for Project.");
        }

        if (matchedRegistration.getStatus() != RegistrationStatus.ACCEPTED) {
            // Registration not accepted (Yet)
            throw new Exception("Officer Registration is not Accepted for this project.");
        }

        team.assignOfficer(officer);
        return 1;
    }

    /**
     * Checks if HdbManager is part of this ProjectTeam.
     *
     * @param team ProjectTeam to check
     * @param manager HdbManager to look for
     * @return true if successful, false if unsucessful
     * @throws Exception Exception thrown if team or manager does not exist
     */

    @Override
    public boolean isInTeam(ProjectTeam team, HdbManager manager) throws Exception {
        if (team == null) {
            // Team object does not exist
            throw new Exception("Team object does not exist.");
        }
        if (manager == null) {
            // Manager object does not exist
            throw new Exception("Manager object does not exist.");
        }
        return team.getManager().equals(manager);
    }
    

    /**
     * Checks if HdbOfficer is part of this ProjectTeam.
     *
     * @param team ProjectTeam to check
     * @param officer Officer to look for
     * @return true if successful, false if unsucessful
     * @throws Exception Exception thrown if team or officer does not exist
     */

    @Override
    public boolean isInTeam(ProjectTeam team, HdbOfficer officer) throws Exception {
        if (team == null) {
            // Team object does not exist
            throw new Exception("Team object does not exist.");
        }
        if (officer == null) {
            // Officer object does not exist
            throw new Exception("Officer object does not exist.");
        }
        List<HdbOfficer> officerList = team.getOfficers();
        return officerList.contains(officer);
    }
    
    /**
     * Adds OfficerRegistration to this ProjectTeam.
     *
     * @param team ProjectTeam to check
     * @param registration OfficerRegistration to look for
     * @return 1 if successful, throws relevant error messages if not
     * @throws Exception Exception thrown if team or registration does not exist, 
     *                                            or registration duplicate is found
     */

    @Override
    public int addRegistration(ProjectTeam team, 
                                OfficerRegistration registration) throws Exception {
        if (team == null) {
            // Team object does not exist.
            throw new Exception("Team object does not exist.");
        }
        if (registration == null) {
            // Registration object does not exist
            throw new Exception("Registration object does not exist.");
        }
        if (team.getOfficerRegistrations().contains(registration)) {
            // Registration already exists
            throw new Exception("Registration already exists.");
        }
        // Successful
        team.addOfficerRegistration(registration);
        return 1;
    }
    
    /**
     * Retrieves List of OfficerRegistration associated with this ProjectTeam.
     *
     * @param team ProjectTeam to retrieve Officer Registrations List
     * @return List of Officer Registrations associated with ProjectTeam
     */
    @Override
    public List<OfficerRegistration> retrieveOfficerRegistrations(ProjectTeam team) {
        return team.getOfficerRegistrations();
    }

    /**
     * Compiles ProjectTeam data into a single string for output.
     *
     * @param data ProjectTeam data to compile
     * @return Formatted string with all relevant ProjectTeam data
     */

    @Override
    public String toString(ProjectTeam data) {
        if (data == null) {
            return "Project Team is empty.";
        }

        String managerName;
        try {
            managerName = data.getManager().getName();
        } catch (Exception e) {
            managerName = "No manager assigned to project team.";
        }

        List<HdbOfficer> listOfficers = data.getOfficers();
        String officers = "";

        for (int i = 0; i < listOfficers.size(); i++) {
            officers += (i + 1) + ") " + listOfficers.get(i).getName() + "\n";
        }

        if (officers.isBlank()) {
            officers = "No officer assigned to project team.";
        }

        List<OfficerRegistration> listRegistrations = data.getOfficerRegistrations();
        String registrations = "";

        for (int i = 0; i < listRegistrations.size(); i++) {
            registrations += (i + 1) + ") " 
                        + listRegistrations.get(i).getOfficer().getName() + "\n";
        }

        if (registrations.isBlank()) {
            registrations = "No Officers applied to project team.";
        }
        return 
            "Manager                : \n" + managerName     + "\n\n" 
            + "Officers               : \n" + officers        + "\n\n" 
            + "Pending registrations  : \n" + registrations   + "\n";
    }

    /**
     * Retrieves team's assigned project.
     *
     * @param team ProjectTeam data to retrieve with
     * @return Project object associated with ProjectTeam
     */

    @Override
    public Project retrieveAssignedProject(ProjectTeam team) {
        return team.getProject();
    }

    /**
     * Checks if team has max officer occupancy.
     *
     * @param team ProjectTeam data to retrieve with
     * @return boolean if officer's occupancy size lesser than MAX_OFFICER size
     */

    @Override
    public boolean hasMaxOfficers(ProjectTeam team) {
        return team.getOfficers().size() > MAX_OFFICER;
    }

    /**
     * Checks if team has assigned manager.
     *
     * @param team ProjectTeam data to retrieve with
     * @return boolean if team has assigned manager
     */

    @Override
    public boolean hasManager(ProjectTeam team) {
        return team.getManager() != null;
    }
    
    /**
     * Sets project to empty
     *
     * @param projectTeam The projectTeam to edit
     */
    
    @Override
    public void removeProject(ProjectTeam projectTeam) {
        projectTeam.setProject(null);
    }

    @Override
    public void addProjectTeam(List<ProjectTeam> projectTeams, ProjectTeam team) {
        projectTeams.add(team);
    }

    @Override
    public void removeProjectTeam(List<ProjectTeam> projectTeams, ProjectTeam team) {
        projectTeams.remove(team);
    }

    @Override
    public List<HdbOfficer> retrieveOfficerTeam(ProjectTeam team) {
        return team.getOfficers();
    }

    @Override
    public HdbManager retrieveManager(ProjectTeam team) {
        return team.getManager();
    }
    
}
