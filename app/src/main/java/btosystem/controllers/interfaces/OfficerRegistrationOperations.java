package btosystem.controllers.interfaces;

import java.util.List;

import btosystem.classes.Project;

import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;

public interface OfficerRegistrationOperations
        extends ListToString<OfficerRegistration> {
    OfficerRegistration createRegistration(Project project, HdbOfficer officer);

    OfficerRegistration retrieveOfficerRegistration(
            List<OfficerRegistration> registrations, int index);

    int approveRegistration(OfficerRegistration registration);

    int rejectRegistration(OfficerRegistration registration);
}
