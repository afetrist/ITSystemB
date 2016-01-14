package common;

import java.math.BigDecimal;
import java.util.List;

import common.EmployeeForProjects;
import common.iEmployeeForProjects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface ITeam {

	
	public BigDecimal getCostOfTeam();
	
	public List<EmployeeForProjects> getFullMemberList();
	
	public String getId();
	
	public String getNameOfTeam();
	
	ObjectProperty<BigDecimal> getCostOfTeamObservable();
	
	StringProperty getNameofTeamObservable();
}