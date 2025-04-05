package btosystem.classes;

import java.util.ArrayList;
import java.util.List;

public class HdbManager extends User {
    private ProjectTeam currentTeam;
    private List<Project> createdProjects;

    public HdbManager(String nric, String name, int age, boolean married) {
        super(nric, name, age, married);
        createdProjects = new ArrayList<Project>();
    }

    public ProjectTeam getCurrentTeam() {
        return currentTeam;
    }

    public List<Project> getCreatedProjects() {
        return createdProjects;
    }
}
