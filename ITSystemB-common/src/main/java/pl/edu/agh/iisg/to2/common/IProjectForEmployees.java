package pl.edu.agh.iisg.to2.common;

import java.math.BigDecimal;
import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javafx.beans.property.ObjectProperty;

public interface IProjectForEmployees {
	
	public String getId();
	public SimpleStringProperty idProperty();
	public void setId(String id);
	
	public ObjectProperty<LocalDate> getDeadline();
	public void setDeadline(ObjectProperty<LocalDate> deadline);
	
	public ObjectProperty<LocalDate> getStartdate();
	public void setStartdate(ObjectProperty<LocalDate> startdate);
	
	
	public ObjectProperty<BigDecimal> getBudget();
	public void setBudget(ObjectProperty<BigDecimal> budget);
}
