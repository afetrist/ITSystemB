package pl.edu.agh.to2.controller;

import java.io.IOException;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.edu.agh.to2.Member;
import pl.edu.agh.to2.Team;
import pl.edu.agh.to2.model.IWorker;
import pl.edu.agh.to2.model.generator.BetterDataGenerator;

public class AddMemberToTeamController extends ModifyMemberInTeamController {

	@Override
	protected void handleAcceptAction() {
		if (supervisorCheckBox.isSelected()) {
			if (supervisor.get() != null) {
				observableWorkers.add(supervisor.get().getWorker());
			}
			supervisor.set(new Member(selectedWorker));
		} else if (selectedMember != null) {
			observableMembers.add(selectedMember);
		} else {
			observableMembers.add(new Member(selectedWorker));
		}
		observableWorkers.remove(selectedWorker);

		Stage stage = (Stage) acceptButton.getScene().getWindow();
		stage.close();
	}

	private void initControls(ObservableList<Member> observableMembers, ObservableList<IWorker> observableWorkers,
			ObjectProperty<Member> supervisor, IWorker worker) {

		this.observableMembers = observableMembers;
		this.observableWorkers = observableWorkers;
		this.selectedWorker = worker;
		this.supervisor = supervisor;

		memberLabel.setText("Worker: " + selectedWorker.getFullName());

		// TODO: query to db for supervisedTeam info
		selectedMember = null;
		List<Team> teamsFromDB = BetterDataGenerator.generateTeams();
		for (Team t : teamsFromDB) {
			if (t.getSupervisor() != null && t.getSupervisor().getWorker().equals(selectedWorker)) {
				selectedMember = t.getSupervisor();
				break;
			}
		}

		if (selectedMember != null && selectedMember.isSupervisor()) {
			supervisorCheckBox.setSelected(false);
			supervisorCheckBox.setDisable(true);
		}
	}

	public static void init(ObservableList<Member> observableMembers, ObservableList<IWorker> observableWorkers,
			ObjectProperty<Member> supervisor, IWorker selectedWorker) {
		try {
			FXMLLoader loader = new FXMLLoader(
					ModifyMemberInTeamController.class.getResource("../view/AddMemberToTeamView.fxml"));
			Parent root = (Parent) loader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.DECORATED);
			stage.setTitle("Assign role");
			stage.setScene(new Scene(root));
			stage.sizeToScene();
			AddMemberToTeamController controller = loader.getController();
			controller.initControls(observableMembers, observableWorkers, supervisor, selectedWorker);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not load .fxml file");
		}
	}
}
