package btosystem.service;

import btosystem.service.applicant.ApplicantBtoApplicationService;
import btosystem.service.applicant.ApplicantEnquiryService;
import btosystem.service.applicant.ApplicantProjectService;

/**
 * Manages the service classes related to applicant functionalities.
 * This class holds instances of {@link ApplicantBtoApplicationService},
 * {@link ApplicantEnquiryService}, {@link ApplicantProjectService}, and
 * {@link GenericService}, providing a central point to access these services.
 */
public class ApplicantServiceManager {
    private ApplicantBtoApplicationService applicationService;
    private ApplicantEnquiryService enquiryService;
    private ApplicantProjectService projectService;
    private GenericService genericService;

    /**
     * Constructs an {@code ApplicantServiceManager} with the provided service instances.
     *
     * @param applicationService The service for handling BTO application related operations 
     *                              for applicants.
     * @param enquiryService     The service for handling enquiry related operations 
     *                              for applicants.
     * @param projectService     The service for handling project related operations 
     *                              for applicants.
     * @param genericService     The generic service providing utility methods 
     *                              for displaying and retrieving business objects.
     */
    public ApplicantServiceManager(
            ApplicantBtoApplicationService applicationService,
            ApplicantEnquiryService enquiryService,
            ApplicantProjectService projectService,
            GenericService genericService) {
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
