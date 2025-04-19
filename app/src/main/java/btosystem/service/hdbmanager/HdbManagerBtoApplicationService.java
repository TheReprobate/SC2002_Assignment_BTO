package btosystem.service.hdbmanager;

import java.util.List;

import btosystem.classes.BtoApplication;
import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.ApplicationStatus;
import btosystem.classes.enums.FlatType;
import btosystem.controllers.interfaces.BtoApplicationOperations;
import btosystem.controllers.interfaces.EnquiryOperations;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;
import btosystem.controllers.interfaces.ProjectOperations;
import btosystem.controllers.interfaces.ProjectTeamOperations;
import btosystem.controllers.interfaces.UserOperations;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class HdbManagerBtoApplicationService extends Service {

    public HdbManagerBtoApplicationService(DataManager dataManager, BtoApplicationOperations applicationManager, EnquiryOperations enquiryManager,
            OfficerRegistrationOperations registrationOperations, ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations, ProjectOperations projectOperations) {
        super(dataManager, applicationManager, enquiryManager, registrationOperations, projectTeamOperations, userOperations, projectOperations);
    }

    public List<BtoApplication> getApplications(Project project) {
        return projectManager.retrieveApplications(project);
    }

    public List<BtoApplication> getApplications(Project project, ApplicationStatus status) {
        return applicationManager.filterApplications(getApplications(project), status);
    }
    
    public void approveApplication(HdbManager user, BtoApplication application) throws Exception {
        if(!hasApplicationAccess(user, application)) {
            throw new Exception("Access Denied. Not allowed to access this application. ");
        }
        if(!applicationManager.isPending(application)) {
            throw new Exception("Application has been processed. ");
        }
        Project project = applicationManager.retrieveProject(application);
        FlatType flatType = applicationManager.retrieveFlatType(application);
        if(!projectManager.unitHasSlots(project, flatType)) {
            throw new Exception("No slots for unit type, unable to approve applicaton. ");
        } 
        applicationManager.approveApplication(application);
    }

    public void rejectApplication(HdbManager user, BtoApplication application) throws Exception {
        if(!hasApplicationAccess(user, application)) {
            throw new Exception("Access Denied. Not allowed to access this application. ");
        }
        if(!applicationManager.isPending(application)) {
            throw new Exception("Application has been processed. ");
        }
        applicationManager.rejectApplication(application);
    }

    private boolean hasApplicationAccess(HdbManager user, BtoApplication application) throws Exception {
        Project applicationProject = applicationManager.retrieveProject(application);
        return hasProjectAccess(user, applicationProject);
    }

    private boolean hasProjectAccess(HdbManager user, Project project) {
        ProjectTeam currentTeam = userManager.retrieveCurrentTeam(user);
        Project projectInCharge = projectTeamManager.retrieveAssignedProject(currentTeam);
        return projectInCharge.equals(project);
    }
    
}
