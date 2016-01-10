package pl.edu.agh.iisg.to2;

import java.util.List;

import common.iEmployeeForProjects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.agh.iisg.to2.common.IProject;
import pl.edu.agh.iisg.to2.common.ProjectMock;
import pl.edu.agh.iisg.to2.model.GeneratedData;
import pl.edu.agh.iisg.to2.model.MySQLAccess;

public class FindEmployees {

	public static ObservableList<iEmployeeForProjects> findAllEmployees(GeneratedData d){
		return d.getEmployees();
	}
	
	public static ObservableList<IProject> findAllProjectsForEmployees(GeneratedData d){
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
	


	public static ObservableList<iEmployeeForProjects> findWithNameAndSureName(String name, String surname, ObservableList<iEmployeeForProjects> e){
		ObservableList<iEmployeeForProjects> empl = FXCollections.observableArrayList();
		empl.addAll(e);
		for (int i = 0; i < empl.size(); i++){
			if (!(name.equals(empl.get(i).getFirstName()) && surname.equals(empl.get(i).getLastName()) )){
				empl.remove(i) ;
				i--;
			}
		}
			//System.out.println("tworze na podstawie stringa team:"+ ss);

		return empl;
	}
	
	public static ObservableList<iEmployeeForProjects> setEmployeesFromString(String s1, ObservableList<iEmployeeForProjects> e){
		ObservableList<iEmployeeForProjects> empl = FXCollections.observableArrayList();
		empl.addAll(e);
		for (int i = 0; i < empl.size(); i++){
			
			String strLong = empl.get(i).getFirstName().getValue() + empl.get(i).getLastName().getValue();
			if (!(s1.toLowerCase().contains(strLong))){
				empl.remove(i) ;
				i--;
			}
		}
		return empl;
	}

	public static ObservableList<iEmployeeForProjects> setEmployeesFromStringNameLastName(String s1, ObservableList<iEmployeeForProjects> e){
		ObservableList<iEmployeeForProjects> empl = FXCollections.observableArrayList();
		empl.addAll(e);
		for (int i = 0; i < empl.size(); i++){
			System.out.println(s1);
			String str = empl.get(i).getFirstName().getValue() + " " + empl.get(i).getLastName().getValue() ;
			System.out.println(str);
			if (!(s1.toLowerCase().contains(str.toLowerCase()))){
				empl.remove(i);
				i--;
			}
		}
		System.out.println("koncowe......................................................................");
		for (int k = 0; k < empl.size(); k = k + 1){
			System.out.println(empl.get(k).getFirstName().getValue() + " " + empl.get(k).getLastName().getValue());
		}
		return empl;
	}
	
	public static StringProperty getStringEmployees(ObservableList<iEmployeeForProjects> etmp){
		StringProperty s = new SimpleStringProperty("");
		if (etmp != null){
			for (iEmployeeForProjects tmp: etmp){
				//System.out.println("ustawiam wartosc stringa Employee:"+ tmp.getId());
				s.setValue(s.getValue()+ " " + tmp.getId());
			}
		}
		else s.setValue("0");
	
		return s;
	}
}







