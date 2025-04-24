package btosystem.operations.interfaces;

import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import java.util.List;

/**
 * An interface that extends {@link ToString},
 * providing various operations related to ProjectTeam management.
 */
public interface ProjectTeamOperations extends 
                ToString<ProjectTeam>
{
    /**
     * Interface method for ProjectTeam creation.
     * Details can be found in {@link btosystem.operations.ProjectTeamController}
     */

    public ProjectTeam createProjectTeam(Project project) throws Exception;

    /**
     * Interface method for assigning of HdbManager to ProjectTeam.
     * Details can be found in {@link btosystem.operations.ProjectTeamController}
     */

    public int assignProject(ProjectTeam team, HdbManager manager) throws Exception;

    /**
     * Interface method for assigning of HdbOfficer to ProjectTeam.
     * Details can be found in {@link btosystem.operations.ProjectTeamController}
     */
    
    public int assignProject(ProjectTeam team, HdbOfficer officer) throws Exception;

    /**
     * Interface method to check if HdbManager is part of ProjectTeam.
     * Details can be found in {@link btosystem.operations.ProjectTeamController}
     */

    public boolean isInTeam(ProjectTeam team, HdbManager manager) throws Exception;

    /**
     * Interface method to check if HdbManager is part of ProjectTeam.
     * Details can be found in {@link btosystem.operations.ProjectTeamController}
     */

    public boolean isInTeam(ProjectTeam team, HdbOfficer officer) throws Exception;

    /**
     * Interface method for assigning of OfficerRegistration to ProjectTeam.
     * Details can be found in {@link btosystem.operations.ProjectTeamController}
     */

    public int addRegistration(ProjectTeam team, OfficerRegistration registration) throws Exception;

    /**
     * Interface method for retrieval of OfficerRegistrations by ProjectTeam object.
     * Details can be found in {@link btosystem.operations.ProjectTeamController}
     */

    public List<OfficerRegistration> retrieveOfficerRegistrations(ProjectTeam team);
    
    /**
     * Sets project to empty.
     *
     * @param projectTeam The projectTeam to edit
     */
    void removeProject(ProjectTeam projectTeam);

    /**
     * Adds a ProjectTeam to the provided list of ProjectTeams.
     *
     * @param projectTeams The list of ProjectTeams to which the team will be added.
     * @param team The ProjectTeam to add.
     */
    void addProjectTeam(List<ProjectTeam> projectTeams, ProjectTeam team); 

    /**
     * Removes a ProjectTeam from the provided list of ProjectTeams.
     *
     * @param projectTeams The list of ProjectTeams from which the team will be removed.
     * @param team The ProjectTeam to remove.
     */
    void removeProjectTeam(List<ProjectTeam> projectTeams, ProjectTeam team);

    /**
     * Retrieves the list of HdbOfficers assigned to the specified ProjectTeam.
     *
     * @param team The ProjectTeam whose officers are to be retrieved.
     * @return A list of HdbOfficer objects assigned to the team.
     */
    List<HdbOfficer> retrieveOfficerTeam(ProjectTeam team);

    /**
     * Retrieves the HdbManager assigned to the specified ProjectTeam.
     *
     * @param team The ProjectTeam whose manager is to be retrieved.
     * @return The HdbManager assigned to the team, or null if none is assigned.
     */
    HdbManager retrieveManager(ProjectTeam team);    

    /**
     * Retrieves the Project assigned to the specified ProjectTeam.
     *
     * @param team The ProjectTeam whose assigned project is to be retrieved.
     * @return The Project assigned to the team, or null if none is assigned.
     */
    public Project retrieveAssignedProject(ProjectTeam team);


    /**
     * Interface method to check if team is at max officers occupancy.
     * Details can be found in {@link btosystem.operations.ProjectTeamController}
     */

    public boolean hasMaxOfficers(ProjectTeam team);
    
    /**
     * Interface method to check if team has manager assigned.
     * Details can be found in {@link btosystem.operations.ProjectTeamController}
     */

    public boolean hasManager(ProjectTeam team);

     /* ---------------------------------- End OfficerRegistration --------------------------------- */
}
