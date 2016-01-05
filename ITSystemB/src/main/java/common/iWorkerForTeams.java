package common;

import javafx.beans.property.StringProperty;

public interface iWorkerForTeams {
	StringProperty getFirstName();
	StringProperty getLastName();
	StringProperty getPosition();
	StringProperty getPesel();
}
