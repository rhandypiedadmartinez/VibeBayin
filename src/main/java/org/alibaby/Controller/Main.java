package org.alibaby.Controller;

import org.alibaby.Model.Database;
import org.alibaby.View.LoginController;
import org.alibaby.View.VibeBayinMain;

import com.google.cloud.firestore.Firestore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    //int currentUser;
    //static Firestore db;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/login.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        // Perform any additional initialization of the login controller if needed
        loginController.btnLogin.setOnAction(event -> loginController.handleLogin());
        loginController.btnSignup.setOnAction(event -> loginController.handleSignup());


        // Create the scene
        Scene scene = new Scene(root);

        // Set the scene to the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Application");
        primaryStage.show();
    }
    public static void main(String[] args) {
        Application.launch(Main.class, args);
        //Main main = new Main(0);
        
        //db = new Database().db;

        //int currentUser = 1;

        // OPEN ACCOUNT ID=0 VB ACCOUNT
        //Thread th_1 = new Thread(()-> {
          //  VibeBayinMain vb = new VibeBayinMain(db, currentUser);
           // vb.setVisible(true);

        //});
        
        // OPEN ACCOUNT ID=1 VB ACCOUNT
        //Thread th_2 = new Thread(()-> {
         //   VibeBayinMain vb = new VibeBayinMain(db, 2);
         //   vb.setVisible(true);

        //});

        // // OPEN ACCOUNT ID=2 VB ACCOUNT
        // Thread th_3 = new Thread(()-> {
        //     VibeBayinMain vb = new VibeBayinMain(db, 2);
        //     vb.setVisible(true);
        // });

       // th_1.start();
       // th_2.start();
        //th_3.start();








    }

    //public Main(int currentUser){
        //this.currentUser = currentUser;
    //}
}
