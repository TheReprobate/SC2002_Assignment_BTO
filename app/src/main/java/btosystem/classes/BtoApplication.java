package btosystem.classes;

import btosystem.classes.enums.ApplicationStatus;
import btosystem.classes.enums.FlatType;

/**
 * BTOApplication class, logical implementation is handled by BtoApplicationController.
 * {@link btosystem.operations.BtoApplicationController}
 */
public class BtoApplication {
    private ApplicationStatus status;
    private HdbOfficer officerInCharge;
    private FlatType flatType;
    private Applicant applicant;
    private Project project;

    /**
     * Constructor for a {@link BtoApplication} object.
     * @param project the {@link Project} being applied for
     * @param applicant the {@link Applicant} making the application
     * @param flatType the {@link Applicant} being applied for
     */

    public BtoApplication(Project project, Applicant applicant, FlatType flatType) {
        this.project = project;
        this.applicant = applicant;
        this.flatType = flatType;

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

    public void setProject(Project project) {
        this.project = project;
    }
}
