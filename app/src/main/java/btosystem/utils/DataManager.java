package btosystem.utils;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.Enquiry;
import btosystem.classes.HdbManager;
import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.User;
import btosystem.classes.enums.ApplicationStatus;
import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;
import btosystem.classes.enums.RegistrationStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        loadApplicant();
        loadManager();
        loadOfficer();
        loadProjects();
        loadEnquiry();
        loadApplication();
        loadProjectTeam();
        loadRegistration();
        loadFlatType();
    }

    /**
     * Retrieves the list of all projects.
     *
     * @return A List of {@link Project} objects.
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Sets the list of projects.
     *
     * @param projects The new List of {@link Project} objects to set.
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Retrieves the map of users, where the key is the user's NRIC.
     *
     * @return A HashMap where the key is the user's NRIC (String) 
     *          and the value is the {@link User} object.
     */
    public HashMap<String, User> getUsers() {
        return users;
    }

    /**
     * Sets the map of users.
     *
     * @param users The new HashMap where the key is the user's NRIC (String) 
     *              and the value is the {@link User} object.
     */
    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }

    /**
     * Saves all the data managed by this DataManager to CSV files.
     * This includes applicants, managers, officers, projects, enquiries,
     * applications, project teams, registrations, and flat types.
     */
    public void save() {
        saveApplicant();
        saveManager();
        saveOfficer();
        saveProject();
        saveEnquiry();
        saveApplications();
        saveProjectTeam();
        saveRegistration();
        saveFlatType();
    }

    /**
     * Saves the list of applicants to the ApplicantList.csv file.
     * The CSV file contains the name, NRIC, age, marital status, and password of each applicant.
     */
    private void saveApplicant() {
        List<User> temp = new ArrayList<>(users.values());
        List<String> buffer = new ArrayList<>();
        buffer.add("Name,NRIC,Age,MaritalStatus,Password");
        for (User user : temp) {
            if (user instanceof HdbOfficer) {
                continue;
            }
            if (!(user instanceof Applicant)) {
                continue;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(CsvParser.escapeCsv(user.getName())).append(",");
            sb.append(CsvParser.escapeCsv(user.getNric())).append(",");
            sb.append(user.getAge()).append(",");
            sb.append(user.isMarried() ? "Married" : "Single").append(",");
            sb.append(CsvParser.escapeCsv(user.getPassword()));
            buffer.add(sb.toString());
        }
        CsvParser.saveToCSV("ApplicantList.csv", buffer);
    }

    /**
     * Saves the list of HDB managers to the ManagerList.csv file.
     * The CSV file contains the name, NRIC, age, marital status, and password of each manager.
     */
    private void saveManager() {
        List<User> temp = new ArrayList<>(users.values());
        List<String> buffer = new ArrayList<>();
        buffer.add("Name,NRIC,Age,MaritalStatus,Password");
        for (User user : temp) {
            if (!(user instanceof HdbManager)) {
                continue;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(CsvParser.escapeCsv(user.getName())).append(",");
            sb.append(CsvParser.escapeCsv(user.getNric())).append(",");
            sb.append(user.getAge()).append(",");
            sb.append(user.isMarried() ? "Married" : "Single").append(",");
            sb.append(CsvParser.escapeCsv(user.getPassword()));
            buffer.add(sb.toString());
        }
        CsvParser.saveToCSV("ManagerList.csv", buffer);
    }

    /**
     * Saves the list of HDB officers to the OfficerList.csv file.
     * The CSV file contains the name, NRIC, age, marital status, and password of each officer.
     */
    private void saveOfficer() {
        List<User> temp = new ArrayList<>(users.values());
        List<String> buffer = new ArrayList<>();
        buffer.add("Name,NRIC,Age,MaritalStatus,Password");
        for (User user : temp) {
            if (!(user instanceof HdbOfficer)) {
                continue;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(CsvParser.escapeCsv(user.getName())).append(",");
            sb.append(CsvParser.escapeCsv(user.getNric())).append(",");
            sb.append(user.getAge()).append(",");
            sb.append(user.isMarried() ? "Married" : "Single").append(",");
            sb.append(CsvParser.escapeCsv(user.getPassword()));
            buffer.add(sb.toString());
        }
        CsvParser.saveToCSV("OfficerList.csv", buffer);
    }

    /**
     * Saves the list of projects to the ProjectList.csv file.
     * The CSV file contains the name, neighborhood index, visibility status,
     * open time, close time, and the NRIC of the HDB manager who created the project.
     */
    private void saveProject() {
        List<Project> temp = projects;
        List<String> buffer = new ArrayList<>();
        List<Neighborhood> neighborhoods = Arrays.asList(Neighborhood.values());
        buffer.add("Name,Neighborhood,Visible,OpenTime,CloseTime,HdbManager Nric");
        for (Project project : temp) {
            StringBuilder sb = new StringBuilder();
            sb.append(CsvParser.escapeCsv(project.getName())).append(",");
            sb.append(CsvParser.escapeCsv(neighborhoods.indexOf(
                                        project.getNeighborhood()))).append(",");
            sb.append(CsvParser.escapeCsv(project.isVisible() ? "Yes" : "No")).append(",");
            sb.append(CsvParser.escapeCsv(project.getOpenTime().toString())).append(",");
            sb.append(CsvParser.escapeCsv(project.getCloseTime().toString())).append(",");
            sb.append(CsvParser.escapeCsv(project.getCreatedBy().getNric())).append(",");
            buffer.add(sb.toString());
        }
        CsvParser.saveToCSV("ProjectList.csv", buffer);
    }

    /**
     * Saves all enquiries from all projects to the EnquiryList.csv file.
     * The CSV file contains the project name, applicant NRIC, content, reply,
     * replied status, creation timestamp, and replied timestamp.
     */
    private void saveEnquiry() {
        List<String> buffer = new ArrayList<>();
        List<Enquiry> enquiries = projects.stream()
            .flatMap(p -> p.getEnquiries().stream())
            .collect(Collectors.toCollection(ArrayList::new));

        buffer.add("ProjectName,ApplicantNric,Content,Reply,Replied,CreatedAt,RepliedAt");
        for (Enquiry enquiry : enquiries) {
            StringBuilder sb = new StringBuilder();
            Project p = enquiry.getProject();
            sb.append(CsvParser.escapeCsv(p == null ? "null" : p.getName())).append(",");
            sb.append(CsvParser.escapeCsv(enquiry.getApplicant().getNric())).append(",");
            sb.append(CsvParser.escapeCsv(enquiry.getContent())).append(",");
            sb.append(CsvParser.escapeCsv(enquiry.getReply())).append(",");
            sb.append(enquiry.hasReplied() ? "Yes" : "No").append(",");
            sb.append(enquiry.getCreatedAt().toString()).append(",");
            sb.append(enquiry.getRepliedAt() != null ? enquiry.getRepliedAt().toString() : "null");
            buffer.add(sb.toString());
        }
        CsvParser.saveToCSV("EnquiryList.csv", buffer);
    }

    /**
     * Saves all BTO applications from all projects to the ApplicationList.csv file.
     * The CSV file contains the project name, applicant NRIC, status index,
     * officer NRIC, flat index, and whether it's the applicant's active application 
     * for the project.
     */
    private void saveApplications() {
        List<String> buffer = new ArrayList<>();
        // Header
        buffer.add("ProjectName,ApplicantNric,StatusIndex,OfficerNric,FlatIndex,ActiveProject");

        // Gather all applications
        List<BtoApplication> applications = projects.stream()
            .flatMap(p -> p.getBtoApplications().stream())
            .collect(Collectors.toList());

        List<ApplicationStatus> statuses = Arrays.asList(ApplicationStatus.values());
        List<FlatType> flats = Arrays.asList(FlatType.values());

        for (BtoApplication application : applications) {
            StringBuilder sb = new StringBuilder();
            Project p = application.getProject();
            sb.append(CsvParser.escapeCsv(p == null ? "null" : p.getName())).append(",");
            sb.append(CsvParser.escapeCsv(application.getApplicant().getNric())).append(",");
            sb.append(statuses.indexOf(application.getStatus())).append(",");
            HdbOfficer officer = application.getOfficerInCharge();
            sb.append(officer != null ? CsvParser.escapeCsv(officer.getNric()) :
                                                            "null").append(",");
            sb.append(flats.indexOf(application.getFlatType())).append(",");
            // Check if this is the applicant's active application for this project
            boolean isActive = application.getApplicant().getActiveApplication() == application;
            sb.append(isActive ? "Yes" : "No");
            buffer.add(sb.toString());
        }
        CsvParser.saveToCSV("ApplicationList.csv", buffer);
    }

    /**
     * Saves the project team information for each project to the ProjectTeamList.csv file.
     * The CSV file contains the project name, 
     * manager NRIC, and a semicolon-separated list of officer NRICs.
     */
    private void saveProjectTeam() {
        List<String> buffer = new ArrayList<>();
        buffer.add("ProjectName,ManagerNric,OfficersNric");

        for (Project project : projects) {
            ProjectTeam team = project.getProjectTeam();

            String projectName = CsvParser.escapeCsv(project.getName());
            String managerNric = "null";
            HdbManager manager = team.getManager();
            if (manager != null) {
                managerNric = CsvParser.escapeCsv(manager.getNric());
            }

            String officersNric = "null";
            List<HdbOfficer> officers = team.getOfficers();
            if (officers != null && !officers.isEmpty()) {
                officersNric = officers.stream()
                    .map(o -> CsvParser.escapeCsv(o.getNric()))
                    .collect(Collectors.joining(";"));
            }

            String row = String.join(",", projectName, managerNric, officersNric);
            buffer.add(row);
        }

        CsvParser.saveToCSV("ProjectTeamList.csv", buffer);
    }

    /**
     * Saves the officer registration status for each project to the RegistrationList.csv file.
     * The CSV file contains the project name, officer NRIC, and the registration status index.
     */
    private void saveRegistration() {
        List<String> buffer = new ArrayList<>();
        // Header
        buffer.add("ProjectName,OfficerNric,StatusIndex");

        List<RegistrationStatus> statuses = Arrays.asList(RegistrationStatus.values());

        for (Project project : projects) {
            ProjectTeam team = project.getProjectTeam();
            // Assuming you have a way to get OfficerRegistration objects for each officer
            for (OfficerRegistration registration : team.getOfficerRegistrations()) {
                String projectName = CsvParser.escapeCsv(project.getName());
                String officerNric = CsvParser.escapeCsv(registration.getOfficer().getNric());
                int statusIndex = statuses.indexOf(registration.getStatus());

                String row = String.join(",",
                                        projectName,
                                        officerNric, String.valueOf(statusIndex));
                buffer.add(row);
            }
        }

        CsvParser.saveToCSV("RegistrationList.csv", buffer);
    }

    /**
     * Saves the flat type counts for each project to the FlatList.csv file.
     * The CSV file contains the project name, flat type index, and the count of available units.
     */
    private void saveFlatType() {
        List<String> buffer = new ArrayList<>();
        buffer.add("ProjectName,FlatTypeIndex,Count");
        List<FlatType> flats = Arrays.asList(FlatType.values());
        for (Project project : projects) {
            HashMap<FlatType, Integer> units = project.getUnits();
            for (int i = 0; i < flats.size(); i++) {
                FlatType flatType = flats.get(i);
                Integer count = units.get(flatType);
                if (count != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(CsvParser.escapeCsv(project.getName())).append(",");
                    sb.append(i).append(",");
                    sb.append(count);
                    buffer.add(sb.toString());
                }
            }
        }
        CsvParser.saveToCSV("FlatList.csv", buffer);
    }

    /**
     * Loads project data from the ProjectList.csv file.
     * This method reads project details and associates them with the HDB manager who created them.
     */
    private void loadProjects() {
        List<List<String>> rawData = CsvParser.loadFromCSV("ProjectList.csv");
        List<Neighborhood> neighborhoods = Arrays.asList(Neighborhood.values());
        for (List<String> row : rawData) {
            String name = row.get(0);
            int neighborhoodIndex = Integer.parseInt(row.get(1));
            Neighborhood neighborhood = neighborhoods.get(neighborhoodIndex);
            boolean visible = row.get(2).equalsIgnoreCase("Yes");
            LocalDate openTime = LocalDate.parse(row.get(3));
            LocalDate closeTime = LocalDate.parse(row.get(4));
            String managerNric = row.get(5);

            HdbManager manager = (HdbManager) users.get(managerNric);
            Project project = new Project(name, neighborhood, openTime, closeTime, manager);
            project.setVisible(visible);
            manager.getCreatedProjects().add(project);
            projects.add(project);
        }
    }

    /**
     * Loads applicant data from the ApplicantList.csv file.
     * This method reads applicant details and creates {@link Applicant} objects,
     * storing them in the {@code users} map with their NRIC as the key.
     */
    private void loadApplicant() {
        List<List<String>> rawData = CsvParser.loadFromCSV("ApplicantList.csv");
        for (List<String> data : rawData) {
            String name = data.get(0);
            String nric = data.get(1);
            int age = Integer.valueOf(data.get(2));
            boolean married = data.get(3).equals("Married");
            String password = data.get(4);
            Applicant applicant = new Applicant(nric, name, age, married);
            applicant.setPassword(password);
            users.put(nric, applicant);
        }
    }

    /**
     * Loads HDB manager data from the ManagerList.csv file.
     * This method reads manager details and creates {@link HdbManager} objects,
     * storing them in the {@code users} map with their NRIC as the key.
     */
    private void loadManager() {
        List<List<String>> rawData = CsvParser.loadFromCSV("ManagerList.csv");
        for (List<String> data : rawData) {
            String name = data.get(0);
            String nric = data.get(1);
            int age = Integer.valueOf(data.get(2));
            boolean married = data.get(3).equals("Married");
            String password = data.get(4);
            HdbManager applicant = new HdbManager(nric, name, age, married);
            applicant.setPassword(password);
            users.put(nric, applicant);
        }
    }

    /**
     * Loads HDB officer data from the OfficerList.csv file.
     * This method reads officer details, creates {@link HdbOfficer} objects,
     * sets their password, and stores them in the {@code users} map with their
     * NRIC as the key.
     */
    private void loadOfficer() {
        List<List<String>> rawData = CsvParser.loadFromCSV("OfficerList.csv");
        for (List<String> data : rawData) {
            String name = data.get(0);
            String nric = data.get(1);
            int age = Integer.valueOf(data.get(2));
            boolean married = data.get(3).equals("Married");
            String password = data.get(4);
            HdbOfficer applicant = new HdbOfficer(nric, name, age, married);
            applicant.setPassword(password);
            users.put(nric, applicant);
        }
    }

    /**
     * Loads enquiry data from the EnquiryList.csv file.
     * This method reads enquiry details, associates them with the corresponding
     * {@link Project} and {@link Applicant}, and stores them. It also handles
     * the parsing of the creation and reply timestamps. The loaded enquiries
     * are added to both the applicant's and the project's respective lists
     * of enquiries.
     */
    private void loadEnquiry() {
        List<List<String>> rawData = CsvParser.loadFromCSV("EnquiryList.csv");
        for (List<String> data : rawData) {
            LocalDateTime repliedAt = null;
            String projectName = data.get(0);
            String applicantNric = data.get(1);
            String content = data.get(2);
            String reply = data.get(3);
            boolean replied = data.get(4).equals("Yes");
            String repliedAtString = data.get(6);
            if (!repliedAtString.equals("null")) {
                repliedAt = LocalDateTime.parse(repliedAtString);
            }
            Optional<Project> projectOptional = projects.stream()
                .filter(p -> projectName.equals(p.getName()))
                .findFirst();
            Project project = projectOptional.get();
            Applicant applicant = (Applicant) users.get(applicantNric);
            Enquiry enquiry = new Enquiry(project, applicant, content);
            LocalDateTime createdAt = LocalDateTime.parse(data.get(5));
            enquiry.setReply(reply);
            enquiry.setReplied(replied);
            enquiry.setCreatedAt(createdAt);
            if (repliedAt != null) {
                enquiry.setRepliedAt(repliedAt);
            }
            applicant.getEnquiries().add(enquiry); // add to applicant
            if (project != null) {
                project.getEnquiries().add(enquiry); // add to project
            }
        }
    }

    /**
     * Loads BTO application data from the ApplicationList.csv file.
     * This method reads application details, associates them with the corresponding
     * {@link Project} and {@link Applicant}, and sets the application status
     * and the officer in charge if available. It also tracks the active
     * application for each applicant and adds the application to the project's
     * list of applications.
     */
    private void loadApplication() {
        List<List<String>> rawData = CsvParser.loadFromCSV("ApplicationList.csv");
        List<ApplicationStatus> statuses = Arrays.asList(ApplicationStatus.values());
        List<FlatType> flats = Arrays.asList(FlatType.values());
        for (List<String> data : rawData) {
            String projectName = data.get(0);
            String applicantNric = data.get(1);
            int statusIndex = Integer.valueOf(data.get(2));
            String officerNric = data.get(3);
            int flatIndex = Integer.valueOf(data.get(4));
            boolean activeProject = data.get(5).equals("Yes");

            Optional<Project> projectOptional = projects.stream()
                .filter(p -> projectName.equals(p.getName()))
                .findFirst();
            Project project = projectOptional.get();
            Applicant applicant = (Applicant) users.get(applicantNric);
            BtoApplication application =
                                    new BtoApplication(project, applicant, flats.get(flatIndex));
            application.setStatus(statuses.get(statusIndex));
            if (!officerNric.equals("null")) {
                application.setOfficerInCharge((HdbOfficer) users.get(officerNric));
            }
            if (activeProject) {
                applicant.setActiveApplication(application);
            }
            if (project != null) {
                project.addBtoApplication(application);
            }
        }
    }

    /**
     * Loads project team assignments from the ProjectTeamList.csv file.
     * This method reads project name, manager NRIC, and officer NRICs,
     * retrieves the corresponding {@link Project}, {@link HdbManager}, and
     * {@link HdbOfficer} objects, and then associates the manager and officers
     * with the project's {@link ProjectTeam}. Multiple officers can be assigned
     * to a team, with their NRICs separated by semicolons.
     * 
     * @throws Error if a project specified in the file does not exist.
     */
    private void loadProjectTeam() {
        List<List<String>> rawData = CsvParser.loadFromCSV("ProjectTeamList.csv");
        for (List<String> data : rawData) {
            String projectName = data.get(0);
            String managerNric = data.get(1);
            String officersNric = data.get(2);
            Optional<Project> projectOptional = projects.stream()
                .filter(p -> projectName.equals(p.getName()))
                .findFirst();
            if (!projectOptional.isPresent()) {
                throw new Error("File load error");
            }
            Project project = projectOptional.get();
            ProjectTeam team = project.getProjectTeam();
            if (!managerNric.equals("null")) {
                HdbManager manager = (HdbManager) users.get(managerNric);
                manager.getTeams().add(team);
                team.setManager(manager);
            }
            if (!officersNric.equals("null")) {
                for (String s : officersNric.split(";")) {
                    HdbOfficer officer = (HdbOfficer) users.get(s);
                    officer.getTeams().add(team);
                    team.getOfficers().add(officer);
                }
            }
        }
    }

    /**
     * Loads officer registration data from the RegistrationList.csv file.
     * This method reads project name, officer NRIC, and registration status,
     * retrieves the corresponding {@link Project} and {@link HdbOfficer} objects,
     * creates an {@link OfficerRegistration} object with the officer, sets its
     * status, and adds it to the project's {@link ProjectTeam}.
     * 
     * @throws Error if a project specified in the file does not exist.
     */
    private void loadRegistration() {
        List<List<String>> rawData = CsvParser.loadFromCSV("RegistrationList.csv");
        List<RegistrationStatus> statuses = Arrays.asList(RegistrationStatus.values());
        for (List<String> data : rawData) {
            String projectName = data.get(0);
            String officerNric = data.get(1);
            int statusIndex = Integer.parseInt(data.get(2));
            Optional<Project> projectOptional = projects.stream()
                .filter(p -> projectName.equals(p.getName()))
                .findFirst();
            if (!projectOptional.isPresent()) {
                throw new Error("File load error");
            }
            Project project = projectOptional.get();
            ProjectTeam team = project.getProjectTeam();
            HdbOfficer officer = (HdbOfficer) users.get(officerNric);
            OfficerRegistration registration = new OfficerRegistration(officer);
            registration.setStatus(statuses.get(statusIndex));
            team.getOfficerRegistrations().add(registration);
        }
    }

    /**
     * Loads the number of available flats of each type for each project
     * from the FlatList.csv file. This method reads the project name,
     * flat type index, and the count of available flats, retrieves the
     * corresponding {@link Project}, and updates its unit count for the
     * specified {@link FlatType}.
     * 
     * @throws Error if a project specified in the file does not exist.
     */
    private void loadFlatType() {
        List<List<String>> rawData = CsvParser.loadFromCSV("FlatList.csv");
        List<FlatType> flats = Arrays.asList(FlatType.values());
        for (List<String> data : rawData) {
            String projectName = data.get(0);
            int flatIndex = Integer.parseInt(data.get(1));
            int count = Integer.parseInt(data.get(2));
            Optional<Project> projectOptional = projects.stream()
                .filter(p -> projectName.equals(p.getName()))
                .findFirst();
            if (!projectOptional.isPresent()) {
                throw new Error("File load error");
            }
            Project project = projectOptional.get();
            project.getUnits().put(flats.get(flatIndex), count);
        }
    }
}
