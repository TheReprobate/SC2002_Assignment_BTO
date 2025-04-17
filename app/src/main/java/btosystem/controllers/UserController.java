package btosystem.controllers;

import btosystem.classes.*;
import btosystem.classes.enums.FlatType;
import btosystem.controllers.interfaces.UserOperations;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Override
    public List<FlatType> getAllowedFlatTypes(Applicant applicant) throws Exception {
        return applicant.isMarried() ? getMarriedFlatType(applicant) : getSingleFlatType(applicant);
    }

    private List<FlatType> getSingleFlatType(Applicant applicant) throws Exception {
        if(applicant.getAge() < 35) {
            throw new Exception("Age under 35 for single applicants have no flats types. ");
        }
        return Arrays.asList(FlatType.TWO_ROOM);
    }

    private List<FlatType> getMarriedFlatType(Applicant applicant) throws Exception {
        if(applicant.getAge() < 21) {
            throw new Exception("Age under 21 for married applicants have no flats types. ");
        }
        return Arrays.asList(FlatType.TWO_ROOM, FlatType.THREE_ROOM);
    }
}
