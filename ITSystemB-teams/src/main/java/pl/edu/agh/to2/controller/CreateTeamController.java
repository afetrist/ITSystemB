package pl.edu.agh.to2.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.edu.agh.to2.common.IWorker;
import pl.edu.agh.to2.db.DbHandle;
import pl.edu.agh.to2.model.Member;
import pl.edu.agh.to2.model.Team;

public class CreateTeamController {

	private DbHandle dbHandle;
	private Team teamToCreate;
	private ObservableList<Team> parentObservableTeams;
	private ObservableList<Team> observableTeams;
	private ObjectProperty<Member> supervisor = new SimpleObjectProperty<>();
	private ObservableList<IWorker> observableWorkers;

	@FXML
	private Button cancelButton;
	@FXML
	private Button createButton;
	@FXML
	private TextField teamNameText;
	@FXML
	private Label errorLabel;

	@FXML
	private TextField workerSearchText;
	@FXML
	private TableView<IWorker> workersTable;
	@FXML
	private TableColumn<IWorker, String> columnFirstNameWorker;
	@FXML
	private TableColumn<IWorker, String> columnLastNameWorker;

	@FXML
	private TableView<Member> membersTable;
	@FXML
	private TableColumn<Member, String> columnFirstNameMember;
	@FXML
	private TableColumn<Member, String> columnLastNameMember;
	@FXML
	private TableColumn<Member, String> columnPost;
	@FXML
	private Label supervisorLabel;

	@FXML
	private TextField teamSearchText;
	@FXML
	private TableView<Team> teamsTable;
	@FXML
	private TableColumn<Team, String> columnTeamName;
	@FXML
	private TableColumn<Team, Member> columnTeamLeader;

	@FXML
	private TableView<Team> subteamsTable;
	@FXML
	private TableColumn<Team, String> columnSubteamName;
	@FXML
	private TableColumn<Team, Member> columnSubteamLeader;

	@FXML
	private void handleCreateAction(Event event) {
		String teamName = teamNameText.getText();

		if (teamName.isEmpty() || teamName.length() < 3) {
			errorLabel.setTextFill(Color.RED);
			errorLabel.setText("Error: Team name cannot be blank or have less than 3 characters");
		} else {
			if (teamToCreate == null) {
				if (supervisor.get() != null) {
					teamToCreate = new Team(-1, teamName, supervisor.get(), Calendar.getInstance().getTime());
					supervisor.get().setSupervisedTeam(teamToCreate);
				} else {
					teamToCreate = new Team(-1, teamName, null, Calendar.getInstance().getTime());
				}
				teamToCreate.setMembers(new ArrayList<>(membersTable.getItems()));
				parentObservableTeams.add(teamToCreate);
			} else {
				if (teamToCreate.getSupervisor() != null) {
					teamToCreate.getSupervisor().setSupervisedTeam(null);
				}
				if (supervisor.get() != null) {
					teamToCreate.setSupervisor(supervisor.get());
					supervisor.get().setSupervisedTeam(teamToCreate);
				} else {
					teamToCreate.setSupervisor(null);
				}

				teamToCreate.setMembers(new ArrayList<>(membersTable.getItems()));
			}
			dbHandle.updateTeam(teamToCreate);
			Stage stage = (Stage) createButton.getScene().getWindow();
			stage.close();
		}
	}

	@FXML
	private void handleCancelAction(Event event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	public static void initCreateTeamDialog(ObservableList<Team> observableTeams, Team team, DbHandle dbHandle) {
		try {
			FXMLLoader loader = new FXMLLoader(CreateTeamController.class.getResource("../view/CreateTeamView.fxml"));
			Parent root = (Parent) loader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.DECORATED);
			if (team == null) {
				stage.setTitle("Create team");
			} else {
				stage.setTitle("Edit team");
			}
			stage.setScene(new Scene(root));
			stage.sizeToScene();
			CreateTeamController controller = loader.getController();
			controller.initControls(observableTeams, team, dbHandle);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not load .fxml file");
		}
	}

	public void initControls(ObservableList<Team> parentObservableTeams, Team teamFromParent, DbHandle dbHandle) {
		this.parentObservableTeams = parentObservableTeams;
		this.observableTeams = FXCollections.observableArrayList();
		this.observableWorkers = FXCollections.observableArrayList();
		this.teamToCreate = teamFromParent;
		this.dbHandle = dbHandle;

		ObservableList<Member> observableMembers;
		ObservableList<Team> observableSubTeams = FXCollections.observableArrayList();

		supervisor.addListener((observable, oldValue, newValue) -> {
			if (newValue == null) {
				supervisorLabel.setText("Supervisor: nobody assigned");
			} else {
				supervisorLabel.setText("Supervisor: " + newValue.getWorker().getFullName());
			}
		});

		if (teamToCreate != null) {
			for (Member member : teamToCreate.getMembers()) {
				if (member.isSupervisor()) {
					observableSubTeams.add(member.getSupervisedTeam());
				}
			}
			observableMembers = FXCollections.observableArrayList(teamToCreate.getMembers());
			teamNameText.setText(teamToCreate.getTeamName());
			supervisor.set(teamToCreate.getSupervisor());

		} else {
			observableMembers = FXCollections.observableArrayList();
		}

		columnFirstNameMember.setCellValueFactory(value -> value.getValue().getWorker().getFirstNameProperty());
		columnLastNameMember.setCellValueFactory(value -> value.getValue().getWorker().getLastNameProperty());
		subteamsTable.setItems(observableSubTeams);
		membersTable.setItems(observableMembers);

		/*
		 * Listen for changes to member list: member added -> add his subteam
		 * and remove it from team list, member removed -> remove his team, add
		 * worker to worker list, add his team to team list
		 */
		observableSubTeams.addListener(new ListChangeListener<Team>() {

			@Override
			public void onChanged(ListChangeListener.Change<? extends Team> c) {
				while (c.next()) {
					if (c.wasAdded()) {

					} else if (c.wasRemoved()) {

					}
				}
			}
		});

		FilteredList<Team> filteredTeams = new FilteredList<>(this.observableTeams, p -> false);
		teamSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null && newValue.length() == 3 && oldValue.length() < newValue.length()) {
				System.out.println("<main_contr>: query to db for '" + newValue + "'");
				observableTeams.clear();
				observableTeams.addAll(dbHandle.loadTeams(newValue, true));
			}

			filteredTeams.setPredicate(team -> {
				if (newValue == null || newValue.isEmpty() || newValue.length() < 3) {
					return false;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (team.equals(teamToCreate)) {
					return false;
				} else if (lowerCaseFilter.equals("all")) {
					return true;
				} else if (team.getTeamName().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (team.getSupervisor() != null
						&& team.getSupervisor().toString().toLowerCase().contains(lowerCaseFilter)) {
					return true;

				}
				return false;
			});
		});

		SortedList<Team> sortedTeams = new SortedList<>(filteredTeams);
		sortedTeams.comparatorProperty().bind(teamsTable.comparatorProperty());
		teamsTable.setItems(sortedTeams);

		FilteredList<IWorker> filteredWorkers = new FilteredList<>(observableWorkers, p -> false);
		workerSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null && newValue.length() == 3 && oldValue.length() < newValue.length()) {
				System.out.println("<main_contr>: query to db for '" + newValue + "'");
				observableWorkers.clear();
				observableWorkers.addAll(dbHandle.loadUnasignedWorkers(newValue.split(" ")));
			}

			filteredWorkers.setPredicate(worker -> {
				if (newValue == null || newValue.isEmpty() || newValue.length() < 3) {
					return false;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (lowerCaseFilter.equals("all")) {
					return true;
				} else if (worker.getFullName().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else {
					return false;
				}
			});
		});

		SortedList<IWorker> sortedWorkers = new SortedList<>(filteredWorkers);
		sortedWorkers.comparatorProperty().bind(workersTable.comparatorProperty());
		workersTable.setItems(sortedWorkers);

		teamsTable.setRowFactory(p -> {
			TableRow<Team> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					Team rowTeam = row.getItem();

					AddMemberToTeamController.init(observableMembers, observableWorkers, supervisor, null,
							rowTeam.getSupervisor(), dbHandle);
				}
			});
			return row;
		});

		subteamsTable.setRowFactory(p -> {
			TableRow<Team> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					Team subTeam = row.getItem();
					observableWorkers.add(subTeam.getSupervisor().getWorker());
					membersTable.getItems().remove(subTeam.getSupervisor());
				}
			});
			return row;
		});

		/*
		 * Double-click on a TableView row opens new dialog
		 */
		workersTable.setRowFactory(p -> {
			TableRow<IWorker> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					// TODO: query to database
					IWorker worker = row.getItem();

					AddMemberToTeamController.init(observableMembers, observableWorkers, supervisor, worker, null,
							dbHandle);
				}
			});
			return row;
		});

		membersTable.setRowFactory(p -> {
			TableRow<Member> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					Member member = row.getItem();

					EditMemberInTeamController.init(observableMembers, observableWorkers, supervisor, member);
				}
			});
			return row;
		});

		membersTable.getItems().addListener(new ListChangeListener<Member>() {

			@Override
			public void onChanged(ListChangeListener.Change<? extends Member> c) {
				while (c.next()) {
					if (c.wasAdded()) {
						System.out.println("ADDDDDDDEEEEEEEEEEEEEEDDDDDDDDd");
						for (Member member : c.getAddedSubList()) {
							System.out.println(member);
							System.err.println(member.isSupervisor());
							if (member.isSupervisor()) {
								subteamsTable.getItems().add(member.getSupervisedTeam());
								observableTeams.remove(member.getSupervisedTeam());
							}
						}
					} else if (c.wasRemoved()) {
						for (Member member : c.getRemoved()) {
							if (member.isSupervisor()) {
								subteamsTable.getItems().remove(member.getSupervisedTeam());
								observableTeams.add(member.getSupervisedTeam());
							}
						}
					}
				}
			}
		});
	}

}