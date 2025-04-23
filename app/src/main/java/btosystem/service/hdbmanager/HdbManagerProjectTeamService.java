package btosystem.service.hdbmanager;

import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.RegistrationStatus;
import btosystem.controllers.interfaces.BtoApplicationOperations;
import btosystem.controllers.interfaces.EnquiryOperations;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;
import btosystem.controllers.interfaces.ProjectOperations;
import btosystem.controllers.interfaces.ProjectTeamOperations;
import btosystem.controllers.interfaces.UserOperations;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import java.util.List;

public class HdbManagerProjectTeamService extends Service {

    public HdbManagerProjectTeamService(DataManager dataManager, 
                                        BtoApplicationOperations applicationManager, 
                                        EnquiryOperations enquiryManager,
                                        OfficerRegistrationOperations registrationOperations, 
                                        ProjectTeamOperations projectTeamOperations,
                                        UserOperations userOperations, 
                                        ProjectOperations projectOperations) {
        super(dataManager, 
            applicationManager, 
            enquiryManager, 
            registrationOperations, 
            projectTeamOperations, 
            userOperations, 
            projectOperations);
    }

    public ProjectTeam getProjectTeam(Project project) {
        return projectManager.retrieveProjectTeam(project);
    }

    public List<OfficerRegistration> getRegistrations(Project project) {
        ProjectTeam team = projectManager.retrieveProjectTeam(project);
        List<OfficerRegistration> registrations = projectTeamManager
                                                .retrieveOfficerRegistrations(team);
        return registrations;
    }

    public List<OfficerRegistration> getRegistrations(Project project, 
                                                    RegistrationStatus status) {
        return registrationManager.filterRegistrations(getRegistrations(project), status);
    } 

    // do we invalidate all his previous registartions when approved?????????????????????????
    public void approveRegistration(HdbManager user, 
                                    ProjectTeam team, 
                                    OfficerRegistration registration) throws Exception {
        Project project = projectTeamManager.retrieveAssignedProject(team);
        if (!hasProjectAccess(user, project)) {
            throw new Exception("Access Denied. Not allowed to access this project. ");
        }
        HdbOfficer officer = registrationManager.retrieveAppliedOfficer(registration);
        if(projectTeamManager.hasMaxOfficers(team)) {
            throw new Exception("Maximum possible officers in team.  ");
        }
        registrationManager.approveRegistration(registration);
        projectTeamManager.assignProject(team, officer);
        projectTeamManager.addProjectTeam(userManager.retrieveTeams(officer), team);
    }

    public void rejectRegistration(HdbManager user, 
                                    ProjectTeam team, 
                                    OfficerRegistration registration) throws Exception {
        Project project = projectTeamManager.retrieveAssignedProject(team);
        if (!hasProjectAccess(user, project)) {
            throw new Exception("Access Denied. Not allowed to access this project. ");
        }
        registrationManager.rejectRegistration(registration);
    }

    public void joinTeam(HdbManager user, Project project) throws Exception {
        ProjectTeam projectTeam = projectManager.retrieveProjectTeam(project);
        if(projectTeamManager.hasManager(projectTeam)) {
            throw new Exception("Project already has a manager. ");
        }
        List<ProjectTeam> userTeams = userManager.retrieveTeams(user);
        for(ProjectTeam t : userTeams){
            Project p = projectTeamManager.retrieveAssignedProject(t);
            if(projectManager.hasTimeOverlap(project, p)){
                throw new Exception("Unable to join team, time overlapped with other projects. ");
            }
        }
        projectTeamManager.assignProject(projectTeam, user);
        projectTeamManager.addProjectTeam(userTeams, projectTeam);
    }

    private boolean hasProjectAccess(HdbManager user, Project project) throws Exception {
        List<ProjectTeam> teams = userManager.retrieveTeams(user);
        for(ProjectTeam t: teams) {
            Project p = projectTeamManager.retrieveAssignedProject(t);
            if(p.equals(project)) {
                return true;
            }
        }
        return false;
    }
}
