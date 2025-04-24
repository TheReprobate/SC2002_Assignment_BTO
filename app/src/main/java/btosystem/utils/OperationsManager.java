package btosystem.utils;

import btosystem.operations.interfaces.BtoApplicationOperations;
import btosystem.operations.interfaces.EnquiryOperations;
import btosystem.operations.interfaces.OfficerRegistrationOperations;
import btosystem.operations.interfaces.ProjectOperations;
import btosystem.operations.interfaces.ProjectTeamOperations;
import btosystem.operations.interfaces.UserOperations;

/**
 * OperationsManager serves as a class bundling all the Operations together.
 */
public class OperationsManager {
    private BtoApplicationOperations applicationManager;
    private EnquiryOperations enquiryManager;
    private OfficerRegistrationOperations registrationManager;
    private ProjectTeamOperations projectTeamManager;
    private UserOperations userManager;
    private ProjectOperations projectManager;

    /**
     * Constructors for OperationManager.
     *
     * @param applicationManager Manager for BtoApplicationOperations
     * @param enquiryManager Manager for EnquiryOperations
     * @param projectOperations Manager for ProjectOperations
     * @param projectTeamOperations Manager for ProjectTeamOperations
     * @param registrationOperations Manager for OfficerRegistrationOperations
     * @param userOperations Manager for UserOperations
     */
    public OperationsManager(BtoApplicationOperations applicationManager,
                             EnquiryOperations enquiryManager,
            OfficerRegistrationOperations registrationOperations,
                             ProjectTeamOperations projectTeamOperations,
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
