package btosystem.service.hdbmanager;

import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;
import btosystem.operations.interfaces.BtoApplicationOperations;
import btosystem.operations.interfaces.EnquiryOperations;
import btosystem.operations.interfaces.OfficerRegistrationOperations;
import btosystem.operations.interfaces.ProjectOperations;
import btosystem.operations.interfaces.ProjectTeamOperations;
import btosystem.operations.interfaces.UserOperations;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class providing functionalities for HDB managers to manage projects.
 * This includes creating, editing, viewing, and deleting projects.
 */
public class HdbManagerProjectService extends Service {

    /**
     * Constructs a new HdbManagerProjectService with the necessary dependencies.
     *
     * @param dataManager Data management operations.
     * @param applicationOperations BTO application related operations.
     * @param enquiryOperations Enquiry related operations.
     * @param registrationOperations Officer registration related operations.
     * @param projectTeamOperations Project team related operations.
     * @param userOperations User related operations.
     * @param projectOperations Project related operations.
     */
    public HdbManagerProjectService(
            DataManager dataManager,
            BtoApplicationOperations applicationOperations,
            EnquiryOperations enquiryOperations,
            OfficerRegistrationOperations registrationOperations,
            ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations,
            ProjectOperations projectOperations) {
        super(dataManager,
            applicationOperations,
            enquiryOperations,
            registrationOperations,
            projectTeamOperations,
            userOperations,
            projectOperations);
    }

    /**
     * Retrieves all existing projects.
     *
     * @return A list of all {@link Project} objects.
     */
    public List<Project> getProject() {
        return dataManager.getProjects();
    }

    /**
     * Retrieves a list of projects created by a specific HDB manager.
     *
     * @param user The {@link HdbManager} whose created projects are to be retrieved.
     * @return A list of {@link Project} objects created by the specified manager.
     */
    public List<Project> getCreatedProject(HdbManager user) {
        return userManager.retrieveCreatedProjects(user);
    }

    /**
     * Retrieves the projects that the given HDB manager is currently assigned to.
     *
     * @param user The {@link HdbManager} whose current projects are to be retrieved.
     * @return A list of {@link Project} objects the manager is currently managing,
     *          or {@code null} if the manager is not currently assigned to any project.
     * @throws Exception If an error occurs during the retrieval process.
     */
    public List<Project> getCurrentProject(HdbManager user) throws Exception {
        List<ProjectTeam> teams = userManager.retrieveTeams(user);
        List<Project> projects = new ArrayList<>();
        for (ProjectTeam t : teams) {
            Project projectInCharge = projectTeamManager.retrieveAssignedProject(t);
            projects.add(projectInCharge);
        }
        if (projects.size() <= 0) {
            return null;
        }
        return projects;
    }

    /**
     * Generates a report of all BTO applications for a specific project.
     * Checks if the requesting manager has access to the project before generating the report.
     *
     * @param user The {@link HdbManager} requesting the report.
     * @param project The {@link Project} for which the report is to be generated.
     * @return A string representation of the BTO applications for the project.
     * @throws Exception If the manager does not have access to the project.
     */
    public String displayReport(HdbManager user, Project project) throws Exception {
        if (!hasProjectAccess(user, project)) {
            throw new Exception("Access Denied. Not allowed to access this project. ");
        }
        List<BtoApplication> applications = projectManager.retrieveApplications(project);
        return applicationManager.toString(applications);
    }

    /**
     * Creates a new BTO project.
     * It checks if a project with the given name already exists and if the provided
     * open and close times are valid. The newly created project is then associated
     * with the creating HDB manager.
     *
     * @param user The {@link HdbManager} creating the project.
     * @param name The name of the new project.
     * @param neighborhood The {@link Neighborhood} of the new project.
     * @param openTime The date when applications for the project will open.
     * @param closeTime The date when applications for the project will close.
     * @throws Exception If a project with the given name already exists, or if the
     *                  open or close times are invalid.
     */
    public void createProject(HdbManager user, 
                            String name, 
                            Neighborhood neighborhood, 
                            LocalDate openTime, 
                            LocalDate closeTime) throws Exception {
        if (projectExist(name)) {
            return;
        }

        List<Project> projects = dataManager.getProjects();
        Project project = projectManager
                        .createProject(name, neighborhood, openTime, closeTime, user);
        if (!hasValidTime(openTime, closeTime)) {
            return;
        }
        List<Project> managerCreatedProjects = userManager.retrieveCreatedProjects(user);
        
        projectManager.addProject(projects, project);
        projectManager.addProject(managerCreatedProjects, project);
    }

    /**
     * Edits the neighborhood of an existing project.
     *
     * @param project The {@link Project} to be edited.
     * @param neighborhood The new {@link Neighborhood} for the project.
     * @throws Exception If an error occurs during the edit operation.
     */
    public void editProject(Project project, Neighborhood neighborhood) throws Exception {
        projectManager.editProject(project, neighborhood);
    }

    /**
     * Updates the number of available units for a specific flat type in a project.
     *
     * @param project The {@link Project} to be updated.
     * @param flatType The {@link FlatType} to update the count for.
     * @param count The new number of units available for the specified flat type.
     * @throws Exception If an error occurs during the update operation.
     */
    public void editProject(Project project, FlatType flatType, int count) throws Exception {
        projectManager.updateUnitCount(project, flatType, count);
    }

    /**
     * Edits the visibility status of a project.
     *
     * @param project The {@link Project} to be edited.
     * @param visibility The new visibility status (true for visible, false for hidden).
     * @throws Exception If an error occurs during the edit operation.
     */
    public void editProject(Project project, boolean visibility) throws Exception {
        projectManager.editProject(project, visibility);
    }

    /**
     * Checks if a project with the given name already exists.
     *
     * @param name The name of the project to check.
     * @return {@code true} if a project with the given name exists, {@code false} otherwise.
     * @throws Exception If an error occurs during the check.
     */
    public boolean projectExist(String name) throws Exception {
        List<Project> projects = dataManager.getProjects();
        boolean exist = projectManager.projectExist(projects, name);
        if (exist) {
            throw new Exception("Project name already exist. ");
        }
        return exist;
    }

    /**
     * Validates if the provided open and close times for a project are valid.
     * Open time must be in the future, and close time must be after the open time.
     *
     * @param open The opening date for applications.
     * @param close The closing date for applications.
     * @return {@code true} if the times are valid.
     * @throws Exception If the open time is in the past or the close time is before the open time.
     */
    public boolean hasValidTime(LocalDate open, LocalDate close) throws Exception {
        if (open.isBefore(LocalDate.now())) {
            throw new Exception("Open time is in the past. "); 
        }
        if (close.isBefore(open)) {
            throw new Exception("Close time has to be after open time. "); 
        }
        return true;
    }

    /**
     * Checks if the given HDB manager has access to the specified project.
     * Access is granted if the manager is currently assigned to the project.
     *
     * @param user The {@link HdbManager} to check for access.
     * @param project The {@link Project} to check access for.
     * @return {@code true} if the manager has access to the project, {@code false} otherwise.
     * @throws Exception If an error occurs during the access check.
     */
    private boolean hasProjectAccess(HdbManager user, Project project) throws Exception {
        return getCurrentProject(user).contains(project);
    }

    /**
     * Deletes a project. This operation also removes all associated enquiries,
     * applications, and removes the project from the assigned project teams.
     * Only managers with access to the project can delete it.
     *
     * @param user The {@link HdbManager} attempting to delete the project.
     * @param project The {@link Project} to be deleted.
     * @throws Exception If the manager does not have access to the project.
     */
    public void deleteProject(HdbManager user, Project project) throws Exception {
        if (!hasProjectAccess(user, project)) {
            throw new Exception("Access Denied. Not allowed to access this project. ");
        }
        List<Enquiry> enquiries = projectManager.retrieveEnquiries(project);
        List<BtoApplication> applications = projectManager.retrieveApplications(project);        
        ProjectTeam projectTeam = projectManager.retrieveProjectTeam(project);
        List<HdbOfficer> officers = projectTeamManager.retrieveOfficerTeam(projectTeam);
        for (Enquiry e : enquiries) {
            enquiryManager.removeProject(e);
        }
        for (BtoApplication b : applications) {
            applicationManager.removeProject(b);
        }
        for (HdbOfficer o : officers) {
            List<ProjectTeam> officerTeams = userManager.retrieveTeams(o);
            projectTeamManager.removeProjectTeam(officerTeams, projectTeam);
        }
        HdbManager manager = projectTeamManager.retrieveManager(projectTeam);
        List<ProjectTeam> managerTeams = userManager.retrieveTeams(manager);
        projectTeamManager.removeProjectTeam(managerTeams, projectTeam);
        projectTeamManager.removeProject(projectTeam);
        projectManager.deleteProject(getProject(), project);
    }
}
