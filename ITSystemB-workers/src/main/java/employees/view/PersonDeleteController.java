package employees.view;

import application.MainApplication;
import employees.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Created by afetrist on 08.12.15.
 */
public class PersonDeleteController {

    private MainApplication mainApplication;
    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelButton;

    @FXML
    private ImageView warningImage;

    public PersonDeleteController(){

    }

    @FXML
    private void initialize(){
        warningImage.setImage(new Image("https://cdn2.iconfinder.com/data/icons/security-2-1/512/user_delete-512.png"));
    }

    public void setPerson(Person person){
        this.person = person;
    }

    public boolean isOkClicked(){
        return okClicked;
    }

    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleDelete(){
        okClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }


}
