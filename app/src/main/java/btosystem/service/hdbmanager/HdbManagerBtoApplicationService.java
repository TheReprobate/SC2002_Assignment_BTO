package btosystem.service.hdbmanager;

import java.util.List;

import btosystem.classes.BtoApplication;
import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.FlatType;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class HdbManagerBtoApplicationService extends Service {

    public HdbManagerBtoApplicationService(DataManager dataManager, OperationsManager operationsManager) {
        super(dataManager, operationsManager);
    }
    
    public void approveApplication(HdbManager user, BtoApplication application) throws Exception {
        if(!hasApplicationAccess(user, application)) {
            throw new Exception("Access Denied. Not allowed to access this application. ");
        }
        if(!getOperationsManager().getApplicationManager().isPending(application)) {
            throw new Exception("Application has been processed. ");
        }
        Project project = getOperationsManager().getApplicationManager().retrieveProject(application);
        FlatType flatType = getOperationsManager().getApplicationManager().retrieveFlatType(application);
        if(!getOperationsManager().getProjectManager().unitHasSlots(project, flatType)) {
            throw new Exception("No slots for unit type, unable to approve applicaton. ");
        } 
        getOperationsManager().getApplicationManager().approveApplication(application);
    }

    public void rejectApplication(HdbManager user, BtoApplication application) throws Exception {
        if(!hasApplicationAccess(user, application)) {
            throw new Exception("Access Denied. Not allowed to access this application. ");
        }
        if(!getOperationsManager().getApplicationManager().isPending(application)) {
            throw new Exception("Application has been processed. ");
        }
        getOperationsManager().getApplicationManager().rejectApplication(application);
    }

    private boolean hasApplicationAccess(HdbManager user, BtoApplication application) throws Exception {
        Project applicationProject = getOperationsManager().getApplicationManager().retrieveProject(application);
        return hasProjectAccess(user, applicationProject);
    }

    private boolean hasProjectAccess(HdbManager user, Project project) {
        ProjectTeam currentTeam = getOperationsManager().getUserManager().retrieveCurrentTeam(user);
        Project projectInCharge = getOperationsManager().getProjectTeamManager().retrieveAssignedProject(currentTeam);
        return projectInCharge.equals(project);
    }
    
}
