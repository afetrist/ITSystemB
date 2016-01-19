package pl.edu.agh.iisg.to2.model;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import common.iEmployeeForProjects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.agh.iisg.to2.EmployeeForProjects;
import pl.edu.agh.iisg.to2.model.Project;
import pl.edu.agh.to2.common.ITeam;

public class DataGenerator {
	

	public static List<iEmployeeForProjects> generateEmployees(int size){
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

		String[] position = {
				"boss",
				"CEO",
				"CEE",
				"CIA xD",
				"student",
				"ordinary employee",
				"secretary",
				"chef",
				"masterchef",
				"boss",
				"CEO",
				"CEE",
				"CIA xD",
				"student",
				"ordinary employee",
				"secretary",
				"chef",
				"masterchef",
				"Ellis",
				"Salin",
				"Owen",
				"somebody",
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
		
		List<iEmployeeForProjects> workers = new ArrayList<>();
		
		
		for (int i = 0; i < size && i < SIZE; i++) {
			Random generator = new Random(); 
			int i1 = Math.abs(generator.nextInt())%20;
			int i2 = Math.abs(generator.nextInt())%20;
			int i3 = Math.abs(generator.nextInt())%20;
			workers.add(new EmployeeForProjects( new SimpleLongProperty(Long.valueOf(i3)), new SimpleStringProperty(names[i1]), new SimpleStringProperty(surnames[i2]), new SimpleStringProperty(surnames[i2]) , new SimpleIntegerProperty(i1*i2*i3+100) , new SimpleStringProperty(pesel[i1])));
		}
		
		return workers;
		
		
	}
	
	public static List<ITeam> generateTeams(int size){
		
		String[] id = {
				"1858",
				"667",
				"3611",
				"6245",
				"84511",
				"6235",
				"82077",
				"189",
				"41001",
				"22826",
				"2311",
				"7699",
				"3468",
				"46851",
				"19317",
				"2139",
				"90726",
				"814523",
				"54861",
				"85066",
				"74433",
				"26453",
				"69182",
				"51568" 
			};
		
		String[] names = {
				"Beauty and Beast",
				"Notre Damme De Paris",
				"Mozart Opera Rock",
				"PSY",
				"Hotel Transilvania",
				"Bold & Beatuiful",
				"Klan",
				"Criminal Minds",
				"Bora Bora",
				"Fiji",
				"Aloha!",
				"GTA: San Andreas",
				"Amadeus",
				"Monsters inc.",
				"Avatar",
				"Book of Life",
				"Belle",
				"DADDY",
				"Aliexpress",
				"Blah Blah Blah",
				"Galapagos",
				"Anaconda & team",
				"Hells' Kitchen",
				"THE END!"	
			};
		
		
		List<ITeam> teams = new ArrayList<>();
		final int SIZE = 12;
		for (int i = 0; i < size && i < SIZE; i++) {
			Random generator = new Random(); 
			int i2 =  Math.abs(generator.nextInt())%20;
			teams.add(new TeamMock(id[i2], generateEmployees(2), new BigDecimal(BigInteger.valueOf(i2*i2*i2)), names[i2]));	
		}
		return teams;
	}
	
	public static ITeam generateTeam(){
		
		String[] id = {
				"1858",
				"667",
				"3611",
				"6245",
				"84511",
				"6235",
				"82077",
				"189",
				"41001",
				"22826",
				"2311",
				"7699",
				"3468",
				"46851",
				"19317",
				"2139",
				"90726",
				"814523",
				"54861",
				"85066",
				"74433",
				"26453",
				"69182",
				"51568" 
			};
		
		String[] names = {
				"Beauty and Beast",
				"Notre Damme De Paris",
				"Mozart Opera Rock",
				"PSY",
				"Hotel Transilvania",
				"Bold & Beatuiful",
				"Klan",
				"Criminal Minds",
				"Bora Bora",
				"Fiji",
				"Aloha!",
				"GTA: San Andreas",
				"Amadeus",
				"Monsters inc.",
				"Avatar",
				"Book of Life",
				"Belle",
				"DADDY",
				"Aliexpress",
				"Blah Blah Blah",
				"Galapagos",
				"Anaconda & team",
				"Hells' Kitchen",
				"THE END!"	
			};
		Random generator = new Random(); 
		int i = generator.nextInt(24);
		int i2 = generator.nextInt(24);
		ITeam team = new TeamMock(id[i],generateEmployees(3), new BigDecimal(BigInteger.valueOf(i2+100)), names[i2]);	
		
		return team;
	}
	
	
	public static List<Project> generateProjects(List<EmployeeForProjects> employees, List<ITeam> teams, int numberOfEmployees, int numberOfTeams, int numberOfProjects){
		List<Project> projects = new ArrayList<>();
		Random generator = new Random(); 
		int iT =  Math.abs(generator.nextInt(numberOfTeams));
		int iE =  Math.abs(generator.nextInt(numberOfEmployees));
		long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
		long maxDay = LocalDate.of(2015, 12, 31).toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
		long randomDay2 = ThreadLocalRandom.current().nextLong(minDay, maxDay);
		LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
		LocalDate randomDate2 = LocalDate.ofEpochDay(randomDay2);
		for(int i = 0; i < 3; i++) {
			Project p = new Project(randomDate, randomDate2, teams.get(iT), employees.get(iE), new BigDecimal(BigInteger.valueOf(i + i*100)));
			projects.add(p);
			System.out.println();
		}
		return projects;
	}
	
	public static Project generateProject(List<EmployeeForProjects> employees, List<ITeam> teams, int numberOfEmployees, int numberOfTeams){;
		Random generator = new Random(); 
		int iT =  Math.abs(generator.nextInt(numberOfTeams));
		int iE =  Math.abs(generator.nextInt(numberOfEmployees));
		long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
		long maxDay = LocalDate.of(2015, 12, 31).toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
		long randomDay2 = ThreadLocalRandom.current().nextLong(minDay, maxDay);
		LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
		LocalDate randomDate2 = LocalDate.ofEpochDay(randomDay2);
		Project p = new Project(randomDate, randomDate2, teams.get(iT), employees.get(iE), new BigDecimal(BigInteger.valueOf(iT + iE*100)));

		return p;
	}
	
	
	
	public static Project generateProjectWithMultipleTeamsEmployees(GeneratedData d, int numberOfEmployees, int numberOfTeams){
		long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
		long maxDay = LocalDate.of(2015, 12, 31).toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
		long randomDay2 = ThreadLocalRandom.current().nextLong(minDay, maxDay);
		LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
		LocalDate randomDate2 = LocalDate.ofEpochDay(randomDay2);
		ObservableList<iEmployeeForProjects> employees = FXCollections.observableArrayList();
		ObservableList<iEmployeeForProjects> employeesTmp = FXCollections.observableArrayList();
		employees.addAll(d.getEmployees());
		ObservableList<ITeam> teams = FXCollections.observableArrayList();
		ObservableList<ITeam> teamsTmp = FXCollections.observableArrayList();
		teams.addAll(d.getTeams());
		Random rn = new Random();
		int range = numberOfEmployees;
		int randomNum =  Math.abs(rn.nextInt(range));
		int i;
		for (i = 0; i < numberOfEmployees; i = i + 1){
			int index = (randomNum + i)%employees.size();
			employeesTmp.add(employees.get(index));
		}
		Random rn2 = new Random();
		int range2 = numberOfTeams;
		int randomNum2 =  Math.abs(rn2.nextInt(range2));
		for (i = 0; i < numberOfTeams; i = i + 1){
			int index = (randomNum2 + i)%teams.size();
			teamsTmp.add(teams.get(index));
		}
		
		Project p = new Project(randomDate, randomDate2, teams, employees, new BigDecimal(BigInteger.valueOf(range2 + range*100)));

		return p;
	}
}