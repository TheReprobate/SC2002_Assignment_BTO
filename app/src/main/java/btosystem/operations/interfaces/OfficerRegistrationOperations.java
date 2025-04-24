package btosystem.operations.interfaces;

import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.RegistrationStatus;
import java.util.List;

/**
 * An interface that extends {@link ListToString},
 * providing various operations related to OfficerRegistration management.
 */

public interface OfficerRegistrationOperations extends 
                ListToString<OfficerRegistration> {
    /**
     * Interface method for OfficerRegistration creation.
     * Details can be found in {@link btosystem.operations.OfficerRegistrationController}
     */

    public OfficerRegistration createRegistration(ProjectTeam team, 
                                                HdbOfficer officer) throws Exception;

    /**
     * Interface method for retrieval of OfficerRegistration.
     * Details can be found in {@link btosystem.operations.OfficerRegistrationController}
     */

    OfficerRegistration retrieveOfficerRegistration(List<OfficerRegistration> registrations, 
                                                    int index) throws Exception;

    /**
     * Interface method for approval of OfficerRegistration.
     * Details can be found in {@link btosystem.operations.OfficerRegistrationController}
     */

    int approveRegistration(OfficerRegistration registration) throws Exception;

    /**
     * Interface method for rejection of OfficerRegistration.
     * Details can be found in {@link btosystem.operations.OfficerRegistrationController}
     */

    int rejectRegistration(OfficerRegistration registration) throws Exception;

    /**
     * Interface method for retrieving officer associated with registration object.
     * Details can be found in {@link btosystem.operations.OfficerRegistrationController}
     */

    HdbOfficer retrieveAppliedOfficer(OfficerRegistration registration);

    /**
     * Interface method for adding of registration to registration list.
     * Details can be found in {@link btosystem.operations.OfficerRegistrationController}
     */

    int addRegistration(List<OfficerRegistration> registrations, OfficerRegistration registration);

    /**
     * Interface method for checking if officer is part of current registration applicants.
     * Details can be found in {@link btosystem.operations.OfficerRegistrationController}
     */

    boolean hasApplied(List<OfficerRegistration> registrations, HdbOfficer officer);

    /**
     * Interface method for filtering and returning Registrations by status parameter.
     * Details can be found in {@link btosystem.operations.OfficerRegistrationController}
     */

    List<OfficerRegistration> filterRegistrations(List<OfficerRegistration> registrations, 
                                                RegistrationStatus status);
}