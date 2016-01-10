package common;


import common.iEmployeeForProjects;
import common.iWorkerForProjects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

public class EmployeeForProjects implements iWorkerForProjects, iEmployeeForProjects {
    private LongProperty id;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty position;
    private IntegerProperty salary;
    private StringProperty pesel;
    
    
    //chwilowo robie visible zeby moc generowac do testow pracownikow z waszego modulu
    public EmployeeForProjects(LongProperty id,StringProperty firstName,StringProperty lastName,StringProperty position,IntegerProperty salary,StringProperty pesel){
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.position = position;
    	this.salary = salary;
    	this.pesel = pesel;
    	
    	// chwilowe na potrzeby projektu - IZABELLA
    	this.id = id;
    }

	/* (non-Javadoc)
	 * @see employees.model.iEmployeeForProjects#getFirstName()
	 */
	@Override
	public StringProperty getFirstName() {
		return firstName;
	}
	

	/* (non-Javadoc)
	 * @see employees.model.iEmployeeForProjects#getLastName()
	 */
	@Override
	public StringProperty getLastName() {
		return lastName;
	}

	/* (non-Javadoc)
	 * @see employees.model.iEmployeeForProjects#getPosition()
	 */
	@Override
	public StringProperty getPosition() {
		return position;
	}

	/* (non-Javadoc)
	 * @see employees.model.iEmployeeForProjects#getPesel()
	 */
	@Override
	public StringProperty getPesel() {
		return pesel;
	}

	/* (non-Javadoc)
	 * @see employees.model.iEmployeeForProjects#getSalary()
	 */
	@Override
	public IntegerProperty getSalary() {
		return salary;
	}

	// dodatkowe pole chyba potrzebuje
	/* (non-Javadoc)
	 * @see employees.model.iEmployeeForProjects#getId()
	 */
	@Override
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
