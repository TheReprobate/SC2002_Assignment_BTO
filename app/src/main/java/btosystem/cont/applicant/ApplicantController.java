package btosystem.cont.applicant;

import btosystem.classes.Applicant;
import btosystem.cont.Controller;

public abstract class ApplicantController extends Controller {
    private Applicant user;

    public Applicant getUser() {
        return user;
    }

    public void getUser(Applicant user) {
        this.user = user;
    }
    
    @Override
    public void execute() { 
        if(user == null) {
            System.out.println("No applicant detected. Login before continuing. ");
        }
        super.execute();
    }
}
