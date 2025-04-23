package btosystem.classes;

import java.util.ArrayList;
import java.util.List;

public class HdbManager extends User {
    private List<ProjectTeam> teams;
    private List<Project> createdProjects;

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
