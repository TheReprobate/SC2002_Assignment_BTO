package btosystem.controllers.interfaces;

import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import java.util.List;

public interface OfficerRegistrationOperations
        extends ListToStringSerializer<OfficerRegistration> {
    OfficerRegistration createRegistration(Project project, HdbOfficer officer);

    OfficerRegistration retrieveOfficerRegistration(
            List<OfficerRegistration> registrations, int index);

    int approveRegistration(OfficerRegistration registration);

    int rejectRegistration(OfficerRegistration registration);
}
