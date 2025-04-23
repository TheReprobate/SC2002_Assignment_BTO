package btosystem.controllers.interfaces;

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
                ToString<ProjectTeam>, 
                CleanupOperations<ProjectTeam> {
    /**
     * Interface method for ProjectTeam creation.
     * Details can be found in {@link btosystem.controllers.ProjectTeamController}
     */

    public ProjectTeam createProjectTeam(Project project) throws Exception;

    /**
     * Interface method for assigning of HdbManager to ProjectTeam.
     * Details can be found in {@link btosystem.controllers.ProjectTeamController}
     */

    public int assignProject(ProjectTeam team, HdbManager manager) throws Exception;

    /**
     * Interface method for assigning of HdbOfficer to ProjectTeam.
     * Details can be found in {@link btosystem.controllers.ProjectTeamController}
     */
    
    public int assignProject(ProjectTeam team, HdbOfficer officer) throws Exception;

    /**
     * Interface method to check if HdbManager is part of ProjectTeam.
     * Details can be found in {@link btosystem.controllers.ProjectTeamController}
     */

    public boolean isInTeam(ProjectTeam team, HdbManager manager) throws Exception;

    /**
     * Interface method to check if HdbManager is part of ProjectTeam.
     * Details can be found in {@link btosystem.controllers.ProjectTeamController}
     */

    public boolean isInTeam(ProjectTeam team, HdbOfficer officer) throws Exception;

    /**
     * Interface method for assigning of OfficerRegistration to ProjectTeam.
     * Details can be found in {@link btosystem.controllers.ProjectTeamController}
     */

    public int addRegistration(ProjectTeam team, OfficerRegistration registration) throws Exception;

    /**
     * Interface method for retrieval of OfficerRegistrations by ProjectTeam object.
     * Details can be found in {@link btosystem.controllers.ProjectTeamController}
     */

    public List<OfficerRegistration> retrieveOfficerRegistrations(ProjectTeam team);
    
    /**
     * Interface method for retrieval of Project by ProjectTeam object.
     * Details can be found in {@link btosystem.controllers.ProjectTeamController}
     */

    public Project retrieveAssignedProject(ProjectTeam team);

    /**
     * Interface method to check if team is at max officers occupancy.
     * Details can be found in {@link btosystem.controllers.ProjectTeamController}
     */

    public boolean hasMaxOfficers(ProjectTeam team);
    
    /**
     * Interface method to check if team has manager assigned.
     * Details can be found in {@link btosystem.controllers.ProjectTeamController}
     */

    public boolean hasManager(ProjectTeam team);
}
