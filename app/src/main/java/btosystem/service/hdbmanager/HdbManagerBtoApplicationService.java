package btosystem.service.hdbmanager;

import btosystem.classes.Applicant;
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
import java.util.List;

public class HdbManagerBtoApplicationService extends Service {

    public HdbManagerBtoApplicationService(DataManager dataManager, 
                                            BtoApplicationOperations applicationOperations, 
                                            EnquiryOperations enquiryOperations,
                                            OfficerRegistrationOperations registrationOperations, 
                                            ProjectTeamOperations projectTeamOperations,
                                            UserOperations userOperations, 
                                            ProjectOperations projectOperations) {
        super(dataManager, 
            applicationOperations, 
            enquiryOperations, 
            registrationOperations, 
            projectTeamOperations, 
            userOperations, 
            projectOperations);
    }

    public List<BtoApplication> getApplications(Project project) {
        return projectManager.retrieveApplications(project);
    }

    public List<BtoApplication> getApplications(Project project, ApplicationStatus status) {
        return applicationManager.filterApplications(getApplications(project), status);
    }
    
    public void approveApplication(HdbManager user, 
                                    BtoApplication application) throws Exception {
        if (!hasApplicationAccess(user, application)) {
            throw new Exception(
                "Access Denied. Not allowed to access this application. ");
        }
        if (!applicationManager.isPending(application)) {
            throw new Exception("Application has been processed. ");
        }
        Project project = applicationManager.retrieveProject(application);
        FlatType flatType = applicationManager.retrieveFlatType(application);
        if (!projectManager.unitHasSlots(project, flatType)) {
            throw new Exception(
                "No slots for unit type, unable to approve applicaton. ");
        } 
        applicationManager.approveApplication(application);
    }

    public void rejectApplication(HdbManager user, BtoApplication application) throws Exception {
        if (!hasApplicationAccess(user, application)) {
            throw new Exception(
                "Access Denied. Not allowed to access this application. ");
        }
        if (!applicationManager.isPending(application)) {
            throw new Exception("Application has been processed. ");
        }
        applicationManager.rejectApplication(application);
    }

    private boolean hasApplicationAccess(HdbManager user, 
                                        BtoApplication application) throws Exception {
        Project applicationProject = applicationManager.retrieveProject(application);
        return hasProjectAccess(user, applicationProject);
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
    
    private ProjectTeam getCurrentTeam(HdbManager user) throws Exception{
        List<ProjectTeam> teams = userManager.retrieveTeams(user);
        for(ProjectTeam t: teams) {
            Project p = projectTeamManager.retrieveAssignedProject(t);
            if(projectManager.isOpen(p)) {
                return t;
            }
        }
        throw new Exception("Currently not in a team.");
    }

    public String generateReport(Project project) {
        StringBuilder sb = new StringBuilder();
        List<BtoApplication> applications = projectManager.retrieveApplications(project);
        for(BtoApplication a: applications) {
            Applicant applicant = applicationManager.retrieveApplicant(a);
            sb.append(userManager.toString(applicant)).append(applicationManager.toString(a)).append('\n');
        }
        return sb.toString();
    }
}
