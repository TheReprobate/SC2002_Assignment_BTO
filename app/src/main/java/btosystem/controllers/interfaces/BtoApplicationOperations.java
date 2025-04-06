package btosystem.controllers.interfaces;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import java.util.List;

public interface BtoApplicationOperations
        extends ListToStringParser<BtoApplication>,
        CleanupOperations<BtoApplication> {
    BtoApplication createApplication(Project project, Applicant applicant);

    BtoApplication retrieveApplication(Applicant applicant);

    BtoApplication retrieveApplication(Project project, int index);

    List<BtoApplication> retrieveApplications(Project project);

    int processApplication(BtoApplication application, HdbOfficer officer);

    int approveApplication(BtoApplication application);

    int rejectApplication(BtoApplication application);

    int withdrawApplication(BtoApplication application);
}
