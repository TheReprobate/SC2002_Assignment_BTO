package btosystem.service.applicant;

import java.util.List;

import btosystem.classes.Applicant;
import btosystem.classes.Enquiry;
import btosystem.classes.Project;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class ApplicantEnquiryService extends Service {
    public ApplicantEnquiryService(DataManager dataManager, OperationsManager operationsManager) {
        super(dataManager, operationsManager);
    }
    
    public List<Enquiry> getPersonalEnquiries(Applicant user) {
        return operationsManager.getUserManager().retrieveEnquiries(user);
    }
    
    public void createEnquiry(Applicant user, Project project, String content) throws Exception {
        Enquiry enquiry = operationsManager.getEnquiryManager().createEnquiry(project, user, content);
        List<Enquiry> projectEnquiryRef = operationsManager.getProjectManager().retrieveEnquiries(project);
        List<Enquiry> applicantEnquiryRef = getPersonalEnquiries(user);
        operationsManager.getEnquiryManager().addEnquiry(projectEnquiryRef, enquiry);
        operationsManager.getEnquiryManager().addEnquiry(applicantEnquiryRef, enquiry);
    }

    public void editEnquiry(Applicant user, Enquiry enquiry, String content) throws Exception {
        Project enquiryProject = operationsManager.getEnquiryManager().retrieveProject(enquiry);
        if(!operationsManager.getProjectManager().isOpen(enquiryProject)) {
            throw new Exception("Project is not open");
        }
        if (!(hasPermission(user, enquiry))) {
            throw new Exception("No permission to reply to this enquiry. ");
        }
        if (!isEditable(enquiry)) {
            throw new Exception("Enquiry has reply unable to process. ");
        }
        operationsManager.getEnquiryManager().editEnquiry(enquiry, content);
    }

    public void deleteEnquiry(Applicant user, Enquiry enquiry) throws Exception {
        Project enquiryProject = operationsManager.getEnquiryManager().retrieveProject(enquiry);
        if(!operationsManager.getProjectManager().isOpen(enquiryProject)) {
            throw new Exception("Project is not open");
        }
        if (!(hasPermission(user, enquiry))) {
            throw new Exception("No permission to reply to this enquiry. ");
        }
        if (!isEditable(enquiry)) {
            throw new Exception("Enquiry has reply unable to process. ");
        }
        Project project = operationsManager.getEnquiryManager().retrieveProject(enquiry);
        List<Enquiry> projectEnquiryRef = operationsManager.getProjectManager().retrieveEnquiries(project);
        List<Enquiry> applicantEnquiryRef = getPersonalEnquiries(user);
        operationsManager.getEnquiryManager().deleteEnquiry(projectEnquiryRef, enquiry);
        operationsManager.getEnquiryManager().deleteEnquiry(applicantEnquiryRef, enquiry);
    }

    public boolean isEditable(Enquiry enquiry) throws Exception {
        return !operationsManager.getEnquiryManager().hasReplied(enquiry);
    }

    private boolean hasPermission(Applicant user, Enquiry enquiry){
        Applicant enquiryApplicant = operationsManager.getEnquiryManager().retrieveApplicant(enquiry);
        return enquiryApplicant.equals(user);
    }
}
