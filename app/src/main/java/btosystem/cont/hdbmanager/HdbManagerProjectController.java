package btosystem.cont.hdbmanager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import btosystem.classes.HdbManager;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;
import btosystem.utils.InputHandler;
import btosystem.utils.ListToStringFormatter;
import btosystem.utils.RegexPatterns;

public abstract class HdbManagerProjectController extends HdbManagerController {
    public HdbManagerProjectController(HdbManager user) {
        super(user);
    }

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    protected Neighborhood getInputNeighborhood() throws Exception {
        Neighborhood[] neighborhoods = Neighborhood.values();
        System.out.println(ListToStringFormatter.toString(neighborhoods));
        int index = InputHandler.getIntIndexInput("Select a neighborhood: ");
        if (index < 0 || index >= neighborhoods.length) {
            throw new Exception("Input out of bounds. ");
        }
        return neighborhoods[index];
    }

    protected FlatType getInputFlat() throws Exception {
        FlatType[] neighborhoods = FlatType.values();
        System.out.println(ListToStringFormatter.toString(neighborhoods));
        int index = InputHandler.getIntIndexInput("Select a flat: ");
        if (index < 0 || index >= neighborhoods.length) {
            throw new Exception("Input out of bounds. ");
        }
        return neighborhoods[index];
    }

    protected LocalDate getInputTime(String type) throws Exception {
        String prompt = String.format("Input %s date (dd/MM/yyy): ", type);
        String input = InputHandler.getStringInput(prompt, RegexPatterns.DATE);
        LocalDate date = LocalDate.parse(input, dateFormatter);
        return date;
    }
}

