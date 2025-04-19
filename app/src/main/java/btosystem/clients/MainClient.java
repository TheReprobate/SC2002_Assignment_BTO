package btosystem.clients;

import btosystem.App;
import btosystem.classes.Applicant;
import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.User;
import btosystem.cont.Controller;
import btosystem.cont.applicant.ApplicantMainController;
import btosystem.cont.hdbmanager.HdbManagerMainController;
import btosystem.cont.hdbofficer.HdbOfficerMainController;
import btosystem.cont.user.UserAccountController;
import btosystem.service.ApplicantServiceManager;
import btosystem.service.HdbManagerServiceManager;
import btosystem.service.HdbOfficerServiceManager;
import btosystem.service.user.UserAccountService;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;


public class MainClient {
    private static final String[] OFFICER_MENU = {"HDB Officer", "Applicant"};
    private ApplicantServiceManager applicantServiceManager;
    private HdbOfficerServiceManager hdbOfficerServiceManager;
    private HdbManagerServiceManager hdbManagerServiceManager;
    private UserAccountService accountService;
    private User user;
    


    public MainClient(ApplicantServiceManager applicantServiceManager,
            HdbOfficerServiceManager hdbOfficerServiceManager, HdbManagerServiceManager hdbManagerServiceManager,
            UserAccountService accountService) {
        this.applicantServiceManager = applicantServiceManager;
        this.hdbOfficerServiceManager = hdbOfficerServiceManager;
        this.hdbManagerServiceManager = hdbManagerServiceManager;
        this.accountService = accountService;
    }

    public void run(){
        UserAccountController accountController = new UserAccountController(accountService);
        while(true) {
            if(user == null) {
                accountController.execute();
                user = accountController.getUser();
            }
            getController().execute();
            user = null;
        }
    }

    private Controller getController(){
        if (user instanceof HdbManager){
            return getController((HdbManager) user);
        }
        if(user instanceof HdbOfficer) {
            return getController((HdbOfficer) user);
        }
        return getController((Applicant) user);
    }

    private Controller getController(HdbManager user) {
        return new HdbManagerMainController(user, hdbManagerServiceManager);
    }

    private Controller getController(Applicant user) {
        return new ApplicantMainController(user, applicantServiceManager);
    }

    private Controller getController(HdbOfficer user) {
        while(true) {
            System.out.println(ListToStringFormatter.toString(OFFICER_MENU));
            int option;
            try {
                option = InputHandler.getIntIndexInput("Your role: ");
                if(option == 0){
                    return new HdbOfficerMainController(user, hdbOfficerServiceManager);
                }
                if(option == 1){
                    return new ApplicantMainController(user, hdbOfficerServiceManager);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
