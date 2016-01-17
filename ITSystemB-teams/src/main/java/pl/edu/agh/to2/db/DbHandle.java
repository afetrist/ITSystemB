package pl.edu.agh.to2.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.edu.agh.to2.common.IWorker;
import pl.edu.agh.to2.common.IWorkerProvider;
import pl.edu.agh.to2.model.Member;
import pl.edu.agh.to2.model.SimpleWorkerProvider;
import pl.edu.agh.to2.model.Team;

public class DbHandle {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs = null;
	private IWorkerProvider workerProvider = new SimpleWorkerProvider();

	public List<Team> loadTeams(String keyword) {
		String query = "SELECT * FROM Team WHERE name LIKE '%" + keyword + "%'";
		List<Team> list = new ArrayList<Team>();
		Team team = null;
		ResultSet rs = null;

		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
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
			DbUtil.close(stmt);
			DbUtil.close(conn);
		}
		return list;
	}

	public List<IWorker> loadUnasignedWorkers(String[] criteria) {
		String query = "SELECT idWorker FROM Member";
		List<IWorker> workers = new ArrayList<>();
		List<Integer> assignedWorkers = new ArrayList<>();
		ResultSet result = null;
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
			while (result.next()) {
				assignedWorkers.add(result.getInt("idWorker"));
			}
			workers = workerProvider.loadWorkers(assignedWorkers, false);
		} catch (SQLException e) {
			System.out.println("<db_log>: ERROR while loading workers from database!\n" + e);
		} finally {
			DbUtil.close(rs);
			DbUtil.close(stmt);
			DbUtil.close(conn);
		}

		return workers;
	}

	public boolean updateTeam(Team team) {
		String newName = team.getTeamName();
		Date newDate = team.getDateOfCreation();
		String supervisorIdString;
		int id = (int) team.getId();
		int resultId;
		boolean updated = false;
		List<Member> members = team.getMembers();

		if (team.getSupervisor() != null) {
			persistMembers(Arrays.asList(team.getSupervisor()));
			supervisorIdString = Long.toString(team.getSupervisor().getId());

		} else {
			supervisorIdString = "null";
		}

		String exists = "SELECT COUNT(*) FROM Team WHERE IdTeam=" + id;
		String update = "UPDATE Team SET name='" + newName + "', idSupervisor=" + supervisorIdString + " WHERE idTeam ="
				+ id;
		String add = "INSERT INTO Team (name, dateOfCreation, idSupervisor) VALUES('" + newName + "', ?, "
				+ supervisorIdString + ")";

		try {
			conn = ConnectionFactory.getConnection();
			PreparedStatement pstmtAdd = conn.prepareStatement(add, Statement.RETURN_GENERATED_KEYS);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(exists);
			rs.next();

			if (rs.getInt(1) == 0) {
				// Insert new team
				pstmtAdd.setDate(1, new java.sql.Date(newDate.getTime()));
				pstmtAdd.executeUpdate();
				ResultSet keys = pstmtAdd.getGeneratedKeys();
				keys.next();
				resultId = keys.getInt(1);
				team.setId(resultId);
				System.out.println("<db_log>: team " + newName + " with id: " + resultId + " added to database");
			} else {
				updated = true;
				stmt.executeUpdate(update);
				System.out.println("<db_log>: team " + newName + " with id: " + id + " updated in database");
			}
		} catch (SQLException e) {
			System.out.println("<db_log>: ERROR while updating team!");
			e.printStackTrace();
		} finally {
			DbUtil.close(stmt);
			DbUtil.close(conn);
		}

		persistMembers(members);
		persistMembersInTeamRelation(team, members);

		return updated;
	}

	public Member loadMemberByWorker(IWorker worker) {
		String query = "SELECT * FROM Member WHERE idWorker=" + worker.getId();
		Member member = null;
		ResultSet rs = null;

		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				member = new Member(rs.getInt("idMember"), worker, rs.getString("role"), null);
			}

		} catch (SQLException e) {
			System.out.println("<db_log>: ERROR while loading member(by worker) from database!\n" + e);
		} finally {
			DbUtil.close(rs);
			DbUtil.close(stmt);
			DbUtil.close(conn);
		}
		return member;
	}

	private Member loadMemberById(int id) {
		String query = "SELECT * FROM Member WHERE idMember=" + id;
		Member member = null;
		ResultSet rs = null;

		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				List<IWorker> workers = workerProvider.loadWorkers(Arrays.asList(rs.getInt("idWorker")), true);
				member = new Member(id, workers.get(0), rs.getString("role"), null);
			}
		} catch (SQLException e) {
			System.out.println("<db_log>: ERROR while loading member(by id) from database!\n" + e);
		} finally {
			DbUtil.close(rs);
			DbUtil.close(stmt);
			DbUtil.close(conn);
		}
		return member;
	}

	private boolean persistMembers(List<Member> members) {
		String existsQuery = "SELECT COUNT(*) FROM Member WHERE idWorker=?";
		String insertQuery = "INSERT INTO Member(role, idWorker) VALUES(?, ?)";
		String updateQuery = "UPDATE Member SET role=? WHERE idWorker=?";
		ResultSet rslt = null;
		ResultSet rsltId = null;
		int resultId;

		try {
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement pstmtExists = conn.prepareStatement(existsQuery);
			PreparedStatement pstmtInsert = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			PreparedStatement pstmsUpdate = conn.prepareStatement(updateQuery);

			for (Member member : members) {
				// Check if member for that worker already exists
				pstmtExists.setInt(1, member.getWorker().getId());
				rslt = pstmtExists.executeQuery();

				if (rslt.next()) {
					if (rslt.getInt(1) == 0) {
						// Insert new member entity
						pstmtInsert.setString(1, member.getRole());
						pstmtInsert.setInt(2, member.getWorker().getId());
						pstmtInsert.executeUpdate();
						rsltId = pstmtInsert.getGeneratedKeys();
						rsltId.next();
						resultId = rsltId.getInt(1);
						member.setId(resultId);
						System.out.println("<db_log>: INSERTED Member(" + member.getId() + ", "
								+ member.getWorker().getId() + ")");
					} else {
						// Update member entity
						pstmsUpdate.setString(1, member.getRole());
						pstmsUpdate.setInt(2, member.getWorker().getId());
						pstmsUpdate.executeUpdate();
						System.out.println(
								"<db_log>: UPDATED Member(" + member.getId() + ", " + member.getWorker().getId() + ")");
					}
				}
			}

		} catch (SQLException e) {
			System.out.println("<db_log>: ERROR while persisting members!\n" + e);
		} finally {
			DbUtil.close(rs);
			DbUtil.close(stmt);
			DbUtil.close(conn);
		}

		System.out.println("<db_log>: members updated");
		return true;
	}

	private boolean persistMembersInTeamRelation(Team team, List<Member> members) {
		String removeQuery = "DELETE FROM TeamMembers WHERE idTeam=?";
		String insertQuery = "INSERT TeamMembers(idTeam, idMember) VALUES(?, ?)";
		
		try {
			conn = ConnectionFactory.getConnection();
			PreparedStatement pstmtRemove = conn.prepareStatement(removeQuery);
			PreparedStatement pstmtInsert = conn.prepareStatement(insertQuery);
			
			pstmtRemove.setInt(1, (int)team.getId());
			pstmtRemove.executeUpdate();
			
			for (Member member : members) {
				pstmtInsert.setInt(1, (int)team.getId());
				pstmtInsert.setInt(2, (int)member.getId());
				pstmtInsert.executeUpdate();
			}
			
		} catch (SQLException e) {
			System.out.println("<db_log>: ERROR while persisting member-team relation!\n" + e);
		} finally {
			DbUtil.close(rs);
			DbUtil.close(stmt);
			DbUtil.close(conn);
		}

		return true;
	}
}
