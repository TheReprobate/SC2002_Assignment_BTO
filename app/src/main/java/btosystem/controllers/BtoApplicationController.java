package btosystem.controllers;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.enums.ApplicationStatus;
import btosystem.classes.enums.FlatType;
import btosystem.controllers.interfaces.BtoApplicationOperations;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for {@link BtoApplication} class that implements {@link BtoApplicationOperations}.
 */
public class BtoApplicationController implements BtoApplicationOperations {

    @Override
    public BtoApplication createApplication(
            Project project,
            Applicant applicant,
            FlatType flatType
    ) {
        if (project == null || applicant == null || flatType == null) {
            throw new IllegalArgumentException(
                    "Error: Project, Applicant, FlatType cannot be null!"
            );
        }

        if (applicant.getActiveApplication() != null) {
            throw new IllegalArgumentException(
                    "Error: Applicant cannot apply for multiple projects!"
            );
        }

        /*
        if (applicant instanceof HdbOfficer &&
            ProjectTeamOperations.isInTeam(project.getProjectTeam(), applicant)
        ) {
            throw new IllegalArgumentException(
                "Error: Officer cannot apply for project he is handling!"
            );
        }
        */

        if (applicant.getAge() < 21) {
            throw new IllegalArgumentException("Error: Applicants must be at least 21 years old!");
        }

        List<FlatType> eligibleFlatTypes = getEligibleFlatTypes(applicant);
        if (!eligibleFlatTypes.contains(flatType)) {
            throw new IllegalArgumentException(
                    String.format("Error: Applicant is not eligible to apply for %s", flatType)
            );
        }

        BtoApplication application = new BtoApplication(project, applicant, flatType);
        project.addBtoApplication(application);
        applicant.setActiveApplication(application);
        return application;
    }

    @Override
    public BtoApplication retrieveApplication(List<BtoApplication> applications, int index) {
        if (applications == null) {
            throw new IllegalArgumentException("Error: List of applications cannot be null!");
        }
        if (index < 0 || index >= applications.size()) {
            throw new IllegalArgumentException("Error: Index out of bounds!");
        }
        return applications.get(index);
    }

    @Override
    public int processApplication(BtoApplication application, HdbOfficer officer) {
        if (application == null || officer == null) {
            throw new IllegalArgumentException("Error: Application, Officer cannot be null!");
        }
        // 1. Update the number of flats for each flat type that are remaining
        // 2. Retrieve applicant's BTO application with applicant's NRIC
        // 4. Update applicant's profile with the type of flat
        application.setOfficerInCharge(officer);
        application.setStatus(ApplicationStatus.BOOKED);
        return 1;
    }

    @Override
    public int approveApplication(BtoApplication application) {
        if (application == null) {
            throw new IllegalArgumentException("Error: Application cannot be null!");
        }
        application.setStatus(ApplicationStatus.SUCCESSFUL);
        return 1;
    }

    @Override
    public int rejectApplication(BtoApplication application) {
        if (application == null) {
            throw new IllegalArgumentException("Error: Application cannot be null!");
        }
        application.setStatus(ApplicationStatus.UNSUCCESSFUL);
        return 1;
    }

    @Override
    public int withdrawApplication(BtoApplication application) {
        if (application == null) {
            throw new IllegalArgumentException("Error: Application cannot be null!");
        }
        application.setStatus(ApplicationStatus.UNSUCCESSFUL);
        return 1;
    }

    @Override
    public List<FlatType> getEligibleFlatTypes(Applicant applicant) {
        List<FlatType> eligibleFlatTypes = new ArrayList<>();
        if (!applicant.isMarried() && applicant.getAge() >= 35) {
            eligibleFlatTypes.add(FlatType.TWO_ROOM);
        } else if (applicant.getAge() >= 21) {
            eligibleFlatTypes.add(FlatType.TWO_ROOM);
            eligibleFlatTypes.add(FlatType.THREE_ROOM);
        }
        return eligibleFlatTypes;
    }

    @Override
    public String toString(List<BtoApplication> data) {
        if (data.isEmpty()) {
            return "There are currently 0 applications!";
        }

        String formattedString = "%24s %9s %24s %10s";
        String output = String.format(
                formattedString, "Applicant", "Type", "Officer-in-Charge", "Status"
        );
        for (BtoApplication application : data) {
            output = output.concat("\n").concat(
                    String.format(
                            formattedString,
                            application.getApplicant().getName(),
                            application.getFlatType(),
                            application.getOfficerInCharge().getName(),
                            application.getStatus()
                    )
            );
        }
        return output;
    }

    @Override
    public String toString(BtoApplication data) {
        return String.format("%s \n%s \n%s \n%s \n%s",
                "Project: " + data.getProject().getName(),
                "Flat Type: " + data.getFlatType(),
                "Applicant: " + data.getApplicant().getName(),
                "Application Status: " + data.getStatus().toString(),
                "Officer-in-Charge: " + data.getOfficerInCharge().getName()
                );
    }

    @Override
    public void cleanup(BtoApplication instance) {
        Project project = instance.getProject();
        Applicant applicant = instance.getApplicant();

        project.getBtoApplications().remove(instance);
        applicant.setActiveApplication(null);
    }
}
