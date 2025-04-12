package btosystem.controllers.interfaces;

import btosystem.classes.*;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;

import java.time.LocalDate;
import java.util.List;

public interface ProjectOperations
        extends ListToString<Project>, CleanupOperations<Project> {
    Project createProject(String name, Neighborhood neighborhood, LocalDate openTime, LocalDate closeTime, HdbManager hdbManager);

    Project retrieveProject(List<Project> projects, String name);

    Project retrieveProject(List<Project> projects, int index);

    List<Project> filterProject(List<Project> projects, Neighborhood neighborhood);

    List<Project> filterProject(List<Project> projects, boolean visible);

    ProjectTeam retrieveProjectTeam(Project project);

    List<Enquiry> retrieveEnquiries(Project project);

    List<BtoApplication> retrieveApplications(Project project);

    int updateUnitCount(Project project, FlatType flatType, int count);

    int editProject(Project project, Neighborhood neighborhood);

    int editProject(Project project, LocalDate openTime, LocalDate closeTime);

    int deleteProject(List<Project> projects, Project project);

    boolean projectExist(List<Project> projects, String name);
}
