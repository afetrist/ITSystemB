package pl.edu.agh.iisg.to2.model;

import common.iEmployeeForProjects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.agh.to2.common.ITeam;

public class GeneratedData {
	private ObservableList<iEmployeeForProjects> employees;
	private ObservableList<ITeam> teams;
	
	public GeneratedData(){
		this.employees = FXCollections.observableArrayList(DataGenerator.generateEmployees(10));
		this.teams = FXCollections.observableArrayList(DataGenerator.generateTeams(5));
	}
	
	
	public ObservableList<iEmployeeForProjects> getEmployees() {
		return employees;
	}
	public void setEmployees(ObservableList<iEmployeeForProjects> employees) {
		this.employees = employees;
	}
	public ObservableList<ITeam> getTeams() {
		return teams;
	}
	public void setTeams(ObservableList<ITeam> teams) {
		this.teams = teams;
	}
	
}
