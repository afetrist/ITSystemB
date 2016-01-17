package pl.edu.agh.to2.controller;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.edu.agh.to2.common.IWorker;
import pl.edu.agh.to2.model.Member;

public class EditMemberInTeamController extends ModifyMemberInTeamController {
	@FXML
	private Button deleteButton;
	
	@FXML
	private void handleDeteleAction() {
		observableMembers.remove(selectedMember);
		observableWorkers.add(selectedMember.getWorker());
		
		Stage stage = (Stage) acceptButton.getScene().getWindow();
		stage.close();
	}
	
	@Override
	protected void handleAcceptAction() {
		if (supervisorCheckBox.isSelected()) {
			if (supervisor.get() != null) {
				observableWorkers.add(supervisor.get().getWorker());
			}
			supervisor.set(selectedMember);
			observableMembers.remove(selectedMember);
		} 
		selectedMember.setRole(roleTextField.getText());
		Stage stage = (Stage) acceptButton.getScene().getWindow();
		stage.close();
	}
	
	private void initControls(ObservableList<Member> observableMembers, ObservableList<IWorker> observableWorkers,
			ObjectProperty<Member> supervisor, Member member) {
		
		this.observableMembers = observableMembers;
		this.observableWorkers = observableWorkers;
		this.selectedMember = member;
		this.supervisor = supervisor;

		memberLabel.setText("Worker: " + selectedMember.getWorker().getFullName());
		roleTextField.setText(selectedMember.getRole());
		
		if (selectedMember.isSupervisor()) {
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
			ObjectProperty<Member> supervisor, Member selectedMember) {
		try {
			FXMLLoader loader = new FXMLLoader(
					ModifyMemberInTeamController.class.getResource("../view/EditMemberInTeamView.fxml"));
			Parent root = (Parent)loader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.DECORATED);
			stage.setTitle("Edit member");
			stage.setScene(new Scene(root));
			stage.sizeToScene();
			EditMemberInTeamController controller = loader.getController();
			controller.initControls(observableMembers, observableWorkers, supervisor, selectedMember);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not load .fxml file");
		}
	}
}
