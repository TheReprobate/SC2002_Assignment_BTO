package btosystem.cont.hdbofficer;

import btosystem.classes.HdbOfficer;
import btosystem.cont.Controller;
import btosystem.service.HdbOfficerServiceManager;

public abstract class HdbOfficerController extends Controller{
    protected HdbOfficer user;
    protected HdbOfficerServiceManager serviceManager;
    
    public HdbOfficerController(HdbOfficer user, HdbOfficerServiceManager serviceManager) {
        this.user = user;
        this.serviceManager = serviceManager;
    }
    
    @Override
    public void execute() { 
        if(user == null) {
            System.out.println("No officer detected. Login before continuing. ");
        }
        super.execute();
    }
}
