package pl.edu.agh.to2.model;

import java.math.BigDecimal;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimpleWorker implements IWorker{

	private int id;
	private StringProperty firstNameProperty;
	private StringProperty lastNameProperty;
	private String pesel;
	private BigDecimal payment;
	
	public SimpleWorker(int id, String firstName, String lastName, String pesel, BigDecimal payment) {
		this.id = id;
		this.firstNameProperty = new SimpleStringProperty(firstName);
		this.lastNameProperty = new SimpleStringProperty(lastName);
		this.pesel = pesel;
		this.payment = payment;
	}
	
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public StringProperty getFirstNameProperty() {
		return firstNameProperty;
	}

	@Override
	public StringProperty getLastNameProperty() {
		return lastNameProperty;
	}

	@Override
	public String getFullName() {
		return firstNameProperty.get() + " " + lastNameProperty.get();
	}

	@Override
	public String getPesel() {
		return pesel;
	}

	@Override
	public BigDecimal getPayment() {
		return payment;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleWorker other = (SimpleWorker) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String getFirstName() {
		return firstNameProperty.get();
	}

	@Override
	public String getLastName() {
		return lastNameProperty.get();
	}


	@Override
	public String toString() {
		return "SimpleWorker [id=" + id + "]";
	}
	
	
}
