package btosystem.service.user.interfaces;

import btosystem.classes.User;

/**
 * Interface defining the contract for user account services,
 * including registration and login functionalities.
 */
public interface IUserAccountService {
    /**
     * Registers a new applicant user in the system.
     *
     * @param nric    The applicant's unique NRIC.
     * @param name    The applicant's full name.
     * @param age     The applicant's age in years.
     * @param married The applicant's marital status (true if married, false otherwise).
     * @throws Exception If registration fails
     */
    public void registerApplicant(String nric, String name, int age, 
                                boolean married) throws Exception;

    /**
     * Authenticates a user based on their NRIC
     * and password.
     *
     * @param nric     The user's NRIC .
     * @param password The user's plain-text password.
     * @return The authenticated {@link User} object if the NRIC and password are valid.
     * @throws Exception If authentication fails
     */
    public User login(String nric, String password) throws Exception;
}