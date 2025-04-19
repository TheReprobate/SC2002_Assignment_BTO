package btosystem.controllers.interfaces;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.enums.ApplicationStatus;
import btosystem.classes.enums.FlatType;
import java.util.List;

/**
 * An interface that extends {@link ListToString}
 * and {@link CleanupOperations} for {@link BtoApplication} objects,
 * providing various operations related to application management.
 */
public interface BtoApplicationOperations extends 
    ListToString<BtoApplication>, 
    CleanupOperations<BtoApplication>
{
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
     * @throws IllegalArgumentException if {@code application}
     * or {@code officer} is null.
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

    int addApplication(List<BtoApplication> applications, BtoApplication application);  
    /**
     * Returns a list of flat types eligible to be applied for by a given {@code Applicant}.
     *
     * @param applicant the potential applicant
     * @return {@code List<FlatType>} containing the flat types
     * eligible to be applied for by the applicant.
     */
    List<FlatType> getEligibleFlatTypes(Applicant applicant);

    FlatType retrieveFlatType(BtoApplication application);

    boolean isReadyToProcess(BtoApplication application);

    List<BtoApplication> filterApplications(List<BtoApplication> applications, ApplicationStatus status);

    Project retrieveProject(BtoApplication application);

    Applicant retrieveApplicant(BtoApplication application);

    boolean isPending(BtoApplication application);

    boolean hasApplied(List<BtoApplication> applications, Applicant applicant);
}
