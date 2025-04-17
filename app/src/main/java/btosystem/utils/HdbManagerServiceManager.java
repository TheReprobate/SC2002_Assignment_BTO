package btosystem.utils;

import btosystem.cont.hdbmanager.HdbManagerBtoApplicationController;
import btosystem.cont.hdbmanager.HdbManagerEnquiryController;
import btosystem.service.GenericService;
import btosystem.service.hdbmanager.HdbManagerProjectService;
import btosystem.service.hdbmanager.HdbManagerProjectTeamService;

public class HdbManagerServiceManager {
    private HdbManagerBtoApplicationController applicationService;
    private HdbManagerEnquiryController enquiryService;
    private HdbManagerProjectService projectService;
    private HdbManagerProjectTeamService teamService;
    private GenericService genericService;
    public HdbManagerServiceManager(HdbManagerBtoApplicationController applicationService,
            HdbManagerEnquiryController enquiryService, HdbManagerProjectService projectService,
            HdbManagerProjectTeamService teamService, GenericService genericService) {
        this.applicationService = applicationService;
        this.enquiryService = enquiryService;
        this.projectService = projectService;
        this.teamService = teamService;
        this.genericService = genericService;
    }
    public HdbManagerBtoApplicationController getApplicationService() {
        return applicationService;
    }
    public HdbManagerEnquiryController getEnquiryService() {
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
