package btosystem.cont.applicant;

import btosystem.classes.Applicant;
import btosystem.cont.Controller;
import btosystem.service.ApplicantServiceManager;

public abstract class ApplicantController extends Controller {
    protected Applicant user;
    protected ApplicantServiceManager serviceManager;

    public ApplicantController(Applicant user, ApplicantServiceManager serviceManager) {
        this.user = user;
        this.serviceManager = serviceManager;
    }
    
    @Override
    public void execute() { 
        if(user == null) {
            System.out.println("No applicant detected. Login before continuing. ");
        }
        super.execute();
    }
}
