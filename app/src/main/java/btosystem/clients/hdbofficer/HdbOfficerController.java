package btosystem.clients.hdbofficer;

import btosystem.classes.HdbOfficer;
import btosystem.clients.Controller;
import btosystem.service.HdbOfficerServiceManager;

/**
 * An abstract base controller class that defines the attributes and base flow of
 * functions specific to the {@link HdbOfficer} role.
 * Delegates specific operations to subclass implementations.
 */
public abstract class HdbOfficerController extends Controller {
    protected HdbOfficer user;
    protected HdbOfficerServiceManager serviceManager;

    /**
     * Base implementation of constructor for all {@link HdbOfficer} related
     * sub-controllers.
     *
     * @param user reference to a {@link HdbOfficer} object
     * @param serviceManager reference to a {@link HdbOfficerServiceManager}
     *                       instance for use by sub-controllers
     */
    public HdbOfficerController(
            HdbOfficer user, HdbOfficerServiceManager serviceManager) {
        this.user = user;
        this.serviceManager = serviceManager;
    }

    /**
     * {@inheritDoc}
     * Includes additional check for non-logged in officer.
     */
    @Override
    public void execute() { 
        if (user == null) {
            System.out.println("No officer detected. Login before continuing. ");
        }
        super.execute();
    }
}
