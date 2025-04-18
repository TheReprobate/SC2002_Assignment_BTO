package btosystem;

/**
* The main application class serving as the entry point for the program.
*/
import btosystem.classes.*;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;
import btosystem.controllers.OfficerRegistrationController;
import btosystem.controllers.ProjectController;
import btosystem.controllers.ProjectTeamController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class App {
    /**
    * The main method serving as the program entry point.
    *
    * @param args Command-line arguments passed to the application 
    */
    
    public static void main(String[] args) {
        Testing test = new Testing();
        test.testProjectTeamController();
    }
}
