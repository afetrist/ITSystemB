package pl.edu.agh.to2.model;

import pl.edu.agh.to2.common.IWorker;

public class Member {
	private long id;
	private IWorker worker;
	private String role;
	private Team supervisedTeam;

	public Member(IWorker worker) {
		this.worker = worker;
	}

	public Member(IWorker worker, Team supervisedTeam) {
		this.id = -1;
		this.worker = worker;
		this.supervisedTeam = supervisedTeam;
	}
	
	public Member(int id, IWorker worker, String role, Team supervisedTeam) {
		this.id = id;
		this.role = role;
		this.worker = worker;
		this.supervisedTeam = supervisedTeam;
	}

	public Team getSupervisedTeam() {
		return supervisedTeam;
	}

	public void setSupervisedTeam(Team supervisedTeam) {
		this.supervisedTeam = supervisedTeam;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public IWorker getWorker() {
		return worker;
	}

	public boolean isSupervisor() {
		return (supervisedTeam != null);
	}

	@Override
	public String toString() {
		return this.worker.getFullName();
	}
	
	public String toStringDebugg() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getId() + ", " + this.worker.getFullName());
		if (this.isSupervisor()) {
			sb.append(" - (" + this.getSupervisedTeam().getId() + "), " + this.getSupervisedTeam().getTeamName() + "\n");
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		if (worker == null) {
			if (other.worker != null)
				return false;
		} else if (!worker.equals(other.worker))
			return false;
		return true;
	}
}