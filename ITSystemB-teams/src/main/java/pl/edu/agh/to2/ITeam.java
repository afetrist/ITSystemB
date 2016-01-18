package pl.edu.agh.to2;

import java.math.BigDecimal;
import java.util.List;

public interface ITeam {
	
	public BigDecimal getCostOfTeam();
	
	public List<Member> getFullMemberList();
}