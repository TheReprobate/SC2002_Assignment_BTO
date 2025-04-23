package btosystem.utils;

import java.util.InputMismatchException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * The InputHandler class provides utility methods for handling different types of user input
 * from the console, including integers, strings, and pattern-matched strings.
 *
 * @author [Your Name]
 * @version 1.0
 * @see ScannerInstance
 * @see Pattern
 */
public class InputHandler {

    /**
     * Reads an integer input from the user.
     *
     * @return the integer value entered by the user
     * @throws InputMismatchException if the input cannot be parsed as an integer
     */
    public static int getIntInput() throws InputMismatchException {
        try {
            int option = ScannerInstance.getInstance().nextInt();
            ScannerInstance.getInstance().nextLine();
            return option;
        } catch (InputMismatchException e) {
            ScannerInstance.getInstance().nextLine();
            throw new InputMismatchException("Input mismatch, please input an integer. ");
        }
    }

    /**
     * Displays a prompt and reads an integer input from the user.
     *
     * @param prompt the message to display before reading input
     * @return the integer value entered by the user
     * @throws InputMismatchException if the input cannot be parsed as an integer
     */
    public static int getIntInput(String prompt) throws InputMismatchException {
        try {
            System.out.print(prompt);
            return getIntInput();
        } catch (InputMismatchException e) {
            ScannerInstance.getInstance().nextLine();
            throw new InputMismatchException("Input mismatch, please input an integer. ");
        }
    }

    /**
     * Reads an integer input from the user and returns it as a zero-based index
     * (subtracts 1 from the input value).
     *
     * @return the zero-based index value
     * @throws Exception if the input cannot be parsed as an integer
     */
    public static int getIntIndexInput() throws Exception {
        try {
            int option = getIntInput() - 1;
            return option;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Displays a prompt and reads an integer input from the user, returning it as
     * a zero-based index (subtracts 1 from the input value).
     *
     * @param prompt the message to display before reading input
     * @return the zero-based index value
     * @throws Exception if the input cannot be parsed as an integer
     */
    public static int getIntIndexInput(String prompt) throws Exception {
        try {
            System.out.print(prompt);
            return getIntIndexInput();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Reads a string input from the user.
     *
     * @return the string entered by the user
     */
    public static String getStringInput() {
        return ScannerInstance.getInstance().nextLine();
    }

    /**
     * Displays a prompt and reads a string input from the user.
     *
     * @param prompt the message to display before reading input
     * @return the string entered by the user
     */
    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return getStringInput();
    }

    /**
     * Reads a string input from the user and validates it against the specified pattern.
     *
     * @param pattern the regular expression pattern to match against
     * @return the validated string input
     * @throws InputMismatchException if the input doesn't match the pattern
     * @throws PatternSyntaxException if the pattern's syntax is invalid
     */
    public static String getStringInput(Pattern pattern) throws PatternSyntaxException  {
        String input = getStringInput();
        if (pattern.matcher(input).matches()) {
            return input;
        }
        throw new InputMismatchException("Input mismatch, please input only the allowed values. ");
    }

    /**
     * Displays a prompt, reads a string input from the user, and validates it against
     * the specified pattern.
     *
     * @param prompt the message to display before reading input
     * @param regex the regular expression pattern to match against
     * @return the validated string input
     * @throws PatternSyntaxException if the pattern's syntax is invalid
     */
    public static String getStringInput(String prompt, Pattern regex)
            throws PatternSyntaxException  {
        try {
            System.out.print(prompt);
            return getStringInput(regex);
        } catch (Exception e) {
            throw e;
        }
    }
}
