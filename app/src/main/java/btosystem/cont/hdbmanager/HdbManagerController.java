package btosystem.cont.hdbmanager;

import btosystem.classes.HdbManager;
import btosystem.cont.Controller;
import btosystem.service.HdbManagerServiceManager;

public abstract class HdbManagerController extends Controller{
    protected HdbManager user;
    protected HdbManagerServiceManager serviceManager;

    public HdbManagerController(HdbManager user, HdbManagerServiceManager serviceManager) {
        this.user = user;
        this.serviceManager = serviceManager;
    }

    
    @Override
    public void execute() { 
        if(user == null) {
            System.out.println("No manager detected. Login before continuing. ");
        }
        super.execute();
    }
}
