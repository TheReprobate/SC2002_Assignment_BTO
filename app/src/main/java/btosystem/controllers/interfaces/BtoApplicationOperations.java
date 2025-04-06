package btosystem.controllers.interfaces;

import btosystem.classes.Applicant;
import btosystem.classes.BtoApplication;
import btosystem.classes.HdbOfficer;
import btosystem.classes.Project;
import btosystem.classes.enums.FlatType;
import java.util.List;

public interface BtoApplicationOperations
        extends ListToString<BtoApplication>,
        CleanupOperations<BtoApplication> {
    BtoApplication createApplication(Project project, Applicant applicant);

    BtoApplication retrieveApplication(List<BtoApplication> applications, int index);

    List<BtoApplication> retrieveApplications(Project project);

    int processApplication(BtoApplication application, HdbOfficer officer, FlatType flatType);

    int approveApplication(BtoApplication application);

    int rejectApplication(BtoApplication application);

    int withdrawApplication(BtoApplication application);
}
