package common;

import java.math.BigDecimal;

import javafx.beans.property.StringProperty;

public interface iEmployeeForTeams {

//	//@Override
//	StringProperty getFirstName();
//
//	//@Override
//	StringProperty getLastName();
//
//	//@Override
//	StringProperty getPosition();
//
//	//@Override
//	StringProperty getPesel();
	
	public int getId();

	public String getFirstName();
	
	public StringProperty getFirstNameProperty();

	public String getLastName();
	
	public StringProperty getLastNameProperty();
	
	public String getFullName();
	
	public String getPesel();
	
	public BigDecimal getPayment();
	
}