package btosystem.controllers;

import java.util.List;

import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.ProjectTeam;
import btosystem.controllers.interfaces.ProjectTeamOperations;

public class ProjectTeamController implements ProjectTeamOperations{

    @Override
    public ProjectTeam createProjectTeam() {
        // TODO where is the project?
        ProjectTeam projTeam = new ProjectTeam(null);
        return projTeam;
    }
    
    // For HdbManager
    /* -------------------------------------------------------------------------------------------- */
    @Override
    public int assignProject(ProjectTeam team, HdbOfficer officer) {
        // TODO return what?
        try {
            team.assignOfficer(officer);
            return 1;
        }
        catch(Exception e){
            return -1;
        }
    }

    @Override
    public List<OfficerRegistration> retrieveOfficerRegistrations(ProjectTeam team) {
        return team.getOfficerRegistrations();
    }

    @Override
    public boolean isInTeam(ProjectTeam team, HdbOfficer officer) {
        List<HdbOfficer> officerList = team.getOfficers();
        return officerList.contains(officer);
    }
    /* -------------------------------------------------------------------------------------------- */

    // For HdbManagers
    /* -------------------------------------------------------------------------------------------- */
    @Override
    public int assignProject(ProjectTeam team, HdbManager manager) {
        try {
            team.setManager(manager);
            return 1;
        }
        catch(Exception e){
            return -1;
        }
    }

    @Override
    public boolean isInTeam(ProjectTeam team, HdbManager manager) {
        return team.getManager().equals(manager);
    }
    /* -------------------------------------------------------------------------------------------- */

    @Override
    public int addRegistration(ProjectTeam team, OfficerRegistration registration) {
        try {
            team.addOfficerRegistration(registration);
            return 1;
        }
        catch(Exception e){
            return -1;
        }
    }

    @Override
    public String toString(ProjectTeam data) {
        String projName = data.getProject().getName();
        String managerName = data.getManager().getName();

        String officers = "";
        for(HdbOfficer o : data.getOfficers())
        {
            officers.concat(o.getName() + "\n");
        }

        String registrations = "";
        for(OfficerRegistration r : data.getOfficerRegistrations())
        {
            registrations.concat(r.getOfficer() + "\n");
        }

        return 
        "Project Name           : \n" + projName        + "\n" +
        "Manager                : \n" + managerName     + "\n" +
        "Officers               : \n" + officers        + "\n" +
        "Pending registrations  : \n" + registrations   + "\n";
    }
}
