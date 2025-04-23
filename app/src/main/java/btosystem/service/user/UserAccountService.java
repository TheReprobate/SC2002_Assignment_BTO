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

public class UserAccountService extends Service{

    public UserAccountService(DataManager dataManager, BtoApplicationOperations applicationManager, EnquiryOperations enquiryManager,
            OfficerRegistrationOperations registrationOperations, ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations, ProjectOperations projectOperations) {
        super(dataManager, applicationManager, enquiryManager, registrationOperations, projectTeamOperations, userOperations, projectOperations);
    }

    public User login(String nric, String password) throws Exception{
        return userManager.authenticate(dataManager.getUsers(), nric, password);
    }
    
    public void registerApplicant(String nric, String name, int age, boolean married) throws Exception {
        userManager.addApplicant(dataManager.getUsers(), nric, name, age, married);
    }
    
    public void changePassword(String nric, String oldPassword, String newPassword) throws Exception {
        User user = login(nric, newPassword);
        userManager.changePassword(user, newPassword);
    }
}
