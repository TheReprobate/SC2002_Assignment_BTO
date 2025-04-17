package btosystem.controllers;

import java.util.List;

import btosystem.classes.Project;
import btosystem.classes.ProjectTeam;
import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;

import btosystem.controllers.interfaces.OfficerRegistrationOperations;

public class OfficerRegistrationController implements OfficerRegistrationOperations
{
    @Override
    public OfficerRegistration createRegistration(ProjectTeam team, HdbOfficer officer) throws Exception{
        OfficerRegistration registration = new OfficerRegistration(officer);
        return registration;
    }

    @Override
    public OfficerRegistration retrieveOfficerRegistration(List<OfficerRegistration> registrations, int index) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retrieveOfficerRegistration'");
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
