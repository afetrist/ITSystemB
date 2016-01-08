package pl.edu.agh.iisg.to2.controller;

import employees.model.EmployeeForProjects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.agh.iisg.to2.model.GeneratedData;

public class FindEmployees {

	public static ObservableList<EmployeeForProjects> findAllEmployees(GeneratedData d){
		return d.getEmployees();
	}

	public static ObservableList<EmployeeForProjects> findWithNameAndSureName(String name, String surname, ObservableList<EmployeeForProjects> e){
		ObservableList<EmployeeForProjects> empl = FXCollections.observableArrayList();
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
	
	public static ObservableList<EmployeeForProjects> setEmployeesFromString(String s1, ObservableList<EmployeeForProjects> e){
		ObservableList<EmployeeForProjects> empl = FXCollections.observableArrayList();
		empl.addAll(e);
		for (int i = 0; i < empl.size(); i++){
			System.out.println(s1);
			System.out.println(empl.get(i).getId()+"moj string");
			String strLong = Long.toString(empl.get(i).getId().getValue());
			if (!(s1.toLowerCase().contains(strLong))){
				empl.remove(i) ;
				i--;
			}
			//System.out.println("tworze na podstawie stringa team:"+ ss);
		}
		return empl;
	}
	
	
	public static StringProperty getStringEmployees(ObservableList<EmployeeForProjects> etmp){
		StringProperty s = new SimpleStringProperty("");
		if (etmp != null){
			for (EmployeeForProjects tmp: etmp){
				//System.out.println("ustawiam wartosc stringa Employee:"+ tmp.getId());
				s.setValue(s.getValue()+ " " + tmp.getId());
			}
		}
		else s.setValue("0");
	
		return s;
	}
}







