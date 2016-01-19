package pl.edu.agh.iisg.to2.controller;
import java.math.BigDecimal;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.edu.agh.iisg.to2.model.Project;
import pl.edu.agh.to2.common.ITeam;

public class AddTeamsController {

		@FXML private TableView<ITeam> teamTable;
		@FXML private TableColumn<ITeam, String> idColumn;
		@FXML private TableColumn<ITeam, String> nameOfTeamColumn;
		@FXML private TableColumn<ITeam, BigDecimal> costOfTeamColumn;
		
		@FXML private Button cancelButton;
		@FXML private Button addButton;
		@FXML private Button okButton;

		@FXML private TextField teamsTextField;
		
		private ProjectController projController; 
		private Project project;
		private ObservableList<ITeam> teams;
		
		private Stage dialogStage;
		private int type;

		@FXML private Label errorTeams;
		
		
		@FXML
		private void initialize() {
			teamTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			nameOfTeamColumn.setCellValueFactory(value -> value.getValue().getNameofTeamObservable());
			costOfTeamColumn.setCellValueFactory(value -> value.getValue().getCostOfTeamObservable());
			addButton.disableProperty().bind(Bindings.isEmpty(teamTable.getSelectionModel().getSelectedItems()));
			errorTeams.setVisible(false);
		}
		
		@FXML
		private void handleCancelAction(ActionEvent event) {
			Stage stage = (Stage)cancelButton.getScene().getWindow();
			stage.close();
		}


		@FXML
		private void handleAddAction(ActionEvent event) {
			ITeam ttmp = teamTable.getSelectionModel().getSelectedItem();
			if (this.type == 0){
				ObservableList<ITeam> e = FXCollections.observableArrayList(ttmp);
        		this.project.setTeams(e);
        		this.type = 1;
			}else{
				this.project.addTeam(ttmp);
			}
	        teamsTextField.setText(project.getStringTeamsForProject().getValue());
	        teamTable.getItems().removeAll(teamTable.getSelectionModel().getSelectedItems());
	        	
	        System.out.println("Refreshing...");
	        teamTable.refresh(); 
		}
		
		@FXML
		private void handleOkAction(ActionEvent event) {
			if (isValid()){
				Stage stage = (Stage) cancelButton.getScene().getWindow();
				stage.close();
			}
		}
		
		private boolean isValid(){
			errorTeams.setVisible(false);
			if ( teamsTextField.getText().isEmpty() == true){
				teamsTextField.setStyle("-fx-background-color: red");
				errorTeams.setText("You didn't choose employees");
				errorTeams.setVisible(true);
				return false;
			}
			return true;
		}
		
		public void setData(Project p, ObservableList<ITeam> t, int i) {
			this.teams = t;
			this.project = p;
			this.type = i;
			teamTable.getItems().setAll(t);
		}
		
		public ProjectController getProjController() {
			return projController;
		}

		public void setProjController(ProjectController projController) {
			this.projController = projController;
		}
		
		public void setDialogStage(Stage dialogStage) {
			this.dialogStage = dialogStage;
		}
		
}

