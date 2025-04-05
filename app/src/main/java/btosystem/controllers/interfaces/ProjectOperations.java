package btosystem.controllers.interfaces;

import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;
import java.util.HashMap;
import java.util.List;

public interface ProjectOperations extends MapToStringParser<Project>, CleanupOperations<Project> {
    Project createProject(String name, Neighborhood neighborhood, long openTime, long closeTime);

    Project retrieveProject(HashMap<String, Project> projects, String name);

    ProjectTeam retrieveProjectTeam(Project project);

    List<Enquiry> retrieveEnquiries(Project project);

    int addEnquiry(Project project, Enquiry enquiry);

    int addApplication(Project project, BtoApplication application);

    int updateUnitCount(Project project, FlatType flatType, int count);

    int deleteProject(List<Project> projects, Project project);

    boolean projectExist(List<Project> projects, String name);

    String toString(HashMap<String, Project> projects);
}
