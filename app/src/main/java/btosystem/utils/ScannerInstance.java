package btosystem.utils;

import java.util.Scanner;

/**
 * Util file for creating scanner inputs.
 */
public class ScannerInstance {
    private static Scanner s;

    /**
     * Constructor to create Scanner if not created.
     */
    public static Scanner getInstance() {
        if (s == null) {
            s = new Scanner(System.in);
        }
        return s;
    }
}
