package btosystem.classes;

import btosystem.classes.enums.FlatType;
import btosystem.classes.enums.Neighborhood;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Project {
    private String name;
    private Neighborhood neighborhood;
    private boolean visible;
    private long openTime;
    private long closeTime;
    private HashMap<FlatType, Integer> units;
    private ProjectTeam projectTeam;
    private List<BtoApplication> btoApplications;
    private List<Enquiry> enquiries;
    private HdbManager createdBy;

    public Project(
            String name,
            Neighborhood neighborhood,
            long openTime,
            long closeTime,
            ProjectTeam projectTeam,
            HdbManager createdBy) {
        this.name = name;
        this.neighborhood = neighborhood;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.projectTeam = projectTeam;
        this.btoApplications = new ArrayList<BtoApplication>();
        this.enquiries = new ArrayList<Enquiry>();
        this.units = new HashMap<FlatType, Integer>();
        this.createdBy = createdBy;
    }
    public String getName() {
        return name;
    }

    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

    public boolean isVisible() {
        return visible;
    }

    public long getOpenTime() {
        return openTime;
    }

    public long getCloseTime() {
        return closeTime;
    }

    public HashMap<FlatType, Integer> getUnits() {
        return units;
    }

    public ProjectTeam getProjectTeam() {
        return projectTeam;
    }

    public List<BtoApplication> getBtoApplications() {
        return btoApplications;
    }

    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    public HdbManager getCreatedBy() {
        return createdBy;
    }

    public void setNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
    }

    public void setCreatedBy(HdbManager createdBy) {
        this.createdBy = createdBy;
    }
}
