package common;

import java.util.ArrayList;

import javafx.beans.property.LongProperty;
import javafx.collections.ObservableList;
import javafx.beans.property.LongProperty;

public interface iWorkerProviderForTeams {
	ObservableList<iEmployeeForTeams> getEmployeesByID(ArrayList<LongProperty> id);
	ObservableList<iEmployeeForTeams> getEmployeesNotMatchByID(ArrayList<LongProperty> id);
	ObservableList<iEmployeeForTeams> getEmployeesByRequestedString(String request);
}
