package common;

import javafx.beans.property.LongProperty;
import javafx.collections.ObservableList;
import pl.edu.agh.iisg.to2.common.IProjectForEmployees;

public interface iProjectProviderForEmployees {
	ObservableList<IProjectForEmployees> getProjectForEmployee(Long id);
}
