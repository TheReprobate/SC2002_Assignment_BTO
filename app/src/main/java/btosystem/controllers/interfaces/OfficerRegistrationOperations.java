package btosystem.controllers.interfaces;

import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import java.util.List;

public interface OfficerRegistrationOperations
        extends ListToString<OfficerRegistration> {
    OfficerRegistration createRegistration(Project project, HdbOfficer officer);

    OfficerRegistration retrieveOfficerRegistration(
            List<OfficerRegistration> registrations, int index);

    HdbOfficer retrieveAppliedOfficer(OfficerRegistration registration);

    int approveRegistration(OfficerRegistration registration);

    int rejectRegistration(OfficerRegistration registration);

    int addRegistration(List<OfficerRegistration> registrations, OfficerRegistration registration);

    boolean hasApplied(List<OfficerRegistration> registrations, HdbOfficer officer);
}
