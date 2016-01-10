package pl.edu.agh.iisg.to2.testITSystemProjects;
import javafx.application.Application;
import javafx.stage.Stage;
import pl.edu.agh.iisg.to2.controller.ProjectController;
import pl.edu.agh.iisg.to2.common.ProjectMock;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class TestProjectController extends Application {
	@Rule 
	public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	public Stage primaryStage;
	
    @BeforeClass
    public static void initJFX() {
        Thread t = new Thread("JavaFX Init Thread") {
            @Override
            public void run() {
                Application.launch(TestProjectController.class, new String[0]);
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
    public void testProjectController() {
    	ProjectController projectController = new ProjectController(this.primaryStage);
    	projectController.initRootLayout();
    	projectController.addProjects(new ProjectMock());
    	assertFalse(projectController.getProjects().isEmpty());
    }
}