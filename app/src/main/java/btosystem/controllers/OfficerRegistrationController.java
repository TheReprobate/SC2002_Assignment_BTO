package btosystem.controllers;

import java.util.List;

import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.Project;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;

public class OfficerRegistrationController implements OfficerRegistrationOperations{

    @Override
    public String toString(List<OfficerRegistration> data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }

    @Override
    public String toString(OfficerRegistration data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }

    @Override
    public OfficerRegistration createRegistration(Project project, HdbOfficer officer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRegistration'");
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
    public HdbOfficer retrieveAppliedOfficer(OfficerRegistration registration) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retrieveAppliedOfficer'");
    }

    @Override
    public int addRegistration(List<OfficerRegistration> registrations, OfficerRegistration registration) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addRegistration'");
    }

    @Override
    public boolean hasApplied(List<OfficerRegistration> registrations, HdbOfficer officer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasApplied'");
    }
}
