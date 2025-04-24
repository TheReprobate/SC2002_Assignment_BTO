package btosystem.service.applicant;

import java.util.List;

import btosystem.classes.Applicant;
import btosystem.classes.Project;
import btosystem.classes.User;
import btosystem.classes.enums.Neighborhood;
import btosystem.controllers.interfaces.BtoApplicationOperations;
import btosystem.controllers.interfaces.EnquiryOperations;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;
import btosystem.controllers.interfaces.ProjectOperations;
import btosystem.controllers.interfaces.ProjectTeamOperations;
import btosystem.controllers.interfaces.UserOperations;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class ApplicantProjectService extends Service {

    public ApplicantProjectService(DataManager dataManager, BtoApplicationOperations applicationOperations, EnquiryOperations enquiryOperations,
            OfficerRegistrationOperations registrationOperations, ProjectTeamOperations projectTeamOperations,
            UserOperations userOperations, ProjectOperations projectOperations) {
        super(dataManager, applicationOperations, enquiryOperations, registrationOperations, projectTeamOperations, userOperations, projectOperations);
    }

    public List<Project> getVisibleProjects() {
        List<Project> projects = dataManager.getProjects();
        List<Project> visibleProjects = projectManager.filterProject(projects, true);
        for(Project p : visibleProjects) {
            if (projectManager.isOpen(p)) {
                continue;
            }
            visibleProjects.remove(p);
        }
        return visibleProjects;
    }
}
