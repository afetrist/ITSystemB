package pl.edu.agh.to2.model.generator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pl.edu.agh.to2.common.IWorker;
import pl.edu.agh.to2.common.SimpleWorker;
import pl.edu.agh.to2.model.Member;
import pl.edu.agh.to2.model.Team;

public class BetterDataGenerator {
	
	public static List<IWorker> generateWorkers(int size) {
		final int SIZE = 24;
		
		String[] names = {
			"Kelsey",
			"Alexandra",
			"Abraham",
			"Rajah",
			"Jasper",
			"Daquan",
			"Nicole",
			"Hermione",
			"Jessamine",
			"Genevieve",
			"Neville",
			"Christine",
			"Skyler",
			"Miriam",
			"Carter",
			"Malachi",
			"Maryam",
			"Rebecca",
			"Kaitlin",
			"Wesley",
			"Dalton",
			"Marny",
			"Guinevere",
			"Veda"   
		};
		
		String[] surnames = {
			"Tillman",
			"Wall",
			"Barron",
			"Bullock",
			"Blackwell",
			"Henderson",
			"Conrad",
			"Benson",
			"Stafford",
			"Hamilton",
			"Peck",
			"Wagner",
			"Alvarado",
			"Reilly",
			"Bruce",
			"Swanson",
			"Barry",
			"Ellis",
			"Moon",
			"Kerrez",
			"Jimenas",
			"Salin",
			"Owen",
			"Kim"	
		};
		
		String[] pesel = {
			"185837",
			"667564",
			"363211",
			"613245",
			"284511",
			"626035",
			"820177",
			"183749",
			"410018",
			"282826",
			"225311",
			"788699",
			"832468",
			"461851",
			"193417",
			"210369",
			"907926",
			"814523",
			"548671",
			"855066",
			"744339",
			"726453",
			"691822",
			"521568" 
		};
		
		List<IWorker> workers = new ArrayList<>();
		
		for (int i = 0; i < size && i < SIZE; i++) {
			workers.add(new SimpleWorker(i, names[i], surnames[i], pesel[i], new BigDecimal(1000)));
		}
		
		return workers;
	}
	
	public static List<Member> generateMembers(int size) {
		List<Member> members = new ArrayList<>();
		List<IWorker> workers = BetterDataGenerator.generateWorkers(size);
		
		for (IWorker worker : workers) {
			members.add(new Member(worker));
		}
		
		return members;
	}
	
	public static List<Team> generateTeams() {
		String[] names = {
			"Mysterious Simpletons",
			"The Midnight Razors",
			"Optimistic Bush Prisoners",
			"Mysterious World Nerds"
		};
		
		List<Member> members = BetterDataGenerator.generateMembers(names.length * 4);
		List<Team> teams = new ArrayList<>();
		
		for (int i = 0; i < names.length; i++) {
			Member supervisor = members.get(i * 4);
			Team newTeam = new Team(i, names[i], supervisor, Calendar.getInstance().getTime());
			supervisor.setSupervisedTeam(newTeam);
			List<Member> teamMembers = new ArrayList<>();
			
			for (int j = 1; j < 4; j++) {
				teamMembers.add(members.get(i * 4 + j));
			}
			newTeam.setMembers(teamMembers);
			teams.add(newTeam);
		}
		
		
		/// manual added subteams for tree tests
		Member supervisior = members.get(1);
		List<Member> teamMembers = new ArrayList<>();
		teamMembers.add(new Member(new SimpleWorker(900,"Isaac","Newton","23994422", new BigDecimal(1000))));
		teamMembers.add(new Member(new SimpleWorker(901,"Joseph Luis","Lagrange","23434422", new BigDecimal(1000))));
		Team newTeam = new Team(4, "Great Minds of the Past", supervisior, Calendar.getInstance().getTime());
		supervisior.setSupervisedTeam(newTeam);
		newTeam.setMembers(teamMembers);
		teams.add(newTeam);
		
		Member supervisior2 =teamMembers.get(0);
		List<Member> teamMembers2 = new ArrayList<>();
		teamMembers2.add(new Member(new SimpleWorker(900,"Donald","Trump","294422", new BigDecimal(1000))));
		teamMembers2.add(new Member(new SimpleWorker(901,"Hillary","Clinton","234422", new BigDecimal(1000))));
		Team newTeam2 = new Team(5, "USA presidental election 2016", supervisior2, Calendar.getInstance().getTime());
		supervisior2.setSupervisedTeam(newTeam2);
		newTeam2.setMembers(teamMembers2);
		teams.add(newTeam2);
		
		//
		return teams;
	}
}
