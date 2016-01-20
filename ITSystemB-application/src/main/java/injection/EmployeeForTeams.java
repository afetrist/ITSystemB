package injection;

import common.iEmployeeForTeams;
import common.iWorkerForTeams;
import employees.model.Person;
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

    EmployeeForTeams(Person person){
    	this.id = person.idProperty();
    	this.firstName = person.firstNameProperty();
    	this.lastName = person.lastNameProperty();
    	this.position = person.positionProperty();
    	this.salary = person.salaryProperty();
    	this.pesel = person.peselProperty();
    }
    
	//@Override
	/* (non-Javadoc)
	 * @see employees.model.iEmployeeForTeams#getFirstName()
	 */
    public LongProperty getID(){
    	return id;
    }
    
	public StringProperty getFirstName() {
		return firstName;
	}
	

	//@Override
	/* (non-Javadoc)
	 * @see employees.model.iEmployeeForTeams#getLastName()
	 */
	public StringProperty getLastName() {
		return lastName;
	}

	//@Override
	/* (non-Javadoc)
	 * @see employees.model.iEmployeeForTeams#getPosition()
	 */
	public StringProperty getPosition() {
		return position;
	}

	//@Override
	/* (non-Javadoc)
	 * @see employees.model.iEmployeeForTeams#getPesel()
	 */
	public StringProperty getPesel() {
		return pesel;
	}
}
