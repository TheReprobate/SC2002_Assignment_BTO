package btosystem.service.hdbofficer;

import java.util.List;

import btosystem.classes.Project;
import btosystem.classes.enums.Neighborhood;
import btosystem.service.applicant.ApplicantProjectService;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class HdbOfficerProjectService extends ApplicantProjectService {

    public HdbOfficerProjectService(DataManager dataManager, OperationsManager operationsManager) {
        super(dataManager, operationsManager);
    }

    public String displayProjects() {
        List<Project> projects = getDataManager().getProjects();
        return getOperationsManager().getProjectManager().toString(projects);
    }

    public String displayProjects(Neighborhood neighborhood) {
        List<Project> projects = getDataManager().getProjects();
        List<Project> filteredProjects = getOperationsManager().getProjectManager().filterProject(projects, neighborhood);
        return getOperationsManager().getProjectManager().toString(filteredProjects);
    }
}
