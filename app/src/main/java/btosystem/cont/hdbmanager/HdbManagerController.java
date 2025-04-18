package btosystem.cont.hdbmanager;

import btosystem.classes.HdbManager;
import btosystem.cont.Controller;

public abstract class HdbManagerController extends Controller{
    private HdbManager user;

    public HdbManagerController(HdbManager user) {
        this.user = user;
    }

    public HdbManager getUser() {
        return user;
    }

    public void setUser(HdbManager user) {
        this.user = user;
    }
    
    @Override
    public void execute() { 
        if(user == null) {
            System.out.println("No manager detected. Login before continuing. ");
        }
        super.execute();
    }
}
