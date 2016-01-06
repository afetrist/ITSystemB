package pl.edu.agh.iisg.to2.model;

import java.math.BigDecimal;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface IEmployee {
	
	String getId();

	void setId(BigDecimal id);

	String getFirstName();

	void setFirstName(String firstName);
	
	StringProperty getLastNameObservable();
	
	StringProperty getFirstNameObservable();

	String getLastName();

	void setLastName(String lastName);

	String getPosition();

	void setPosition(String position);

	BigDecimal getSalary();
	
	ObjectProperty<BigDecimal> getSalaryObservable();

	void setSalary(BigDecimal salary);

	String getPhone();

	void setPhone(String phone);

	String getDate();

	void setDate(String date);

	String getEmail();

	void setEmail(String email);

}