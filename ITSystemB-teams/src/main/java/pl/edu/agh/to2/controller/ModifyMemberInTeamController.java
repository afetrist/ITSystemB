package pl.edu.agh.to2.controller;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.edu.agh.to2.Member;
import pl.edu.agh.to2.model.IWorker;

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
	protected Button acceptButton;
	@FXML
	protected ComboBox roleComboBox;
	@FXML
	protected Button newRoleButton;


	@FXML
	protected void handleCancelAction() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	protected abstract void handleAcceptAction();

}
