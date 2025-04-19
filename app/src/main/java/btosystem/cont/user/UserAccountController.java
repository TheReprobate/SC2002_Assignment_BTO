package btosystem.cont.user;

import btosystem.classes.User;
import btosystem.cont.Controller;
import btosystem.service.user.UserAccountService;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;
import btosystem.utils.RegexPatterns;

public class UserAccountController extends Controller{
    private static final String[] MENU = {"Login", "Register Applicant", "Exit"};
    private UserAccountService accountService;
    private User user;

    public UserAccountController(UserAccountService accountService) {
        this.accountService = accountService;
    }
    
    public User getUser(){
        if(this.user == null) {
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
        switch(input) {
            case 0: login(); return -1;
            case 1: register(); return 0;
            case 2: return -1;
            default: throw new Exception("Please enter a valid input. ");
        }
    }

    private void login() throws Exception{
        String nric = InputHandler.getStringInput("Input nric: ", RegexPatterns.NRIC);
        String password = InputHandler.getStringInput("Input password: ");
        user = accountService.login(nric, password);
    }

    private void register() throws Exception{
        String nric = InputHandler.getStringInput("Input NRIC: ", RegexPatterns.NRIC);
        String name = InputHandler.getStringInput("Input name: ");
        int age = InputHandler.getIntInput("Input age: ");
        String marriedString = InputHandler.getStringInput("Are you married? [Y/N]", RegexPatterns.YES_NO);
        boolean married = false;
        if(marriedString.equals("Y") || marriedString.equals("y")){
            married = true;
        }
        accountService.registerApplicant(nric, name, age, married);
        System.out.println("User registered with password: password. ");
    }
}
