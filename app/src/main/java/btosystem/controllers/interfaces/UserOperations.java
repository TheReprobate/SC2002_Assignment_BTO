package btosystem.controllers.interfaces;

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
    public User authenticate(HashMap<String, User> users, String username, String password) throws Exception;

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

    public void removeApplication(Applicant applicant) throws Exception;

    public User retrieveUser(HashMap<String, User> users, String nric) throws Exception;

    public void addApplicant(HashMap<String, User> users, String nric,  String name, int age, boolean married) throws Exception;

    public void setApplication(Applicant applicant, BtoApplication application);

    public void changePassword(User user, String password);
}
