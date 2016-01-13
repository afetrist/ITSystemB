package pl.edu.agh.iisg.to2;


import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.agh.iisg.to2.common.IProjectForEmployees;
import pl.edu.agh.iisg.to2.model.MySQLAccess;
import pl.edu.agh.iisg.to2.model.ProjectMock;

public class MethodsEmployees {

//--------------------------------------------Funkcje dla modu³u pracownicy ----------------------------------------
	
	public ObservableList<IProjectForEmployees> findAllProjectsExisting(){
		ObservableList<IProjectForEmployees> projects = FXCollections.observableArrayList();
		MySQLAccess sqlAccess = new MySQLAccess();
	    try {
	    	List<ProjectMock> fetched = sqlAccess.fetchAllProjects();
	    	
	    	//projects.addAll(fetched);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return projects;
	}
	// dla obiektu employee
	public ObservableList<IProject> findAllProjectsForEmployee(iEmployeeForProjects employee){
		ObservableList<IProject> projects = FXCollections.observableArrayList();
		ObservableList<IProject> projectsFinal = FXCollections.observableArrayList();
		MySQLAccess sqlAccess = new MySQLAccess();
	    try {
	    	List<ProjectMock> fetched = sqlAccess.fetchAllProjects();
	    	projects.addAll(fetched);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    for (int i = 0; i < projects.size(); i = i + 1){
	    	IProject tmp = projects.get(i);
	    	ObservableList<iEmployeeForProjects> etmp = tmp.getEmployees();
	    	for (int k = 0; k < etmp.size(); k = k + 1){
	    		iEmployeeForProjects etmp2 = etmp.get(k);
	    		if (etmp2 == employee ){
	    			projectsFinal.add(tmp);
	    		}
	    	}
	    }
	    return projectsFinal;
	}
// dla id 	
	public ObservableList<IProject> findAllProjectsForEmployeeId(String id){
		ObservableList<IProject> projects = FXCollections.observableArrayList();
		ObservableList<IProject> projectsFinal = FXCollections.observableArrayList();
		MySQLAccess sqlAccess = new MySQLAccess();
	    try {
	    	List<ProjectMock> fetched = sqlAccess.fetchAllProjects();
	    	projects.addAll(fetched);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    for (int i = 0; i < projects.size(); i = i + 1){
	    	IProject tmp = projects.get(i);
	    	ObservableList<iEmployeeForProjects> etmp = tmp.getEmployees();
	    	for (int k = 0; k < etmp.size(); k = k + 1){
	    		iEmployeeForProjects etmp2 = etmp.get(k);
	    		if (etmp2.getId().toString().toLowerCase().equals(id.toLowerCase()) ){
	    			projectsFinal.add(tmp);
	    		}
	    	}
	    }
	    return projectsFinal;
	}
//	dla imienia & nazwiska
	public ObservableList<IProject> findAllProjectsForEmployeeFirstNameLastName(String name, String lastName){
		ObservableList<IProject> projects = FXCollections.observableArrayList();
		ObservableList<IProject> projectsFinal = FXCollections.observableArrayList();
		MySQLAccess sqlAccess = new MySQLAccess();
	    try {
	    	List<ProjectMock> fetched = sqlAccess.fetchAllProjects();
	    	projects.addAll(fetched);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    for (int i = 0; i < projects.size(); i = i + 1){
	    	IProject tmp = projects.get(i);
	    	ObservableList<iEmployeeForProjects> etmp = tmp.getEmployees();
	    	for (int k = 0; k < etmp.size(); k = k + 1){
	    		iEmployeeForProjects etmp2 = etmp.get(k);
	    		String str = name + " " + lastName;
	    		if (etmp2.getId().toString().toLowerCase().equals(str.toLowerCase())){
	    			projectsFinal.add(tmp);
	    		}
	    	}
	    }
	    return projectsFinal;
	}	

}







