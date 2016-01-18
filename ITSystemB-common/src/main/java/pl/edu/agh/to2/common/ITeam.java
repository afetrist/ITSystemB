package pl.edu.agh.to2.common;

import java.math.BigDecimal;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface ITeam {
	public int getId();
	
	public BigDecimal getCostOfTeam();
	
	public String getNameOfTeam();
	
	ObjectProperty<BigDecimal> getCostOfTeamObservable();
	
	StringProperty getNameofTeamObservable();
}