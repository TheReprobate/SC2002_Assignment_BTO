package btosystem.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a manager within the Housing & Development Board (HDB) system.
 * An HDB manager is a type of user who can manage project teams and created projects.
 */
public class HdbManager extends User {
    private List<ProjectTeam> teams;
    private List<Project> createdProjects;

    /**
     * Constructs an {@code HdbManager} object with the specified NRIC, 
     *                                  name, age, and marital status.
     * Initializes the lists for created projects and project teams.
     *
     * @param nric    The NRIC of the HDB manager.
     * @param name    The name of the HDB manager.
     * @param age     The age of the HDB manager.
     * @param married {@code true} if the HDB manager is married, {@code false} otherwise.
     */
    public HdbManager(String nric, String name, int age, boolean married) {
        super(nric, name, age, married);
        createdProjects = new ArrayList<Project>();
        teams = new ArrayList<ProjectTeam>();
    }

    public List<ProjectTeam> getTeams() {
        return teams;
    }

    public List<Project> getCreatedProjects() {
        return createdProjects;
    }
}
