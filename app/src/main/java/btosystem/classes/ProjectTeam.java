package btosystem.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectTeam class, logical implementation is handled by ProjectTeamController.
 * {@link btosystem.controllers.ProjectTeamController}
 */
public class ProjectTeam {
    private List<HdbOfficer> officers;
    private HdbManager manager;
    private List<OfficerRegistration> officerRegistrations;
    private Project project;

    public ProjectTeam(Project project) {
        this.project = project;
        this.manager = null;
        officers = new ArrayList<HdbOfficer>();
        officerRegistrations = new ArrayList<OfficerRegistration>();
    }

    public Project getProject() {
        return project;
    }

    public HdbManager getManager() {
        return manager;
    }

    public List<HdbOfficer> getOfficers() {
        return officers;
    }

    public List<OfficerRegistration> getOfficerRegistrations() {
        return officerRegistrations;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setManager(HdbManager manager) {
        this.manager = manager;
    }

    public void assignOfficer(HdbOfficer officer) {
        this.officers.addLast(officer);
    }
    
    public void addOfficerRegistration(OfficerRegistration officerRegistration)
    {
        this.officerRegistrations.addLast(officerRegistration);
    }
}
