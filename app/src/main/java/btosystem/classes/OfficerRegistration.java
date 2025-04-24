package btosystem.classes;

import btosystem.classes.enums.RegistrationStatus;

/**
 * ProjectTeam class, logical implementation is handled by OfficerRegistrationController.
 * {@link btosystem.operations.OfficerRegistrationController}
 */
public class OfficerRegistration {
    private RegistrationStatus status;
    private HdbOfficer officer;

    /**
     * Constructor for OfficerRegistration object.
     * Actual implementation for creation of object is handled by:
     * {@link btosystem.operations.ProjectTeamController}
     */

    public OfficerRegistration(HdbOfficer officer) {
        this.officer = officer;
        status = RegistrationStatus.PENDING;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public HdbOfficer getOfficer() {
        return officer;
    }

    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    public void setOfficer(HdbOfficer officer) {
        this.officer = officer;
    }
}
