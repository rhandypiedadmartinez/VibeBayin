package org.alibaby.Controller;



import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.alibaby.Model.Database;
import org.alibaby.Model.User;
import org.alibaby.View.VibeBayinMain2;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;


public class LoginController {
    static Firestore db = new Database().db;

    @FXML
    public TextField txtUsername;

    @FXML
    public TextField txtPW;

    @FXML
    public Button btnLogin;

    @FXML
    public Button btnSignup;

    @FXML
    public Label lblStatus;




    @FXML
    public void initialize() {
        // Add initialization code here (if needed)
    }

    @FXML
    public void handleLogin() {
        String username = txtUsername.getText();
        String password = txtPW.getText();

        // Create a reference to the "all_users" collection
        CollectionReference usersCollection = db.collection("all_users");

        // Query the collection to find a user with the given username
        Query query = usersCollection.whereEqualTo("name", username);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        try {
            // Get the query results
            List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

            if (documents.isEmpty()) {
                lblStatus.setText("User not found.");
                return;
            }
            // Assuming the email is unique, there should be only one document
            DocumentSnapshot document = documents.get(0);
            User user = document.toObject(User.class);

            // Validate the password
            if (verifyPassword(password, user.getEncryptedPassword(password, user.password_salt))) {
                lblStatus.setText("Login Successful.");
                VibeBayinMain2 vibeBayinMain = new VibeBayinMain2(db, user.userID);
                vibeBayinMain.setVisible(true);
            } else {
                lblStatus.setText("Invalid password.");
            }
        } catch (Exception e) {
            lblStatus.setText("An error occured: " + e.getMessage());
        }
    }

    public static boolean verifyPassword(String password, String encryptedPassword) {
        return BCrypt.checkpw(password, encryptedPassword);
    }

    @FXML
    public void handleSignup() {
        String username = txtUsername.getText();
        String password = txtPW.getText();
        User user = new User();

        int last = user.getNumberOfUSers(db);
        // Generate a salt
        String salt = BCrypt.gensalt();
        String ii = String.format("%05d", last);
        DocumentReference addedDocRef = db.collection("all_users").document(ii);

        // Encrypt the password
        String encryptedPassword = user.encryptPassword(password, salt);

        User data = new User(last, username, encryptedPassword, false, salt, username.replace(" ", "").toLowerCase()+"@gmail.com");

        ApiFuture<WriteResult> writeResult = addedDocRef.set(data);

        lblStatus.setText("User signed up.");



    }

}
