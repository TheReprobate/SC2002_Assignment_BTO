package btosystem.service.hdbmanager;

import java.time.LocalDate;
import java.util.List;

import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.ApplicationStatus;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;
import btosystem.controllers.interfaces.BtoApplicationOperations;
import btosystem.controllers.interfaces.EnquiryOperations;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;
import btosystem.controllers.interfaces.ProjectOperations;
import btosystem.controllers.interfaces.ProjectTeamOperations;
import btosystem.controllers.interfaces.UserOperations;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class HdbManagerProjectService extends Service {

    public HdbManagerProjectService(DataManager dataManager, BtoApplicationOperations applicationManager, EnquiryOperations enquiryManager,
            OfficerRegistrationOperations registrationOperations, ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations, ProjectOperations projectOperations) {
        super(dataManager, applicationManager, enquiryManager, registrationOperations, projectTeamOperations, userOperations, projectOperations);
    }
    
    public List<Project> getProject() {
        return dataManager.getProjects();
    }

    public List<Project> getCreatedProject(HdbManager user) {
        return userManager.retrieveCreatedProjects(user);
    }

    public Project getCurrentProject(HdbManager user) {
        ProjectTeam currentTeam = userManager.retrieveCurrentTeam(user);
        if(currentTeam == null) {
            return null;
        }
        Project projectInCharge = projectTeamManager.retrieveAssignedProject(currentTeam);
        return projectInCharge;
    }

    public String displayReport(HdbManager user, Project project) throws Exception {
        if(!hasProjectAccess(user, project)) {
            throw new Exception("Access Denied. Not allowed to access this project. ");
        }
        List<BtoApplication> applications = projectManager.retrieveApplications(project);
        return applicationManager.toString(applications);
    }
    
    public void createProject(HdbManager user, String name, Neighborhood neighborhood, LocalDate openTime, LocalDate closeTime) throws Exception {
        if(projectExist(name)) {
            return;
        }

        List<Project> projects = dataManager.getProjects();
        List<Project> managerCreatedProjects = userManager.retrieveCreatedProjects(user);
        Project project = projectManager.createProject(name, neighborhood, openTime, closeTime, user);
        if(!hasValidTime(openTime, closeTime)){
            return;
        }
        ProjectTeam team = projectTeamManager.createProjectTeam(project);
        projectManager.setProjectTeam(project, team);
        projectManager.addProject(projects, project);
        projectManager.addProject(managerCreatedProjects, project);
    }

    public void editProject(Project project, LocalDate openTime, LocalDate closeTime) throws Exception {
        if(!hasValidTime(openTime, closeTime)){
            return;
        }
        projectManager.editProject(project, openTime, closeTime);
    }

    public void editProject(Project project, Neighborhood neighborhood) throws Exception {
        projectManager.editProject(project, neighborhood);
    }

    public void editProject(Project project, FlatType flatType, int count) throws Exception {
        projectManager.updateUnitCount(project, flatType, count);
    }

    public boolean projectExist(String name) throws Exception{
        List<Project> projects = dataManager.getProjects();
        boolean exist = projectManager.projectExist(projects, name);
        if(exist) {
            throw new Exception("Project name already exist. ");
        }
        return exist;
    }

    public boolean hasValidTime(LocalDate open, LocalDate close) throws Exception{
        if(open.isBefore(LocalDate.now())){
            throw new Exception("Open time is in the past. "); 
        }
        if(close.isBefore(open)) {
            throw new Exception("Close time has to be after open time. "); 
        }
        return true;
    }

    private boolean hasProjectAccess(HdbManager user, Project project) {
        return getCurrentProject(user).equals(project);
    }
}
