package btosystem.service;

import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.operations.interfaces.BtoApplicationOperations;
import btosystem.operations.interfaces.EnquiryOperations;
import btosystem.operations.interfaces.OfficerRegistrationOperations;
import btosystem.operations.interfaces.ProjectOperations;
import btosystem.operations.interfaces.ProjectTeamOperations;
import btosystem.operations.interfaces.UserOperations;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;
import java.util.List;

/**
 * The GenericService class provides a set of utility methods for displaying and retrieving
 * various business objects such as Enquiries, Projects, Applications, Teams, and Registrations.
 * It extends the Service class and utilizes various operation managers to perform its functions.
 *
 * @author [Your Name]
 * @version 1.0
 * @see Service
 * @see BtoApplicationOperations
 * @see EnquiryOperations
 * @see OfficerRegistrationOperations
 * @see ProjectTeamOperations
 * @see UserOperations
 * @see ProjectOperations
 */
public class GenericService extends Service {

    /**
     * Constructs a new GenericService with the specified dependencies.
     *
     * @param dataManager the data manager instance
     * @param applicationManager the BTO application operations manager
     * @param enquiryManager the enquiry operations manager
     * @param registrationOperations the officer registration operations manager
     * @param projectTeamOperations the project team operations manager
     * @param userOperations the user operations manager
     * @param projectOperations the project operations manager
     */
    public GenericService(DataManager dataManager, BtoApplicationOperations applicationManager,
                          EnquiryOperations enquiryManager,
                          OfficerRegistrationOperations registrationOperations,
                          ProjectTeamOperations projectTeamOperations,
                          UserOperations userOperations,
                          ProjectOperations projectOperations) {
        super(dataManager, applicationManager, enquiryManager, registrationOperations,
                projectTeamOperations, userOperations, projectOperations);
    }

    /**
     * Displays a list of enquiries as a formatted string.
     *
     * @param enquiries the list of enquiries to display
     * @return formatted string representation of the enquiries, or empty string if null
     */
    public String displayEnquiry(List<Enquiry> enquiries) {
        if (enquiries == null) {
            return "";
        }
        return enquiryManager.toString(enquiries);
    }

    /**
     * Displays a single enquiry as a formatted string.
     *
     * @param enquiry the enquiry to display
     * @return formatted string representation of the enquiry, or empty string if null
     */
    public String displayEnquiry(Enquiry enquiry) {
        if (enquiry == null) {
            return "";
        }
        Project project = enquiryManager.retrieveProject(enquiry);
        if (project == null) {
            return "Project no longer exist" + enquiryManager.toString(enquiry);
        }
        return projectManager.toString(project)
                + userManager.toString(enquiryManager.retrieveApplicant(enquiry))
                + enquiryManager.toString(enquiry);
    }


    /**
     * Retrieves a specific enquiry from a list by index.
     *
     * @param enquiries the list of enquiries
     * @param index the index of the enquiry to retrieve
     * @return the requested enquiry
     * @throws Exception if the retrieval fails
     */
    public Enquiry getEnquiry(List<Enquiry> enquiries, int index) throws Exception {
        return enquiryManager.retrieveEnquiry(enquiries, index);
    }

    /**
     * Displays a single project as a formatted string.
     *
     * @param project the project to display
     * @return formatted string representation of the project, or empty string if null
     */
    public String displayProject(Project project) {
        if (project == null) {
            return "";
        }
        return projectManager.toString(project);
    }

    /**
     * Displays a list of projects as a formatted string.
     *
     * @param projects the list of projects to display
     * @return formatted string representation of the projects, or empty string if null
     */
    public String displayProject(List<Project> projects) {
        if (projects == null) {
            return "";
        }
        return projectManager.toString(projects);
    }

    /**
     * Retrieves a specific project from a list by index.
     *
     * @param projects the list of projects
     * @param index the index of the project to retrieve
     * @return the requested project
     * @throws Exception if the retrieval fails
     */
    public Project getProject(List<Project> projects, int index) throws Exception {
        return projectManager.retrieveProject(projects, index);
    }

    /**
     * Displays a single BTO application as a formatted string.
     *
     * @param application the application to display
     * @return formatted string representation of the application, or empty string if null
     */
    public String displayApplication(BtoApplication application) {
        if (application == null) {
            return "";
        }
        Project project = applicationManager.retrieveProject(application);
        if (project == null) {
            return "Project no longer exist" + applicationManager.toString(application);
        }
        return projectManager.toString(project) 
            + userManager.toString(applicationManager.retrieveApplicant(application)) 
            + applicationManager.toString(application);
    }

    /**
     * Displays a list of BTO applications as a formatted string.
     *
     * @param applications the list of applications to display
     * @return formatted string representation of the applications
     */
    public String displayApplication(List<BtoApplication> applications) {
        return applicationManager.toString(applications);
    }

    /**
     * Displays a project team as a formatted string.
     *
     * @param team the project team to display
     * @return formatted string representation of the team
     */
    public String displayTeam(ProjectTeam team) {
        return projectTeamManager.toString(team);
    }

    /**
     * Displays a list of officer registrations as a formatted string.
     *
     * @param registrations the list of registrations to display
     * @return formatted string representation of the registrations
     */
    public String displayRegistration(List<OfficerRegistration> registrations) {
        return registrationManager.toString(registrations);
    }

    /**
     * Retrieves a specific application from a list by index.
     *
     * @param applications the list of applications
     * @param index the index of the application to retrieve
     * @return the requested application
     */
    public BtoApplication getApplication(List<BtoApplication> applications, int index) {
        return applicationManager.retrieveApplication(applications, index);
    }

    /**
     * Retrieves a specific officer registration from a list by index.
     *
     * @param registrations the list of registrations
     * @param index the index of the registration to retrieve
     * @return the requested registration
     * @throws Exception if the retrieval fails
     */
    public OfficerRegistration getRegistration(List<OfficerRegistration> registrations, int index)
            throws Exception {
        return registrationManager.retrieveOfficerRegistration(registrations, index);
    }
}
