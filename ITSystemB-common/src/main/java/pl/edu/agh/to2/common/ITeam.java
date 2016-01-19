package pl.edu.agh.to2.common;

import java.math.BigDecimal;
import java.util.List;

import common.iEmployeeForProjects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface ITeam {

	
	public BigDecimal getCostOfTeam();
	
	public List<iEmployeeForProjects> getFullMemberList();
	
	public String getId();
	
	public String getNameOfTeam();
	
	ObjectProperty<BigDecimal> getCostOfTeamObservable();
	
	StringProperty getNameofTeamObservable();
}