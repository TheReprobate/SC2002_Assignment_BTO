package btosystem.controllers;

import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;
import btosystem.controllers.interfaces.ProjectOperations;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Controller for Project class that implements ProjectOperation interface.
 */
public class ProjectController implements ProjectOperations {

    /**
     * Constructor for creating Project.
     *
     * @param name Name of Project
     * @param neighborhood Name of neighbourhood the project will be built in
     * @param openTime BTO Application start date
     * @param closeTime BTO Application end date
     * @param hdbManager HDB Manager managing this object
     * @return Project object created
     */
    @Override
    public Project createProject(String name, Neighborhood neighborhood,
                                 LocalDate openTime, LocalDate closeTime, HdbManager hdbManager) {

        return new Project(name, neighborhood, openTime, closeTime, hdbManager);
    }

    /**
     * Retrieves a project from the list based on its name.
     *
     * @param projects List of projects to search through
     * @param name Name of Project to retrieve
     * @return Project object if found, null if no project with given name exists
     */
    @Override
    // Retrieve project by name
    public Project retrieveProject(List<Project> projects, String name) {
        for (Project proj : projects) {
            if (proj.getName().equals(name)) {
                // If found project, return proj
                return proj;
            }
        }
        // Return null if no such project exists
        return null;
    }

    /**
     * Retrieves a project from the list based on its index.
     *
     * @param projects List of projects
     * @param index Index of project in projects list
     * @return Project object if found, null if no project with given name exists
     */
    @Override
    // Retrieve project by index
    public Project retrieveProject(List<Project> projects, int index) {
        if (index < projects.size()) {
            return projects.get(index);
        }
        // Index is out of bounds
        return null;
    }


    /**
     * Method for filtering projects based on neighborhood.
     *
     * @param projects List of projects
     * @param neighborhood Neighborhood to filter
     * @return List of Projects with the same neighbourhood
     */
    @Override
    public List<Project> filterProject(List<Project> projects, Neighborhood neighborhood) {
        List<Project> filteredProjects = new ArrayList<>();
        for (Project proj : projects) {
            if (proj.getNeighborhood().equals(neighborhood)) {
                filteredProjects.add(proj);
            }
        }

        return filteredProjects;
    }

    /**
     * Method for filtering projects based on visibility.
     *
     * @param projects List of projects
     * @param visible Visibility of project
     * @return List of Projects with the selected visibility - true/false
     */
    @Override
    public List<Project> filterProject(List<Project> projects, boolean visible) {
        List<Project> filteredProjects = new ArrayList<>();
        for (Project proj : projects) {
            // If project's visibility is same as boolean visible, return project
            if (proj.isVisible() == visible) {
                filteredProjects.add(proj);
            }
        }
        return filteredProjects;
    }

    /**
     * Method for retrieving project team handling this project.
     *
     * @param project Project object
     * @return ProjectTeam object
     */
    @Override
    public ProjectTeam retrieveProjectTeam(Project project) {
        return project.getProjectTeam();
    }

    /**
     * Method for retrieving enquiries for this project.
     *
     * @param project Project object
     * @return List of Enquiry
     */
    @Override
    public List<Enquiry> retrieveEnquiries(Project project) {
        return project.getEnquiries();
    }

    /**
     * Method for retrieving BTO Applications for this project.
     *
     * @param project Project object
     * @return List of BTO Applications
     */
    @Override
    public List<BtoApplication> retrieveApplications(Project project) {
        return project.getBtoApplications();
    }

    /**
     * Method for updating project's unit availability.
     *
     * @param project Project object
     * @param flatType Flat type of object (enums)
     * @param count Unit count to be updated to
     * @return count to indicate successful update
     */
    @Override
    public int updateUnitCount(Project project, FlatType flatType, int count) {
        // Retrieve project's unit hashmap
        HashMap<FlatType, Integer> projectUnits = project.getUnits();
        // Update with the flatType and count. If it doesn't exist, it will create, else update
        projectUnits.put(flatType, count);
        // Update project's units
        project.setUnits(projectUnits);
        return count;
    }

    /**
     * Method for updating project's neighborhood.
     *
     * @param project Project object
     * @param neighborhood neighborhood to update project to
     * @return 1 for successful update
     */
    @Override
    public int editProject(Project project, Neighborhood neighborhood) {
        project.setNeighborhood(neighborhood);
        return 1;
    }

    /**
     * Method for updating project's application period.
     *
     * @param project Project object
     * @param openTime Application open date to update to
     * @param closeTime Application close date to update to
     * @return 1 for successful update
     */
    @Override
    public int editProject(Project project, LocalDate openTime, LocalDate closeTime) {
        project.setOpenTime(openTime);
        project.setCloseTime(closeTime);
        return 1;
    }

    /**
     * Method for deleting a project.
     *
     * @param projects List of projects
     * @param project Project to be deleted from the list
     * @return 1 for successful deletion else 0
     */
    @Override
    public int deleteProject(List<Project> projects, Project project) {
        // .remove already checks whether element exists in collection
        // so we do not need to check .contains
        boolean removed = projects.remove(project);
        return removed ? 1 : 0;
    }

    /**
     * Method for checking if project exists based on name.
     *
     * @param projects List of project
     * @param name Project name to be checked
     * @return true if exists, else false
     */
    @Override
    public boolean projectExist(List<Project> projects, String name) {
        for (Project proj : projects) {
            if (proj.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Cleanup operation to ensure no dangling reference.
     * Unused for now.
     */
    @Override
    public void cleanup(Project instance) {
        // When we delete a project,
        //instance.setProjectTeam(null);
    }

    /**
     * Method to print out all project's details.
     *
     * @param data List of projects
     * @return String consisting of all project's details
     */
    @Override
    public String toString(List<Project> data) {
        StringBuilder sb = new StringBuilder();

        // To adjust width of each column for formatting purposes
        String stringFormat = "%-4s %-30s %-15s %-12s %-12s%n";
        // Table header
        sb.append(String.format(stringFormat,
                "No.", "Name", "Neighborhood", "Open date", "Close date"));

        // Data rows
        int count = 1;
        for (Project p : data) {
            sb.append(String.format(stringFormat,
                    count++,
                    p.getName(),
                    p.getNeighborhood(),
                    p.getOpenTime(),
                    p.getCloseTime()));
        }

        return sb.toString();
    }

    /**
     * Method to print out a single project's details.
     *
     * @param data Project object to format into a string
     * @return String consisting of project's details
     */
    @Override
    public String toString(Project data) {
        return "Project Name: " + data.getName() + "\n"
                + "Neighbourhood: " + data.getNeighborhood() + "\n"
                + "Visible: " + data.isVisible() + "\n"
                + "Open Time: " + data.getOpenTime() + "\n"
                + "Close Time: " + data.getCloseTime() + "\n"
                + "Flat Type: " + data.getUnits() + "\n"
                + "Number of Applications: " + data.getBtoApplications().size() + "\n"
                + "Number of Enquiries: " + data.getEnquiries().size() + "\n"
                + "Created by Manager: " + data.getCreatedBy().getName() + "\n";

    }
}
