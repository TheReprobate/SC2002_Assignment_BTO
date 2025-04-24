package btosystem.operations.interfaces;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.enums.ApplicationStatus;
import btosystem.classes.enums.FlatType;
import java.util.List;

/**
 * An interface that extends {@link ListToString}
 * {@link BtoApplication} objects,
 * providing various operations related to application management.
 */
public interface BtoApplicationOperations extends 
    ListToString<BtoApplication> {
    /**
     * Creates an application for a project with a specific flat type.
     *
     * @param project the project being applied for
     * @param applicant the applicant making the application
     * @param flatType the type of flat being applied for
     * @return the completed <code>Application</code> object
     */
    
    BtoApplication createApplication(
            Project project,
            Applicant applicant,
            FlatType flatType
    ) throws IllegalArgumentException;

    /**
     * Retrieves the application in position specified by <code>index</code>
     * from a list of applications.
     *
     * @param applications a list of BTO applications
     * @param index the position of the application within the list
     * @return <code>Application</code> at index <code>index</code>
     * @throws IllegalArgumentException if <code>index</code> exceeds the bounds of the list
     */
    BtoApplication retrieveApplication(
            List<BtoApplication> applications,
            int index
    ) throws IllegalArgumentException;

    /**
     * Marks the application as <i>{@code ApplicationStatus.BOOKED}</i>
     * by the officer in charge.
     *
     * @param application the application to be processed
     * @param officer the officer processing the application
     * @return {@code 1} if the operation is successful.
     * @throws IllegalArgumentException if {@code application} or {@code officer} is null.
     */
    int processApplication(
            BtoApplication application,
            HdbOfficer officer
    ) throws IllegalArgumentException;

    /**
     * Marks the application as <i>{@code ApplicationStatus.APPROVED}</i>.
     *
     * @param application the application to be approved
     * @return {@code 1} if the operation is successful.
     * @throws IllegalArgumentException if {@code application} is null.
     */
    int approveApplication(BtoApplication application) throws IllegalArgumentException;

    /**
     * Marks the application as <i>{@code ApplicationStatus.REJECTED}</i>.
     *
     * @param application tbe application to be rejected
     * @return {@code 1} if the operation is successful.
     * @throws IllegalArgumentException if {@code application} is null.
     */
    int rejectApplication(BtoApplication application) throws IllegalArgumentException;

    /**
     * Withdraw an application, marking it as <i>{@code ApplicationStatus.UNSUCCESSFUL}</i>
     * in the process.
     *
     * @param application tbe application to be rejected
     * @return {@code 1} if the operation is successful.
     * @throws IllegalArgumentException if {@code application} is null.
     */
    int withdrawApplication(BtoApplication application) throws IllegalArgumentException;

    /**
     * Adds a BtoApplication object to the provided list of applications.
     *
     * @param applications The list to which the BtoApplication will be added.
     * It is assumed that this list is already instantiated.
     * @param application  The BtoApplication object to be added to the list.
     * @return An integer value indicating the success of the operation.
     */
    int addApplication(List<BtoApplication> applications, BtoApplication application);

    /**
     * Returns a list of flat types eligible to be applied for by a given {@code Applicant}.
     *
     * @param applicant the potential applicant
     * @return {@code List<FlatType>} containing the flat types eligible 
     *                                to be applied for by the applicant.
     */
    List<FlatType> getEligibleFlatTypes(Applicant applicant);

    /**
     * Retrieves the flat type associated with the given BTO application.
     *
     * @param application The BtoApplication from which to retrieve the flat type.
     * @return The FlatType selected in the application, or null if not set.
     */
    FlatType retrieveFlatType(BtoApplication application);

    /**
     * Checks if the specified BTO application is ready to be processed.
     *
     * @param application The BtoApplication to check.
     * @return true if the application is ready to process; false otherwise.
     */
    boolean isReadyToProcess(BtoApplication application);

    /**
     * Filters a list of BTO applications by the specified application status.
     *
     * @param applications The list of BtoApplication objects to filter.
     * @param status The ApplicationStatus to filter by.
     * @return A list of BtoApplication objects with the specified status.
     */
    List<BtoApplication> filterApplications(List<BtoApplication> applications, 
                                            ApplicationStatus status);

    /**
     * Retrieves the project associated with the given BTO application.
     *
     * @param application The BtoApplication from which to retrieve the project.
     * @return The Project associated with the application, or null if not set.
     */
    Project retrieveProject(BtoApplication application);

    /**
     * Retrieves the applicant who submitted the specified BTO application.
     *
     * @param application The BtoApplication from which to retrieve the applicant.
     * @return The Applicant who submitted the application.
     */
    Applicant retrieveApplicant(BtoApplication application);

    /**
     * Checks if the specified BTO application is currently pending.
     *
     * @param application The BtoApplication to check.
     * @return true if the application is pending; false otherwise.
     */
    boolean isPending(BtoApplication application);

    /**
     * Determines whether the specified applicant has already applied,
     * based on the provided list of BTO applications.
     *
     * @param applications The list of BtoApplication objects to search.
     * @param applicant The Applicant to check for.
     * @return true if the applicant has applied; false otherwise.
     */
    boolean hasApplied(List<BtoApplication> applications, Applicant applicant);

    /**
     * Sets project to empty.
     *
     * @param application The application to edit
     */
    void removeProject(BtoApplication application);
}