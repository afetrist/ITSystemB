package pl.edu.agh.to2.db;

import java.util.List;

import pl.edu.agh.to2.model.Team;
 
public class DbTester {
    public static void main(String[] args) {
    	DbHandle handle = new DbHandle();
        showTeams();
        Team test = new Team(1, "Sieci neuronowe", null, null);
        handle.updateTeam(test);
        showTeams();
    }
    private static void showTeams(){
    	DbHandle handle = new DbHandle();
    	List<Team> teams;
    	teams = handle.loadTeams("");
		if(teams==null){
			System.out.println("something went terribly wrong :(");
			return;
		}
		for (Team t : teams){
			System.out.println("Team id: "+t.getId()+" Team name: \""+t.getTeamName()+"\"");
		}      
    	
    }
  
}