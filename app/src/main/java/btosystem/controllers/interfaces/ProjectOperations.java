package btosystem.controllers.interfaces;

import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.ApplicationStatus;
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
        extends ListToString<Project> {

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
     * Filters projects based on their date range.
     *
     * @param projects the list of projects to filter
     * @param start the start date of the desired period (inclusive)
     * @param end the end date of the desired period (inclusive)
     * @return list of projects that fall within the specified date range
     */
    List<Project> filterProject(List<Project> projects, LocalDate start, LocalDate end);

    /**
     * Interface method for project team retrieval.
     * Details can be found in {@link btosystem.controllers.ProjectController}
     */
    ProjectTeam retrieveProjectTeam(Project project);

    /**
     * Interface method for setting project team .
     * Details can be found in {@link btosystem.controllers.ProjectController}
     */
    int setProjectTeam(Project project, ProjectTeam team);

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
     * Interface method for updating project's visibility.
     * Details can be found in {@link btosystem.controllers.ProjectController}
     */
    int editProject(Project project, boolean visibility);

    /**
     * Interface method for project deletion.
     * Details can be found in {@link btosystem.controllers.ProjectController}
     * @throws Exception 
     */
    int deleteProject(List<Project> projects, Project project) throws Exception;

    /**
     * Interface method for checking if project exists.
     * Details can be found in {@link btosystem.controllers.ProjectController}
     */
    boolean projectExist(List<Project> projects, String name);

    /**
     * Checks if a project has available units of the specified flat type.
     *
     * @param project the project to check
     * @param flatType the type of flat to check availability for
     * @return true if there are available units of the specified type, false otherwise
     */
    boolean unitHasSlots(Project project, FlatType flatType);

    /**
     * Checks if a project is currently open for applications.
     *
     * @param project the project to check
     * @return true if the project is open, false otherwise
     */
    boolean isOpen(Project project);

    /**
     * Determines if two projects have overlapping time periods.
     *
     * @param firstProject the first project to compare
     * @param secondProject the second project to compare
     * @return true if the projects' time periods overlap, false otherwise
     */
    boolean hasTimeOverlap(Project firstProject, Project secondProject);

    /**
     * Adds a new project to the projects list.
     *
     * @param projects the list of projects to add to
     * @param project the project to add
     * @return the index at which the project was added, or -1 if unsuccessful
     */
    int addProject(List<Project> projects, Project project);

    /**
     * Decreases the available unit count for a specific flat type in a project.
     *
     * @param project the project to update
     * @param flatType the flat type whose count should be decreased
     * @return the new count of available units after decrement
     */
    int decreaseUnitCount(Project project, FlatType flatType);

    /**
     * Gets the list of flat types that still have available units in a project.
     *
     * @param project the project to check
     * @return list of available flat types (may be empty)
     */
    List<FlatType> getAvailableFlatTypes(Project project);
}
