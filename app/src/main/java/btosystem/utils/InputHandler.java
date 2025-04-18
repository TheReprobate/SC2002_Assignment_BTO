package btosystem.utils;

import java.util.InputMismatchException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class InputHandler {
    public static int getIntInput() throws InputMismatchException{
        try {
            int option = ScannerInstance.getInstance().nextInt();
            ScannerInstance.getInstance().nextLine();
            return option;
        } catch (InputMismatchException e) {
            ScannerInstance.getInstance().nextLine();
            throw new InputMismatchException("Input mismatch, please input an integer. ");
        }
    }
    public static int getIntInput(String prompt) throws InputMismatchException{ 
        try {
            System.out.print(prompt);
            return getIntInput();
        } catch (InputMismatchException e) {
            ScannerInstance.getInstance().nextLine();
            throw new InputMismatchException("Input mismatch, please input an integer. ");
        }
    }
    public static int getIntIndexInput() throws Exception{
        try {
            int option = getIntInput()-1;
            return option;
        } catch (Exception e) {
            throw e;
        }
    }
    public static int getIntIndexInput(String prompt) throws Exception{ 
        try {
            System.out.print(prompt);
            return getIntIndexInput();
        } catch (Exception e) {
            throw e;
        }
    } 
    public static String getStringInput() {
        return ScannerInstance.getInstance().nextLine();
    }
    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return getStringInput();
    }
    public static String getStringInput(Pattern pattern) throws PatternSyntaxException  {
        String input = getStringInput();
            if(pattern.matcher(input).matches()){
                return input;
            }
        throw new InputMismatchException("Input mismatch, please input only the allowed values. ");
    }
    public static String getStringInput(String prompt, Pattern regex) throws PatternSyntaxException  { 
        try {
            System.out.print(prompt);
            return getStringInput(regex);
        } catch (Exception e) {
            throw e;
        }
    }
}
