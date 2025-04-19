package btosystem.service;

import java.util.List;

import btosystem.classes.Enquiry;
import btosystem.classes.Project;
import btosystem.classes.enums.Neighborhood;
import btosystem.utils.DataManager;
import btosystem.utils.OperationsManager;

public abstract class Service {
    protected DataManager dataManager;
    protected OperationsManager operationsManager;
    public Service(DataManager dataManager, OperationsManager operationsManager) {
        this.dataManager = dataManager;
        this.operationsManager = operationsManager;
    }
}
