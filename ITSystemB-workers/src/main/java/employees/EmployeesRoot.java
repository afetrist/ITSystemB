package employees;

import java.io.IOException;

import employees.model.*;
import employees.view.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.*;

import application.MainApplication;

public class EmployeesRoot {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private ObservableList<Person> personData = FXCollections.observableArrayList();
	
	public EmployeesRoot(Stage primaryStage, BorderPane rootLayout){
		this.primaryStage = primaryStage;
		this.rootLayout = rootLayout;
		
		//injectDataBase();		//While DB is available
		injectMockPeople();
	}
	
	public ObservableList<Person> getPersonData() {
        return personData;
    }
	
	private void injectMockPeople(){
		personData.add(new Person("Geralt", "Z Rivii", "Witcher master", 10000, "24.12.665", "iamthewitcher@witchers.com", "+48 600 700 800", "00000000000", "http://streemo.pl/Image/538862_m.jpg"));
        personData.add(new Person("Bertrand", "Tauler"));
        personData.add(new Person("Dermot", "Marranga"));
        personData.add(new Person("Ivar", "Zle Oko"));
        personData.add(new Person("Hemminks", "Blada Twarz"));
        personData.add(new Person("Stary", "Wiedzmin"));
        personData.add(new Person("Gaetan", "Zia"));
        personData.add(new Person("Kruk", "TheBest"));
        personData.add(new Person("Leo", "Messi"));
        personData.add(new Person("Sorel", "Sample"));
        personData.add(new Person("Geraltinnio", "Rodriquez"));
	}
	
	private void injectDataBase(){
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
			
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			stmt = conn.createStatement();
			String sql = "SELECT * FROM employees";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				Long id = rs.getLong("id");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String position = rs.getString("position");
				int salary = rs.getInt("salary");
				String employedate = rs.getString("employdate");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				String pesel = rs.getString("pesel");
				String url = rs.getString("url");
				
				personData.add(new Person(id, name, surname, position, salary, employedate, email, phone, pesel, url));
			}
			
			rs.close();
			stmt.close();
			conn.close();
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}
	
	public boolean showPersonDelete(Person person){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/employees/view/PersonDelete.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Delete Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			PersonDeleteController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(person);
			
			dialogStage.showAndWait();
			
			return controller.isOkClicked();
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void deletePerson(Person person){
		personData.remove(person);
	}
	
    public boolean showPersonEditDialog(Person person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("/employees/view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
