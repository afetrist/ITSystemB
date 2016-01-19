package injection;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import pl.edu.agh.iisg.to2.common.IProjectForEmployees;

public class ProjectForEmployees implements IProjectForEmployees {
	private SimpleStringProperty id;
	private ObjectProperty<LocalDate> deadline;
	private ObjectProperty<LocalDate> startdate;
	private ObjectProperty<BigDecimal> budget;

	public ProjectForEmployees(){
		this.id = new SimpleStringProperty(UUID.randomUUID().toString());
	}
	
	public ProjectForEmployees(LocalDate deadline, LocalDate startdate, BigDecimal budget) {
		this.deadline = new SimpleObjectProperty<LocalDate>(deadline);
		this.startdate = new SimpleObjectProperty<LocalDate>(startdate);
		this.id = new SimpleStringProperty(UUID.randomUUID().toString());
		this.budget = new SimpleObjectProperty<BigDecimal>(budget);
	}

	public String getId() {
		return id.get();
	}

	public void setId(String id) {
		this.id.set(id);
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
	
	
	public String normalString(StringProperty p){
		String s = p.getValue();
		return s;
	}

	public SimpleStringProperty idProperty() {
		// TODO Auto-generated method stub
		return id;
	}
	

}
