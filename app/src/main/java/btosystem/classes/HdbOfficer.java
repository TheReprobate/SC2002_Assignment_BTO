package btosystem.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an HDB (Housing & Development Board) officer, who is also an applicant.
 * HDB officers can be part of project teams.
 */
public class HdbOfficer extends Applicant {
    private List<ProjectTeam> teams;

    /**
     * Constructs an {@code HdbOfficer} object with the specified NRIC, name, age, and marital status.
     *
     * @param nric    The NRIC of the HDB officer.
     * @param name    The name of the HDB officer.
     * @param age     The age of the HDB officer.
     * @param married {@code true} if the HDB officer is married, {@code false} otherwise.
     */
    public HdbOfficer(String nric, String name, int age, boolean married) {
        super(nric, name, age, married);
        teams = new ArrayList<ProjectTeam>();
    }

    public List<ProjectTeam> getTeams() {
        return teams;
    }
}
