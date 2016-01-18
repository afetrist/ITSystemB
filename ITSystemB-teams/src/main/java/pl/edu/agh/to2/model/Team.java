package pl.edu.agh.to2.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pl.edu.agh.to2.common.ITeam;

public class Team implements ITeam {
	private long id;
	private StringProperty teamName;
	private Date dateOfCreation;
	private ObjectProperty<Member> supervisor;
	private List<Member> members;
	
	public Team(long id, String teamName, Member leader, Date dateOfCreation) {
		this.id = id;
		this.teamName = new SimpleStringProperty(teamName);
		this.supervisor = new SimpleObjectProperty<Member>(leader);
		this.dateOfCreation = dateOfCreation;
	}

	public int getId() {
		return (int) id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public void setDateOfCreation(Date date){
		this.dateOfCreation=date;
	}
	public Date getDateOfCreation() {
		return dateOfCreation;
	}

	public void setLeader(ObjectProperty<Member> leader) {
		this.supervisor = leader;
	}

	public StringProperty getTeamNameProperty() {
		return teamName;
	}

	public String getTeamName() {
		return teamName.get();
	}

	public void setTeamName(String name) {
		this.teamName.set(name);
	}

	public ObjectProperty<Member> getSupervisorProperty() {
		return supervisor;
	}
	
	public Member getSupervisor() {
		return supervisor.get();
	}

	public void setSupervisor(Member supervisor) {
		this.supervisor.set(supervisor);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Team " + teamName.get() +", " + supervisor.get() + "\n");
		for (Member member : members) {
			stringBuilder.append(member + "\n");
		}
		return stringBuilder.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public BigDecimal getCostOfTeam() {
		BigDecimal total = new BigDecimal(0);
		
		for (Member member : members) {
			total.add(member.getWorker().getPayment());
		}
		
		if (supervisor != null) {
			total.add(supervisor.get().getWorker().getPayment());
		}
		return null;
	}

	@Override
	public String getNameOfTeam() {
		return getTeamName();
	}

	@Override
	public ObjectProperty<BigDecimal> getCostOfTeamObservable() {
		return new SimpleObjectProperty<BigDecimal>(getCostOfTeam());
	}

	@Override
	public StringProperty getNameofTeamObservable() {
		return getTeamNameProperty();
	}

}