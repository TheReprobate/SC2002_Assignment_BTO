package btosystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
import btosystem.cont.Controller;
import btosystem.cont.applicant.ApplicantMainController;
import btosystem.cont.hdbmanager.HdbManagerBtoApplicationController;
import btosystem.cont.hdbmanager.HdbManagerCurrentProjectController;
import btosystem.cont.hdbmanager.HdbManagerEnquiryController;
import btosystem.cont.hdbmanager.HdbManagerMainController;
import btosystem.cont.hdbmanager.HdbManagerSystemProjectController;
import btosystem.cont.hdbofficer.HdbOfficerBtoApplicationController;
import btosystem.cont.hdbofficer.HdbOfficerEnquiryController;
import btosystem.cont.hdbofficer.HdbOfficerMainController;
import btosystem.cont.user.UserAccountController;
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
 * The main application class serving as the entry point for the program. This
 * implementation focuses on testing the Enquiry functionality.
 */
public class App {

    /**
     * The main method serving as the program entry point.
     *
     * @param args Command-line arguments passed to the application
     */
    public static void main(String[] args) {
        // Fake Data
        HdbOfficer officer = new HdbOfficer("T1200000A", "OFFICER1", 40, false);


        HdbManager manager1 = new HdbManager("T0200000A", "MANAGER1", 90, false);
        Project p1 = new Project("Project 1", Neighborhood.ANG_MO_KIO, LocalDate.now().minusDays(1), LocalDate.now().plusDays(5), manager1);
        ProjectTeam team1 = new ProjectTeam(p1);
        manager1.setCurrentTeam(team1);
        officer.setCurrentTeam(team1);
        team1.assignOfficer(officer);
        p1.setProjectTeam(team1);
        p1.setVisible(true);
        p1.getUnits().put(FlatType.TWO_ROOM, 2);
        p1.getUnits().put(FlatType.THREE_ROOM, 2);


        HdbManager manager2 = new HdbManager("T0300000A", "MANAGER2", 100, false);
        Project p2 = new Project("Project 2", Neighborhood.ANG_MO_KIO, LocalDate.now().minusDays(1), LocalDate.now().plusDays(5), manager2);
        ProjectTeam team2 = new ProjectTeam(p2);
        manager1.setCurrentTeam(team1);
        p2.setProjectTeam(team2);
        p2.setVisible(true);
        p2.getUnits().put(FlatType.TWO_ROOM, 100);
        p2.getUnits().put(FlatType.THREE_ROOM, 100);

        Applicant app1 = new Applicant("T0100000A", "TESTER1", 25, true);
        BtoApplication application1 = new BtoApplication(p1, app1, FlatType.THREE_ROOM);
        p1.addBtoApplication(application1);
        Enquiry notreplied = new Enquiry(p1, app1, "notreplied");
        Enquiry replied = new Enquiry(p1, app1, "replied");
        replied.setReplied(true);
        replied.setContent("hi");
        application1.setStatus(ApplicationStatus.SUCCESSFUL);
        app1.getEnquiries().add(notreplied);
        app1.getEnquiries().add(replied);
        app1.setActiveApplication(application1);

        Applicant app2 = new Applicant("T0200000A", "TESTER1", 40, false);
        BtoApplication application2 = new BtoApplication(p2, app2, FlatType.THREE_ROOM);
        p1.addBtoApplication(application2);
        Enquiry notreplied2 = new Enquiry(p2, app2, "notreplied2");
        Enquiry replied2 = new Enquiry(p2, app2, "replied2");
        replied2.setReplied(true);
        replied2.setContent("hi2");
        app2.getEnquiries().add(notreplied2);
        app2.getEnquiries().add(replied2);
        application2.setStatus(ApplicationStatus.SUCCESSFUL);
        app2.setActiveApplication(application2);

        p1.getEnquiries().add(replied);
        p1.getEnquiries().add(replied2);
        p1.getEnquiries().add(notreplied);
        p1.getEnquiries().add(notreplied2);


        List<Project> projects = new ArrayList<>();
        projects.add(p1);
        projects.add(p2);

        HashMap<String, User> users = new HashMap<>();
        users.put("T0200000A", manager1);
        users.put("T1200000A", officer);
        users.put("T0300000A", manager2);
        users.put("T0100000A", app1);
        users.put("T0500000A", app2);

        // Init Managers and Services
        BtoApplicationOperations applicationManager = new BtoApplicationController();
        EnquiryOperations enquiryManager = new EnquiryController();
        OfficerRegistrationOperations officerManager = new OfficerRegistrationController();
        ProjectOperations projectManager = new ProjectController();
        ProjectTeamOperations projectTeamManager = new ProjectTeamController();
        UserOperations userManager = new UserController();

        DataManager dManager = new DataManager();
        dManager.setProjects(projects); // testing
        dManager.setUsers(users); // testing
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
