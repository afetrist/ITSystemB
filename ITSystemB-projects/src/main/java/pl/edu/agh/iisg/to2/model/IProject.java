package pl.edu.agh.iisg.to2.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import common.iEmployeeForProjects;
//import injection.EmployeeForProjects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import pl.edu.agh.to2.common.ITeam;

public interface IProject {
	
	public String getId();
	public void setId(String id);
	
	public ObjectProperty<LocalDate> getDeadline();
	public void setDeadline(ObjectProperty<LocalDate> deadline);
	
	public ObjectProperty<LocalDate> getStartdate();
	public void setStartdate(ObjectProperty<LocalDate> startdate);
	
	public ObservableList<ITeam> getTeams();
	public void setTeams(ObservableList<ITeam> teams);
	
	public ObservableList<iEmployeeForProjects> getEmployees();
	public void setEmployees(ObservableList<iEmployeeForProjects> employees);
	
	public ObjectProperty<BigDecimal> getBudget();
	public void setBudget(ObjectProperty<BigDecimal> budget);
	
	
	
}
