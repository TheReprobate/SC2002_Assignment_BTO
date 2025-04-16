package btosystem.controllers.interfaces;

import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;
import java.time.LocalDate;
import java.util.List;

/**
 * An interface that extends {@link ListToString}
 * and {@link CleanupOperations} for {@link Project} objects,
 * providing various operations related to project management.
 */
public interface ProjectOperations
        extends ListToString<Project>, CleanupOperations<Project> {

    /**
     * Interface method for project creation.
     * Details can be found in {@link btosystem.controllers.ProjectController}
     */
    Project createProject(String name, Neighborhood neighborhood,
                          LocalDate openTime, LocalDate closeTime, HdbManager hdbManager);

    /**
     * Interface method for project retrieval by name.
     * Details can be found in {@link btosystem.controllers.ProjectController}
     */
    Project retrieveProject(List<Project> projects, String name);

    /**
     * Interface method for project retrieval by index.
     * Details can be found in {@link btosystem.controllers.ProjectController}
     */
    Project retrieveProject(List<Project> projects, int index);

    /**
     * Interface method for project filtering by neighborhood.
     * Details can be found in {@link btosystem.controllers.ProjectController}
     */
    List<Project> filterProject(List<Project> projects, Neighborhood neighborhood);

    /**
     * Interface method for project filtering by visibility.
     * Details can be found in {@link btosystem.controllers.ProjectController}
     */
    List<Project> filterProject(List<Project> projects, boolean visible);

    /**
     * Interface method for project team retrieval.
     * Details can be found in {@link btosystem.controllers.ProjectController}
     */
    ProjectTeam retrieveProjectTeam(Project project);

    /**
     * Interface method for project's enquiries retrieval.
     * Details can be found in {@link btosystem.controllers.ProjectController}
     */
    List<Enquiry> retrieveEnquiries(Project project);

    /**
     * Interface method for project's BTO application retrieval.
     * Details can be found in {@link btosystem.controllers.ProjectController}
     */
    List<BtoApplication> retrieveApplications(Project project);

    /**
     * Interface method for updating project's unit count.
     * Details can be found in {@link btosystem.controllers.ProjectController}
     */
    int updateUnitCount(Project project, FlatType flatType, int count);

    /**
     * Interface method for updating project's neighborhood.
     * Details can be found in {@link btosystem.controllers.ProjectController}
     */
    int editProject(Project project, Neighborhood neighborhood);

    /**
     * Interface method for project's application period.
     * Details can be found in {@link btosystem.controllers.ProjectController}
     */
    int editProject(Project project, LocalDate openTime, LocalDate closeTime);

    /**
     * Interface method for project deletion.
     * Details can be found in {@link btosystem.controllers.ProjectController}
     */
    int deleteProject(List<Project> projects, Project project);

    /**
     * Interface method for checking if project exists.
     * Details can be found in {@link btosystem.controllers.ProjectController}
     */
    boolean projectExist(List<Project> projects, String name);
}
