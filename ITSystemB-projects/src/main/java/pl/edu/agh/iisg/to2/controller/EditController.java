package pl.edu.agh.iisg.to2.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import employees.model.EmployeeForProjects;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import pl.edu.agh.iisg.to2.ProjectMain;
import pl.edu.agh.iisg.to2.model.GeneratedData;
import pl.edu.agh.iisg.to2.model.ITeam;
import pl.edu.agh.iisg.to2.model.MySQLAccess;
import pl.edu.agh.iisg.to2.model.ProjectMock;
import static java.lang.Math.toIntExact;


public class EditController {

	@FXML private DatePicker deadlineDatePicker;
	@FXML private DatePicker startdateDatePicker;
	@FXML private TextField employeesTextField;
	@FXML private TextField teamsTextField;
	@FXML private TextField budgetTextField;
	@FXML private TextField calculatedCost;
	
	@FXML private Button editEmployeesButton;
	@FXML private Button editTeamsButton;
	@FXML private Button cancelButton;
	@FXML private Button okButton;
	
	@FXML private Label errorId;
	@FXML private Label errorDate;
	@FXML private Label errorTeams;
	@FXML private Label errorEmployees;
	@FXML private Label errorBudget;

	private Stage dialogStage;
	private ProjectMock projectEdit;

	private LocalDateStringConverter converter;
	private DateTimeFormatter formatter;
	
	private ObservableList<ITeam> teams;
	private ObservableList<EmployeeForProjects> employees;
	private ObservableList<ProjectMock> projects;
	
	private GeneratedData d;
	
	@FXML
	public void initialize() {
		String pattern = "yyyy-MM-dd";
		formatter = DateTimeFormatter.ofPattern(pattern);
		setConverter(new LocalDateStringConverter(formatter, formatter));
	}
	
	public String toStringDate(LocalDate date) {
        if (date != null) {
            return formatter.format(date);
        } else {
            return "";
        }
    }
	
	public LocalDate fromStringDate(String string) {
         if (string != null && !string.isEmpty()) {
             return LocalDate.parse(string, formatter);
         } else {
             return null;
         }
     }


	public void setData(ObservableList<ProjectMock> projects, ProjectMock project, GeneratedData d) {
		this.projectEdit = project;
		this.d = d;
		this.teams = d.getTeams();
		this.employees = d.getEmployees();
		this.projects = projects;
		updateControls();
	}

	@FXML
	private void handleOkAction(ActionEvent event) {
		updateModel();
		if (isInputValid()){
			this.projects.add(projectEdit);
			//setData(this.projectEdit, this.d);
			Stage stage = (Stage) cancelButton.getScene().getWindow();
			stage.close();
		}else{
			
		}
	}
	
	@FXML
	private void handleCancelAction(ActionEvent event) {
		dialogStage.close();
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
			controllerAddEmployee.setData(this.projectEdit, this.employees, 0);
			
            stageAddEmployee.showAndWait();
            System.out.println("Refreshing...");
    		String s2 = projectEdit.getStringEmployeesForProject().getValue();
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
			controllerAddTeam.setData(this.projectEdit, this.teams,0);
			
            stageAddTeams.showAndWait();
            System.out.println("Refreshing...");
            String s1 = projectEdit.getStringTeamsForProject().getValue();
    		teamsTextField.setText(s1);
            //projectTable.refresh(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	

	private boolean isInputValid() {/*
		if (projectEdit.getDeadline() == null || projectEdit.getDeadline().getValue() == null || projectEdit.getStartdate() == null || projectEdit.getStartdate().getValue() == null) {
			errorDate.setText("ERROR! Deadline or startdate is empty!");
			errorDate.setVisible(true);
			return false;
		}
		if (!(projectEdit.getStartdate().getValue()).isBefore(projectEdit.getDeadline().getValue())) {

			errorDate.setVisible(true);
			return false;
		}
		DecimalFormat decimalFormatter = new DecimalFormat();
		decimalFormatter.setParseBigDecimal(true);
		if (projectEdit.getBudget() != null){
				if (projectEdit.calculateBudget() > projectEdit.getBudget().getValue().intValueExact() ){
					errorBudget.setTextFill(Color.RED);
					errorBudget.setText("ERROR! Not enough money for project!");
					errorBudget.setVisible(true);
					return false;
				}	
		}else{
			errorBudget.setText("ERROR! Budget musn't be empty!");
			errorBudget.setVisible(true);
			return false;
		}
		if (projectEdit.getEmployees() == null){
			errorEmployees.setText("ERROR! You have to choose employees!");
			errorEmployees.setVisible(true);
			return false;
		}
		if (projectEdit.getTeams() == null){
			errorTeams.setText("ERROR! You have to choose teams!");
			errorTeams.setVisible(true);
			return false;
		}*/
		return true;
	}

	private void updateModel() {
		if (deadlineDatePicker.getValue() != null){
			projectEdit.setDeadline(new SimpleObjectProperty<LocalDate>(deadlineDatePicker.getValue()));
		}
		if (startdateDatePicker.getValue() != null){
			projectEdit.setStartdate(new SimpleObjectProperty<LocalDate>(startdateDatePicker.getValue()));
		}
		
		if (!(budgetTextField.getText().isEmpty())){
			DecimalFormat decimalFormatter = new DecimalFormat();
			decimalFormatter.setParseBigDecimal(true);
			try {
				SimpleObjectProperty<BigDecimal> bTmp =  new SimpleObjectProperty<BigDecimal>(((BigDecimal) decimalFormatter.parse(budgetTextField.getText())));
				projectEdit.setBudget(bTmp);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (!(teamsTextField.getText().isEmpty())){
			ObservableList<ITeam> ttmp = FXCollections.observableArrayList();
			ttmp.addAll(FindTeams.setTeamsFromString(teamsTextField.getText(), teams));
			projectEdit.setTeams(ttmp);
			
		}
		if (!(employeesTextField.getText().isEmpty())){
			ObservableList<EmployeeForProjects> etmp = FXCollections.observableArrayList();
			etmp.addAll(FindEmployees.setEmployeesFromString(employeesTextField.getText(), employees));
			projectEdit.setEmployees(etmp);
		}
		
		saveDatabase();
	}
	
	private void saveDatabase() {
		MySQLAccess dao = new MySQLAccess();
	    try {
			dao.updateProject(projectEdit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateControls() {
		deadlineDatePicker.setValue(projectEdit.getDeadline().getValue());
		startdateDatePicker.setValue(projectEdit.getStartdate().getValue());
		String s1 = projectEdit.getStringTeamsForProject().getValue();
		String s2 = projectEdit.getStringEmployeesForProject().getValue();
		employeesTextField.setText(s2);
		teamsTextField.setText(s1);
		budgetTextField.setText(projectEdit.getBudget().getValue().toString());	
	}


	public ProjectMock getProjectEdit() {
		return projectEdit;
	}

	public void setProjectEdit(ProjectMock projectEdit) {
		this.projectEdit = projectEdit;
	}
	
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public LocalDateStringConverter getConverter() {
		return converter;
	}

	public void setConverter(LocalDateStringConverter converter) {
		this.converter = converter;
	}
	
	@FXML
	private void handleCalculateAction(ActionEvent event) {
		BigDecimal budget = new BigDecimal(0);
		int finalBudget = 0;
		ObjectProperty<LocalDate> deadline =  new SimpleObjectProperty<LocalDate>(deadlineDatePicker.getValue());
		ObjectProperty<LocalDate> startdate =  new SimpleObjectProperty<LocalDate>(startdateDatePicker.getValue());
		long days = ChronoUnit.DAYS.between(deadline.getValue(), startdate.getValue());
		int daysInt = toIntExact(days);
		ObservableList<ITeam> ttmp = FXCollections.observableArrayList();
		ttmp.addAll(FindTeams.setTeamsFromString(teamsTextField.getText(), teams));
		ObservableList<EmployeeForProjects> etmp = FXCollections.observableArrayList();
		etmp.addAll(FindEmployees.setEmployeesFromString(employeesTextField.getText(), employees));
		for (EmployeeForProjects e: etmp ) finalBudget = finalBudget +  e.getSalary().getValue();
		for (ITeam t: ttmp) budget = budget.add(t.getCostOfTeam());
		budget = budget.multiply(new BigDecimal(daysInt*8)); 
		finalBudget = finalBudget*daysInt*8;
		int tmp = budget.intValueExact() + finalBudget;
		calculatedCost.setText(Integer.toString(tmp));	
	}

}
