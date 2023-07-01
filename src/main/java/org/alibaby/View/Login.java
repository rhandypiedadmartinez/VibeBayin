package org.alibaby.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/login.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        // Perform any additional initialization of the login controller if needed
        loginController.btnLogin.setOnAction(event -> loginController.handleLogin());


        // Create the scene
        Scene scene = new Scene(root);

        // Set the scene to the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Application");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
