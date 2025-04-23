package btosystem.service.user;

import btosystem.classes.User;
import btosystem.controllers.interfaces.BtoApplicationOperations;
import btosystem.controllers.interfaces.EnquiryOperations;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;
import btosystem.controllers.interfaces.ProjectOperations;
import btosystem.controllers.interfaces.ProjectTeamOperations;
import btosystem.controllers.interfaces.UserOperations;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

/**
 * Service class handling user account operations including login and
 * registration.
 */
public class UserAccountService extends Service {

    /**
     * Constructs a new UserAccountService with required dependencies.
     *
     * @param dataManager Data management operations
     * @param applicationManager BTO application operations
     * @param enquiryManager Enquiry operations
     * @param registrationOperations Officer registration operations
     * @param projectTeamOperations Project team operations
     * @param userOperations User operations
     * @param projectOperations Project operations
     */
    public UserAccountService(
            DataManager dataManager,
            BtoApplicationOperations applicationManager,
            EnquiryOperations enquiryManager,
            OfficerRegistrationOperations registrationOperations,
            ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations,
            ProjectOperations projectOperations) {

        super(dataManager, applicationManager, enquiryManager,
                registrationOperations, projectTeamOperations,
                userOperations, projectOperations);
    }

    /**
     * Authenticates a user with NRIC and password.
     *
     * @param nric The user's NRIC
     * @param password The user's password
     * @return The authenticated User object
     * @throws Exception If authentication fails
     */
    public User login(String nric, String password) throws Exception {
        return userManager.authenticate(dataManager.getUsers(), nric, password);
    }

    /**
     * Registers a new applicant user.
     *
     * @param nric The applicant's NRIC
     * @param name The applicant's name
     * @param age The applicant's age
     * @param married The applicant's marital status
     * @throws Exception If registration fails
     */
    public void registerApplicant(
            String nric,
            String name,
            int age,
            boolean married) throws Exception {
        userManager.addApplicant(dataManager.getUsers(), nric, name, age, married);
    }
}
