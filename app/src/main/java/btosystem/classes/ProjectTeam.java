package btosystem.classes;

import java.util.ArrayList;
import java.util.List;

public class ProjectTeam {
    private List<HdbOfficer> officers;
    private HdbManager manager;
    private List<OfficerRegistration> officerRegistrations;
    private Project project;

    public ProjectTeam(Project project) {
        this.project = project;
        officers = new ArrayList<HdbOfficer>();
        officerRegistrations = new ArrayList<OfficerRegistration>();
    }

    public List<HdbOfficer> getOfficers() {
        return officers;
    }

    public HdbManager getManager() {
        return manager;
    }

    public List<OfficerRegistration> getOfficerRegistrations() {
        return officerRegistrations;
    }

    public Project getProject() {
        return project;
    }

    public void setManager(HdbManager manager) {
        this.manager = manager;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void assignOfficer(HdbOfficer officer) {
        this.officers.addLast(officer);
    }

    // Idk if this is supposed to be here or OfficerRegistrationController
    public void addOfficerRegistration(OfficerRegistration officerRegistration)
    {
        this.officerRegistrations.addLast(officerRegistration);
    }
}
