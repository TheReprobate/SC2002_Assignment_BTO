package btosystem.service.applicant;

import java.util.List;

import btosystem.classes.Applicant;
import btosystem.classes.Enquiry;
import btosystem.classes.Project;
import btosystem.controllers.interfaces.BtoApplicationOperations;
import btosystem.controllers.interfaces.EnquiryOperations;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;
import btosystem.controllers.interfaces.ProjectOperations;
import btosystem.controllers.interfaces.ProjectTeamOperations;
import btosystem.controllers.interfaces.UserOperations;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class ApplicantEnquiryService extends Service {
    public ApplicantEnquiryService(DataManager dataManager, BtoApplicationOperations applicationManager, EnquiryOperations enquiryManager,
            OfficerRegistrationOperations registrationOperations, ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations, ProjectOperations projectOperations) {
        super(dataManager, applicationManager, enquiryManager, registrationOperations, projectTeamOperations, userOperations, projectOperations);
    }
    
    public List<Enquiry> getPersonalEnquiries(Applicant user) {
        return userManager.retrieveEnquiries(user);
    }
    
    public void createEnquiry(Applicant user, Project project, String content) throws Exception {
        Enquiry enquiry = enquiryManager.createEnquiry(project, user, content);
        List<Enquiry> projectEnquiryRef = projectManager.retrieveEnquiries(project);
        List<Enquiry> applicantEnquiryRef = getPersonalEnquiries(user);
        enquiryManager.addEnquiry(projectEnquiryRef, enquiry);
        enquiryManager.addEnquiry(applicantEnquiryRef, enquiry);
    }

    public void editEnquiry(Applicant user, Enquiry enquiry, String content) throws Exception {
        Project enquiryProject = enquiryManager.retrieveProject(enquiry);
        if (!projectManager.isOpen(enquiryProject)) {
            throw new Exception("Project is not open");
        }
        if (!(hasPermission(user, enquiry))) {
            throw new Exception("No permission to reply to this enquiry. ");
        }
        if (!isEditable(enquiry)) {
            throw new Exception("Enquiry has reply unable to process. ");
        }
        enquiryManager.editEnquiry(enquiry, content);
    }

    public void deleteEnquiry(Applicant user, Enquiry enquiry) throws Exception {
        Project enquiryProject = enquiryManager.retrieveProject(enquiry);
        if (!projectManager.isOpen(enquiryProject)) {
            throw new Exception("Project is not open");
        }
        if (!(hasPermission(user, enquiry))) {
            throw new Exception("No permission to reply to this enquiry. ");
        }
        if (!isEditable(enquiry)) {
            throw new Exception("Enquiry has reply unable to process. ");
        }
        Project project = enquiryManager.retrieveProject(enquiry);
        List<Enquiry> projectEnquiryRef = projectManager.retrieveEnquiries(project);
        List<Enquiry> applicantEnquiryRef = getPersonalEnquiries(user);
        enquiryManager.deleteEnquiry(projectEnquiryRef, enquiry);
        enquiryManager.deleteEnquiry(applicantEnquiryRef, enquiry);
    }

    public boolean isEditable(Enquiry enquiry) throws Exception {
        return !enquiryManager.hasReplied(enquiry);
    }

    private boolean hasPermission(Applicant user, Enquiry enquiry) {
        Applicant enquiryApplicant = enquiryManager.retrieveApplicant(enquiry);
        return enquiryApplicant.equals(user);
    }
}
