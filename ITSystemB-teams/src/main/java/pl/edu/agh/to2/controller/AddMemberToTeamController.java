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
import pl.edu.agh.to2.common.IWorker;
import pl.edu.agh.to2.db.DbHandle;
import pl.edu.agh.to2.model.Member;
import pl.edu.agh.to2.model.Team;
import pl.edu.agh.to2.model.generator.BetterDataGenerator;

public class AddMemberToTeamController extends ModifyMemberInTeamController {

	@Override
	protected void handleAcceptAction() {
		if (supervisorCheckBox.isSelected()) {
			if (supervisor.get() != null) {
				observableWorkers.add(supervisor.get().getWorker());
			}
			supervisor.set(new Member(-1, selectedWorker, roleTextField.getText(), null));
		} else if (selectedMember != null) {
			observableMembers.add(selectedMember);
		} else {
			
				observableMembers.add(new Member(-1, selectedWorker, roleTextField.getText(), null));
			
		}
		observableWorkers.remove(selectedWorker);

		Stage stage = (Stage) acceptButton.getScene().getWindow();
		stage.close();
	}

	private void initControls(ObservableList<Member> observableMembers, ObservableList<IWorker> observableWorkers,
			ObjectProperty<Member> supervisor, IWorker worker, DbHandle dbHandle) {

		this.observableMembers = observableMembers;
		this.observableWorkers = observableWorkers;
		this.selectedWorker = worker;
		this.supervisor = supervisor;

		memberLabel.setText("Worker: " + selectedWorker.getFullName());

		selectedMember = dbHandle.loadMemberByWorker(worker);
		if (selectedMember != null && selectedMember.isSupervisor()) {
			supervisorCheckBox.setSelected(false);
			supervisorCheckBox.setDisable(true);
		}

		supervisorCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				roleTextField.setText("Lider zespołu");
				roleTextField.setEditable(false);
			} else {
				roleTextField.setText("");
				roleTextField.setEditable(true);
			}
		});
	}

	public static void init(ObservableList<Member> observableMembers, ObservableList<IWorker> observableWorkers,
			ObjectProperty<Member> supervisor, IWorker selectedWorker, DbHandle dbHandle) {
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
			controller.initControls(observableMembers, observableWorkers, supervisor, selectedWorker, dbHandle);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not load .fxml file");
		}
	}
}
