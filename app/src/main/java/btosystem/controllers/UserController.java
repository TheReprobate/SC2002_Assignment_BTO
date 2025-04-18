package btosystem.controllers;

import btosystem.classes.*;
import btosystem.controllers.interfaces.UserOperations;

import java.util.HashMap;
import java.util.List;

public class UserController implements UserOperations {

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
        if(applicant.getActiveApplication() == null) {
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
    
}
