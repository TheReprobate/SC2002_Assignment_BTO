package btosystem.classes;

import btosystem.classes.enums.ApplicationStatus;
import btosystem.classes.enums.FlatType;

public class BtoApplication {
    private ApplicationStatus status;
    private HdbOfficer officerInCharge;
    private FlatType flatType;
    private Applicant applicant;
    private Project project;

    public BtoApplication(Project project, Applicant applicant) {
        this.project = project;
        this.applicant = applicant;

        status = ApplicationStatus.PENDING;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public HdbOfficer getOfficerInCharge() {
        return officerInCharge;
    }

    public FlatType getFlatType() {
        return flatType;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Project getProject() {
        return project;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public void setOfficerInCharge(HdbOfficer officerInCharge) {
        this.officerInCharge = officerInCharge;
    }

    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }
}
