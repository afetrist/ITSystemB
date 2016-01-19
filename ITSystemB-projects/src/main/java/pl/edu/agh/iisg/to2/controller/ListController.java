package pl.edu.agh.iisg.to2.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import common.iEmployeeForProjects;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import pl.edu.agh.iisg.to2.ProjectMain;
import pl.edu.agh.iisg.to2.model.GeneratedData;
import pl.edu.agh.iisg.to2.model.Project;
import pl.edu.agh.to2.common.ITeam;
import pl.edu.agh.iisg.to2.FindEmployees;
import pl.edu.agh.iisg.to2.FindTeams;

public class ListController {


	@FXML private TableView<Project> projectTable;
	@FXML private TableColumn<Project, LocalDate> deadlineColumn;
	@FXML private TableColumn<Project, LocalDate> startDateColumn;
	@FXML private TableColumn<Project, String> employeesColumn;
	@FXML private TableColumn<Project, String> teamsColumn;
	@FXML private TableColumn<Project, BigDecimal> budgetColumn;
	
	@FXML private Button deleteButton;
	@FXML private Button cancelButton;
	@FXML private Button editButton;
	@FXML private Button findButton;
	@FXML private Button addButton;
	@FXML private Button allProjects;
	
	@FXML private TextField paramId;
	@FXML private TextField paramDeadline;
	@FXML private TextField paramStartDate;
	@FXML private TextField paramEmployees;
	@FXML private TextField paramTeams;
	@FXML private TextField paramBudget;

	private LocalDateStringConverter converter;
	private ProjectController projController; 
	private ObservableList<Project> projects;
	private ObservableList<ITeam> teams;
	private ObservableList<iEmployeeForProjects> employees;
	private ObservableList<Project> projectsTmp;
	
	@FXML private Label errorId;
	@FXML private Label errorDate;
	@FXML private Label errorTeams;
	@FXML private Label errorEmployees;
	@FXML private Label errorBudget;
	
	private DateTimeFormatter formatter;
	
	private GeneratedData d;
	
	
	@FXML
	private void initialize() {
		projectTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		budgetColumn.setCellValueFactory(value -> value.getValue().getBudget());
		deadlineColumn.setCellValueFactory(dataValue -> dataValue.getValue().getDeadline());
		startDateColumn.setCellValueFactory(dataValue -> dataValue.getValue().getStartdate());
		teamsColumn.setCellValueFactory(value -> value.getValue().getStringTeamsForProject());
		employeesColumn.setCellValueFactory(value -> value.getValue().getStringEmployeesForProject());
		deleteButton.disableProperty().bind(Bindings.isEmpty(projectTable.getSelectionModel().getSelectedItems()));
		editButton.disableProperty().bind(Bindings.isEmpty(projectTable.getSelectionModel().getSelectedItems()));
		errorId.setVisible(false);
		errorDate.setVisible(false);
		errorTeams.setVisible(false);
		errorEmployees.setVisible(false);
		errorBudget.setVisible(false);
	}
	
	private void initializeWithArguments(int mode) {
		ObservableList<Project> tmpWithArguments = FXCollections.observableArrayList();
		tmpWithArguments.addAll(projects);
		String tmp = "";
		if (!paramId.getText().isEmpty()) {
			tmp = paramId.getText();
			//System.out.print("string id" + tmp);
			for (int i = 0; i < tmpWithArguments.size(); i++){
				if (!(tmp.equals(tmpWithArguments.get(i).getId()))){
					//System.out.print("string id2" + tmpWithArguments.get(i).getId()+"\n");
					tmpWithArguments.remove(i) ;
					i--;
				}
			}
		}
		if (!paramDeadline.getText().isEmpty()){
			tmp = paramDeadline.getText();
			//System.out.print("string deadline" + tmp);
			for (int i = 0; i < tmpWithArguments.size(); i++){
				if (!(tmp.equals(tmpWithArguments.get(i).getDeadline().getValue().toString()))){
					//System.out.print("string deadline2" + tmpWithArguments.get(i).getDeadline().getValue().toString()+"\n");
					tmpWithArguments.remove(i) ;
					i--;
				}
			}
		}
		if (!paramStartDate.getText().isEmpty()){
			tmp = paramStartDate.getText();
			//System.out.print("string startdate" + tmp);
			for (int i = 0; i < tmpWithArguments.size(); i++){
				if (!(tmp.equals(tmpWithArguments.get(i).getStartdate().getValue().toString()))){
					//System.out.print("string startdate2" + tmpWithArguments.get(i).getStartdate().getValue().toString()+"\n");
					tmpWithArguments.remove(i);
					i--;
				}
			}
		}
		if (!paramEmployees.getText().isEmpty()){
			tmp = paramEmployees.getText();
			//System.out.println("string Employees" + tmp);
			for (int i = 0; i < tmpWithArguments.size(); i++){
				ObservableList<iEmployeeForProjects> etmp = FXCollections.observableArrayList();
				etmp.addAll(tmpWithArguments.get(i).getEmployees());
				String tmpEmployees = FindEmployees.getStringEmployees(etmp).getValue().replace(",", "");
				if (!(tmpEmployees.toLowerCase().contains(tmp.toLowerCase()))){
					System.out.println("wartosc tmp: " + tmp+ " wartosc tmpEmployees: "+ tmpEmployees);
					tmpWithArguments.remove(i) ;
					i--;
				}
			}
		}
		if (!paramTeams.getText().isEmpty()){
			tmp = paramTeams.getText();
			//System.out.print("string Teams" + tmp);
			for (int i = 0; i < tmpWithArguments.size(); i++){
				ObservableList<ITeam> ttmp = FXCollections.observableArrayList();
				ttmp.addAll(tmpWithArguments.get(i).getTeams());
				String tmpTeams = FindTeams.getStringTeams(ttmp).getValue().replace(",", "");
				if (!(tmpTeams.toLowerCase().contains(tmp.toLowerCase()))){
					//System.out.println("wartosc tmp: " + tmp+ " wartosc tmpEmployees: "+ tmpTeams);
					tmpWithArguments.remove(i) ;
					i--;
				}
			}
		}
		if (!paramBudget.getText().isEmpty()){
			tmp = paramBudget.getText();
			//System.out.print("string Budget:" + tmp +"\n");
			//System.out.println("size: "+tmpWithArguments.size());
			for (int i = 0; i < tmpWithArguments.size(); i++){
				if (!(tmp.equals(tmpWithArguments.get(i).getBudget().getValue().toString())) ){
					//System.out.print("string budget2 to remove: " + tmpWithArguments.get(i).getBudget().getValue().toString()+"\n");
					tmpWithArguments.remove(i);
					i--;
				} 
			}
		}
		for (Project tmpp: tmpWithArguments){
			//System.out.println("koncowe:"+ tmpp.getDeadline().getValue().toString()+ tmpp.getDeadline().getValue().toString());
		}
		if ((areParametersValid())&& (mode == 1)){
			setData(tmpWithArguments, this.d, 1);
		}	
	}
	
	@FXML
	private void handleAllProjectsAction(ActionEvent event) {
		setData(this.projectsTmp, this.d, 1);
	}
	

	@FXML
	private void handleDeleteAction(ActionEvent event) {
		projectTable.getItems().removeAll(projectTable.getSelectionModel().getSelectedItems());
	}
	
	@FXML
	private void handleCancelAction(ActionEvent event) {
		Stage stage = (Stage)cancelButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void handleFindAction(ActionEvent event) {
		initializeWithArguments(1);
	}

	@FXML
	private void handleAddAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoaderAdd = new FXMLLoader();
            fxmlLoaderAdd.setLocation(ProjectMain.class.getResource("view/AddView.fxml"));
            Parent root1 = (Parent) fxmlLoaderAdd.load();
            
            Stage stageAdd = new Stage();
            stageAdd.initModality(Modality.APPLICATION_MODAL);
            stageAdd.setTitle("Add project");
            stageAdd.setScene(new Scene(root1));  

            AddController controllerAdd = fxmlLoaderAdd.getController();
			controllerAdd.setDialogStage(stageAdd);
			controllerAdd.setData(projectTable.getItems(),this.d);
			
            stageAdd.showAndWait();
            System.out.println("Refreshing...");
            //projectTable.refresh(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@FXML
	private void handleEditAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoaderEdit = new FXMLLoader();
            fxmlLoaderEdit.setLocation(ProjectMain.class.getResource("view/EditView.fxml"));
            Parent root1 = (Parent) fxmlLoaderEdit.load();
            
            Stage stageEdit = new Stage();
            stageEdit.initModality(Modality.APPLICATION_MODAL);
            stageEdit.setTitle("Edit project");
            stageEdit.setScene(new Scene(root1)); 
            
            EditController controllerEdit = fxmlLoaderEdit.getController();
            controllerEdit.setDialogStage(stageEdit);
            Project editproject = projectTable.getSelectionModel().getSelectedItem();
            projectTable.getItems().removeAll(projectTable.getSelectionModel().getSelectedItems());
    		controllerEdit.setData(projectTable.getItems(), editproject, this.d);
            
            stageEdit.showAndWait();
            //projectTable.refresh(); 
            
            System.out.println("Refreshing...");
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
	}
	
	public void setDataToTest(ObservableList<Project> p) {
		this.projects = p;
		projectTable.getItems().setAll(p);
	}
	
	public void setData(ObservableList<Project> p, GeneratedData d, int i) {
		this.d = d;
		this.employees = d.getEmployees();
		if (i == 0){
			this.projectsTmp = p;
		}
		this.projects = p;
		for (Project ptmp: p){
			//System.out.println("deadline: "+ ptmp.getDeadline().getValue().toString()+ "startdate: "+ ptmp.getStartdate().getValue().toString() + "\n");
		} 
		this.teams = d.getTeams();
		projectTable.getItems().setAll(p);
	}
	
	public ProjectController getProjController() {
		return projController;
	}

	public void setProjController(ProjectController projController) {
		this.projController = projController;
	}
	
	public LocalDate fromStringDate(String string) {
		String pattern = "yyyy-MM-dd";
		formatter = DateTimeFormatter.ofPattern(pattern);
		setConverter(new LocalDateStringConverter(formatter, formatter));
        if (string != null && !string.isEmpty()) {
            return LocalDate.parse(string, formatter);
        } else {
            return null;
        }
    }
	
	private boolean areParametersValid() {
		//TODO: Implementation
		return true;
	}

	public LocalDateStringConverter getConverter() {
		return converter;
	}

	public void setConverter(LocalDateStringConverter converter) {
		this.converter = converter;
	}
	
	public ObservableList<Project> getProjects(){
		return this.projects;
	}

	
	
}
