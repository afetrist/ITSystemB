package common;

import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public interface iWorkerForProjects {
	StringProperty getFirstName();
	StringProperty getLastName();
	StringProperty getPosition();
	StringProperty getPesel();
	IntegerProperty getSalary();
}
