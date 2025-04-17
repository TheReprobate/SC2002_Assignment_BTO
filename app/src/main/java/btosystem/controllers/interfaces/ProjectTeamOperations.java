package btosystem.controllers.interfaces;

import java.util.List;

import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;

import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;

/**
 * An interface that extends {@link ToString},
 * providing various operations related to ProjectTeam management.
 */
public interface ProjectTeamOperations extends ToString<ProjectTeam> 
{
    /**
     * Interface method for ProjectTeam creation.
     * Details can be found in {@link btosystem.controllers.ProjectTeamController}
     */
    ProjectTeam createProjectTeam(Project project);

    /* -------------------------------------- For HdbManager -------------------------------------- */
    /**
     * Interface method for assigning of HdbManager to ProjectTeam
     * Details can be found in {@link btosystem.controllers.ProjectTeamController}
     */
    public int assignProjectManager(ProjectTeam team, HdbManager manager, boolean overwrite) throws Exception;

    /**
     * Interface method to check if HdbManager is part of ProjectTeam
     * Details can be found in {@link btosystem.controllers.ProjectTeamController}
     */
    boolean isInTeam(ProjectTeam team, HdbManager manager);
    /* -------------------------------------- End HdbManager -------------------------------------- */
    
    /* -------------------------------------- For HdbOfficer -------------------------------------- */
    /**
     * Interface method for assigning of HdbOfficer to ProjectTeam
     * Details can be found in {@link btosystem.controllers.ProjectTeamController}
     */
    int assignProjectOfficer(ProjectTeam team, HdbOfficer officer) throws Exception;

    /**
     * Interface method to check if HdbManager is part of ProjectTeam
     * Details can be found in {@link btosystem.controllers.ProjectTeamController}
     */
    boolean isInTeam(ProjectTeam team, HdbOfficer officer);
    /* -------------------------------------- End HdbOfficer -------------------------------------- */

    /* ---------------------------------- For OfficerRegistration --------------------------------- */
    /**
     * Interface method for assigning of OfficerRegistration to ProjectTeam
     * Details can be found in {@link btosystem.controllers.ProjectTeamController}
     */
    public int addRegistration(ProjectTeam team, OfficerRegistration registration) throws Exception;

    /**
     * Interface method for retrieval of OfficerRegistrations by ProjectTeam object
     * Details can be found in {@link btosystem.controllers.ProjectTeamController}
     */
    List<OfficerRegistration> retrieveOfficerRegistrations(ProjectTeam team);
    /* ---------------------------------- End OfficerRegistration --------------------------------- */
}
