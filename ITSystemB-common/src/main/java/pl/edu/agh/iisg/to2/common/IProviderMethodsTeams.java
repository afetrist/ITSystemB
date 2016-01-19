package pl.edu.agh.iisg.to2.common;

import javafx.collections.ObservableList;
import pl.edu.agh.to2.common.ITeam;

public interface IMethodsTeams {

	//jesli nie dzialaja tak jak powinny mozecie poprawic w implementacji
	public ObservableList<IProjectForTeams> findAllProjectsForTeamName(String name);
	
	public ObservableList<IProjectForTeams> findAllProjectsForTeamId(String id);
	
	public ObservableList<IProjectForTeams> findAllProjectsForTeam(ITeam team);
	
	public ObservableList<IProjectForTeams> findAllProjectsExisting();
}
