package btosystem.service;

import btosystem.service.applicant.ApplicantBtoApplicationService;
import btosystem.service.applicant.ApplicantEnquiryService;
import btosystem.service.applicant.ApplicantProjectService;

public class ApplicantServiceManager {
    private ApplicantBtoApplicationService applicationService;
    private ApplicantEnquiryService enquiryService;
    private ApplicantProjectService projectService;
    private GenericService genericService; 
    public ApplicantServiceManager(ApplicantBtoApplicationService applicationService,
            ApplicantEnquiryService enquiryService, ApplicantProjectService projectService, GenericService genericService) {
        this.applicationService = applicationService;
        this.enquiryService = enquiryService;
        this.projectService = projectService;
        this.genericService = genericService;
    }
    public ApplicantBtoApplicationService getApplicationService() {
        return applicationService;
    }
    public ApplicantEnquiryService getEnquiryService() {
        return enquiryService;
    }
    public ApplicantProjectService getProjectService() {
        return projectService;
    }
    public GenericService getGenericService() {
        return genericService;
    }
}
