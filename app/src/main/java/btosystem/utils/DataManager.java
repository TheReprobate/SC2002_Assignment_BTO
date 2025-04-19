package btosystem.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import btosystem.classes.Project;
import btosystem.classes.User;

public class DataManager {
    private List<Project> projects;
    private HashMap<String, User> users;

    public DataManager() {
        projects = new ArrayList<>();
        users = new HashMap<>();
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }
}
