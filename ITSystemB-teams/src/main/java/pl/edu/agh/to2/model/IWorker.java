package pl.edu.agh.to2.model;
import java.math.BigDecimal;

import javafx.beans.property.StringProperty;

public interface IWorker {
	
	public int getId();

	public String getFirstName();
	
	public StringProperty getFirstNameProperty();

	public String getLastName();
	
	public StringProperty getLastNameProperty();
	
	public String getFullName();
	
	public String getPesel();
	
	public BigDecimal getPayment();
}