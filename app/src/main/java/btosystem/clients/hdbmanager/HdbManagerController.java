package btosystem.clients.hdbmanager;

import btosystem.classes.HdbManager;
import btosystem.clients.Controller;
import btosystem.service.HdbManagerServiceManager;

/**
 * An abstract base controller class that defines the attributes and base flow of
 * functions specific to the {@link HdbManager} role.
 * Delegates specific operations to subclass implementations.
 */
public abstract class HdbManagerController extends Controller {
    protected HdbManager user;
    protected HdbManagerServiceManager serviceManager;

    /**
     * Base implementation of constructor for all {@link HdbManager} related
     * sub-controllers.
     *
     * @param user reference to a {@link HdbManager} object
     * @param serviceManager reference to a {@link HdbManagerServiceManager}
     *                       instance for use by sub-controllers
     */
    public HdbManagerController(
            HdbManager user,
            HdbManagerServiceManager serviceManager) {
        this.user = user;
        this.serviceManager = serviceManager;
    }

    /**
     * {@inheritDoc}
     * Includes additional check for non-logged in user.
     */
    @Override
    public void execute() { 
        if (user == null) {
            System.out.println("No manager detected. Login before continuing. ");
        }
        super.execute();
    }
}
