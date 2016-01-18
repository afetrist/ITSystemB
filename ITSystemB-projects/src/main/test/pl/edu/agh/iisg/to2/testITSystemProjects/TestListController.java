package pl.edu.agh.iisg.to2.testITSystemProjects;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import pl.edu.agh.iisg.to2.controller.ListController;
import pl.edu.agh.iisg.to2.controller.ProjectController;
import pl.edu.agh.iisg.to2.model.Project;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class TestListController extends Application {
	@Rule 
	public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	public Stage primaryStage;
	
    @BeforeClass
    public static void initJFX() {
        Thread t = new Thread("JavaFX Init Thread") {
            @Override
            public void run() {
                Application.launch(TestListController.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
    }
    
    @Test
    public void testListController() {
    	ProjectController projectController = new ProjectController(this.primaryStage);
    	projectController.initRootLayout();
    	ListController controller = projectController.getListController();
    	ObservableList<Project> projects = FXCollections.observableArrayList();
    	projects.add( new Project());
		controller.setDataToTest(projects);
		controller.setProjController(projectController);
    	assertFalse(controller.getProjects().isEmpty());
    }
}