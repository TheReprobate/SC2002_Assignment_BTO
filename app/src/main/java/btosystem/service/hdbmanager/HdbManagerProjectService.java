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
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class HdbManagerProjectService extends Service {

    public HdbManagerProjectService(DataManager dataManager, OperationsManager operationsManager) {
        super(dataManager, operationsManager);
    }
    
    public List<Project> getProject() {
        return dataManager.getProjects();
    }

    public List<Project> getCreatedProject(HdbManager user) {
        return operationsManager.getUserManager().retrieveCreatedProjects(user);
    }

    public Project getCurrentProject(HdbManager user) {
        ProjectTeam currentTeam = operationsManager.getUserManager().retrieveCurrentTeam(user);
        if(currentTeam == null) {
            return null;
        }
        Project projectInCharge = operationsManager.getProjectTeamManager().retrieveAssignedProject(currentTeam);
        return projectInCharge;
    }

    public String displayReport(HdbManager user, Project project) throws Exception {
        if(!hasProjectAccess(user, project)) {
            throw new Exception("Access Denied. Not allowed to access this project. ");
        }
        List<BtoApplication> applications = operationsManager.getProjectManager().retrieveApplications(project);
        return operationsManager.getApplicationManager().toString(applications);
    }
    
    public void createProject(HdbManager user, String name, Neighborhood neighborhood, LocalDate openTime, LocalDate closeTime) throws Exception {
        if(projectExist(name)) {
            return;
        }

        List<Project> projects = dataManager.getProjects();
        List<Project> managerCreatedProjects = operationsManager.getUserManager().retrieveCreatedProjects(user);
        Project project = operationsManager.getProjectManager().createProject(name, neighborhood, openTime, closeTime, user);
        if(!hasValidTime(openTime, closeTime)){
            return;
        }
        ProjectTeam team = operationsManager.getProjectTeamManager().createProjectTeam(project);
        operationsManager.getProjectManager().setProjectTeam(project, team);
        operationsManager.getProjectManager().addProject(projects, project);
        operationsManager.getProjectManager().addProject(managerCreatedProjects, project);
    }

    public void editProject(Project project, LocalDate openTime, LocalDate closeTime) throws Exception {
        if(!hasValidTime(openTime, closeTime)){
            return;
        }
        operationsManager.getProjectManager().editProject(project, openTime, closeTime);
    }

    public void editProject(Project project, Neighborhood neighborhood) throws Exception {
        operationsManager.getProjectManager().editProject(project, neighborhood);
    }

    public void editProject(Project project, FlatType flatType, int count) throws Exception {
        operationsManager.getProjectManager().updateUnitCount(project, flatType, count);
    }

    public boolean projectExist(String name) throws Exception{
        List<Project> projects = dataManager.getProjects();
        boolean exist = operationsManager.getProjectManager().projectExist(projects, name);
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
