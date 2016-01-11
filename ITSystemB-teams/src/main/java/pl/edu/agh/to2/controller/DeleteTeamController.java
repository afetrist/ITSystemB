package pl.edu.agh.to2.controller;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.edu.agh.to2.Team;

public class DeleteTeamController {
	private Team overviewedTeam;
	private ObservableList<Team> observableTeams;
	private Stage parentStage;

	private String deleteMessage = "Are you sure you want to delete\n ";
	@FXML
	private Button Cancel;
	@FXML
	private Button Delete;
	@FXML
	private Label question;
	@FXML
	private Label warning;

	@FXML
	private void handleCancelAction(Event event) {
		Stage stage = (Stage) Cancel.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void handleDeleteAction(Event event) {
		overviewedTeam.setMembers(null);
		overviewedTeam.getSupervisor().setSupervisedTeam(null);

		observableTeams.remove(observableTeams.indexOf(overviewedTeam));
		System.out.println("team deleted");
		Stage stage = (Stage) Delete.getScene().getWindow();
		stage.close();
		parentStage.close();
	}

	public static void initDeleteTeamDialog(Stage parentStage, Team t, ObservableList<Team> oTeams) {
		try {
			FXMLLoader loader = new FXMLLoader(DeleteTeamController.class.getResource("../view/DeleteTeamView.fxml"));
			Parent root = (Parent) loader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.DECORATED);
			stage.setTitle("Deleting " + t.getTeamName());
			stage.setScene(new Scene(root));
			stage.sizeToScene();
			DeleteTeamController controller = loader.getController();
			controller.overviewedTeam = t;
			controller.observableTeams = oTeams;
			controller.parentStage = parentStage;
			controller.initControls();
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not load .fxml file");
		}
	}

	public void initControls() {
		question.setWrapText(true);
		question.setText(deleteMessage + overviewedTeam.getTeamName() + " ?");
		warning.setText("");
	}

}
