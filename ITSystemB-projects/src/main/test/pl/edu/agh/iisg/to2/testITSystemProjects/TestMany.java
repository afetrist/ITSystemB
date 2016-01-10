package pl.edu.agh.iisg.to2.testITSystemProjects;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import pl.edu.agh.iisg.to2.controller.ListController;
import pl.edu.agh.iisg.to2.controller.ProjectController;
import pl.edu.agh.iisg.to2.common.ProjectMock;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class TestMany  {
	
	
	 @org.junit.Test
	 public void testConversion() {
		 String date = "2016-01-07";
		 LocalDate dateNow = LocalDate.now();
		 ListController ltmp = new ListController();
		 Assert.assertEquals(ltmp.fromStringDate(date), dateNow);
	 }
	 
	 
}