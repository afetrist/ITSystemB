package pl.edu.agh.iisg.to2.model;

import java.math.BigDecimal;
import java.math.BigInteger;
//Adapted from http://www.vogella.com/tutorials/MySQLJava/article.html
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.iEmployeeForProjects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import pl.edu.agh.iisg.to2.model.Project;
import pl.edu.agh.to2.common.ITeam;
import pl.edu.agh.to2.common.TeamMock;
import pl.edu.agh.iisg.to2.model.IProject;

public class MySQLAccess {
     
private Connection connect = null;
private Statement statement = null;
private ResultSet resultSet = null;

final private String host = "localhost";
final private String user = "root";
final private String passwd = "";

public void connect() throws ClassNotFoundException, SQLException {
	
	   Class.forName("com.mysql.jdbc.Driver");
	   
	   connect = DriverManager
	       .getConnection("jdbc:mysql://" + host + "/TOProjects?"
	           + "user=" + user + "&password=" + passwd );
	   statement = connect.createStatement();
	   // Result set get the result of the SQL query
	   //statement.executeQuery("TRUNCATE TABLE IProject_Team");
	   //statement.executeQuery("TRUNCATE TABLE IProject_Employee");
	   //statement.executeQuery("TRUNCATE TABLE IProject");
	   //statement.executeQuery("DELETE from IProject");
	   
}

public void readDataBase() throws Exception {
 try {
   connect();

   // Statements allow to issue SQL queries to the database
   statement = connect.createStatement();
   resultSet = statement
       .executeQuery("select * from IProject");
   writeResultSet(resultSet);

   
 } catch (Exception e) {
   throw e;
 } finally {
   close();
 }

}

public ArrayList<ITeam> fetchTeamsObjectsForProject(String projectId) throws SQLException, ClassNotFoundException
{
	/*Statement fetchStatement = null;
	ResultSet teamSet = null;
	
	fetchStatement = connect.createStatement();
	teamSet = fetchStatement.executeQuery("SELECT * FROM IProject_Team WHERE projectId="
			+ "\"" + projectId + "\"");
	*/
	ArrayList<ITeam> teamsList = new ArrayList<ITeam>();/*
	while (teamSet.next()) {
		String teamId = teamSet.getString("teamId");
		//System.out.println("Fetch team with id " + teamId);
		
		// ===========================================================//
		// 				TODO: Fetch from other modules
		// TeamMock team = fetch team with id = teamId;
		//
		
		// ===========================================================//
		
		TeamMock team = new TeamMock(teamId);
		teamsList.addAll(DataGenerator.generateTeams(5));
		teamsList.add(team);
	}	
	*/
	return teamsList;
}

public List<iEmployeeForProjects> fetchEmployeeObjectsForProject(String projectId) throws SQLException, ClassNotFoundException
{
	/*Statement fetchStatement = null;
	ResultSet employeeSet = null;
	
	fetchStatement = connect.createStatement();
	employeeSet = fetchStatement.executeQuery("SELECT * FROM IProject_Employee WHERE projectId="
			+ "\"" + projectId + "\"");
*/
	List<iEmployeeForProjects> employeeList = new ArrayList<iEmployeeForProjects>();/*
	while (employeeSet.next()) {
		String employeeId = employeeSet.getString("employeeId");
		//System.out.println("Fetch emplotee with id " + employeeId);*/
		
		// ===========================================================//
		// 				TODO: Fetch from other modules
		// IEmployee employee = fetch employee with id = employeeId;
		//
		// ===========================================================//
		
		/*iEmployeeForProjects employee = new EmployeeForProjects( new SimpleLongProperty(Long.valueOf(1232)), new SimpleStringProperty("stas"), new SimpleStringProperty("test1"), new SimpleStringProperty("szef") , new SimpleIntegerProperty(100) , new SimpleStringProperty("223113"));
		employeeList.addAll(DataGenerator.generateEmployees(10));
		employeeList.add(employee);
	}	
	*/
	return employeeList;
}

public List<Project> fetchAllProjects() throws SQLException, ClassNotFoundException
{
	ArrayList<Project> projectsList = new ArrayList<Project>();
	
	 try {
		   connect();
		   statement = connect.createStatement();
		   resultSet = statement.executeQuery("SELECT * FROM IProject");

		   while (resultSet.next()) {
			   Date sDate = resultSet.getDate("startDate");
			   Date deadDate = resultSet.getDate("deadline");
			   int budget = resultSet.getInt("budget");
			   String projectId = resultSet.getString("projectId");
			   
			   LocalDate startDate = Instant.ofEpochMilli(sDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
			   LocalDate deadline = Instant.ofEpochMilli(deadDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

			   
			   List<ITeam> teams = this.fetchTeamsObjectsForProject(projectId);
			   List<iEmployeeForProjects> employees = this.fetchEmployeeObjectsForProject(projectId);
			   BigDecimal budgetDecimal = new BigDecimal(BigInteger.valueOf(budget));

			   Project p = new Project(deadline, startDate, teams, employees, budgetDecimal);
			   p.setId(projectId);
			  
			   projectsList.add(p);
		   }

		 } catch (Exception e) {
		   throw e;
		 } finally {
		   close();
		 }
	return projectsList;
}

public void insertProject(IProject project) throws SQLException, ClassNotFoundException
{
	connect();

	// Insert new IProject into database
	String command = "INSERT INTO TOProjects.IProject (projectId, deadline, startDate, budget) VALUES "
			+ "(\"" + project.getId() + "\", "
			+ "\"" + project.getDeadline().getValue() + "\", "
			+ "\"" + project.getStartdate().getValue() + "\", "
			+ project.getBudget().getValue() + ")";
	PreparedStatement preparedStatement = connect.prepareStatement(command);
	preparedStatement.executeUpdate();
	
	// Add relationships with Employee
	for (iEmployeeForProjects employee: project.getEmployees()){
		//System.out.println("Add relationship...");
		this.insertEmployeeRelationship(project, employee);
	} 
	// Add relationships with Team
	for (ITeam team: project.getTeams()){
		//System.out.println("Add team relationship...");
		this.insertTeamRelationship(project, team);
	} 
	
	close();
}




private void insertTeamRelationship(IProject project, ITeam team) throws SQLException, ClassNotFoundException
{/*
	String command = "INSERT INTO TOProjects.IProject_Team (teamId, projectId) VALUES "
			+ "(\"" + team.getId() + "\", "
			+ "\"" + project.getId() + "\")";
	//System.out.println(command);
	PreparedStatement preparedStatement = connect.prepareStatement(command);
	preparedStatement.executeUpdate();*/
}

private void removeAllTeamRelationships(IProject project) throws SQLException, ClassNotFoundException
{/*
	String command = "DELETE FROM TOProjects.IProject_Team WHERE projectId="
			+ "\"" + project.getId() + "\"";
	//System.out.println(command);
	PreparedStatement preparedStatement = connect.prepareStatement(command);
	preparedStatement.executeUpdate();*/
}

private void insertEmployeeRelationship(IProject project, iEmployeeForProjects employee) throws SQLException, ClassNotFoundException
{
	/*String command = "INSERT INTO TOProjects.IProject_Employee (employeeId, projectId) VALUES "
			+ "(\"" + employee.getId() + "\", "
			+ "\"" + project.getId() + "\")";
	//System.out.println(command);
	PreparedStatement preparedStatement = connect.prepareStatement(command);
	preparedStatement.executeUpdate();*/
}

private void removeAllEmployeeRelationships(IProject project) throws SQLException, ClassNotFoundException
{
	/*String command = "DELETE FROM TOProjects.IProject_Employee WHERE projectId="
			+ "\"" + project.getId() + "\"";
	//System.out.println(command);
	PreparedStatement preparedStatement = connect.prepareStatement(command);
	preparedStatement.executeUpdate();*/
}

public void updateProject(IProject project) throws SQLException, ClassNotFoundException
{
	connect();
	
	// Update IProject in database
	String command = "UPDATE TOProjects.IProject SET "
			+ "deadline=" + "\"" + project.getDeadline().getValue() + "\", "
			+ "startDate=" + "\"" + project.getStartdate().getValue() + "\", "
			+ "budget=" + project.getBudget().getValue()
			+ " WHERE projectId=" + "\"" + project.getId() + "\";";
	
	//System.out.println(command);
	
	PreparedStatement preparedStatement = connect.prepareStatement(command);
	preparedStatement.executeUpdate();
	System.out.println("DID EXECUTE");
	
	// Remove all Employee relationships
	this.removeAllEmployeeRelationships(project);
	this.removeAllTeamRelationships(project);
	
	// and recreate them once again
	for (iEmployeeForProjects employee: project.getEmployees()){
		this.insertEmployeeRelationship(project, employee);
	}
	// and recreate them once again
	for (ITeam team: project.getTeams()){
		this.insertTeamRelationship(project, team);
	}
	
	close();
}

private void writeMetaData(ResultSet resultSet) throws SQLException {
 //   Now get some metadata from the database
 // Result set get the result of the SQL query
 
 //System.out.println("The columns in the table are: ");
 
 //System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
 for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
   //System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
 }
}

private void writeResultSet(ResultSet resultSet) throws SQLException {
 // ResultSet is initially before the first data set
 while (resultSet.next()) {
   // It is possible to get the columns via name
   // also possible to get the columns via the column number
   // which starts at 1
   // e.g. resultSet.getSTring(2);
   Date startDate = resultSet.getDate("startDate");
   Date deadline = resultSet.getDate("deadline");
   int budget = resultSet.getInt("budget");
   
   //System.out.println("Start Date: " + startDate);
   //System.out.println("Deadline: " + deadline);
   //System.out.println("Budget: " + budget);
 }
}

// You need to close the resultSet
private void close() {
 try {
   if (resultSet != null) {
     resultSet.close();
   }

   if (statement != null) {
     statement.close();
   }

   if (connect != null) {
     connect.close();
   }
 } catch (Exception e) {

 }
}

}
