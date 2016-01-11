package pl.edu.agh.to2.view;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.edu.agh.to2.Team;
import pl.edu.agh.to2.controller.TeamDetailsController;
import pl.edu.agh.to2.model.generator.BetterDataGenerator;

public class TeamDetailsView extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		Team t=BetterDataGenerator.generateTeams().get(0);
	//	TeamDetailsController.initTeamDetailsDialog(t,BetterDataGenerator.generateTeams());
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}