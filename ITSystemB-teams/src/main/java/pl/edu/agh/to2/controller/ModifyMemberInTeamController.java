package pl.edu.agh.to2.controller;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.edu.agh.to2.common.IWorker;
import pl.edu.agh.to2.model.Member;

public abstract class ModifyMemberInTeamController {

	protected ObservableList<IWorker> observableWorkers;
	protected ObservableList<Member> observableMembers;
	protected ObjectProperty<Member> supervisor;
	protected IWorker selectedWorker;
	protected Member selectedMember;

	@FXML
	protected Label memberLabel;
	@FXML
	protected CheckBox supervisorCheckBox;
	@FXML
	protected Label warningLabel;
	@FXML
	protected Button cancelButton;
	@FXML
	protected TextField roleTextField;
	@FXML
	protected Button acceptButton;


	@FXML
	protected void handleCancelAction() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	protected abstract void handleAcceptAction();

}
