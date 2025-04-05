package btosystem.controllers.interfaces;

import btosystem.classes.User;

public interface UserOperations extends ToStringParser<User> {
    public boolean authenticate(User user, String password);
}
