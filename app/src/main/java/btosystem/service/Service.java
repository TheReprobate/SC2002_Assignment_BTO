package btosystem.service;

import java.util.List;

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

public abstract class Service {
    protected DataManager dataManager;
    protected BtoApplicationOperations applicationManager;
    protected EnquiryOperations enquiryManager;
    protected OfficerRegistrationOperations registrationManager;
    protected ProjectTeamOperations projectTeamManager;
    protected UserOperations userManager;
    protected ProjectOperations projectManager;
    public Service(DataManager dataManager, BtoApplicationOperations applicationManager,
            EnquiryOperations enquiryManager, OfficerRegistrationOperations registrationManager,
            ProjectTeamOperations projectTeamManager, UserOperations userManager, ProjectOperations projectManager) {
        this.dataManager = dataManager;
        this.applicationManager = applicationManager;
        this.enquiryManager = enquiryManager;
        this.registrationManager = registrationManager;
        this.projectTeamManager = projectTeamManager;
        this.userManager = userManager;
        this.projectManager = projectManager;
    }
    
}
