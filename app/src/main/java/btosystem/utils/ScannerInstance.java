package btosystem.utils;

import java.util.Scanner;

public class ScannerInstance {
    private static Scanner s;
    public static Scanner getInstance(){
        if(s == null) {
            s = new Scanner(System.in);
        }
        return s;
    }
}
