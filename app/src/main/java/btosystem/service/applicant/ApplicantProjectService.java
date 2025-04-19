package btosystem.service.applicant;

import java.util.List;

import btosystem.classes.Project;
import btosystem.classes.enums.Neighborhood;
import btosystem.service.Service;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public class ApplicantProjectService extends Service {

    public ApplicantProjectService(DataManager dataManager, OperationsManager operationsManager) {
        super(dataManager, operationsManager);
    }

    public List<Project> getVisibleProjects(){
        List<Project> projects = dataManager.getProjects();
        List<Project> visibleProjects = operationsManager.getProjectManager().filterProject(projects, true);
        for(Project p : visibleProjects) {
            if(operationsManager.getProjectManager().isOpen(p)) {
                continue;
            }
            visibleProjects.remove(p);
        }
        return visibleProjects;
    }

    public List<Project> filterProject(List<Project> projects, Neighborhood neighborhood) {
        return operationsManager.getProjectManager().filterProject(projects, neighborhood);
    }
}
