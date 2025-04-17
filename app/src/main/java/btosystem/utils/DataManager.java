package btosystem.utils;

import java.util.ArrayList;
import java.util.List;

import btosystem.classes.Project;
import btosystem.classes.User;

public class DataManager {
    private List<Project> projects;
    private List<User> users;

    public DataManager() {
        projects = new ArrayList<>();
        users = new ArrayList<>();
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
}
