package btosystem.classes;

import btosystem.classes.enums.RegistrationStatus;

public class OfficerRegistration {
    private RegistrationStatus status;
    private HdbOfficer officer;

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
}
