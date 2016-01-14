package pl.edu.agh.iisg.to2;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.agh.iisg.to2.common.IMethodsTeams;
import pl.edu.agh.iisg.to2.common.IProjectForTeams;
import pl.edu.agh.iisg.to2.model.MySQLAccess;
import pl.edu.agh.iisg.to2.model.Project;
import pl.edu.agh.iisg.to2.model.ProjectForTeams;
import pl.edu.agh.to2.common.ITeam;

public class MethodsTeams implements IMethodsTeams {

	public ObservableList<IProjectForTeams> findAllProjectsExisting(){
		ObservableList<IProjectForTeams> projects = FXCollections.observableArrayList();
		ObservableList<Project> projectsTmp = FXCollections.observableArrayList();
		MySQLAccess sqlAccess = new MySQLAccess();
	    try {
	    	List<Project> fetched = sqlAccess.fetchAllProjects();
	    	projectsTmp.addAll(fetched);
	    	ProjectForTeams tmp = new ProjectForTeams();
	    	for (int i = 0; i < fetched.size(); i = i + 1){
	    		tmp.setBudget(projectsTmp.get(i).getBudget());
	    		tmp.setDeadline(projectsTmp.get(i).getDeadline());
	    		tmp.setDeadline(projectsTmp.get(i).getStartdate());
	    		tmp.setId(projectsTmp.get(i).getId());
	    		projects.add(tmp);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return projects;
	}
	// dla obiektu employee
	public ObservableList<IProjectForTeams> findAllProjectsForTeam(ITeam team){
		ObservableList<IProjectForTeams> projects = FXCollections.observableArrayList();
		ObservableList<Project> projectsTmp = FXCollections.observableArrayList();
		MySQLAccess sqlAccess = new MySQLAccess();
	    try {
	    	List<Project> fetched = sqlAccess.fetchAllProjects();
	    	projectsTmp.addAll(fetched);
	    	ProjectForTeams tmp = new ProjectForTeams();
	    	for (int i = 0; i < fetched.size(); i = i + 1){
		    	ObservableList<ITeam> etmp = projectsTmp.get(i).getTeams();
		    	for (int k = 0; k < etmp.size(); k = k + 1){
		    		ITeam etmp2 = etmp.get(k);
		    		if (etmp2 == team ){
		    			tmp.setBudget(projectsTmp.get(i).getBudget());
			    		tmp.setDeadline(projectsTmp.get(i).getDeadline());
			    		tmp.setDeadline(projectsTmp.get(i).getStartdate());
			    		tmp.setId(projectsTmp.get(i).getId());
			    		projects.add(tmp);
		    		}
		    	}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return projects;
	}
// dla id 	
	public ObservableList<IProjectForTeams> findAllProjectsForTeamId(String id){
		ObservableList<IProjectForTeams> projects = FXCollections.observableArrayList();
		ObservableList<Project> projectsTmp = FXCollections.observableArrayList();
		MySQLAccess sqlAccess = new MySQLAccess();
	    try {
	    	List<Project> fetched = sqlAccess.fetchAllProjects();
	    	projectsTmp.addAll(fetched);
	    	ProjectForTeams tmp = new ProjectForTeams();
	    	for (int i = 0; i < fetched.size(); i = i + 1){
		    	ObservableList<ITeam> etmp = projectsTmp.get(i).getTeams();
		    	for (int k = 0; k < etmp.size(); k = k + 1){
		    		ITeam etmp2 = etmp.get(k);
		    		if (etmp2.getId().toString().toLowerCase().equals(id.toLowerCase()) ){
		    			tmp.setBudget(projectsTmp.get(i).getBudget());
			    		tmp.setDeadline(projectsTmp.get(i).getDeadline());
			    		tmp.setDeadline(projectsTmp.get(i).getStartdate());
			    		tmp.setId(projectsTmp.get(i).getId());
			    		projects.add(tmp);
		    		}
		    	}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return projects;
	}
//	dla imienia & nazwiska
	public ObservableList<IProjectForTeams> findAllProjectsForTeamName(String name){
		ObservableList<IProjectForTeams> projects = FXCollections.observableArrayList();
		ObservableList<Project> projectsTmp = FXCollections.observableArrayList();
		MySQLAccess sqlAccess = new MySQLAccess();
	    try {
	    	List<Project> fetched = sqlAccess.fetchAllProjects();
	    	projectsTmp.addAll(fetched);
	    	ProjectForTeams tmp = new ProjectForTeams();
	    	for (int i = 0; i < fetched.size(); i = i + 1){
		    	ObservableList<ITeam> etmp = projectsTmp.get(i).getTeams();
		    	for (int k = 0; k < etmp.size(); k = k + 1){
		    		ITeam etmp2 = etmp.get(k);
		    		String str = name;
		    		if (etmp2.getId().toString().toLowerCase().equals(str.toLowerCase())){
		    			tmp.setBudget(projectsTmp.get(i).getBudget());
			    		tmp.setDeadline(projectsTmp.get(i).getDeadline());
			    		tmp.setDeadline(projectsTmp.get(i).getStartdate());
			    		tmp.setId(projectsTmp.get(i).getId());
			    		projects.add(tmp);
		    		}
		    	}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return projects;
	}		
}
