package pl.edu.agh.iisg.to2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.agh.iisg.to2.model.GeneratedData;
import common.ITeam;

public class FindTeams {


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
