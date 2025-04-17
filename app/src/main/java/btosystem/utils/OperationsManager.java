package btosystem.utils;

import btosystem.controllers.interfaces.BtoApplicationOperations;
import btosystem.controllers.interfaces.EnquiryOperations;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;
import btosystem.controllers.interfaces.ProjectOperations;
import btosystem.controllers.interfaces.ProjectTeamOperations;
import btosystem.controllers.interfaces.UserOperations;

public class OperationsManager {
    private BtoApplicationOperations applicationManager;
    private EnquiryOperations enquiryManager;
    private OfficerRegistrationOperations registrationManager;
    private ProjectTeamOperations projectTeamManager;
    private UserOperations userManager;
    private ProjectOperations projectManager;
    public OperationsManager(BtoApplicationOperations applicationManager, EnquiryOperations enquiryManager,
            OfficerRegistrationOperations registrationOperations, ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations, ProjectOperations projectOperations) {
        this.applicationManager = applicationManager;
        this.enquiryManager = enquiryManager;
        this.registrationManager = registrationOperations;
        this.projectTeamManager = projectTeamOperations;
        this.userManager = userOperations;
        this.projectManager = projectOperations;
    }
    public BtoApplicationOperations getApplicationManager() {
        return applicationManager;
    }
    public EnquiryOperations getEnquiryManager() {
        return enquiryManager;
    }
    public OfficerRegistrationOperations getRegistrationManager() {
        return registrationManager;
    }
    public ProjectTeamOperations getProjectTeamManager() {
        return projectTeamManager;
    }
    public UserOperations getUserManager() {
        return userManager;
    }
    public ProjectOperations getProjectManager() {
        return projectManager;
    }
    
}
