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
}
