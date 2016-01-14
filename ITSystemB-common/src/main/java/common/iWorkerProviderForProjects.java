package common;

import javafx.collections.ObservableList;
import javafx.beans.property.LongProperty;

public interface iWorkerProviderForProjects {
	ObservableList<iEmployeeForProjects> getEmployeesByID(LongProperty id);
	
	//ObservableList<iEmployeeForProjects> getAllEmployees();
}
