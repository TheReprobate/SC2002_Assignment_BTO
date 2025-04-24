package btosystem.clients.applicant;

import btosystem.classes.Applicant;
import btosystem.clients.Controller;
import btosystem.service.ApplicantServiceManager;

/**
 * An abstract base controller class that defines the attributes and base flow of
 * functions specific to the {@link Applicant} role.
 * Delegates specific operations to subclass implementations.
 */
public abstract class ApplicantController extends Controller {
    protected Applicant user;
    protected ApplicantServiceManager serviceManager;

    /**
     * Base implementation of constructor for all {@link Applicant} related
     * sub-controllers.
     *
     * @param user reference to a {@link Applicant} object
     * @param serviceManager reference to a {@link ApplicantServiceManager}
     *                       instance for use by sub-controllers
     */
    public ApplicantController(Applicant user, ApplicantServiceManager serviceManager) {
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
            System.out.println("No applicant detected. Login before continuing. ");
        }
        super.execute();
    }
}
