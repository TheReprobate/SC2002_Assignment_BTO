package btosystem.service.hdbofficer;

import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.operations.interfaces.BtoApplicationOperations;
import btosystem.operations.interfaces.EnquiryOperations;
import btosystem.operations.interfaces.OfficerRegistrationOperations;
import btosystem.operations.interfaces.ProjectOperations;
import btosystem.operations.interfaces.ProjectTeamOperations;
import btosystem.operations.interfaces.UserOperations;
import btosystem.service.applicant.ApplicantProjectService;
import btosystem.utils.DataManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class handling project-related operations for HDB officers.
 * Extends {@link ApplicantProjectService} to provide additional
 * officer-specific functionalities for managing BTO projects.
 */
public class HdbOfficerProjectService extends ApplicantProjectService {

    /**
     * Constructs a new {@code HdbOfficerProjectService} with the necessary
     * dependencies for managing project-related data and operations.
     *
     * @param dataManager             Data management operations.
     * @param applicationOperations   BTO application operations.
     * @param enquiryOperations       Enquiry operations.
     * @param registrationOperations  Officer registration operations.
     * @param projectTeamOperations   Project team operations.
     * @param userOperations          User operations.
     * @param projectOperations       Project operations.
     */
    public HdbOfficerProjectService (
            DataManager dataManager,
            BtoApplicationOperations applicationOperations,
            EnquiryOperations enquiryOperations,
            OfficerRegistrationOperations registrationOperations,
            ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations,
            ProjectOperations projectOperations)
    {
        super(
            dataManager,
            applicationOperations,
            enquiryOperations,
            registrationOperations,
            projectTeamOperations,
            userOperations,
            projectOperations
        );
    }

    /**
     * Retrieves a list of all BTO projects currently stored in the system.
     *
     * @return A {@code List} of {@link Project} objects representing all BTO projects.
     */
    public List<Project> getProjects() {
        return dataManager.getProjects();
    }

    /**
     * Retrieves a list of BTO projects that were launched within a specified date range.
     *
     * @param start The starting {@code LocalDate} (inclusive) of the date range.
     * @param end   The ending {@code LocalDate} (inclusive) of the date range.
     * @return A {@code List} of {@link Project} objects that were launched between the
     * specified start and end dates.
     */
    public List<Project> getProjects(LocalDate start, LocalDate end) {
        return projectManager.filterProject(getProjects(), start, end);
    }

    /**
     * Retrieves a list of projects that the given HDB officer is currently working on.
     * This is determined by the project teams the officer is a member of.
     *
     * @param user The {@link HdbOfficer} for whom to retrieve the working projects.
     * @return A {@code List} of {@link Project} objects that the officer is involved in.
     */
    public List<Project> getWorkingProject(HdbOfficer user) {
        List<Project> projects = new ArrayList<>();
        List<ProjectTeam> teams = userManager.retrieveTeams(user);
        for (ProjectTeam t: teams) {
            Project p = projectTeamManager.retrieveAssignedProject(t);
            projects.add(p);
        }
        return projects;
    }

    /**
     * Retrieves the project that the given HDB officer's current team is assigned to
     * and is currently open for application.
     *
     * @param user The {@link HdbOfficer} for whom to retrieve the current project.
     * @return The {@link Project} object that the officer is currently working on,
     * or {@code null} if the officer is not currently assigned to an open project.
     */
    public Project getCurrentProject(HdbOfficer user) {
        ProjectTeam currentTeam = getCurrentTeam(user);
        if (currentTeam == null) {
            return null;
        }
        Project projectInCharge = projectTeamManager.retrieveAssignedProject(currentTeam);
        return projectInCharge;
    }

    /**
     * Retrieves the current project team that the given HDB officer is actively working on.
     * A team is considered the current team if it is assigned to a project that is currently open
     * for application. If the officer is not currently assigned to any open project, this method returns {@code null}.
     *
     * @param user The {@link HdbOfficer} object for whom to retrieve the current team.
     * @return The {@link ProjectTeam} object of the officer's current team, or {@code null} if none.
     */
    private ProjectTeam getCurrentTeam(HdbOfficer user) {
        List<ProjectTeam> teams = userManager.retrieveTeams(user);
        for (ProjectTeam t: teams) {
            Project p = projectTeamManager.retrieveAssignedProject(t);
            if (projectManager.isOpen(p)) {
                return t;
            }
        }
        return null;
    }
}
