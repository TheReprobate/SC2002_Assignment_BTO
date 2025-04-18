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

public interface UserOperations extends ToString<User> {
    public User authenticate(HashMap<String, User> users, String username, String password) throws Exception;

    public BtoApplication retrieveApplication(Applicant applicant);

    public List<Enquiry> retrieveEnquiries(Applicant applicant);

    public ProjectTeam retrieveCurrentTeam(HdbOfficer officer);

    public ProjectTeam retrieveCurrentTeam(HdbManager manager);

    public List<Project> retrieveCreatedProjects(HdbManager manager);

    public void removeApplication(Applicant applicant) throws Exception;

    public User retrieveUser(HashMap<String, User> users, String nric) throws Exception;

    public void addApplicant(HashMap<String, User> users, String nric,  String name, int age, boolean married) throws Exception;

    public void setApplication(Applicant applicant, BtoApplication application);
}
