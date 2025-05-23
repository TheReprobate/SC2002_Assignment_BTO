package btosystem.utils;

import java.util.regex.Pattern;

/**
 * Regex class to create custom regex needed for our app.
 */
public class RegexPatterns {
    public static final Pattern YES_NO = Pattern.compile("^[YyNn]$");
    public static final Pattern NRIC = Pattern.compile("^[STFG]\\d{7}[A-Z]$");
    public static final Pattern COMMA_DELIMITER = Pattern.compile(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    public static final Pattern DATE = Pattern.compile(
            "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$");
}
