package btosystem.service;

import btosystem.service.hdbmanager.HdbManagerBtoApplicationService;
import btosystem.service.hdbmanager.HdbManagerEnquiryService;
import btosystem.service.hdbmanager.HdbManagerProjectService;
import btosystem.service.hdbmanager.HdbManagerProjectTeamService;

/**
 * Manages the service classes related to HDB Manager functionalities.
 * This class aggregates instances of various HDB Manager specific services,
 * providing a centralized access point for these services.
 */
public class HdbManagerServiceManager {
    private HdbManagerBtoApplicationService applicationService;
    private HdbManagerEnquiryService enquiryService;
    private HdbManagerProjectService projectService;
    private HdbManagerProjectTeamService teamService;
    private GenericService genericService;

    /**
     * Constructs an {@code HdbManagerServiceManager} 
     *                           with the necessary HDB Manager service instances.
     * @param applicationService The service for handling BTO application operations 
     *                           for HDB Managers.
     * @param enquiryService     The service for handling enquiry operations for HDB Managers.
     * @param projectService     The service for handling project related operations 
     *                           for HDB Managers.
     * @param teamService        The service for managing project teams.
     * @param genericService     The generic service providing utility methods 
     *                           for displaying and retrieving business objects.
     */
    public HdbManagerServiceManager(
            HdbManagerBtoApplicationService applicationService,
            HdbManagerEnquiryService enquiryService,
            HdbManagerProjectService projectService,
            HdbManagerProjectTeamService teamService,
            GenericService genericService) {
        this.applicationService = applicationService;
        this.enquiryService = enquiryService;
        this.projectService = projectService;
        this.teamService = teamService;
        this.genericService = genericService;
    }

    public HdbManagerBtoApplicationService getApplicationService() { 
        return applicationService; 
    }

    public HdbManagerEnquiryService getEnquiryService() {
        return enquiryService;
    }

    public HdbManagerProjectService getProjectService() {
        return projectService;
    }

    public HdbManagerProjectTeamService getTeamService() {
        return teamService;
    }

    public GenericService getGenericService() {
        return genericService;
    }
}
