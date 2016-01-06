package application;

import java.io.IOException;

import employees.EmployeesRoot;
import employees.view.PersonOverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.Scene;

import pl.edu.agh.iisg.to2.ProjectMain;

public class MainApplication extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	private TabPane paneLayout;
	private EmployeesRoot employeesRoot;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("IT System Application");
		
		initRootLayout();
		
		//UWAGA: obecnie jest mock na Wasze pany, możecie je zastąpić z pozimomu fxmla lub javy
		employeesRoot = new EmployeesRoot(primaryStage, rootLayout);
		showPersonOverview();
		addProject();
		
	}
	
	public void initRootLayout(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/application/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			FXMLLoader tabPaneLoader = new FXMLLoader();
			tabPaneLoader.setLocation(MainApplication.class.getResource("/application/TabPaneLayout.fxml"));
			paneLayout = (TabPane) tabPaneLoader.load();
			rootLayout.setCenter(paneLayout);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showPersonOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/employees/view/PersonOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();
			
			Tab tab = new Tab();
			tab.setText("Employees");
			tab.setContent(personOverview);
			paneLayout.getTabs().add(tab);
			

			PersonOverviewController controller = loader.getController();
			controller.setMainApplication(this);
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void addProject() {
		ProjectMain projectMain = new ProjectMain();
		projectMain.run();
		Tab tab = new Tab();
		tab.setText("Projects");
		tab.setContent(projectMain.getPane());
		paneLayout.getTabs().add(tab);		
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public EmployeesRoot getEmployeesRoot(){
		return employeesRoot;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
