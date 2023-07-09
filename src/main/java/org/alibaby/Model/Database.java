package org.alibaby.Model;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;

public class Database {
    public Firestore db;
    
    public Database(){
        try {
            URI path = Thread.currentThread().getContextClassLoader().getResource("admin_key.json").toURI();
            FileInputStream serviceAccount = new FileInputStream(new File(path));
                
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            db = FirestoreClient.getFirestore();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}