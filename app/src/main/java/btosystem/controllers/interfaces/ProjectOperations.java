package btosystem.controllers.interfaces;

import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;
import java.util.HashMap;
import java.util.List;

public interface ProjectOperations
        extends MapToStringParser<Project>, CleanupOperations<Project>, ListToStringParser<Project> {
    Project createProject(String name, Neighborhood neighborhood, long openTime, long closeTime);

    Project retrieveProject(HashMap<String, Project> projects, String name);

    List<Project> filterProject(HashMap<String, Project> projects, Neighborhood neighborhood);

    List<Project> filterProject(HashMap<String, Project> projects, boolean visible);

    ProjectTeam retrieveProjectTeam(Project project);

    List<Enquiry> retrieveEnquiries(Project project);

    int addEnquiry(Project project, Enquiry enquiry);

    int addApplication(Project project, BtoApplication application);

    int updateUnitCount(Project project, FlatType flatType, int count);

    int editProject(Project project, Neighborhood neighborhood);

    int editProject(Project project, long openTime, long closeTime);

    int deleteProject(HashMap<String, Project> projects, Project project);

    boolean projectExist(HashMap<String, Project> projects, String name);

    String toString(HashMap<String, Project> projects);

    String toString(List<Project> projects);
}
