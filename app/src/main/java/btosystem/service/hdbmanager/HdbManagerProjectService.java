package btosystem.service.hdbmanager;

import java.time.LocalDateTime;
import java.util.List;

import btosystem.classes.BtoApplication;
import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
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
        return getDataManager().getProjects();
    }

    public List<Project> getCreatedProject(HdbManager user) {
        return getOperationsManager().getUserManager().retrieveCreatedProjects(user);
    }

    public Project getCurrentProject(HdbManager user) {
        ProjectTeam currentTeam = getOperationsManager().getUserManager().retrieveCurrentTeam(user);
        Project projectInCharge = getOperationsManager().getProjectTeamManager().retrieveAssignedProject(currentTeam);
        return projectInCharge;
    }

    public String filterProjects(List<Project> projects, Neighborhood neighborhood) {
        List<Project> filteredProjects = getOperationsManager().getProjectManager().filterProject(projects, neighborhood);
        return getOperationsManager().getProjectManager().toString(filteredProjects);
    }

    public String displayReport(HdbManager user, Project project) throws Exception {
        if(!hasProjectAccess(user, project)) {
            throw new Exception("Access Denied. Not allowed to access this project. ");
        }
        List<BtoApplication> applications = getOperationsManager().getApplicationManager().retrieveApplications(project);
        return getOperationsManager().getApplicationManager().toString(applications);
    }
    
    public void createProject(HdbManager user, String name, Neighborhood neighborhood, LocalDateTime openTime, LocalDateTime closeTime) throws Exception {
        if(projectExist(name)) {
            return;
        }

        List<Project> projects = getDataManager().getProjects();
        List<Project> managerCreatedProjects = getOperationsManager().getUserManager().retrieveCreatedProjects(user);
        Project project = getOperationsManager().getProjectManager().createProject(name, neighborhood, openTime, closeTime);
        if(!hasValidTime(openTime, closeTime)){
            return;
        }
        getOperationsManager().getProjectManager().addProject(projects, project);
        getOperationsManager().getProjectManager().addProject(managerCreatedProjects, project);
    }

    public void editProject(Project project, LocalDateTime openTime, LocalDateTime closeTime) throws Exception {
        if(!hasValidTime(openTime, closeTime)){
            return;
        }
        getOperationsManager().getProjectManager().editProject(project, openTime, closeTime);
    }

    public void editProject(Project project, Neighborhood neighborhood) throws Exception {
        getOperationsManager().getProjectManager().editProject(project, neighborhood);
    }

    public void editProject(Project project, FlatType flatType, int count) throws Exception {
        getOperationsManager().getProjectManager().updateUnitCount(project, flatType, count);
    }

    public boolean projectExist(String name) throws Exception{
        List<Project> projects = getDataManager().getProjects();
        if(!getOperationsManager().getProjectManager().projectExist(projects, name)) {
            throw new Exception("Project name already exist. ");
        }
        return true;
    }

    public boolean hasValidTime(LocalDateTime open, LocalDateTime close) throws Exception{
        if(open.isBefore(LocalDateTime.now())){
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
