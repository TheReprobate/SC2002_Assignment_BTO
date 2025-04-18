package btosystem.service;

import btosystem.service.hdbofficer.HdbOfficerBtoApplicationService;
import btosystem.service.hdbofficer.HdbOfficerEnquiryService;
import btosystem.service.hdbofficer.HdbOfficerProjectService;
import btosystem.service.hdbofficer.HdbOfficerProjectTeamService;

public class HdbOfficerServiceManager extends ApplicantServiceManager{
    private HdbOfficerBtoApplicationService applicationService;
    private HdbOfficerEnquiryService enquiryService;
    private HdbOfficerProjectService projectService;
    private HdbOfficerProjectTeamService teamService;
    public HdbOfficerServiceManager(HdbOfficerBtoApplicationService applicationService,
            HdbOfficerEnquiryService enquiryService, HdbOfficerProjectService projectService,
            HdbOfficerProjectTeamService teamService, GenericService genericService) {
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
