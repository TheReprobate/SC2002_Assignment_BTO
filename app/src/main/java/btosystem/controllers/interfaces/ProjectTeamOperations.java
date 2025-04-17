package btosystem.controllers.interfaces;

import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import java.util.List;

public interface ProjectTeamOperations extends ToString<ProjectTeam> {
    ProjectTeam createProjectTeam(Project project);

    List<OfficerRegistration> retrieveOfficerRegistrations(ProjectTeam team);
    
    Project retrieveAssignedProject(ProjectTeam team);

    int assignProject(ProjectTeam team, HdbManager manager);

    int assignProject(ProjectTeam team, HdbOfficer officer);

    boolean isInTeam(ProjectTeam team, HdbManager manager);

    boolean isInTeam(ProjectTeam team, HdbOfficer officer);

    boolean hasMaxOfficers(ProjectTeam team);
    
    boolean hasManager(ProjectTeam team);

    int addRegistration(ProjectTeam team, OfficerRegistration registration);

}
