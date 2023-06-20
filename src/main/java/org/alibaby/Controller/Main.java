package org.alibaby.Controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args){
        try {
            FileInputStream serviceAccount = new FileInputStream("C:\\Users\\A-222\\IdeaProjects\\VibeBayin\\src\\main\\resources\\admin_key.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            Firestore db = FirestoreClient.getFirestore();

            DBWrite Test = new DBWrite(db);
            Test.prepareExamples();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}