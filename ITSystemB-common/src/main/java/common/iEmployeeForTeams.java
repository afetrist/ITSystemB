package common;

import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

public interface iEmployeeForTeams {
	
	LongProperty getID();

	//@Override
	StringProperty getFirstName();

	//@Override
	StringProperty getLastName();

	//@Override
	StringProperty getPosition();

	//@Override
	StringProperty getPesel();

}