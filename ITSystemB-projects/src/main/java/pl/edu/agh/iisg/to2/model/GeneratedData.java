package pl.edu.agh.iisg.to2.model;

import employees.model.EmployeeForProjects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GeneratedData {
	private ObservableList<EmployeeForProjects> employees;
	private ObservableList<ITeam> teams;
	
	public GeneratedData(){
		this.employees = FXCollections.observableArrayList(DataGenerator.generateEmployees(30));
		this.teams = FXCollections.observableArrayList(DataGenerator.generateTeams(15));
	}
	
	
	public ObservableList<EmployeeForProjects> getEmployees() {
		return employees;
	}
	public void setEmployees(ObservableList<EmployeeForProjects> employees) {
		this.employees = employees;
	}
	public ObservableList<ITeam> getTeams() {
		return teams;
	}
	public void setTeams(ObservableList<ITeam> teams) {
		this.teams = teams;
	}
	
}
