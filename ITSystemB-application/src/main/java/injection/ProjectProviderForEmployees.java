package injection;

import common.iEmployeeForProjects;
import common.iProjectProviderForEmployees;
import employees.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.agh.iisg.to2.common.IProjectForEmployees;
import pl.edu.agh.iisg.to2.model.Project;

public class ProjectProviderForEmployees implements iProjectProviderForEmployees{

	private ObservableList<Project> allProjects;
	
	public ProjectProviderForEmployees(ObservableList<Project> allProjects){
		this.allProjects = allProjects;
	}
	
	public ObservableList<IProjectForEmployees> getProjectForEmployee(String id) {
		ObservableList<IProjectForEmployees> returnList = FXCollections.observableArrayList();
		
		for(Project p : allProjects){
			if(p.wasInvolved(id))
				returnList.add(new ProjectForEmployees(p.getDeadline().get(), p.getStartdate().get(), p.getBudget().get()));
		}
		
		for(IProjectForEmployees pfe : returnList)
			pfe.setId(id);

		return returnList;
	}

}
