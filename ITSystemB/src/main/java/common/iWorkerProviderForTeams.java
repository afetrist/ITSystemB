package common;

import java.util.ArrayList;

import employees.model.EmployeeForTeams;
import javafx.beans.property.LongProperty;
import javafx.collections.ObservableList;

public interface iWorkerProviderForTeams {
	ObservableList<EmployeeForTeams> getEmployeesByID(ArrayList<LongProperty> id);
	ObservableList<EmployeeForTeams> getEmployeesNotMatchByID(ArrayList<LongProperty> id);
	ObservableList<EmployeeForTeams> getEmployeesByRequestedString(String request);
}
