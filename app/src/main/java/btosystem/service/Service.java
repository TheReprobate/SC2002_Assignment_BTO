package btosystem.service;


import btosystem.classes.Enquiry;
import btosystem.classes.Project;
import btosystem.classes.enums.Neighborhood;
import btosystem.controllers.interfaces.BtoApplicationOperations;
import btosystem.controllers.interfaces.EnquiryOperations;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;
import btosystem.controllers.interfaces.ProjectOperations;
import btosystem.controllers.interfaces.ProjectTeamOperations;
import btosystem.controllers.interfaces.UserOperations;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;
import java.util.List;

/**
 * Abstract base class for service layer components in the BTO system.
 * Provides common dependencies and functionality for concrete service implementations.
 * Acts as a central point for accessing various business operations managers
 * and the application's data repository.
 */
public abstract class Service {
    protected DataManager dataManager;
    protected BtoApplicationOperations applicationManager;
    protected EnquiryOperations enquiryManager;
    protected OfficerRegistrationOperations registrationManager;
    protected ProjectTeamOperations projectTeamManager;
    protected UserOperations userManager;
    protected ProjectOperations projectManager;

    /**
     * Constructs a new Service instance with required dependencies.
     *
     * @param dataManager the data repository manager
     * @param applicationManager BTO application operations manager
     * @param enquiryManager enquiry operations manager
     * @param registrationManager officer registration operations manager
     * @param projectTeamManager project team operations manager
     * @param userManager user operations manager
     * @param projectManager project operations manager
     */
    public Service(DataManager dataManager, BtoApplicationOperations applicationManager,
               EnquiryOperations enquiryManager, OfficerRegistrationOperations registrationManager,
               ProjectTeamOperations projectTeamManager, UserOperations userManager,
               ProjectOperations projectManager) {
        this.dataManager = dataManager;
        this.applicationManager = applicationManager;
        this.enquiryManager = enquiryManager;
        this.registrationManager = registrationManager;
        this.projectTeamManager = projectTeamManager;
        this.userManager = userManager;
        this.projectManager = projectManager;
    }
}
