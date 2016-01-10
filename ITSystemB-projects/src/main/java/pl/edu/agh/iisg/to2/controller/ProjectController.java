
package pl.edu.agh.iisg.to2.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import common.iEmployeeForProjects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.edu.agh.iisg.to2.ProjectMain;
import common.ITeam;
import pl.edu.agh.iisg.to2.model.MySQLAccess;
import pl.edu.agh.iisg.to2.common.ProjectMock;
import pl.edu.agh.iisg.to2.model.DataGenerator;
import pl.edu.agh.iisg.to2.model.GeneratedData;

public class ProjectController {

	private ObservableList<ProjectMock> projects;
	private ObservableList<ITeam> teams;
	private ObservableList<iEmployeeForProjects> employees;
	private Stage primaryStage;
	private GeneratedData data;
	private BorderPane rootLayout;
	private ListController listController;

	public ProjectController() {
	}
	
	public ProjectController(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public BorderPane getPane() {
		return rootLayout;
	}
	
	public void addProjects(ProjectMock p){
		this.projects.add(p);
	}
	
	public ObservableList<ProjectMock> getProjects(){
		return projects;
	}

	public void setProjects(ObservableList<ProjectMock> p){
		this.projects = p;
	}
	
	public void generateMockData() {
		//int numberOfEmployees = 12;
		//int numberOFTeams = 5;
		this.data = new GeneratedData();
		this.employees = FXCollections.observableArrayList(data.getEmployees());
		this.teams = FXCollections.observableArrayList(data.getTeams());
		this.projects = FXCollections.observableArrayList();
		int i = 0;
//		for (i = 0; i < 15; i = i + 1){
//			this.projects.add(DataGenerator.generateProjectWithMultipleTeamsEmployees(data, numberOfEmployees ,numberOFTeams));
//		}
		
		MySQLAccess sqlAccess = new MySQLAccess();
	    try {
	    	List<ProjectMock> fetched = sqlAccess.fetchAllProjects();
	    	this.projects.addAll(fetched);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public ListController getListController() {
		return listController;
	}

	public void setListController(ListController listController) {
		this.listController = listController;
	}

	public void initRootLayout() {
		try {
			//this.primaryStage.setTitle("Project");

			/*for (ProjectMock tmp: this.getProjects()){
				System.out.println(tmp.getId());
			}*/
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ProjectMain.class.getResource("view/ListView.fxml"));
			this.rootLayout = (BorderPane) loader.load();
			
			listController = loader.getController();
			generateMockData();
			listController.setData(projects, this.data , 0);
			listController.setProjController(this);
			
			//Scene scene = new Scene(rootLayout);
			//primaryStage.setScene(scene);
			//primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
