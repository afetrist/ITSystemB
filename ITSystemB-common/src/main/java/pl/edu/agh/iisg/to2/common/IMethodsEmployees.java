package pl.edu.agh.iisg.to2.common;

import common.iEmployeeForProjects;
import javafx.collections.ObservableList;
public interface IMethodsEmployees {
	
	//jesli nie dzialaja tak jak powinny mozecie poprawic w implementacji
	public ObservableList<IProjectForEmployees> findAllProjectsForEmployeeFirstNameLastName(String name, String lastName);
	
	public ObservableList<IProjectForEmployees> findAllProjectsForEmployeeId(String id);
	
	public ObservableList<IProjectForEmployees> findAllProjectsForEmployee(iEmployeeForProjects employee);
	
	public ObservableList<IProjectForEmployees> findAllProjectsExisting();
}
