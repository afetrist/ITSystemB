package pl.edu.agh.to2.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.edu.agh.to2.Member;
import pl.edu.agh.to2.Role;
import pl.edu.agh.to2.Team;

public class TeamDetailsController {
	private Team overviewedTeam;
	private ObservableList<Team> observableTeams;
	@FXML
	private Button Edit;
	@FXML
	private Button Delete;
	@FXML
	private Label name;
	@FXML
	private Label leader;
	@FXML
	private Label created;
	@FXML
	private TableView<Member> membersTable;
	@FXML
	private TableColumn<Member, String> firstNameColumn;
	@FXML
	private TableColumn<Member, String> lastNameColumn;
	@FXML
	private TableColumn<Member, Role> roleColumn;
	@FXML
	private TableColumn<Member, String> peselColumn;
	@FXML
	private TreeView<Member> teamTreeView;

	public TeamDetailsController() {
	}

	@FXML
	private void handleEditAction(Event event) {
		CreateTeamController.initCreateTeamDialog(observableTeams, overviewedTeam, null);
	}

	@FXML
	private void handleDeleteAction(Event event) {
		Stage stage = (Stage) name.getScene().getWindow();
		DeleteTeamController.initDeleteTeamDialog(stage, overviewedTeam, observableTeams);
	}

	//// ==||| Lazy Tree Loadnig |||==

	private class LazyTreeItem extends TreeItem<Member> {

		private boolean hasLoadedChildren = false;

		public LazyTreeItem(Member member) {
			super(member);

		}

		@Override
		public ObservableList<TreeItem<Member>> getChildren() {
			if (hasLoadedChildren == false) {
				loadChildren();
			}
			return super.getChildren();
		}

		@Override
		public boolean isLeaf() {
			if (hasLoadedChildren == false) {
				loadChildren();
			}
			return super.getChildren().isEmpty();
		}

		private void loadChildren() {
			System.out.println("<tree_view_log>:loading children for: \'" + this.getValue() + "\'");
			Team team = this.getValue().getSupervisedTeam();
			if (team != null) {
				List<Member> members = team.getMembers();

				if (members != null) {
					hasLoadedChildren = true;
					for (Member m : members) {
						LazyTreeItem child1 = new LazyTreeItem(m);
						super.getChildren().add(child1);
					}
				}
			}
		}
	}

	//// ==|||end of lazy tree loading stuff |||==

	@Deprecated
	private void initTree(Team team) {
		TreeItem<Member> rootItem = new TreeItem<Member>(team.getSupervisor());
		List<Member> members = team.getMembers();
		teamTreeView.setRoot(rootItem);

		for (Member m : members) {
			TreeItem<Member> link = recursionTree(m);
			rootItem.getChildren().add(link);
		}
	}

	@Deprecated
	private TreeItem<Member> recursionTree(Member member) {
		TreeItem<Member> link = new TreeItem<Member>(member);

		if (member.getSupervisedTeam() != null) {
			List<Member> subs = member.getSupervisedTeam().getMembers();
			for (Member s : subs) {
				System.out.println(s);
				link.getChildren().add(recursionTree(s));
			}
		}

		return link;
	}

	public static void initTeamDetailsDialog(Team t, ObservableList<Team> oTeams) {
		try {
			FXMLLoader loader = new FXMLLoader(TeamDetailsController.class.getResource("../view/TeamDetailsView.fxml"));
			Parent root = (Parent) loader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.DECORATED);
			stage.setTitle("team details");
			stage.setScene(new Scene(root));
			stage.sizeToScene();
			TeamDetailsController controller = loader.getController();
			controller.overviewedTeam = t;
			controller.observableTeams = oTeams;
			controller.initControls();
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not load .fxml file");
		}
	}

	private void initDynamicTree(Team team) {
		LazyTreeItem rootItem = new LazyTreeItem(team.getSupervisor());
		teamTreeView.setRoot(rootItem);
		teamTreeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

	}

	public void initControls() {
		name.setText(overviewedTeam.getTeamName());
		if (overviewedTeam.getSupervisor() != null)
			leader.setText(overviewedTeam.getSupervisor().toString());
		else
			leader.setText("Leader not assigned!");

		created.setText(new SimpleDateFormat("dd-MM-yyyy").format(overviewedTeam.getDateOfCreation()));

		List<Member> members = overviewedTeam.getMembers();
		if (members != null) {
			ObservableList<Member> oMembers = FXCollections.observableArrayList(members);
			firstNameColumn.setCellValueFactory(value -> value.getValue().getWorker().getFirstNameProperty());
			lastNameColumn.setCellValueFactory(value -> value.getValue().getWorker().getLastNameProperty());
			// peselColumn.setCellValueFactory(value ->
			// value.getValue().getWorker().getPeselProperty());
			// postColumn.setCellValueFactory(value
			// ->value.getValue().getRoleProperty());
			membersTable.setItems(oMembers);
			initDynamicTree(overviewedTeam);

		}
	}

}
