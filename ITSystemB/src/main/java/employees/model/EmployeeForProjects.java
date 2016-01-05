package employees.model;

import common.iWorkerForProjects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

public class EmployeeForProjects implements iWorkerForProjects {
    private LongProperty id;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty position;
    private IntegerProperty salary;
    private StringProperty pesel;
    
    EmployeeForProjects(LongProperty id,StringProperty firstName,StringProperty lastName,StringProperty position,IntegerProperty salary,StringProperty pesel){
    	this.id = id;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.position = position;
    	this.salary = salary;
    	this.pesel = pesel;
    }

	@Override
	public StringProperty getFirstName() {
		return firstName;
	}
	

	@Override
	public StringProperty getLastName() {
		return lastName;
	}

	@Override
	public StringProperty getPosition() {
		return position;
	}

	@Override
	public StringProperty getPesel() {
		return pesel;
	}

	@Override
	public IntegerProperty getSalary() {
		return salary;
	}
    
    
    
}
