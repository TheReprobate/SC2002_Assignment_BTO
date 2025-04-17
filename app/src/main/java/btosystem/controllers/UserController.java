package btosystem.controllers;

import btosystem.classes.*;
import btosystem.controllers.interfaces.UserOperations;

import java.util.HashMap;
import java.util.List;

public class UserController implements UserOperations {

    public User authenticate(HashMap<String, User> users, String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user; 
        }
        return null; 
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
}
