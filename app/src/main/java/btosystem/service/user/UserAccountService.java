package btosystem.service.user;

import btosystem.classes.User;
import btosystem.operations.interfaces.BtoApplicationOperations;
import btosystem.operations.interfaces.EnquiryOperations;
import btosystem.operations.interfaces.OfficerRegistrationOperations;
import btosystem.operations.interfaces.ProjectOperations;
import btosystem.operations.interfaces.ProjectTeamOperations;
import btosystem.operations.interfaces.UserOperations;
import btosystem.service.Service;
import btosystem.service.user.interfaces.IUserAccountService;
import btosystem.utils.DataManager;
import btosystem.utils.HashUtil;
import btosystem.utils.OperationsManager;

/**
 * Service class handling user account operations including login and
 * registration.
 */
public class UserAccountService extends Service implements IUserAccountService {

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
        String hashedPassword = HashUtil.hashPassword(password);
        return userManager.authenticate(dataManager.getUsers(), nric, hashedPassword);
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

    /**
     * Allows a user to change their password. This method first authenticates the user
     * using their current password. If the authentication is successful, the new password
     * is hashed and updated for the user in the system.
     *
     * @param nric The NRIC of the user whose password needs to be changed.
     * @param oldPassword The user's current password for authentication.
     * @param newPassword The new password that the user wants to set. This will be hashed
     * before being stored.
     * @throws Exception If the authentication with the old password fails, or if there is
     * an issue updating the password in the system.
     */
    public void changePassword(String nric, String oldPassword, String newPassword) throws Exception {
        User user = login(nric, oldPassword);
        String hashedPassword = HashUtil.hashPassword(newPassword);
        userManager.changePassword(user, hashedPassword);
    }
}
