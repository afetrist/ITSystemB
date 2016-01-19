package pl.edu.agh.iisg.to2.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import common.iEmployeeForProjects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pl.edu.agh.to2.common.ITeam;

public class TeamMock implements ITeam {
	private String id;
	private List<iEmployeeForProjects> employees;
	private BigDecimal costOfTeam;
	private String nameOfTeam;

	public TeamMock() {
		this.employees = new ArrayList<>();	
	}
	
	public TeamMock(String id) {
		this.id = id;
		this.employees = new ArrayList<>();	
	}

	public TeamMock(String id, List<iEmployeeForProjects> employees, BigDecimal costofteam, String nameOfTeam) {
		this.id = id;
		this.employees = employees;
		this.costOfTeam = costofteam;
		this.nameOfTeam = nameOfTeam;
	}


	@Override
	public BigDecimal getCostOfTeam() {
		return costOfTeam;
	}

	@Override
	public List<iEmployeeForProjects> getFullMemberList() {
		return employees;
	}

	@Override
	public String getId() {
		return id;
	}

	public List<iEmployeeForProjects> getEmployees() {
		return employees;
	}

	public void setEmployees(ArrayList<iEmployeeForProjects> employees) {
		this.employees = employees;
	}

	public String getNameOfTeam() {
		return nameOfTeam;
	}

	public void setNameOfTeam(String nameOfTeam) {
		this.nameOfTeam = nameOfTeam;
	}

	public void setCostOfTeam(BigDecimal costOfTeam) {
		this.costOfTeam = costOfTeam;
	}

	@Override
	public ObjectProperty<BigDecimal> getCostOfTeamObservable() {
		ObjectProperty<BigDecimal> salary = new SimpleObjectProperty<BigDecimal>(this.getCostOfTeam());
		return salary;
	}

	@Override
	public StringProperty getNameofTeamObservable() {
		StringProperty p = new SimpleStringProperty(this.getNameOfTeam());
		return p;
	}

}
