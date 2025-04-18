package btosystem.controllers;

import java.util.List;

import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.RegistrationStatus;
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
        
        if(team.getOfficers().stream()
                    .anyMatch(o -> o.equals(officer))) {
            // Officer already part of project team
            throw new Exception("Officer already part of project team.");
        }
        if(team.getOfficerRegistrations().stream()
                    .anyMatch(registration -> registration.getOfficer().equals(officer))) {
            // officer already in registration list
            throw new Exception("Officer already applied for registration.");
        }

        OfficerRegistration registration = new OfficerRegistration(officer);
        return registration;
    }

    /**
     * Approves registration for Officer
     *
     * @param registration Registration object to accept
     * @param index index in list to return
     * @return OfficerRegistration object
     * @throws Exception Exception thrown if registrations does not exist, or index out of bounds
     */
    @Override
    public OfficerRegistration retrieveOfficerRegistration(List<OfficerRegistration> registrations, int index) throws Exception{
        if(registrations == null)
        {
            throw new Exception("List<OfficerRegistration> object does not exist.");
        }
        if(index > registrations.size())
        {
            throw new Exception("Index out of bounds.");
        }
        return registrations.get(index);
    }

    /**
     * Approves registration for Officer
     *
     * @param registration Registration object to accept
     * @return 1 if successful, throws relevant error messages if not
     * @throws Exception Exception thrown if registration does not exist, or registration already accepted
     */
    @Override
    public int approveRegistration(OfficerRegistration registration) throws Exception {
        if(registration == null) {
            // OfficerRegistration object does not exist
            throw new Exception("Registration object does not exist.");
        }
        if(registration.getStatus() == RegistrationStatus.ACCEPTED) {
            // Registration has already been accepted
            throw new Exception("Registration has already been accepted.");
        }
        registration.setStatus(RegistrationStatus.ACCEPTED);
        return 1;
    }

    /**
     * Rejects registration for Officer
     *
     * @param registration Registration object to reject
     * @return 1 if successful, throws relevant error messages if not
     * @throws Exception Exception thrown if registration does not exist, or registration already rejected
     */
    @Override
    public int rejectRegistration(OfficerRegistration registration) throws Exception {
        if(registration == null) {
            // OfficerRegistration object does not exist
            throw new Exception("Registration object does not exist.");
        }
        if(registration.getStatus() == RegistrationStatus.REJECTED) {
            // Registration has already been rejected
            throw new Exception("Registration has already been rejected.");
        }
        registration.setStatus(RegistrationStatus.REJECTED);
        return 1;
    }

    /**
     * Compiles OfficerRegistrations data into a single string for output
     *
     * @param data OfficerRegistrations data
     * @return Formatted string with relevant OfficerRegistration data
     */
    @Override
    public String toString(OfficerRegistration data) {
        if(data == null) {
            // Invalid OfficerRegistration data
            return "Invalid OfficerRegistration data";
        }
        String registration = String.format("%-4s    %-20s   %-20s\n", 
                    1 + ")", 
                    data.getOfficer().getName(), 
                    data.getStatus().toString());

        return String.format("%-4s    %-20s   %-20s\n",
        "No.", 
        "Officer Name", 
        "Application Status")
        + registration;
    }

    /**
     * Compiles list of OfficerRegistrations data into a single string for output
     *
     * @param data List of OfficerRegistrations
     * @return Formatted string with all relevant OfficerRegistrations data
     */
    @Override
    public String toString(List<OfficerRegistration> data) {
        if(data == null) {
            // Invalid OfficerRegistration data
            return "Invalid List of data.";
        }

        String registrations = "";

        for (int i = 0; i < data.size(); i++) {
            registrations += String.format("%-4s    %-20s   %-20s\n", 
                    (i + 1) + ")", 
                    data.get(i).getOfficer().getName(), 
                    data.get(i).getStatus().toString());
        }

        if(registrations.isBlank()) {
            registrations = "No Officer Registrations applied to project team.";
        }

        return String.format("%-4s    %-20s   %-20s\n",
         "No.", 
         "Officer Name", 
         "Application Status")
        + registrations;
    }

    /**
     * Cleans up the OfficerRegistration object, resetting its fields.
     *
     * @param instance OfficerRegistration objects to clean
     */
    @Override
    public void cleanup(OfficerRegistration instance) {
        if(instance != null)
        {
            instance.setOfficer(null);
            instance.setStatus(null);
        }
    }

    /**
     * Cleans up a list of OfficerRegistration objects, resetting its fields.
     *
     * @param instance List of OfficerRegistration objects to clean
     */
    public void cleanup(List<OfficerRegistration> instance) {
        if(instance != null)
        {
            for(OfficerRegistration registration : instance)
            {
                this.cleanup(registration);
            }
            instance.clear();
        }
    }
}
