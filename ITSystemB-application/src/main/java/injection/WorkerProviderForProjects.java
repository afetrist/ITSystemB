package injection;

import common.iEmployeeForProjects;
import common.iWorkerProviderForProjects;
import employees.model.Person;
import javafx.beans.property.LongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WorkerProviderForProjects implements iWorkerProviderForProjects{

	private ObservableList<Person> personData;
	
	public WorkerProviderForProjects(ObservableList<Person> personData){
		this.personData = personData;
	}
	
	public ObservableList<iEmployeeForProjects> getEmployeesByID(LongProperty id) {
		ObservableList<iEmployeeForProjects> returnList = FXCollections.observableArrayList();
		for(Person person : personData){
			if(id.get() == person.getId())
				returnList.add(new EmployeeForProjects(person));
		}
		return returnList; //czy tutaj serio wymagana jest lista?
	}

	public ObservableList<iEmployeeForProjects> getAllEmployees() {
		ObservableList<iEmployeeForProjects> returnList = FXCollections.observableArrayList();
		for(Person person : personData){
			returnList.add(new EmployeeForProjects(person));
		}
		return returnList;
	}

}
