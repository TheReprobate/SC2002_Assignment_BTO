package btosystem.controllers;

import btosystem.classes.HdbOfficer;
import btosystem.classes.OfficerRegistration;
import btosystem.classes.ProjectTeam;
import btosystem.classes.enums.RegistrationStatus;
import btosystem.controllers.interfaces.OfficerRegistrationOperations;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for OfficerRegistration class that implements OfficerRegistrationOperations interface.
 */
public class OfficerRegistrationController implements OfficerRegistrationOperations {
    /**
     * Constructor for creating OfficerRegistration.
     *
     * @param team ProjectTeam that this officer is applying to
     * @param officer Officer that intends to apply for ProjectTeam
     * @return OfficerRegistration object
     */

    @Override
    public OfficerRegistration createRegistration(ProjectTeam team, 
                                            HdbOfficer officer) throws Exception {
        
        if (team.getOfficers().stream()
                    .anyMatch(o -> o.equals(officer))) {
            // Officer already part of project team
            throw new Exception("Officer already part of project team.");
        }
        if (team.getOfficerRegistrations().stream()
                    .anyMatch(registration -> registration.getOfficer().equals(officer))) {
            // officer already in registration list
            throw new Exception("Officer already applied for registration.");
        }

        OfficerRegistration registration = new OfficerRegistration(officer);
        return registration;
    }

    /**
     * Approves registration for Officer.
     *
     * @param registrations List of registrations
     * @param index index in list to return
     * @return OfficerRegistration object
     * @throws Exception Exception thrown if registrations does not exist, or index out of bounds
     */

    @Override
    public OfficerRegistration retrieveOfficerRegistration(List<OfficerRegistration> registrations, 
                                                        int index) throws Exception {
        if (registrations == null) {
            throw new Exception("List<OfficerRegistration> object does not exist.");
        }
        if (index > registrations.size()) {
            throw new Exception("Index out of bounds.");
        }
        return registrations.get(index);
    }

    /**
     * Approves registration for Officer.
     *
     * @param registration Registration object to accept
     * @return 1 if successful, throws relevant error messages if not
     * @throws Exception Exception thrown if registration does not exist, 
     *                                  or registration already accepted
     */

    @Override
    public int approveRegistration(OfficerRegistration registration) throws Exception {
        if (registration == null) {
            // OfficerRegistration object does not exist
            throw new Exception("Registration object does not exist.");
        }
        if (registration.getStatus() == RegistrationStatus.ACCEPTED) {
            // Registration has already been accepted
            throw new Exception("Registration has already been accepted.");
        }
        registration.setStatus(RegistrationStatus.ACCEPTED);
        return 1;
    }

    /**
     * Rejects registration for Officer.
     *
     * @param registration Registration object to reject
     * @return 1 if successful, throws relevant error messages if not
     * @throws Exception Exception thrown if registration does not exist, 
     *                                      or registration already rejected
     */

    @Override
    public int rejectRegistration(OfficerRegistration registration) throws Exception {
        if (registration == null) {
            // OfficerRegistration object does not exist
            throw new Exception("Registration object does not exist.");
        }
        if (registration.getStatus() == RegistrationStatus.REJECTED) {
            // Registration has already been rejected
            throw new Exception("Registration has already been rejected.");
        }
        registration.setStatus(RegistrationStatus.REJECTED);
        return 1;
    }

    /**
     * Compiles OfficerRegistrations data into a single string for output.
     *
     * @param data OfficerRegistrations data
     * @return Formatted string with relevant OfficerRegistration data
     */

    @Override
    public String toString(OfficerRegistration data) {
        if (data == null) {
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
     * Compiles list of OfficerRegistrations data into a single string for output.
     *
     * @param data List of OfficerRegistrations
     * @return Formatted string with all relevant OfficerRegistrations data
     */

    @Override
    public String toString(List<OfficerRegistration> data) {
        if (data == null) {
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

        if (registrations.isBlank()) {
            registrations = "No Officer Registrations applied to project team.";
        }

        return String.format("%-4s    %-20s   %-20s\n",
             "No.", 
             "Officer Name", 
             "Application Status") + registrations;
    }

    /**
     * Retrieves officer associated with registration object.
     *
     * @param registration object to retrieve with
     * @return HdbOfficer object associated with registration
     */

    @Override
    public HdbOfficer retrieveAppliedOfficer(OfficerRegistration registration) {
        return registration.getOfficer();
    }

    /**
     * Inserts registration object into registrations list.
     *
     * @param registrations list to insert into
     * @param registration object to insert
     * @return 1 when successful
     */

    @Override
    public int addRegistration(List<OfficerRegistration> registrations, 
                                OfficerRegistration registration) {
        registrations.add(registration);
        return 1;
    }

    /**
     * Checks if officer has already applied for the project.
     *
     * @param registrations list to check from
     * @param officer object to check for
     * @return true if officer has already applied, false if not
     */

    @Override
    public boolean hasApplied(List<OfficerRegistration> registrations, HdbOfficer officer) {
        for (OfficerRegistration r : registrations) {
            if (!r.getOfficer().equals(officer)) {
                continue;
            }
            return true;
        }
        return false;
    }

    /**
     * Filters and returns a list of registrations based off status filter.
     *
     * @param registrations list to filter from
     * @param status parameter to filter by
     * @return list of OfficerRegistrations based off status as filter
     */

    @Override
    public List<OfficerRegistration> filterRegistrations(List<OfficerRegistration> registrations,
            RegistrationStatus status) {
        return registrations.stream()
                            .filter(r -> r.getStatus() == status)
                            .collect(Collectors.toList());
    }
}
