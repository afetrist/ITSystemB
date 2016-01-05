package common;

import employees.model.EmployeeForProjects;
import javafx.collections.ObservableList;
import javafx.beans.property.LongProperty;

public interface iWorkerProviderForProjects {
	ObservableList<EmployeeForProjects> getEmployeesByID(LongProperty id);
}
