package btosystem.controllers;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.User;
import btosystem.controllers.interfaces.UserOperations;
import java.util.HashMap;
import java.util.List;

/**
 * Controller class for handling user-related operations. Implements the
 * UserOperations interface to provide functionality for authentication and data
 * retrieval for different user types.
 */
public class UserController implements UserOperations {

    /**
     * Authenticates a user based on username and password.
     *
     * @param users HashMap of registered users
     * @param username Username to authenticate
     * @param password Password to verify
     * @return Authenticated User object if successful, null otherwise
     * @throws Exception 
     */
    public User authenticate(HashMap<String, User> users, String username, String password) throws Exception {
        User user = retrieveUser(users, username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        throw new Exception("Password does not match. "); 
    }

    @Override
    public BtoApplication retrieveApplication(Applicant applicant) {
        return applicant.getActiveApplication();
    }

    @Override
    public List<Enquiry> retrieveEnquiries(Applicant applicant) {
        return applicant.getEnquiries();
    }

    @Override
    public ProjectTeam retrieveCurrentTeam(HdbOfficer officer) {
        return officer.getCurrentTeam();
    }

    @Override
    public ProjectTeam retrieveCurrentTeam(HdbManager manager) {
        return manager.getCurrentTeam();
    }

    @Override
    public List<Project> retrieveCreatedProjects(HdbManager manager) {
        return manager.getCreatedProjects();
    }

    @Override
    public String toString(User data) {
        return data.getName() + " (" + data.getNric() + ")";
    }

    @Override
    public void removeApplication(Applicant applicant) throws Exception {
        if (applicant.getActiveApplication() == null) {
            throw new Exception("Applicant does not have active application. ");
        }
        applicant.setActiveApplication(null);
    }

    @Override
    public User retrieveUser(HashMap<String, User> users, String nric) throws Exception {
        User user = users.get(nric);
        if (user == null) {
            throw new Exception("User does not exist. ");
        }
        return user;
    }

    @Override
    public void addApplicant(HashMap<String, User> users, String nric, String name, int age, boolean married) throws Exception {
        if (users.get(nric) != null) {
            throw new Exception("User already exist. ");
        }
        Applicant applicant = new Applicant(nric, name, age, married);
        users.put(nric, applicant);
    }

    @Override
    public void setApplication(Applicant applicant, BtoApplication application) {
        applicant.setActiveApplication(application);
    }

    @Override
    public void setTeam(ProjectTeam team, HdbManager user) {
        user.setCurrentTeam(team);
    }

    @Override
    public void setTeam(ProjectTeam team, HdbOfficer user) {
        user.setCurrentTeam(team);
    }
}
