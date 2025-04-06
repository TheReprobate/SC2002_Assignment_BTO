package btosystem.controllers.interfaces;

import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;
import java.util.List;

public interface ProjectOperations
        extends ListToStringSerializer<Project>, CleanupOperations<Project> {
    Project createProject(String name, Neighborhood neighborhood, long openTime, long closeTime);

    Project retrieveProject(List<Project> projects, String name);

    Project retrieveProject(List<Project> projects, int index);

    List<Project> filterProject(List<Project> projects, Neighborhood neighborhood);

    List<Project> filterProject(List<Project> projects, boolean visible);

    ProjectTeam retrieveProjectTeam(Project project);

    List<Enquiry> retrieveEnquiries(Project project);

    List<BtoApplication> retrieveApplications(Project project);

    int addEnquiry(Project project, Enquiry enquiry);

    int addApplication(Project project, BtoApplication application);

    int updateUnitCount(Project project, FlatType flatType, int count);

    int editProject(Project project, Neighborhood neighborhood);

    int editProject(Project project, long openTime, long closeTime);

    int deleteProject(List<Project> projects, Project project);

    boolean projectExist(List<Project> projects, String name);
}
