package btosystem.service.hdbofficer;

import java.util.List;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.FlatType;
import btosystem.service.applicant.ApplicantBtoApplicationService;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class HdbOfficerBtoApplicationService extends ApplicantBtoApplicationService{

    public HdbOfficerBtoApplicationService(DataManager dataManager, OperationsManager operationsManager) {
        super(dataManager, operationsManager);
    }
    
    public void createApplication(HdbOfficer user, Project project) throws Exception {
        if(hasProjectAccess(user, project)){
            throw new Exception("Access Denied. Not allowed to apply for this project. ");
        }
        super.createApplication((Applicant) user, project);
    }

    public void processApplication(Applicant applicant, HdbOfficer officer, FlatType flatType) throws Exception {
        BtoApplication application = getOperationsManager().getUserManager().retrieveApplication(applicant);
        if(!hasApplicationAccess(officer, application)){
            throw new Exception("Access Denied. Not allowed to access this application. ");
        }
        List<FlatType> allowedflatTypes = getOperationsManager().getUserManager().getAllowedFlatTypes(applicant);
        if(!allowedflatTypes.contains(flatType)) {
            throw new Exception("Access Denied. Not allowed to choose this flat type. ");
        }
        Project applicationProject = getOperationsManager().getApplicationManager().retrieveProject(application);
        if(!getOperationsManager().getProjectManager().unitHasSlots(applicationProject, flatType)) {
            throw new Exception("Flat type does don't have slots. ");
        }
        getOperationsManager().getApplicationManager().processApplication(application, officer, flatType);
        getOperationsManager().getProjectManager().processUnitCount(applicationProject, flatType);
    }
    private boolean hasApplicationAccess(HdbOfficer user, BtoApplication application) {
        Project applicationProject = getOperationsManager().getApplicationManager().retrieveProject(application);
        return hasProjectAccess(user, applicationProject);
    }
    private boolean hasProjectAccess(HdbOfficer user, Project project) {
        ProjectTeam currentTeam = getOperationsManager().getUserManager().retrieveCurrentTeam(user);
        Project projectInCharge = getOperationsManager().getProjectTeamManager().retrieveAssignedProject(currentTeam);
        return projectInCharge.equals(project);
    }
}
