package pl.edu.agh.to2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.edu.agh.to2.db.DbHandle;
import pl.edu.agh.to2.model.Member;
import pl.edu.agh.to2.model.Team;

public class TeamsMainController {

	private ObservableList<Team> observableTeams;
	private DbHandle dbHandle = new DbHandle();
	
	@FXML
	private Button createTeamButton;
	@FXML
	private TextField teamSearcherTextField;
	@FXML
	private TableView<Team> teamsTable;
	@FXML
	private TableColumn<Team, String> teamNameColumn;
	@FXML
	private TableColumn<Team, Member> teamLeaderColumn;
	@FXML
	private TableView<Team> orphanTeamsTable;

	@FXML
	private void handleCreateTeamAction(Event event) {
		System.out.println("<main_contr>: opening 'create_contr'");
		CreateTeamController.initCreateTeamDialog(observableTeams, null, dbHandle);
	}

	private void handleTeamDoubleClick(Team team) {
		System.out.println("<main_contr>: opening 'team_detail_contr'");
		TeamDetailsController.initTeamDetailsDialog(team, observableTeams, dbHandle);
	}
	
	@FXML
	public void initialize() {
		System.out.println("<main_contr>: controller initiation");
		observableTeams = FXCollections.observableArrayList();
		
		teamsTable.setPlaceholder(new Label("No matching data"));

		FilteredList<Team> filteredTeams = new FilteredList<>(observableTeams, p -> false);
		teamSearcherTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null && newValue.length() == 3 && oldValue.length() < newValue.length()) {
				System.out.println("<main_contr>: query to db for '" + newValue + "'");
				observableTeams.clear();
				observableTeams.addAll(dbHandle.loadTeams(newValue, false));
			}
			
			filteredTeams.setPredicate(team -> {
				if (newValue == null || newValue.isEmpty() || newValue.length() < 3) {
					return false;
				}

				String lowerCaseFilter = newValue.toLowerCase();
//				if (lowerCaseFilter.equals("all")) {
//					return true;
//				} else 
				if (team.getTeamName().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (team.getSupervisor() != null
						&& team.getSupervisor().toString().toLowerCase().contains(lowerCaseFilter)) {
					return true;

				}
				return false;
			});
		});

		FilteredList<Team> orphanTeams = new FilteredList<>(observableTeams, team -> {
			if (team.getSupervisor() == null) {
				return true;
			} else {
				return false;
			}
		});

		SortedList<Team> sortedFilteredTeams = new SortedList<>(filteredTeams);
		sortedFilteredTeams.comparatorProperty().bind(teamsTable.comparatorProperty());
		teamsTable.setItems(sortedFilteredTeams);

		SortedList<Team> sortedOrphanTeams = new SortedList<>(orphanTeams);
		sortedOrphanTeams.comparatorProperty().bind(orphanTeamsTable.comparatorProperty());
		orphanTeamsTable.setItems(sortedOrphanTeams);

		teamsTable.setRowFactory(p -> {
			TableRow<Team> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					Team selectedTeam = row.getItem();
					System.out.println(selectedTeam.getId());
					System.out.println(selectedTeam.getTeamName());
					System.out.println(selectedTeam.getMembers());
					handleTeamDoubleClick(selectedTeam);
				}
			});
			return row;
		});
	}

	public static void init() {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(TeamsMainController.class.getResource("../view/TeamsMainView.fxml"));
			root = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setTitle("Teams Overwiev");
			stage.setScene(new Scene(root));
			stage.sizeToScene();
			stage.show();
			

		} catch (Exception e) {
			System.err.println("Could not load .fxml file");
			e.printStackTrace();
		}
	}
}
