package pl.edu.agh.iisg.to2.model;

import java.math.BigDecimal;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface ITeam {

	
	public BigDecimal getCostOfTeam();
	
	public List<IEmployee> getFullMemberList();
	
	public String getId();
	
	public String getNameOfTeam();
	
	ObjectProperty<BigDecimal> getCostOfTeamObservable();
	
	StringProperty getNameofTeamObservable();
}