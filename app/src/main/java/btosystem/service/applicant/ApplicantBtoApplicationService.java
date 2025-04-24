package btosystem.service.applicant;

import java.util.List;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.Project;
import btosystem.classes.enums.FlatType;
import btosystem.operations.interfaces.BtoApplicationOperations;
import btosystem.operations.interfaces.EnquiryOperations;
import btosystem.operations.interfaces.OfficerRegistrationOperations;
import btosystem.operations.interfaces.ProjectOperations;
import btosystem.operations.interfaces.ProjectTeamOperations;
import btosystem.operations.interfaces.UserOperations;
import btosystem.service.Service;
import btosystem.utils.DataManager;

/**
 * Service class handling application-related operations for applicants. Provides
 * functionality for creating and withdrawing applications..
 */
public class ApplicantBtoApplicationService extends Service {

    /**
     * Constructs a new ApplicantBtoApplicationService with required dependencies.
     *
     * @param dataManager Data management operations
     * @param applicationOperations BTO application operations
     * @param enquiryOperations Enquiry operations
     * @param registrationOperations Officer registration operations
     * @param projectTeamOperations Project team operations
     * @param userOperations User operations
     * @param projectOperations Project operations
     */
    public ApplicantBtoApplicationService(
            DataManager dataManager,
            BtoApplicationOperations applicationOperations,
            EnquiryOperations enquiryOperations,
            OfficerRegistrationOperations registrationOperations,
            ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations,
            ProjectOperations projectOperations
    ) {
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
     * Retrieves the current BTO application of the specified applicant.
     *
     * @param user The applicant whose BTO application to retrieve.
     * @return The {@link BtoApplication} object associated with the applicant,
     * or null if the applicant has not submitted an application.
     */
    public BtoApplication getApplication(Applicant user) {
        return userManager.retrieveApplication(user);
    }

    /**
     * Retrieves a list of flat types available for application by the given applicant
     * for a specific project. This method considers both the applicant's eligibility
     * and the availability of flat types within the project.
     *
     * @param user    The applicant for whom to find available flat types.
     * @param project The project for which to check flat type availability.
     * @return A list of {@link FlatType} that the applicant is eligible for and are
     * currently available in the specified project.
     */
    public List<FlatType> getAvailableFlatTypes(Applicant user, Project project) {
        List<FlatType> eligibleFlatTypes = applicationManager.getEligibleFlatTypes(user);
        List<FlatType> availableFlatTypes = projectManager.getAvailableFlatTypes(project);
        eligibleFlatTypes.removeIf(f -> !availableFlatTypes.contains(f));
        return eligibleFlatTypes;
    }

    /**
     * Creates a new BTO application for the specified applicant for a given project
     * and flat type. This method performs several checks before creating the application,
     * including whether the project is open, if the applicant has already applied for
     * the project, if the applicant has an existing application, and if there are
     * available units of the selected flat type in the project.
     *
     * @param user     The applicant submitting the application.
     * @param project  The project for which the application is being made.
     * @param flatType The type of flat the applicant is applying for.
     * @throws Exception If the project is not open, the applicant has already applied
     * for the project, the applicant has an existing application, or
     * there are no available units of the selected flat type.
     */
    public void createApplication(Applicant user, 
                                Project project, 
                                FlatType flatType) throws Exception {
        List<BtoApplication> projectApplicationRef = projectManager.retrieveApplications(project);
        BtoApplication application = userManager.retrieveApplication(user);
        if (!projectManager.isOpen(project)) { 
            throw new Exception("Project is unavailable. ");
        }
        if (applicationManager.hasApplied(projectApplicationRef, user)) {
            throw new Exception(
                    "Applicant has applied for this project before, unable to reapply."
            );
        }
        if (application != null) {
            throw new Exception("Applicant has an existing application. ");
        }
        if (!projectManager.unitHasSlots(project, flatType)) {
            throw new Exception("There is no slots available for your requirements.  ");
        }
        application = applicationManager.createApplication(project, user, flatType);
        applicationManager.addApplication(projectApplicationRef, application);
        userManager.setApplication(user, application);
    }

    /**
     * Withdraws the existing BTO application of the specified applicant.
     *
     * @param user The applicant who wants to withdraw their application.
     * @throws Exception If the applicant does not have an existing application to withdraw.
     */
    public void withdrawApplication(Applicant user) throws Exception {
        BtoApplication application = userManager.retrieveApplication(user);
        if (application == null) {
            throw new Exception("Applicant does not have an existing application. ");
        }
        applicationManager.withdrawApplication(application);
        userManager.removeApplication(user);
    }
}