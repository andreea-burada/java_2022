package eu.ase.jfxmltest;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Subscription;

import eu.ase.iojson.User;
import eu.ase.sqldao.SqlDAO;
import eu.ase.sqldao.UsersSubscriberReactStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;

public class RegistrationFormController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;
    
    @FXML
    private Button submitMThButton;
    
    @FXML
    private Button submitReactStreamsButton;
    
    @FXML
    private Button displayButton;
    
    
    private static int objectRegisteredUsersCount = 0;
//    public static int getObjectRegisteredUsersCount() {
//    	return objectRegisteredUsersCount;
//    }
//    public static void setObjectRegisteredUsersCount(int objectRegisteredUsersCount) {
//    	RegistrationFormController.objectRegisteredUsersCount = objectRegisteredUsersCount;
//    }
    private static SqlDAO sqlObj;
//    public static SqlDAO getSqlObj() {
//    	return sqlObj;
//    }
    
    public RegistrationFormController() {
    	super();
    	sqlObj = SqlDAO.getInstance();
    }
    
    
    
    private void doValidationGUI(Window owner) {
        if(nameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                    "Please enter your name");
            return;
        }
        if(emailField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                    "Please enter your email id");
            return;
        }
        if(passwordField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                    "Please enter a password");
            return;
        }
    }
    
    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
    	
    }
    
    @FXML
    protected void handleSubmitMThButtonAction(ActionEvent event) {
    	
    }
    
    @FXML
    protected void handleSubmitReactStreamsButtonAction(ActionEvent event) {
    	
    }
    
    @FXML
    protected void handleDisplayButtonAction(ActionEvent event) {
    	sqlObj.displayDB();
    }
    
    
}

