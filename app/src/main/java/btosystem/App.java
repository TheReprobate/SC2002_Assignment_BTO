package btosystem;

import btosystem.operations.BtoApplicationController;
import btosystem.operations.EnquiryController;
import btosystem.operations.OfficerRegistrationController;
import btosystem.operations.ProjectController;
import btosystem.operations.ProjectTeamController;
import btosystem.operations.UserController;
import btosystem.operations.interfaces.BtoApplicationOperations;
import btosystem.operations.interfaces.EnquiryOperations;
import btosystem.operations.interfaces.OfficerRegistrationOperations;
import btosystem.operations.interfaces.ProjectOperations;
import btosystem.operations.interfaces.ProjectTeamOperations;
import btosystem.operations.interfaces.UserOperations;
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
        GenericService gService = new GenericService(dManager, applicationManager,
                enquiryManager, officerManager, projectTeamManager, userManager, projectManager);
        
        ApplicantBtoApplicationService applicantAppService = new ApplicantBtoApplicationService(
                dManager, applicationManager, enquiryManager, officerManager,
                projectTeamManager, userManager, projectManager);

        ApplicantEnquiryService applicantEnquiryService = new ApplicantEnquiryService(
                dManager, applicationManager, enquiryManager, officerManager,
                projectTeamManager, userManager, projectManager);

        ApplicantProjectService applicantProjectService = new ApplicantProjectService(
                dManager, applicationManager, enquiryManager, officerManager,
                projectTeamManager, userManager, projectManager);
        
        HdbManagerBtoApplicationService managerAppService = new HdbManagerBtoApplicationService(
                dManager, applicationManager, enquiryManager,
                officerManager, projectTeamManager,
                userManager, projectManager);

        HdbManagerEnquiryService managerEnquiryService = new HdbManagerEnquiryService(
                dManager, applicationManager, enquiryManager, officerManager,
                projectTeamManager, userManager, projectManager);

        HdbManagerProjectService managerProjectService = new HdbManagerProjectService(
                dManager, applicationManager, enquiryManager,
                officerManager, projectTeamManager, userManager, projectManager);

        HdbManagerProjectTeamService managerTeamService = new HdbManagerProjectTeamService(
                dManager, applicationManager, enquiryManager, officerManager,
                projectTeamManager, userManager, projectManager);

        HdbOfficerBtoApplicationService officerAppService = new HdbOfficerBtoApplicationService(
                dManager, applicationManager, enquiryManager, officerManager,
                projectTeamManager, userManager, projectManager);

        HdbOfficerEnquiryService officerEnquiryService = new HdbOfficerEnquiryService(
                dManager, applicationManager, enquiryManager, officerManager,
                projectTeamManager, userManager, projectManager);

        HdbOfficerProjectService officerProjectService = new HdbOfficerProjectService(
                dManager, applicationManager, enquiryManager, officerManager,
                projectTeamManager, userManager, projectManager);

        HdbOfficerProjectTeamService officerProjectTeamService = new HdbOfficerProjectTeamService(
                dManager, applicationManager, enquiryManager, officerManager,
                projectTeamManager, userManager, projectManager);

        UserAccountService accountService = new UserAccountService(
                dManager, applicationManager, enquiryManager,
                officerManager, projectTeamManager, userManager, projectManager);

        ApplicantServiceManager applicantServiceManager = new ApplicantServiceManager(
                applicantAppService, applicantEnquiryService,
                applicantProjectService, gService);

        HdbManagerServiceManager hdbMangerServiceManager = new HdbManagerServiceManager(
                managerAppService, managerEnquiryService,
                managerProjectService, managerTeamService, gService);

        HdbOfficerServiceManager hdbOfficerServiceManager = new HdbOfficerServiceManager(
                officerAppService, officerEnquiryService,
                officerProjectService, officerProjectTeamService, gService);

        
        Entry client = new Entry(
                applicantServiceManager, hdbOfficerServiceManager,
                hdbMangerServiceManager, accountService, 
                dManager);
        client.run();
    }
}
