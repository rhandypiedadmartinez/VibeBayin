package org.alibaby.View;



import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.google.cloud.firestore.Firestore;
import org.alibaby.Model.Database;

public class LoginController {
    static Firestore db;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPW;

    @FXML
    public Button btnLogin;

    @FXML
    private void initialize() {
        // Add initialization code here (if needed)
    }

    @FXML
    public void handleLogin() {
        // Perform login logic here
        // You can add your own code to authenticate the user
        db = new Database().db;


    }
}