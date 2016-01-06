package pl.edu.agh.iisg.to2.model;

import java.math.BigDecimal;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeMock implements IEmployee {
	private String firstName;
	private String lastName;
	private BigDecimal salary;
	private String pesel;
	private String id;
	
	public EmployeeMock(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public EmployeeMock(String id, String firstName, String lastName, String pesel, BigDecimal salary) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.salary = salary;
		this.pesel = pesel;
	}
	
    /* (non-Javadoc)
	 * @see com.agh.to2.mock.IEmployee#getFirstName()
	 */
    @Override
	public String getFirstName() {
    	return firstName;
    }

    /* (non-Javadoc)
	 * @see com.agh.to2.mock.IEmployee#setFirstName(java.lang.String)
	 */
    @Override
	public void setFirstName(String firstName) {
    }

    /* (non-Javadoc)
	 * @see com.agh.to2.mock.IEmployee#getLastName()
	 */
    @Override
	public String getLastName() {
    	return lastName;
    }

    /* (non-Javadoc)
	 * @see com.agh.to2.mock.IEmployee#setLastName(java.lang.String)
	 */
    @Override
	public void setLastName(String lastName) {
    }

    /* (non-Javadoc)
	 * @see com.agh.to2.mock.IEmployee#getPosition()
	 */
    @Override
	public String getPosition() {
    	return null;
    }

    /* (non-Javadoc)
	 * @see com.agh.to2.mock.IEmployee#setPosition(java.lang.String)
	 */
    @Override
	public void setPosition(String position) {
    }

    /* (non-Javadoc)
	 * @see com.agh.to2.mock.IEmployee#getSalary()
	 */
    @Override
	public BigDecimal getSalary() {
    	return salary;
    }

    /* (non-Javadoc)
	 * @see com.agh.to2.mock.IEmployee#setSalary(int)
	 */
    @Override
	public void setSalary(BigDecimal salary) {
    }

    /* (non-Javadoc)
	 * @see com.agh.to2.mock.IEmployee#getPhone()
	 */
    @Override
	public String getPhone() {
    	return null;
    }

    /* (non-Javadoc)
	 * @see com.agh.to2.mock.IEmployee#setPhone(java.lang.String)
	 */
    @Override
	public void setPhone(String phone) {
    }

    /* (non-Javadoc)
	 * @see com.agh.to2.mock.IEmployee#getDate()
	 */
    @Override
	public String getDate() {
    	return null;
    }

    /* (non-Javadoc)
	 * @see com.agh.to2.mock.IEmployee#setDate(java.lang.String)
	 */
    @Override
	public void setDate(String date) {
    }

    /* (non-Javadoc)
	 * @see com.agh.to2.mock.IEmployee#getEmail()
	 */
    @Override
	public String getEmail() {
    	return null;
    }

    /* (non-Javadoc)
	 * @see com.agh.to2.mock.IEmployee#setEmail(java.lang.String)
	 */
    @Override
	public void setEmail(String email) {
    }

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(BigDecimal id) {
		this.id = Integer.toString(id.intValueExact()) ;
		
	}

	@Override
	public StringProperty getLastNameObservable() {
		StringProperty p = new SimpleStringProperty(getLastName());
		return p;
	}

	@Override
	public StringProperty getFirstNameObservable() {
		StringProperty p = new SimpleStringProperty(getFirstName());
		return p;
	}

	@Override
	public ObjectProperty<BigDecimal> getSalaryObservable() {
		ObjectProperty<BigDecimal> salary = new SimpleObjectProperty<BigDecimal>(this.getSalary());
		return salary;
	}
}