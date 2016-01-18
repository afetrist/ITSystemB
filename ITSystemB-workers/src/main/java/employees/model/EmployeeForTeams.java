package employees.model;

import common.iEmployeeForTeams;
import common.iWorkerForTeams;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

public class EmployeeForTeams implements iWorkerForTeams, iEmployeeForTeams {
    private LongProperty id;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty position;
    private IntegerProperty salary;
    private StringProperty pesel;
    
    EmployeeForTeams(LongProperty id,StringProperty firstName,StringProperty lastName,StringProperty position,IntegerProperty salary,StringProperty pesel){
    	this.id = id;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.position = position;
    	this.pesel = pesel;
    }

	//@Override
	/* (non-Javadoc)
	 * @see employees.model.iEmployeeForTeams#getFirstName()
	 */
	@Override
	public StringProperty getFirstName() {
		return firstName;
	}
	

	//@Override
	/* (non-Javadoc)
	 * @see employees.model.iEmployeeForTeams#getLastName()
	 */
	@Override
	public StringProperty getLastName() {
		return lastName;
	}

	//@Override
	/* (non-Javadoc)
	 * @see employees.model.iEmployeeForTeams#getPosition()
	 */
	@Override
	public StringProperty getPosition() {
		return position;
	}

	//@Override
	/* (non-Javadoc)
	 * @see employees.model.iEmployeeForTeams#getPesel()
	 */
	@Override
	public StringProperty getPesel() {
		return pesel;
	}
}
