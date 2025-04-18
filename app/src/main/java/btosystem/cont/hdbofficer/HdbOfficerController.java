package btosystem.cont.hdbofficer;

import btosystem.classes.HdbOfficer;
import btosystem.cont.Controller;

public abstract class HdbOfficerController extends Controller{
    private HdbOfficer user;
    public HdbOfficerController(HdbOfficer user) {
        this.user = user;
    }

    public HdbOfficer getUser() {
        return user;
    }

    public void setUser(HdbOfficer user) {
        this.user = user;
    }
    
    @Override
    public void execute() { 
        if(user == null) {
            System.out.println("No officer detected. Login before continuing. ");
        }
        super.execute();
    }
}
