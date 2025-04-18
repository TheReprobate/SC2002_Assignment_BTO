package btosystem.service;

import btosystem.service.hdbmanager.HdbManagerBtoApplicationService;
import btosystem.service.hdbmanager.HdbManagerEnquiryService;
import btosystem.service.hdbmanager.HdbManagerProjectService;
import btosystem.service.hdbmanager.HdbManagerProjectTeamService;

public class HdbManagerServiceManager {
    private HdbManagerBtoApplicationService applicationService;
    private HdbManagerEnquiryService enquiryService;
    private HdbManagerProjectService projectService;
    private HdbManagerProjectTeamService teamService;
    private GenericService genericService;
    public HdbManagerServiceManager(HdbManagerBtoApplicationService applicationService,
    HdbManagerEnquiryService enquiryService, HdbManagerProjectService projectService,
            HdbManagerProjectTeamService teamService, GenericService genericService) {
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
