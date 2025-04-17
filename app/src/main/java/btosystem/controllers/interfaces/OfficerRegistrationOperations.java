package btosystem.controllers.interfaces;

import java.util.List;

import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;

public interface OfficerRegistrationOperations
        extends ListToString<OfficerRegistration> {
    public OfficerRegistration createRegistration(ProjectTeam team, HdbOfficer officer) throws Exception;

    OfficerRegistration retrieveOfficerRegistration(
            List<OfficerRegistration> registrations, int index);

    int approveRegistration(OfficerRegistration registration);

    int rejectRegistration(OfficerRegistration registration);
}
