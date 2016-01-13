package pl.edu.agh.iisg.to2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.agh.iisg.to2.model.IProject;
import pl.edu.agh.iisg.to2.model.ProjectMock;
import pl.edu.agh.iisg.to2.model.GeneratedData;
import pl.edu.agh.iisg.to2.model.MySQLAccess;

import java.util.List;

import common.ITeam;
import common.iEmployeeForProjects;

public class FindTeams {


//--------------------------------------------Funkcje dla modu³u zespo³y ----------------------------------------
	
	
	public static ObservableList<IProject> findAllProjectsExisting(){
		ObservableList<IProject> projects = FXCollections.observableArrayList();
		MySQLAccess sqlAccess = new MySQLAccess();
	    try {
	    	List<ProjectMock> fetched = sqlAccess.fetchAllProjects();
	    	projects.addAll(fetched);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return projects;
	}
	// dla obiektu employee
	public static ObservableList<IProject> findAllProjectsForTeam(ITeam team){
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
	    	ObservableList<ITeam> etmp = tmp.getTeams();
	    	for (int k = 0; k < etmp.size(); k = k + 1){
	    		ITeam etmp2 = etmp.get(k);
	    		if (etmp2 == team ){
	    			projectsFinal.add(tmp);
	    		}
	    	}
	    }
	    return projectsFinal;
	}
// dla id 	
	public static ObservableList<IProject> findAllProjectsForTeamId(String id){
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
	    	ObservableList<ITeam> etmp = tmp.getTeams();
	    	for (int k = 0; k < etmp.size(); k = k + 1){
	    		ITeam etmp2 = etmp.get(k);
	    		if (etmp2.getId().toString().toLowerCase().equals(id.toLowerCase()) ){
	    			projectsFinal.add(tmp);
	    		}
	    	}
	    }
	    return projectsFinal;
	}
//	dla imienia & nazwiska
	public static ObservableList<IProject> findAllProjectsForTeamName(String name){
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
	    	ObservableList<ITeam> etmp = tmp.getTeams();
	    	for (int k = 0; k < etmp.size(); k = k + 1){
	    		ITeam etmp2 = etmp.get(k);
	    		String str = name;
	    		if (etmp2.getId().toString().toLowerCase().equals(str.toLowerCase())){
	    			projectsFinal.add(tmp);
	    		}
	    	}
	    }
	    return projectsFinal;
	}		
	
	
	
//----------------------------------------------- Funkcje dla mnie  -------------------------------------------
	
	public static ObservableList<ITeam> findAllTeams(GeneratedData d){
		return d.getTeams();
	}
	
	public static ObservableList<ITeam> findTeamsWithName(String name, ObservableList<ITeam> t){

		ObservableList<ITeam> tmpWithName = FXCollections.observableArrayList();
		tmpWithName.addAll(t);
		String[] arr = name.split(" ");
		for ( String ss : arr) {
			for (int i = 0; i < tmpWithName.size(); i++){
				if (!(ss.equals(tmpWithName.get(i).getNameOfTeam()))){
					tmpWithName.remove(i) ;
					i--;
				}
			}
			//System.out.println("tworze na podstawie stringa team:"+ ss);
		}
		return tmpWithName;
	}
	
	public static ObservableList<ITeam> setTeamsFromString(String s1,  ObservableList<ITeam> t) {
		System.out.println("string: "+ s1);
		ObservableList<ITeam> tmpWithID = FXCollections.observableArrayList();
		tmpWithID.addAll(t);
		for (int i = 0; i < tmpWithID.size(); i++){
			if (!(s1.toLowerCase().contains(tmpWithID.get(i).getId().toLowerCase()))){
				tmpWithID.remove(i) ;
				i--;
			}
		}
		return tmpWithID;
	}
	
	public static StringProperty getStringTeams(ObservableList<ITeam> tmpp){
		StringProperty s = new SimpleStringProperty("");
		if (tmpp != null){
			for (ITeam tmp: tmpp){
				//System.out.println("ustawiam wartosc stringa Team:"+ tmp.getId());
				s.setValue(s.getValue() + tmp.getId()+ " ");
			}
		}
		else s.setValue("0");
		return s;
	}

}
