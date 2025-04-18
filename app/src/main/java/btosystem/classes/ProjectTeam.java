package btosystem.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectTeam class, logical implementation is handled by:
 * {@link btosystem.controllers.ProjectTeamController}
 */
public class ProjectTeam {
    private Project project;
    
    private HdbManager manager;
    private List<HdbOfficer> officers;
    private List<OfficerRegistration> officerRegistrations;

    /**
     * Constructor for ProjectTeam object.
     * Actual implementation for creation of object is handled by:
     * {@link btosystem.controllers.ProjectTeamController}
     */
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
