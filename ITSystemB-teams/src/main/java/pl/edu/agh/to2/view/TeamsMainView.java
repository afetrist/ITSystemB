package pl.edu.agh.to2.view;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.edu.agh.to2.controller.TeamsMainController;

public class TeamsMainView extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		TeamsMainController.init();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
