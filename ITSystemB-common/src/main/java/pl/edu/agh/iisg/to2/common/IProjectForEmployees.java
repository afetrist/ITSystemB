package pl.edu.agh.iisg.to2.common;

import java.math.BigDecimal;
import java.time.LocalDate;

import common.iEmployeeForProjects;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

public interface IProjectForEmployees {

	public String getId();
	public void setId(String id);
	
	public ObjectProperty<LocalDate> getDeadline();
	public void setDeadline(ObjectProperty<LocalDate> deadline);
	
	public ObjectProperty<LocalDate> getStartdate();
	public void setStartdate(ObjectProperty<LocalDate> startdate);
	
	
	public ObjectProperty<BigDecimal> getBudget();
	public void setBudget(ObjectProperty<BigDecimal> budget);
}
