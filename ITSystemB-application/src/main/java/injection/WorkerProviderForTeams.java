package injection;

import java.util.ArrayList;

import common.iEmployeeForTeams;
import common.iWorkerProviderForTeams;
import employees.model.Person;
import javafx.beans.property.LongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.agh.iisg.to2.common.IProjectForEmployees;
import pl.edu.agh.iisg.to2.model.Project;

public class WorkerProviderForTeams implements iWorkerProviderForTeams{

	private ObservableList<Person> allPerson;
	
	public WorkerProviderForTeams(ObservableList<Person> personData) {
		this.allPerson = personData;
	}

	public ObservableList<iEmployeeForTeams> getEmployeesByID(ArrayList<LongProperty> id) {
		ObservableList<iEmployeeForTeams> returnList = FXCollections.observableArrayList();
		
		for(LongProperty singleId : id){
			for(Person person : allPerson){
				if(person.getId() == singleId.get())
					returnList.add(new EmployeeForTeams(person));
			}
		}
		
		return returnList;
	}

	public ObservableList<iEmployeeForTeams> getEmployeesNotMatchByID(ArrayList<LongProperty> id) {
		ObservableList<iEmployeeForTeams> returnList = FXCollections.observableArrayList();
		for(Person person : allPerson){
			returnList.add(new EmployeeForTeams(person));
		}
		
		for(iEmployeeForTeams iEFT : returnList){
			for(LongProperty singleId : id){
				if(iEFT.getID().get() == singleId.get()){
					returnList.remove(iEFT);
				}
			}
		}
		
		return returnList;
	}

	public ObservableList<iEmployeeForTeams> getEmployeesByRequestedString(String request) {
		ObservableList<iEmployeeForTeams> returnList = FXCollections.observableArrayList();
		for(Person person : allPerson){
			returnList.add(new EmployeeForTeams(person));
		}
		
		return returnList;
	}

}
