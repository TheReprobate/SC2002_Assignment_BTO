package btosystem.classes;

public class HdbOfficer extends Applicant {
    private ProjectTeam currentTeam;

    public HdbOfficer(String nric, String name, int age, boolean married) {
        super(nric, name, age, married);
    }

    public ProjectTeam getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(ProjectTeam currentTeam) {
        this.currentTeam = currentTeam;
    }
}
