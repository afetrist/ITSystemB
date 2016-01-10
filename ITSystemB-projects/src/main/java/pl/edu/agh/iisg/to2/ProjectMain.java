package pl.edu.agh.iisg.to2;


import java.sql.SQLException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.edu.agh.iisg.to2.controller.ProjectController;
import pl.edu.agh.iisg.to2.model.MySQLAccess;
import pl.edu.agh.iisg.to2.common.ProjectMock;
import pl.edu.agh.iisg.to2.model.GeneratedData;
import pl.edu.agh.iisg.to2.model.DataGenerator;
 
//import pl.edu.agh.iisg.to.javafx.cw4.controller.AccountAppController;

public class ProjectMain {
	private ProjectController presenter;

	/*@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("My first JavaFX app");

		this.presenter = new ProjectController(primaryStage);
		this.presenter.initRootLayout();
	}*/

	public void run() throws ClassNotFoundException, SQLException {
		MySQLAccess dao = new MySQLAccess();
		GeneratedData genData = new GeneratedData();
		for (int numTmp = 0; numTmp < 5; numTmp = numTmp + 1){
			try {
				dao.insertProject(DataGenerator.generateProjectWithMultipleTeamsEmployees(genData, 3, 3));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	    try {
			dao.readDataBase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
		this.presenter = new ProjectController();
		this.presenter.initRootLayout();
	}
	
	public BorderPane getPane() {
		return this.presenter.getPane();
	}
	
}