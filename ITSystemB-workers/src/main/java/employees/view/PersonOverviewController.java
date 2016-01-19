package employees.view;

import employees.EmployeesRoot;
import employees.mockExteriorClasses.Project;
import employees.mockExteriorClasses.Team;
import employees.model.Payment;

import employees.model.Person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
//import pl.edu.agh.application.MainApplication;
import pl.edu.agh.iisg.to2.common.IProjectForEmployees;

import java.awt.event.ActionEvent;
import java.lang.reflect.Array;
import java.util.*;
import java.sql.*;


public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private TableColumn<Person, String> positionColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label positionLabel;
    @FXML
    private Label salaryLabel;
    @FXML
    private Label employedLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Label peselLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField filtringData;
    @FXML
    private Button findButton;

    @FXML
    private CheckBox fullWordsCheckBox;
    @FXML
    private CheckBox allWordsCheckBox;

    @FXML
    private TableView<Payment> salaryHistoryTable;
    @FXML
    private TableColumn<Payment, String> salaryColumn;
    @FXML
    private TableColumn<Payment, String> endDateColumn;

    @FXML
    private TableView<Team> teamsHistoryTable;
    @FXML
    private TableColumn<Team, String> teamNameColumn;
    @FXML
    private TableColumn<Team, String> teamLeaderColumn;

    @FXML
    private TableView<IProjectForEmployees> projectsHistoryTable;
    @FXML
    private TableColumn<IProjectForEmployees, String> projectIDColumn;
    @FXML
    private TableColumn<IProjectForEmployees, String> projectStartDateColumn;
    @FXML
    private TableColumn<IProjectForEmployees, String> projectDeadlineColumn;
    @FXML
    private TableColumn<IProjectForEmployees, String> projectBudgetColumn;


    // Reference to the main application.
    //private MainApplication mainApplication;
    private EmployeesRoot employeesRoot;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PersonOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().lastNameProperty());
        positionColumn.setCellValueFactory(
                cellData -> cellData.getValue().positionProperty());

        // Clear person details.
        showPersonDetails(null);

        fullWordsCheckBox.setSelected(false);
        allWordsCheckBox.setSelected(false);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApplication
     */
    public void setEmployeesRoot(EmployeesRoot employeesRoot) {
        this.employeesRoot = employeesRoot;

        // Add observable list data to the table
        personTable.setItems(filterPeople());
    }

    private void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            positionLabel.setText(person.getPosition());
            salaryLabel.setText(Integer.toString(person.getSalary()));
            employedLabel.setText(person.getDate());
            emailLabel.setText(person.getEmail());
            phoneNumberLabel.setText(person.getPhone());
            peselLabel.setText(person.getPesel());

            imageView.setImage(new Image(person.getUrl()));

            salaryColumn.setCellValueFactory(cellData -> cellData.getValue().salaryProperty());
            endDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateToProperty());
            salaryHistoryTable.setItems(person.getSalaryHistory());

            person.loadTeams();
            teamNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            teamLeaderColumn.setCellValueFactory(cellData -> cellData.getValue().leaderProperty());
            teamsHistoryTable.setItems(person.getTeamsJoined());

            //HERE
            //ObservableList<IProjectForEmployees> projects = employeesRoot.
            //		getProjectSupplier().findAllProjectsForEmployeeId(new Long(person.getId()).toString());
            ObservableList<IProjectForEmployees> projects = employeesRoot.
            		getProjectSupplier().getProjectForEmployee(new Long(person.getId()).toString());
            projectIDColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
            projectsHistoryTable.setItems(projects);
            
            //OLD
            //person.loadProjects();
            //projectTopicColumn.setCellValueFactory(cellData -> cellData.getValue().topicProperty());
            //projectLeaderColumn.setCellValueFactory(cellData -> cellData.getValue().leaderProperty());
            //projectsHistoryTable.setItems(person.getProjectsParticipated());


        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            positionLabel.setText("");
            salaryLabel.setText("");
            employedLabel.setText("");
            emailLabel.setText("");
            phoneNumberLabel.setText("");
            peselLabel.setText("");

            imageView.setImage(new Image("https://pbs.twimg.com/profile_images/425274582581264384/X3QXBN8C.jpeg"));
        }
    }
    
    
    
    private ObservableList<Person> filterPeople(){
        ObservableList<Person> myResult = FXCollections.observableArrayList();
        ObservableList<Person> originalList = employeesRoot.getPersonData();
        HashMap<Person, Integer> personMap = new HashMap<>();

        if(filtringData.getText().length() > 0) {

            for (Person person : originalList){
                int fits = howManyFits(person);

                if(!allWordsCheckBox.isSelected()){
                    if (fits > 0)
                        personMap.put(person, new Integer(fits));
                }
                else {
                    if(fits == filtringData.getText().split(" ").length)
                        myResult.add(person);
                }
            }
            if(!allWordsCheckBox.isSelected()) {
                LinkedHashMap<Person, Integer> sortedMap = sortHashMap(personMap);
                for(Person person : sortedMap.keySet())
                    myResult.add(person);
            }
        }
        else {
            myResult = originalList;
        }

        return myResult;
    }

    private LinkedHashMap<Person, Integer> sortHashMap(HashMap<Person, Integer> oldHashMap){
        Collection<Integer> values = oldHashMap.values();
        LinkedHashMap<Person, Integer> newHashMap = new LinkedHashMap<Person, Integer>();
        int max;
        Person temporaryPerson = null;


        while(!oldHashMap.isEmpty()){
            max = 0;
            for(Integer i : values){
                if(i.intValue() > max) {
                    max = i.intValue();
                }
            }

            for(Person person : oldHashMap.keySet())
                if(oldHashMap.get(person).equals(new Integer(max)))
                    temporaryPerson = person;

            newHashMap.put(temporaryPerson, new Integer(max));
            oldHashMap.remove(temporaryPerson);
        }
        return newHashMap;
    }

    private int howManyFits(Person person){
        int result = 0;
        String[] keys = filtringData.getText().split(" ");

        if(fullWordsCheckBox.isSelected()){
            for(String key : keys) {
                if(person.getFirstName().equals(key)){
                    result++;
                }
                if(person.getLastName().equals(key)){
                    result++;
                }
            }
        }
        else{
            for(String key : keys) {
                if(person.getFirstName().contains(key)){
                    result++;
                }
                if(person.getLastName().contains(key)) {
                    result++;
                }
            }
        }
        return result;
    }
    


    @FXML
    private void handleFilterPeople(){
        personTable.setItems(filterPeople());
    }

    @FXML
    private void handleDeletePerson(){
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if(selectedPerson != null){
            boolean okClicked = employeesRoot.showPersonDelete(selectedPerson);
            if(okClicked){
            	//deletePersonFromDB(selectedPerson);		//needs DB
            	employeesRoot.deletePerson(selectedPerson);
            }
        }
        personTable.setItems(filterPeople());

    }
	
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson() {
        Person newPerson = new Person(true);
        boolean okClicked = employeesRoot.showPersonEditDialog(newPerson);
        if (okClicked) {
        	employeesRoot.getPersonData().add(newPerson);
            //addNewPersonToDB(newPerson);			//needs DB
        }
    }
	
    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = employeesRoot.showPersonEditDialog(selectedPerson);
            if (okClicked) {
            	//savePersonToDB(selectedPerson);		//needs DB
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(employeesRoot.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
    
    private void savePersonToDB(Person sp){
    	String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    	String DB_URL = "jdbc:mysql://localhost/employeesdb";
    	
    	String USER = "root";
    	String PASS = "";
    	
    	Connection conn = null;
    	Statement stmt = null;
    	
    	try{
    		try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		conn = DriverManager.getConnection(DB_URL,USER, PASS);
    		stmt = conn.createStatement();
    		
    		String sql;
        	sql = String.format("UPDATE employees SET " + 
        			"name = '%s', surname = '%s', position = '%s', " + 
        			"salary = %d, employdate = '%s', email = '%s', " +
        			"phone = '%s', pesel = '%s', url = '%s' " + 
        			"WHERE id = %d", sp.getFirstName(), sp.getLastName(),
        			sp.getPosition(), sp.getSalary(), sp.getDate(),
        			sp.getEmail(), sp.getPhone(), sp.getPesel(), sp.getUrl(), sp.getId());
    		
        	stmt.executeUpdate(sql);    		
    		
    	}catch(SQLException se){
    		se.printStackTrace();
    	}finally{
    		try{
    			if(stmt!=null)
    				conn.close();
    		}catch(SQLException se2){
    		}
    		try{
    			if(conn!=null)
    				conn.close();
    		}catch(SQLException se3){
    			se3.printStackTrace();
    		}
    	}
    	
    }
    
    private void addNewPersonToDB(Person np){
    	String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    	String DB_URL = "jdbc:mysql://localhost/employeesdb";
    	
    	String USER = "root";
    	String PASS = "";
    	
    	Connection conn = null;
    	Statement stmt = null;
    	
    	try{
    		try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		conn = DriverManager.getConnection(DB_URL,USER, PASS);
    		stmt = conn.createStatement();
    		
    		String sql;
        	sql = String.format("INSERT INTO employees VALUES (" + 
        			"%d, '%s', '%s', '%s', " + 
        			"%d, '%s', '%s', " +
        			"'%s', '%s', '%s')",
        			np.getId(),	np.getFirstName(), np.getLastName(),
        			np.getPosition(), np.getSalary(), np.getDate(),
        			np.getEmail(), np.getPhone(), np.getPesel(), np.getUrl());
    		
        	stmt.executeUpdate(sql);    		
    		
    	}catch(SQLException se){
    		se.printStackTrace();
    	}finally{
    		try{
    			if(stmt!=null)
    				conn.close();
    		}catch(SQLException se2){
    		}
    		try{
    			if(conn!=null)
    				conn.close();
    		}catch(SQLException se3){
    			se3.printStackTrace();
    		}
    	}	
    }
    
    private void deletePersonFromDB(Person p){
    	String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    	String DB_URL = "jdbc:mysql://localhost/employeesdb";
    	
    	String USER = "root";
    	String PASS = "";
    	
    	Connection conn = null;
    	Statement stmt = null;
    	
    	try{
    		try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		conn = DriverManager.getConnection(DB_URL,USER, PASS);
    		stmt = conn.createStatement();
    		
    		String sql;
        	sql = String.format("DELETE FROM employees " + 
        			"WHERE id = %d", p.getId());
    		
        	stmt.executeUpdate(sql);    		
    		
    	}catch(SQLException se){
    		se.printStackTrace();
    	}finally{
    		try{
    			if(stmt!=null)
    				conn.close();
    		}catch(SQLException se2){
    		}
    		try{
    			if(conn!=null)
    				conn.close();
    		}catch(SQLException se3){
    			se3.printStackTrace();
    		}
    	}
    	
    }
}