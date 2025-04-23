package btosystem.classes;

import java.util.ArrayList;
import java.util.List;

public class HdbOfficer extends Applicant {
    private List<ProjectTeam> teams;

    public HdbOfficer(String nric, String name, int age, boolean married) {
        super(nric, name, age, married);
        teams = new ArrayList<ProjectTeam>();
    }

    public List<ProjectTeam> getTeams() {
        return teams;
    }
}
