package pl.edu.agh.iisg.to2.model;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import pl.edu.agh.iisg.to2.common.IProjectForEmployees;

public class ProjectForEmployees implements IProjectForEmployees {
	private String id;
	private ObjectProperty<LocalDate> deadline;
	private ObjectProperty<LocalDate> startdate;
	private ObjectProperty<BigDecimal> budget;

	public ProjectForEmployees(){
		this.id = UUID.randomUUID().toString();
	}
	
	public ProjectForEmployees(LocalDate deadline, LocalDate startdate, BigDecimal budget) {
		this.deadline = new SimpleObjectProperty<>(deadline);
		this.startdate = new SimpleObjectProperty<>(startdate);
		this.id = UUID.randomUUID().toString();
		this.budget = new SimpleObjectProperty<BigDecimal>(budget);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ObjectProperty<LocalDate> getDeadline() {
		return deadline;
	}

	public void setDeadline(ObjectProperty<LocalDate> deadline) {
		this.deadline = deadline;
	}

	
	public ObjectProperty<LocalDate> getStartdate() {
		return startdate;
	}

	public void setStartdate(ObjectProperty<LocalDate> startdate) {
		this.startdate = startdate;
	}
	
	public ObjectProperty<BigDecimal> getBudget() {
		return budget;
	}

	public void setBudget(ObjectProperty<BigDecimal> budget) {
		this.budget = budget;
	}
	
	public void printProject(Project p){
		System.out.println(p.getId());
	}
	
	public String normalString(StringProperty p){
		String s = p.getValue();
		return s;
	}
	

}
