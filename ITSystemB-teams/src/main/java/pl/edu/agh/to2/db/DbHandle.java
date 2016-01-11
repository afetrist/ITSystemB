package pl.edu.agh.to2.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.edu.agh.to2.Member;
import pl.edu.agh.to2.Team;
import pl.edu.agh.to2.model.IWorker;
import pl.edu.agh.to2.model.IWorkerProvider;
import pl.edu.agh.to2.model.SimpleWorkerProvider;

public class DbHandle {
	private Connection connection;
	private Statement statement;
	private ResultSet rs = null;
	private IWorkerProvider workerProvider = new SimpleWorkerProvider();

	public List<Team> loadTeams() {
		String query = "SELECT * FROM Team";
		List<Team> list = new ArrayList<Team>();
		Team team = null;
		ResultSet rs = null;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) {
				team = new Team(0, "", null, null);
				team.setId(rs.getInt("idTeam"));
				team.setTeamName(rs.getString("name"));
				team.setDateOfCreation(rs.getDate("dateOfCreation"));
				if (rs.getString("idSupervisor") != null) {
					Member supervisor = loadMemberById(rs.getInt("idSupervisor"));
					team.setSupervisor(supervisor);
					supervisor.setSupervisedTeam(team);
				}
				list.add(team);
			}
		} catch (SQLException e) {
			System.out.println("<db_log>: ERROR while loading teams from database!\n" + e);

		} finally {
			DbUtil.close(rs);
			DbUtil.close(statement);
			DbUtil.close(connection);
		}
		return list;
	}

	public List<IWorker> loadUnasignedWorkers(String[] criteria) {
		String query = "SELECT idWorker FROM Member";
		List<IWorker> workers = new ArrayList<>();
		List<Integer> assignedWorkers = new ArrayList<>();
		ResultSet result = null;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			while (result.next()) {
				assignedWorkers.add(result.getInt("idWorker"));
			}
			workers = workerProvider.loadWorkers(assignedWorkers, false);
		} catch (SQLException e) {
			System.out.println("<db_log>: ERROR while loading workers from database!\n" + e);
		} finally {
			DbUtil.close(rs);
			DbUtil.close(statement);
			DbUtil.close(connection);
		}

		return workers;
	}

	public boolean updateTeam(Team team) {
		String newName = team.getTeamName();
		int id = (int) team.getId();
		int resultId;
		List<Member> members = team.getMembers();
		String exists = "SELECT COUNT(*) FROM Team WHERE IdTeam=" + id;
		String update = "UPDATE Team SET name='" + newName + "' WHERE idTeam =" + id;
		String supervisorId;

		if (team.getSupervisor() != null) {
			persistMembers(Arrays.asList(team.getSupervisor()));
			supervisorId = Long.toString(team.getSupervisor().getId());

		} else {
			supervisorId = "null";
		}
		
		String add = "INSERT INTO Team (name, dateOfCreation, idSupervisor) VALUES('" + newName + "',NOW()," + supervisorId + ")";
		System.out.println(add);
		int updateCount = 0;
		
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(exists);
			rs.next();
			
			if (rs.getInt(1) == 0) {
				PreparedStatement pstmt = connection.prepareStatement(add, Statement.RETURN_GENERATED_KEYS);  
				pstmt.executeUpdate();  
				ResultSet keys = pstmt.getGeneratedKeys();    
				keys.next();  
				resultId = keys.getInt(1);
				team.setId(resultId);
				updateCount = 1;
				System.out.println("<db_log>: team " + newName + " with id: " + resultId + " added to database");
			} else {
				updateCount = statement.executeUpdate(update);
				System.out.println("<db_log>: team " + newName + " with id: " + id + " updated in database");
			}
		} catch (SQLException e) {
			System.out.println("<db_log>: ERROR while updating team!");
			e.printStackTrace();
		} finally {
			DbUtil.close(statement);
			DbUtil.close(connection);
		}
		
		persistMembers(members);
		persistMembersInTeamRelation(team, members);
		
		if (updateCount == 1)
			return true;
		return false;
	}
	
	public boolean loadMember(Member member) {
		return true;
	}
	
	private Member loadMemberById(int id) {
		String query = "SELECT * FROM Member WHERE idMember=" + id;
		Member supervisor = null;
		ResultSet rs = null;

		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) {
				List<IWorker> workers = workerProvider.loadWorkers(Arrays.asList(rs.getInt("idWorker")), true);
				supervisor = new Member(id, workers.get(0), rs.getString("role"), null);
			}
		} catch (SQLException e) {
			System.out.println("<db_log>: ERROR while loading teams from database!\n" + e);
		} finally {
			DbUtil.close(rs);
			DbUtil.close(statement);
			DbUtil.close(connection);
		}
		return supervisor;
	}
	
	private boolean persistMembers(List<Member> members) {
		String addQuery;
		int resultId;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.createStatement();
			System.out.println("---------------");

			for (Member member : members) {
				addQuery = "INSERT INTO Member (role, idWorker) VALUES('" + "none" + "'," + member.getWorker().getId() + ")";
				PreparedStatement pstmt = connection.prepareStatement(addQuery, Statement.RETURN_GENERATED_KEYS);  
				pstmt.executeUpdate();  
				ResultSet keys = pstmt.getGeneratedKeys();    
				keys.next();  
				resultId = keys.getInt(1);
				member.setId(resultId);
				System.out.println(member.toStringDebugg());
			}
			
		} catch (SQLException e) {
			System.out.println("<db_log>: ERROR while persisting members!\n" + e);
		} finally {
			DbUtil.close(rs);
			DbUtil.close(statement);
			DbUtil.close(connection);
		}
		
		System.out.println("<db_log>: members updated");
		return true;
	}
	
	private boolean persistMembersInTeamRelation(Team team, List<Member> members) {
		String addQuery;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.createStatement();
			
			for (Member member : members) {
				addQuery = "INSERT INTO TeamMembers (idTeam, idMember) VALUES(" + team.getId() + "," + member.getId() + ")";
				statement.executeQuery(addQuery);
			}
		} catch (SQLException e) {
			System.out.println("<db_log>: ERROR while persisting member-team relation!\n" + e);
		} finally {
			DbUtil.close(rs);
			DbUtil.close(statement);
			DbUtil.close(connection);
		}
		
		System.out.println("<db_log>: members updated");
		return true;
	}
}
