package btosystem.service.user;

import btosystem.classes.User;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class UserAccountService extends Service{

    public UserAccountService(DataManager dataManager, OperationsManager operationsManager) {
        super(dataManager, operationsManager);
    }

    public User login(String nric, String password) throws Exception{
        return operationsManager.getUserManager().authenticate(dataManager.getUsers(), nric, password);
    }
    
    public void registerApplicant(String nric, String name, int age, boolean married) throws Exception {
        operationsManager.getUserManager().addApplicant(dataManager.getUsers(), nric, name, age, married);
    }
}
