package btosystem.cont.hdbmanager;

import btosystem.classes.HdbManager;
import btosystem.cont.Controller;

public abstract class HdbManagerController extends Controller{
    private HdbManager user;

    public HdbManager getUser() {
        return user;
    }

    public void getUser(HdbManager user) {
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
