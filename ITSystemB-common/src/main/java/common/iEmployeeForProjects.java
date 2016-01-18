package common;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

public interface iEmployeeForProjects {

	StringProperty getFirstName();

	StringProperty getLastName();

	StringProperty getPosition();

	StringProperty getPesel();

	IntegerProperty getSalary();

	// dodatkowe pole chyba potrzebuje
	LongProperty getId();

}