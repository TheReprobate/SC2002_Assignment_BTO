package btosystem.controllers;

import btosystem.classes.*;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;
import btosystem.controllers.interfaces.ProjectOperations;
import btosystem.controllers.interfaces.ProjectTeamOperations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProjectController implements ProjectOperations {

    // Default constructor

    @Override
    public Project createProject(String name, Neighborhood neighborhood, LocalDate openTime, LocalDate closeTime, HdbManager hdbManager) {
        // Create project object first
        Project proj = new Project(name, neighborhood, openTime, closeTime, hdbManager);
        // Then we create ProjectTeam based on proj obj
        ProjectTeam projTeam = new ProjectTeam(proj);
        // Now we set project's project team to newly created projTeam
        proj.setProjectTeam(projTeam);

        return proj;
    }

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

    @Override
    // Retrieve project by index
    public Project retrieveProject(List<Project> projects, int index) {
        if (index < projects.size()) {
            return projects.get(index);
        }
        // Index is out of bounds
        return null;
    }

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

    @Override
    public ProjectTeam retrieveProjectTeam(Project project) {
        return project.getProjectTeam();
    }

    @Override
    public List<Enquiry> retrieveEnquiries(Project project) {
        return project.getEnquiries();
    }

    @Override
    public List<BtoApplication> retrieveApplications(Project project) {
        return project.getBtoApplications();
    }

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

    @Override
    public int editProject(Project project, Neighborhood neighborhood) {
        project.setNeighborhood(neighborhood);
        return 1;
    }

    @Override
    public int editProject(Project project, LocalDate openTime, LocalDate closeTime) {
        project.setOpenTime(openTime);
        project.setCloseTime(closeTime);
        return 1;
    }

    @Override
    public int deleteProject(List<Project> projects, Project project) {
        // .remove already checks whether element exists in collection
        // so we do not need to check .contains
        boolean removed = projects.remove(project);
        return removed ? 1 : 0;
    }

    @Override
    public boolean projectExist(List<Project> projects, String name) {
        for (Project proj : projects) {
            if (proj.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void cleanup(Project instance) {
        // When we delete a project,
        //instance.setProjectTeam(null);
    }

    @Override
    public String toString(List<Project> data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            sb.append(i).append(". ").append(toString(data.get(i))).append("\n");
        }
        return sb.toString();
    }

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
