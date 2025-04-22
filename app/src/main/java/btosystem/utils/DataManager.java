package btosystem.utils;

import btosystem.classes.Project;
import btosystem.classes.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Manages and provides access to the application's core data structures
 * including projects and user accounts.
 */
public class DataManager {
    private List<Project> projects;
    private HashMap<String, User> users;

    /**
     * Constructs a new DataManager with empty collections.
     */
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
