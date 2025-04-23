package btosystem.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvParser {
    public static List<List<String>> loadFromCSV(String filename) {
        List<List<String>> records = new ArrayList<>();
        File f = new File(String.format("src/main/resources/%s", filename));
        if(!(f.exists() && !f.isDirectory())) { 
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

    public static void saveToCSV(String filename, List<String> strings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.format("src/main/resources/%s", filename)))) {
            for(String s: strings){
                writer.write(s);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String escapeCsv(String field) {
        if (field == null) return "";
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            field = field.replace("\"", "\"\"");
            return "\"" + field + "\"";
        }
        return field;
    }

    public static String escapeCsv(int field) {
        return escapeCsv(String.valueOf(field));
    }
}
