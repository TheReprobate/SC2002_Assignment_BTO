package btosystem.service.user.interfaces;

import btosystem.classes.User;

public interface IUserAccountService{
    public void registerApplicant(String nric, String name, int age, boolean married) throws Exception;
    public User login(String nric, String password) throws Exception;
}
