package btosystem.controllers.interfaces;

import java.util.List;

import btosystem.classes.ProjectTeam;

import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;

/**
 * An interface that extends {@link ListToString},
 * providing various operations related to OfficerRegistration management.
 */

public interface OfficerRegistrationOperations extends 
                ListToString<OfficerRegistration>,
                CleanupOperations<OfficerRegistration> 
{
    /**
     * Interface method for OfficerRegistration creation.
     * Details can be found in {@link btosystem.controllers.OfficerRegistrationController}
     */
    public OfficerRegistration createRegistration(ProjectTeam team, HdbOfficer officer) throws Exception;

    /**
     * Interface method for retrieval of OfficerRegistration
     * Details can be found in {@link btosystem.controllers.OfficerRegistrationController}
     */
    OfficerRegistration retrieveOfficerRegistration(List<OfficerRegistration> registrations, int index) throws Exception;

    /**
     * Interface method for approval of OfficerRegistration
     * Details can be found in {@link btosystem.controllers.OfficerRegistrationController}
     */
    int approveRegistration(OfficerRegistration registration) throws Exception;

    /**
     * Interface method for rejection of OfficerRegistration
     * Details can be found in {@link btosystem.controllers.OfficerRegistrationController}
     */
    int rejectRegistration(OfficerRegistration registration) throws Exception;
}
