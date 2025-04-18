package btosystem.controllers;

import java.util.List;

import btosystem.classes.ProjectTeam;

import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;

import btosystem.controllers.interfaces.OfficerRegistrationOperations;

/**
 * Controller for OfficerRegistration class that implements OfficerRegistrationOperations interface.
 */
public class OfficerRegistrationController implements OfficerRegistrationOperations
{
    /**
     * Constructor for creating OfficerRegistration.
     *
     * @param team ProjectTeam that this officer is applying to
     * @param officer Officer that intends to apply for ProjectTeam
     * @return OfficerRegistration object
     */
    @Override
    public OfficerRegistration createRegistration(ProjectTeam team, HdbOfficer officer) throws Exception{
        // need to check if dupe
        OfficerRegistration registration = new OfficerRegistration(officer);
        return registration;
    }

    @Override
    public OfficerRegistration retrieveOfficerRegistration(List<OfficerRegistration> registrations, int index) {
        return registrations.get(index);
    }

    @Override
    public int approveRegistration(OfficerRegistration registration) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'approveRegistration'");
    }

    @Override
    public int rejectRegistration(OfficerRegistration registration) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rejectRegistration'");
    }

    @Override
    public String toString(OfficerRegistration data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }

    @Override
    public String toString(List<OfficerRegistration> data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
