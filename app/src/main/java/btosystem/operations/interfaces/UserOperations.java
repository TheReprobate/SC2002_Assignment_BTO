package btosystem.operations.interfaces;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.User;
import java.util.HashMap;
import java.util.List;

/**
 * Interface defining operations related to user management. Extends ToString to
 * provide string representation functionality for User objects.
 */
public interface UserOperations extends ToString<User> {

    /**
     * Authenticates a user based on username and password.
     *
     * @param users Map of users with usernames as keys
     * @param username Username to authenticate
     * @param password Password to verify
     * @return Authenticated User object if successful, null otherwise
     */
    public User authenticate(HashMap<String, User> users, String username, String password)
            throws Exception;

    /**
     * Retrieves the active application for an applicant.
     *
     * @param applicant The applicant to query
     * @return The applicant's active BTO application
     */
    public BtoApplication retrieveApplication(Applicant applicant);

    /**
     * Retrieves all enquiries made by an applicant.
     *
     * @param applicant The applicant to query
     * @return List of enquiries made by the applicant
     */
    public List<Enquiry> retrieveEnquiries(Applicant applicant);

    /**
     * Retrieves the current project team for an HDB officer.
     *
     * @param officer The officer to query
     * @return The officers's history of all teams
     */
    public List<ProjectTeam> retrieveTeams(HdbOfficer officer);

    /**
     * Retrieves the current project team for an HDB manager.
     *
     * @param manager The manager to query
     * @return The manager's history of all teams
     */
    public List<ProjectTeam> retrieveTeams(HdbManager manager);

    /**
     * Retrieves all projects created by an HDB manager.
     *
     * @param manager The manager to query
     * @return List of projects created by the manager
     */
    public List<Project> retrieveCreatedProjects(HdbManager manager);

    /**
     * Removes the application associated with the specified applicant.
     *
     * @param applicant the applicant whose application should be removed
     * @throws Exception if the removal operation fails or if applicant is not found
     */
    public void removeApplication(Applicant applicant) throws Exception;

    /**
     * Retrieves a user from the user registry based on NRIC.
     *
     * @param users the user registry to search in
     * @param nric the NRIC number to search for
     * @return the User object matching the NRIC
     * @throws Exception if user is not found or retrieval fails
     */
    public User retrieveUser(HashMap<String, User> users, String nric) throws Exception;

    /**
     * Adds a new applicant to the user registry.
     *
     * @param users the user registry to add to
     * @param nric the NRIC of the new applicant
     * @param name the full name of the applicant
     * @param age the age of the applicant
     * @param married marital status of the applicant
     * @throws Exception if addition fails (e.g., duplicate NRIC or invalid data)
     */
    public void addApplicant(HashMap<String, User> users, String nric,
                             String name, int age, boolean married)
            throws Exception;

    /**
     * Associates an application with an applicant.
     *
     * @param applicant the applicant to associate with
     * @param application the application to set
     */
    public void setApplication(Applicant applicant, BtoApplication application);

    /**
     * Changes the password of the specified user to the provided new password.
     *
     * @param user The user whose password will be changed.
     * @param password The new password to set for the user.
     */
    public void changePassword(User user, String password);
}
