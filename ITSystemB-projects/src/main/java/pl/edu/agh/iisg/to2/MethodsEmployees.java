package pl.edu.agh.iisg.to2;


import java.util.List;

import common.iEmployeeForProjects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.agh.iisg.to2.common.IProviderMethodsEmployees;
import pl.edu.agh.iisg.to2.common.IProjectForEmployees;
import pl.edu.agh.iisg.to2.model.MySQLAccess;
import pl.edu.agh.iisg.to2.model.Project;
import pl.edu.agh.iisg.to2.model.ProjectForEmployees;

public class MethodsEmployees implements IProviderMethodsEmployees {


	public ObservableList<IProjectForEmployees> findAllProjectsExisting(){
		ObservableList<IProjectForEmployees> projects = FXCollections.observableArrayList();
		ObservableList<Project> projectsTmp = FXCollections.observableArrayList();
		MySQLAccess sqlAccess = new MySQLAccess();
	    try {
	    	List<Project> fetched = sqlAccess.fetchAllProjects();
	    	projectsTmp.addAll(fetched);
	    	ProjectForEmployees tmp = new ProjectForEmployees();
	    	for (int i = 0; i < fetched.size(); i = i + 1){
	    		tmp.setBudget(projectsTmp.get(i).getBudget());
	    		tmp.setDeadline(projectsTmp.get(i).getDeadline());
	    		tmp.setDeadline(projectsTmp.get(i).getStartdate());
	    		tmp.setId(projectsTmp.get(i).getId());
	    		projects.add(tmp);
	    	}
	    	//projects.addAll(fetched);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return projects;
	}
	// dla obiektu employee
	public ObservableList<IProjectForEmployees> findAllProjectsForEmployee(iEmployeeForProjects employee){
		ObservableList<IProjectForEmployees> projects = FXCollections.observableArrayList();
		ObservableList<Project> projectsTmp = FXCollections.observableArrayList();
		MySQLAccess sqlAccess = new MySQLAccess();
	    try {
	    	List<Project> fetched = sqlAccess.fetchAllProjects();
	    	projectsTmp.addAll(fetched);
	    	ProjectForEmployees tmp = new ProjectForEmployees();
	    	for (int i = 0; i < fetched.size(); i = i + 1){
	    		ObservableList<iEmployeeForProjects> etmp = projectsTmp.get(i).getEmployees();
	    		for (int k = 0; k < etmp.size(); k = k + 1){
		    		iEmployeeForProjects etmp2 = etmp.get(k);
		    		if (etmp2 == employee ){
		    			tmp.setBudget(projectsTmp.get(i).getBudget());
			    		tmp.setDeadline(projectsTmp.get(i).getDeadline());
			    		tmp.setDeadline(projectsTmp.get(i).getStartdate());
			    		tmp.setId(projectsTmp.get(i).getId());
			    		projects.add(tmp);
		    		}
		    	}
	    	}
	    	//projects.addAll(fetched);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return projects;
	}

// dla id 	
	public ObservableList<IProjectForEmployees> findAllProjectsForEmployeeId(String id){
		ObservableList<IProjectForEmployees> projects = FXCollections.observableArrayList();
		ObservableList<Project> projectsTmp = FXCollections.observableArrayList();
		MySQLAccess sqlAccess = new MySQLAccess();
	    try {
	    	List<Project> fetched = sqlAccess.fetchAllProjects();
	    	projectsTmp.addAll(fetched);
	    	ProjectForEmployees tmp = new ProjectForEmployees();
	    	for (int i = 0; i < fetched.size(); i = i + 1){
	    		ObservableList<iEmployeeForProjects> etmp = projectsTmp.get(i).getEmployees();
	    		for (int k = 0; k < etmp.size(); k = k + 1){
	    			iEmployeeForProjects etmp2 = etmp.get(k);
		    		if (etmp2.getId().toString().toLowerCase().equals(id.toLowerCase()) ){
		    			tmp.setBudget(projectsTmp.get(i).getBudget());
			    		tmp.setDeadline(projectsTmp.get(i).getDeadline());
			    		tmp.setDeadline(projectsTmp.get(i).getStartdate());
			    		tmp.setId(projectsTmp.get(i).getId());
			    		projects.add(tmp);
		    		}
		    	}
	    	}
	    	//projects.addAll(fetched);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return projects;
	}
//	dla imienia & nazwiska
	public ObservableList<IProjectForEmployees> findAllProjectsForEmployeeFirstNameLastName(String name, String lastName){
		ObservableList<IProjectForEmployees> projects = FXCollections.observableArrayList();
		ObservableList<Project> projectsTmp = FXCollections.observableArrayList();
		MySQLAccess sqlAccess = new MySQLAccess();
	    try {
	    	List<Project> fetched = sqlAccess.fetchAllProjects();
	    	projectsTmp.addAll(fetched);
	    	ProjectForEmployees tmp = new ProjectForEmployees();
	    	for (int i = 0; i < fetched.size(); i = i + 1){
	    		ObservableList<iEmployeeForProjects> etmp = projectsTmp.get(i).getEmployees();
	    		for (int k = 0; k < etmp.size(); k = k + 1){
	    			iEmployeeForProjects etmp2 = etmp.get(k);
		    		String str = name + " " + lastName;
		    		if (etmp2.getId().toString().toLowerCase().equals(str.toLowerCase())){
		    			tmp.setBudget(projectsTmp.get(i).getBudget());
			    		tmp.setDeadline(projectsTmp.get(i).getDeadline());
			    		tmp.setDeadline(projectsTmp.get(i).getStartdate());
			    		tmp.setId(projectsTmp.get(i).getId());
			    		projects.add(tmp);
		    		}
		    	}
	    	}
	    	//projects.addAll(fetched);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return projects;
	}	
}







