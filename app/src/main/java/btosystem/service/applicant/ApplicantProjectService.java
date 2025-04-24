package btosystem.service.applicant;

import btosystem.classes.Project;
import btosystem.operations.interfaces.BtoApplicationOperations;
import btosystem.operations.interfaces.EnquiryOperations;
import btosystem.operations.interfaces.OfficerRegistrationOperations;
import btosystem.operations.interfaces.ProjectOperations;
import btosystem.operations.interfaces.ProjectTeamOperations;
import btosystem.operations.interfaces.UserOperations;
import btosystem.service.Service;
import btosystem.utils.DataManager;

import java.util.Iterator;
import java.util.List;

/**
 * Service class for applicants to interact with project-related information.
 * Provides functionalities such as viewing available and open BTO projects.
 */
public class ApplicantProjectService extends Service {

    /**
     * Constructs a new ApplicantProjectService with the necessary dependencies
     * to perform project-related operations for applicants.
     *
     * @param dataManager           Data management operations for accessing and storing data.
     * @param applicationOperations BTO application-related operations.
     * @param enquiryOperations     Operations for handling user enquiries.
     * @param registrationOperations Operations for officer registration 
     *                              (not directly used in this service but inherited).
     * @param projectTeamOperations Operations for managing project teams 
     *                              (not directly used in this service but inherited).
     * @param userOperations        Operations related to user management 
     *                              (not directly used in this service but inherited).
     * @param projectOperations     Operations specifically related to project management.
     */
    public ApplicantProjectService(
            DataManager dataManager,
            BtoApplicationOperations applicationOperations,
            EnquiryOperations enquiryOperations,
            OfficerRegistrationOperations registrationOperations,
            ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations,
            ProjectOperations projectOperations) {

        super(dataManager, applicationOperations, enquiryOperations,
                registrationOperations, projectTeamOperations,
                userOperations, projectOperations);
    }

    /**
     * Retrieves a list of currently visible and open BTO projects.
     * This method fetches all projects from the data manager, filters them to include
     * only those marked as visible, and then further filters this list to include
     * only projects that are currently open for application.
     *
     * @return A List of {@link Project} objects that are both visible to applicants
     *         and currently open for application.
     */
    public List<Project> getVisibleProjects() {
        List<Project> projects = dataManager.getProjects();
        List<Project> visibleProjects = projectManager.filterProject(projects, true);
        Iterator<Project> it = visibleProjects.iterator();
        while (it.hasNext()) {
            if (projectManager.isOpen(it.next())) {
                continue;
            }
            it.remove();
        }
        return visibleProjects;
    }
}