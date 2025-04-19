package btosystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.User;
import btosystem.classes.enums.ApplicationStatus;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;
import btosystem.clients.MainClient;
import btosystem.controllers.BtoApplicationController;
import btosystem.controllers.EnquiryController;
import btosystem.controllers.OfficerRegistrationController;
import btosystem.controllers.ProjectController;
import btosystem.controllers.ProjectTeamController;
import btosystem.controllers.UserController;
import btosystem.controllers.interfaces.BtoApplicationOperations;
import btosystem.controllers.interfaces.EnquiryOperations;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;
import btosystem.controllers.interfaces.ProjectOperations;
import btosystem.controllers.interfaces.ProjectTeamOperations;
import btosystem.controllers.interfaces.UserOperations;
import btosystem.service.ApplicantServiceManager;
import btosystem.service.GenericService;
import btosystem.service.HdbManagerServiceManager;
import btosystem.service.HdbOfficerServiceManager;
import btosystem.service.applicant.ApplicantBtoApplicationService;
import btosystem.service.applicant.ApplicantEnquiryService;
import btosystem.service.applicant.ApplicantProjectService;
import btosystem.service.hdbmanager.HdbManagerBtoApplicationService;
import btosystem.service.hdbmanager.HdbManagerEnquiryService;
import btosystem.service.hdbmanager.HdbManagerProjectService;
import btosystem.service.hdbmanager.HdbManagerProjectTeamService;
import btosystem.service.hdbofficer.HdbOfficerBtoApplicationService;
import btosystem.service.hdbofficer.HdbOfficerEnquiryService;
import btosystem.service.hdbofficer.HdbOfficerProjectService;
import btosystem.service.hdbofficer.HdbOfficerProjectTeamService;
import btosystem.service.user.UserAccountService;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

/**
 * The main application class serving as the entry point for the program.
 */
public class App {

    /**
     * The main method serving as the program entry point.
     *
     * @param args Command-line arguments passed to the application
     */
    public static void main(String[] args) {
        // Init Managers and Services
        BtoApplicationOperations applicationManager = new BtoApplicationController();
        EnquiryOperations enquiryManager = new EnquiryController();
        OfficerRegistrationOperations officerManager = new OfficerRegistrationController();
        ProjectOperations projectManager = new ProjectController();
        ProjectTeamOperations projectTeamManager = new ProjectTeamController();
        UserOperations userManager = new UserController();

        DataManager dManager = new DataManager();
        OperationsManager oManager = new OperationsManager(applicationManager, enquiryManager, officerManager, projectTeamManager, userManager, projectManager);
        GenericService gService = new GenericService(dManager, oManager);
        
        ApplicantBtoApplicationService applicantAppService = new ApplicantBtoApplicationService(dManager, oManager);
        ApplicantEnquiryService applicantEnquiryService = new ApplicantEnquiryService(dManager, oManager);
        ApplicantProjectService applicantProjectService = new ApplicantProjectService(dManager, oManager);
        
        HdbManagerBtoApplicationService managerAppService = new HdbManagerBtoApplicationService(dManager, oManager);
        HdbManagerEnquiryService managerEnquiryService = new HdbManagerEnquiryService(dManager, oManager);
        HdbManagerProjectService managerProjectService = new HdbManagerProjectService(dManager, oManager);
        HdbManagerProjectTeamService managerTeamService = new HdbManagerProjectTeamService(dManager, oManager);

        HdbOfficerBtoApplicationService officerAppService = new HdbOfficerBtoApplicationService(dManager, oManager);
        HdbOfficerEnquiryService officerEnquiryService = new HdbOfficerEnquiryService(dManager, oManager);
        HdbOfficerProjectService officerProjectService = new HdbOfficerProjectService(dManager, oManager);
        HdbOfficerProjectTeamService officerProjectTeamService = new HdbOfficerProjectTeamService(dManager, oManager);

        UserAccountService accountService = new UserAccountService(dManager, oManager);

        ApplicantServiceManager applicantServiceManager = new ApplicantServiceManager(applicantAppService, applicantEnquiryService, applicantProjectService, gService);
        HdbManagerServiceManager hdbMangerServiceManager = new HdbManagerServiceManager(managerAppService, managerEnquiryService, managerProjectService, managerTeamService, gService);
        HdbOfficerServiceManager hdbOfficerServiceManager = new HdbOfficerServiceManager(officerAppService, officerEnquiryService, officerProjectService, officerProjectTeamService, gService);

        
        MainClient client = new MainClient(applicantServiceManager, hdbOfficerServiceManager, hdbMangerServiceManager, accountService);
        client.run();
    }
}
