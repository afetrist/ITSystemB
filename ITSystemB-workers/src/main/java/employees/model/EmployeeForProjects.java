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
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.position = position;
    	this.salary = salary;
    	this.pesel = pesel;
    }

	public StringProperty getFirstName() {
		return firstName;
	}
	

	public StringProperty getLastName() {
		return lastName;
	}

	public StringProperty getPosition() {
		return position;
	}

	public StringProperty getPesel() {
		return pesel;
	}

	public IntegerProperty getSalary() {
		return salary;
	}

	// dodatkowe pole chyba potrzebuje
	public LongProperty getId() {
		return id;
	}
	
	// chwilowe sety do tworzenia nowych pracownikow

	void setId(LongProperty id){
		this.id = id;
	}

	void setFirstName(StringProperty firstName){
		this.firstName = firstName;
	}
	

	void setLastName(StringProperty lastName){
		this.lastName = lastName;
	}

	void setSalary(IntegerProperty salary){
		this.salary = salary;
	}
    
    
}
