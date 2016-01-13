package pl.edu.agh.iisg.to2.controller;

import static java.lang.Math.toIntExact;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


import common.iEmployeeForProjects; 
import common.EmployeeForProjects;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import pl.edu.agh.iisg.to2.ProjectMain;
import pl.edu.agh.iisg.to2.model.GeneratedData;
import common.ITeam;
import pl.edu.agh.iisg.to2.model.MySQLAccess;
import pl.edu.agh.iisg.to2.model.ProjectMock; 
import pl.edu.agh.iisg.to2.model.IProject; 
import pl.edu.agh.iisg.to2.FindEmployees;
import pl.edu.agh.iisg.to2.FindTeams;

public class AddController {

	private ProjectController controller;
	

	@FXML private DatePicker deadlineDatePicker;
	@FXML private DatePicker startdateDatePicker;
	@FXML private TextField employeesTextField;
	@FXML private TextField teamsTextField;
	@FXML private TextField budgetTextField;
	@FXML private TextField calculatedCost;
	
	
	@FXML private Button addEmployeesButton;
	@FXML private Button addTeamsButton;
	@FXML private Button cancelButton;
	@FXML private Button okButton;
	@FXML private Button calculateBudget;
	
	@FXML private Label errorDate;
	@FXML private Label errorTeams;
	@FXML private Label errorEmployees;
	@FXML private Label errorBudget;

	private Stage dialogStage;
	private ProjectMock project;
	private ObservableList<ProjectMock> projectsTmp;
	private ObservableList<ITeam> teams;
	private ObservableList<iEmployeeForProjects> employees;
	
	private GeneratedData d;
	
	@FXML
	public void initialize() {
		this.project = new ProjectMock();
		errorDate.setVisible(false);
		errorTeams.setVisible(false);
		errorEmployees.setVisible(false);
		errorBudget.setVisible(false);
	}
	
	public ProjectController getController() {
		return controller;
	}

	public void setController(ProjectController controller) {
		this.controller = controller;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setData(ObservableList<ProjectMock> projects, GeneratedData d) {
		this.d = d;
		this.projectsTmp = projects;
		this.teams = d.getTeams();
		this.employees = d.getEmployees();
	}
	
	@FXML
	private void handleOkAction(ActionEvent event) {
		updateModel();
		if (isInputValid()){
			projectsTmp.add(project);
			Stage stage = (Stage) cancelButton.getScene().getWindow();
			stage.close();
		}else{
			
		}
	}
	
	@FXML
	private void handleCalculateAction(ActionEvent event) {
		BigDecimal budget = new BigDecimal(0);
		ObjectProperty<LocalDate> deadline =  new SimpleObjectProperty<LocalDate>(deadlineDatePicker.getValue());
		ObjectProperty<LocalDate> startdate =  new SimpleObjectProperty<LocalDate>(startdateDatePicker.getValue());
		long days = ChronoUnit.DAYS.between(deadline.getValue(), startdate.getValue());
		int daysInt = toIntExact(days);
		ObservableList<ITeam> ttmp = FXCollections.observableArrayList();
		ttmp.addAll(FindTeams.setTeamsFromString(teamsTextField.getText(), teams));
		ObservableList<iEmployeeForProjects> etmp = FXCollections.observableArrayList();
		etmp.addAll(FindEmployees.setEmployeesFromStringNameLastName(employeesTextField.getText(), employees));
		int finalBudget = 0;
		for (iEmployeeForProjects e: etmp ) finalBudget = finalBudget +  e.getSalary().getValue();
		for (ITeam t: ttmp) budget = budget.add(t.getCostOfTeam());
		budget = budget.multiply(new BigDecimal(daysInt*8)); 
		finalBudget = finalBudget*daysInt*8;
		int tmp = Math.abs(budget.intValueExact() + finalBudget);
		calculatedCost.setText(Integer.toString(tmp));	
	}

	@FXML
	private void handleCancelAction(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void handleAddEmployeesAction(ActionEvent event) {
		try {
            FXMLLoader fxmlLoaderAddEmployee = new FXMLLoader();
            fxmlLoaderAddEmployee.setLocation(ProjectMain.class.getResource("view/AddEmployee.fxml"));
            Parent root1 = (Parent) fxmlLoaderAddEmployee.load();
            
            Stage stageAddEmployee = new Stage();
            stageAddEmployee.initModality(Modality.APPLICATION_MODAL);
            stageAddEmployee.setTitle("Add employees");
            stageAddEmployee.setScene(new Scene(root1));  

            AddEmployeeController controllerAddEmployee = fxmlLoaderAddEmployee.getController();
			controllerAddEmployee.setDialogStage(stageAddEmployee);
			controllerAddEmployee.setData(this.project, employees, 0);
			
            stageAddEmployee.showAndWait();
            //System.out.println("Refreshing...");
    		String s2 = project.getStringEmployeesForProject().getValue();
    		employeesTextField.setText(s2);
            //projectTable.refresh(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	private void handleAddTeamsAction(ActionEvent event) {
		try {
            FXMLLoader fxmlLoaderAddTeam = new FXMLLoader();
            fxmlLoaderAddTeam.setLocation(ProjectMain.class.getResource("view/AddTeam.fxml"));
            Parent root1 = (Parent) fxmlLoaderAddTeam.load();
            
            Stage stageAddTeams = new Stage();
            stageAddTeams.initModality(Modality.APPLICATION_MODAL);
            stageAddTeams.setTitle("Add teams");
            stageAddTeams.setScene(new Scene(root1));  

            AddTeamsController controllerAddTeam = fxmlLoaderAddTeam.getController();
			controllerAddTeam.setDialogStage(stageAddTeams);
			controllerAddTeam.setData(this.project, this.teams,0);
			
            stageAddTeams.showAndWait();
            System.out.println("Refreshing...");
            String s1 = project.getStringTeamsForProject().getValue();
    		teamsTextField.setText(s1);
            //projectTable.refresh(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	private boolean isInputValid() {
		if (project.getDeadline() == null || project.getDeadline().getValue() == null || project.getStartdate() == null || project.getStartdate().getValue() == null) {
			errorDate.setText("ERROR! Deadline or startdate is empty!");
			errorDate.setVisible(true);
			return false;
		}
		if (!(project.getStartdate().getValue()).isBefore(project.getDeadline().getValue())) {

			errorDate.setVisible(true);
			return false;
		}
		DecimalFormat decimalFormatter = new DecimalFormat();
		decimalFormatter.setParseBigDecimal(true);
		if (project.getBudget() != null){
				if (project.calculateBudget() > project.getBudget().getValue().intValueExact() ){
					errorBudget.setTextFill(Color.RED);
					errorBudget.setText("ERROR! Not enough money for project!");
					errorBudget.setVisible(true);
					return false;
				}		
		}else{
			budgetTextField.setStyle("-fx-background-color: red");
			errorBudget.setText("ERROR! Budget musn't be empty!");
			errorBudget.setVisible(true);
			return false;
		}
		if (project.getEmployees() == null){
			employeesTextField.setStyle("-fx-background-color: red");
			errorEmployees.setText("ERROR! You have to choose employees!");
			errorEmployees.setVisible(true);
			return false;
		}
		if (project.getTeams() == null ){
			teamsTextField.setStyle("-fx-background-color: red");
			errorTeams.setText("ERROR! You have to choose teams!");
			errorTeams.setVisible(true);
			return false;
		}
		return true;
	}

	
	private void updateModel() {
		
		if (deadlineDatePicker.getValue() != null){ 
			project.setDeadline(new SimpleObjectProperty<LocalDate>(deadlineDatePicker.getValue()));
			//System.out.println("data pobrana2:"+ project.getDeadline().getValue().toString() );
			
		}
		if (startdateDatePicker.getValue() != null){
			project.setStartdate(new SimpleObjectProperty<LocalDate>(startdateDatePicker.getValue()));
			//System.out.println("data pobrana COCOOCCO:"+ project.getStartdate().getValue().toString());
		}
		
		if (!(budgetTextField.getText().isEmpty())){
			DecimalFormat decimalFormatter = new DecimalFormat();
			decimalFormatter.setParseBigDecimal(true);
			try {
				SimpleObjectProperty<BigDecimal> bTmp =  new SimpleObjectProperty<BigDecimal>(((BigDecimal) decimalFormatter.parse(budgetTextField.getText())));
				project.setBudget(bTmp);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (!(teamsTextField.getText().isEmpty())){
			System.out.println("chce ustawic team:"+ teamsTextField.getText());
			ObservableList<ITeam> ttmp = FXCollections.observableArrayList();
			ttmp.addAll(FindTeams.setTeamsFromString(teamsTextField.getText(), teams));

			project.setTeams(ttmp);
		}
		if (!(employeesTextField.getText().isEmpty())){
			System.out.println("chce ustawic pracownika:"+ employeesTextField.getText());
			ObservableList<iEmployeeForProjects> etmp = FXCollections.observableArrayList();
			etmp.addAll(FindEmployees.setEmployeesFromStringNameLastName(employeesTextField.getText(), employees));
			project.setEmployees(etmp);
		}
		
		
		
		saveToDatabase();
	}
	
	private void saveToDatabase()  {
		MySQLAccess dao = new MySQLAccess();
	    try {
			dao.insertProject(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void initAddEmployeesDialog() {
		// TODO: implement
	}
	
	public static void initAddTeamsDialog() {
		// TODO: implement
	}
}
