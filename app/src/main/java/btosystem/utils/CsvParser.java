package btosystem.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class for parsing and writing CSV (Comma Separated Values) files.
 */
public class CsvParser {
    /**
     * Loads data from a CSV file.
     * The file is expected to be located in the `src/main/resources/` directory.
     * If the file does not exist, it will be created.
     * The first line of the CSV file is treated as headers and is removed from the returned data.
     *
     * @param filename The name of the CSV file to load.
     * @return A List of Lists of Strings, where each inner List represents a row of data
     *          and each String represents a cell value. Returns an empty List if an error occurs
     *          during file reading or creation.
     */
    public static List<List<String>> loadFromCSV(String filename) {
        List<List<String>> records = new ArrayList<>();
        File f = new File(String.format("src/main/resources/%s", filename));
        if (!(f.exists() && !f.isDirectory())) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        try (Scanner scanner = new Scanner(f)) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        records.remove(0); // remove headers
        return records;
    }

    /**
     * Parses a single line of CSV data into a List of String values.
     * The line is expected to be delimited by commas, 
     * as defined by {@link RegexPatterns#COMMA_DELIMITER}.
     *
     * @param line The CSV line to parse.
     * @return A List of Strings representing the values in the CSV line.
     */
    private static List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(RegexPatterns.COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    /**
     * Saves a List of Strings to a CSV file, with each String on a new line.
     * The file will be created or overwritten in the `src/main/resources/` directory.
     *
     * @param filename The name of the CSV file to save to.
     * @param strings  The List of Strings to write to the file. Each String will be a new row.
     */
    public static void saveToCSV(String filename, List<String> strings) {
        try (BufferedWriter writer = new BufferedWriter(
                        new FileWriter(String.format("src/main/resources/%s", filename)))) {
            for (String s : strings) {
                writer.write(s);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Escapes a String field for CSV formatting.
     * If the field contains a comma, double quote, or newline character, 
     * it will be enclosed in double quotes,
     * and any existing double quotes within the field will be escaped by doubling them.
     * If the field is null, an empty string is returned.
     *
     * @param field The String field to escape.
     * @return The escaped String suitable for CSV format.
     */
    public static String escapeCsv(String field) {
        if (field == null) {
            return "";
        }
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            field = field.replace("\"", "\"\"");
            return "\"" + field + "\"";
        }
        return field;
    }

    /**
     * Escapes an integer field for CSV formatting.
     * This method converts the integer to its String representation and then calls
     * {@link #escapeCsv(String)} to handle the escaping.
     *
     * @param field The integer field to escape.
     * @return The escaped String representation of the integer suitable for CSV format.
     */
    public static String escapeCsv(int field) {
        return escapeCsv(String.valueOf(field));
    }
}