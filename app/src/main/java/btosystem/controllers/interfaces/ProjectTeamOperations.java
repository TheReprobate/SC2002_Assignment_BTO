package btosystem.controllers.interfaces;

import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.ProjectTeam;
import java.util.List;

public interface ProjectTeamOperations extends ToStringParser<ProjectTeam> {
    ProjectTeam createProjectTeam();

    List<OfficerRegistration> retrieveOfficerRegistrations(ProjectTeam team);

    int assignProject(ProjectTeam team, HdbManager manager);

    int assignProject(ProjectTeam team, HdbOfficer officer);

    boolean isInTeam(ProjectTeam team, HdbManager manager);

    boolean isInTeam(ProjectTeam team, HdbOfficer officer);

    int addRegistration(ProjectTeam team, OfficerRegistration registration);
}
