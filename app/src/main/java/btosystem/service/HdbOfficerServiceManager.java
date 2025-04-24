package btosystem.service;

import btosystem.service.hdbofficer.HdbOfficerBtoApplicationService;
import btosystem.service.hdbofficer.HdbOfficerEnquiryService;
import btosystem.service.hdbofficer.HdbOfficerProjectService;
import btosystem.service.hdbofficer.HdbOfficerProjectTeamService;

/**
 * Manages the service classes related to HDB Officer functionalities.
 * This class aggregates instances of various HDB officer specific services,
 * providing a centralized access point for these services.
 */
public class HdbOfficerServiceManager extends ApplicantServiceManager {
    private HdbOfficerBtoApplicationService applicationService;
    private HdbOfficerEnquiryService enquiryService;
    private HdbOfficerProjectService projectService;
    private HdbOfficerProjectTeamService teamService;

    /**
     * Constructs an {@code HdbOfficerServiceManager} 
     * with the necessary HDB Officer service instances.
     *
     * @param applicationService The service for handling BTO application operations 
     *                           for HDB Officers.
     * @param enquiryService     The service for handling enquiry operations for HDB Officers.
     * @param projectService     The service for handling project related operations 
     *                           for HDB Officers.
     * @param teamService        The service for managing project teams.
     * @param genericService     The generic service providing utility methods 
     *                           for displaying and retrieving business objects.
     */
    public HdbOfficerServiceManager(
        HdbOfficerBtoApplicationService applicationService,
        HdbOfficerEnquiryService enquiryService,
        HdbOfficerProjectService projectService,
        HdbOfficerProjectTeamService teamService,
        GenericService genericService) {
        super(applicationService, enquiryService, projectService, genericService);
        this.applicationService = applicationService;
        this.enquiryService = enquiryService;
        this.projectService = projectService;
        this.teamService = teamService;
    }

    @Override
    public HdbOfficerBtoApplicationService getApplicationService() {
        return applicationService;
    }

    @Override
    public HdbOfficerEnquiryService getEnquiryService() {
        return enquiryService;
    }
    
    @Override
    public HdbOfficerProjectService getProjectService() {
        return projectService;
    }
    
    public HdbOfficerProjectTeamService getTeamService() {
        return teamService;
    }    
}
