package btosystem.service.hdbmanager;

import java.util.List;

import btosystem.classes.BtoApplication;
import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.ApplicationStatus;
import btosystem.classes.enums.FlatType;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class HdbManagerBtoApplicationService extends Service {

    public HdbManagerBtoApplicationService(DataManager dataManager, OperationsManager operationsManager) {
        super(dataManager, operationsManager);
    }

    public List<BtoApplication> getApplications(Project project) {
        return operationsManager.getProjectManager().retrieveApplications(project);
    }

    public List<BtoApplication> getApplications(Project project, ApplicationStatus status) {
        return operationsManager.getApplicationManager().filterApplications(getApplications(project), status);
    }
    
    public void approveApplication(HdbManager user, BtoApplication application) throws Exception {
        if(!hasApplicationAccess(user, application)) {
            throw new Exception("Access Denied. Not allowed to access this application. ");
        }
        if(!operationsManager.getApplicationManager().isPending(application)) {
            throw new Exception("Application has been processed. ");
        }
        Project project = operationsManager.getApplicationManager().retrieveProject(application);
        FlatType flatType = operationsManager.getApplicationManager().retrieveFlatType(application);
        if(!operationsManager.getProjectManager().unitHasSlots(project, flatType)) {
            throw new Exception("No slots for unit type, unable to approve applicaton. ");
        } 
        operationsManager.getApplicationManager().approveApplication(application);
    }

    public void rejectApplication(HdbManager user, BtoApplication application) throws Exception {
        if(!hasApplicationAccess(user, application)) {
            throw new Exception("Access Denied. Not allowed to access this application. ");
        }
        if(!operationsManager.getApplicationManager().isPending(application)) {
            throw new Exception("Application has been processed. ");
        }
        operationsManager.getApplicationManager().rejectApplication(application);
    }

    private boolean hasApplicationAccess(HdbManager user, BtoApplication application) throws Exception {
        Project applicationProject = operationsManager.getApplicationManager().retrieveProject(application);
        return hasProjectAccess(user, applicationProject);
    }

    private boolean hasProjectAccess(HdbManager user, Project project) {
        ProjectTeam currentTeam = operationsManager.getUserManager().retrieveCurrentTeam(user);
        Project projectInCharge = operationsManager.getProjectTeamManager().retrieveAssignedProject(currentTeam);
        return projectInCharge.equals(project);
    }
    
}
