package pl.edu.agh.to2.model;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.to2.common.ITeam;
import pl.edu.agh.to2.common.ITeamProvider;
import pl.edu.agh.to2.db.DbHandle;

public class SimpleTeamProvider implements ITeamProvider {

	DbHandle dbHandle = new DbHandle();

	@Override
	public List<ITeam> getTeams() {
		List<Team> teams = dbHandle.loadTeams("", false);
		List<ITeam> iteams = new ArrayList<ITeam>(teams);
		return iteams;
	}
	
}
