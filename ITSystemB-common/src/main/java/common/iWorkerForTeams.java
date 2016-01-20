package common;

import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

public interface iWorkerForTeams {
	LongProperty getID();
	StringProperty getFirstName();
	StringProperty getLastName();
	StringProperty getPosition();
	StringProperty getPesel();
}
