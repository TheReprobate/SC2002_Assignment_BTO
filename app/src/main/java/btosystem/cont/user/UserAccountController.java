package btosystem.cont.user;

import btosystem.classes.User;
import btosystem.cont.Controller;
import btosystem.service.user.UserAccountService;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;
import btosystem.utils.RegexPatterns;

/**
 * The main controller class specific for user/account related functions.
 * Implements the main control loop and performs delegation of
 * operations to the other controllers in the family.
 */
public class UserAccountController extends Controller {
    private static final String[] MENU = {
        "Login", "Change Password", "Register Applicant"
    };
    private UserAccountService accountService;
    private User user;

    /**
     * Constructor for the users/accounts controller.
     *
     * @param accountService reference to a {@link UserAccountService}
     */
    public UserAccountController(UserAccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Retrieves the current user object.
     *
     * @return the current user, or {@code null} if no current user.
     */

    public User getUser() {
        if (this.user == null) {
            return null;
        }
        return this.user;
    }

    @Override
    protected boolean load() throws Exception {
        return true;
    }

    @Override
    protected String display() {
        return ListToStringFormatter.toString(MENU);
    }

    @Override
    protected int process(int input) throws Exception {
        switch (input) {
          case 0:
              login();
              return -1;
          case 1:
              changePassword();
              return -1;
          case 2: 
              register();
              return -1;
          default: 
              throw new Exception("Please enter a valid input. ");
        }
    }

    /**
     * Gather input and perform user login via service class invocation.
     *
     * @throws Exception propagated errors from service call.
     */
    private void login() throws Exception {
        String nric = InputHandler.getStringInput("Input NRIC: ", RegexPatterns.NRIC);
        String password = InputHandler.getStringInput("Input password: ");
        user = accountService.login(nric, password);
    }

    /**
     * Gather input and perform user registration via service class invocation.
     *
     * @throws Exception propagated errors from service call.
     */
    private void register() throws Exception {
        String nric = InputHandler.getStringInput("Input NRIC: ", RegexPatterns.NRIC);
        String name = InputHandler.getStringInput("Input name: ");
        int age = InputHandler.getIntInput("Input age: ");
        String marriedString = InputHandler.getStringInput(
                "Are you married? [Y/N]", RegexPatterns.YES_NO
        );
        boolean married = false;
        if (marriedString.equals("Y") || marriedString.equals("y")) {
            married = true;
        }
        accountService.registerApplicant(nric, name, age, married);
        System.out.println("User registered with password: password. ");
    }

    private void changePassword() throws Exception{
        String nric = InputHandler.getStringInput("Input NRIC: ", RegexPatterns.NRIC);
        String password = InputHandler.getStringInput("Current Password: ");
        user = accountService.login(nric, password);
        if(user != null) {
            String newPassword = InputHandler.getStringInput("New Password: ");
            accountService.changePassword(nric, password, newPassword);
            System.out.println("Password changed successfully. ");
        }
    }
}
