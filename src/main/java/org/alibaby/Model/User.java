package org.alibaby.Model;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.PropertyName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.mindrot.jbcrypt.BCrypt;


import org.alibaby.Model.Database;

public class User {


    @PropertyName("userID")
    public int userID;

    @PropertyName("name")
    public String name;

    @PropertyName("password")
    public String password;

    @PropertyName("isOnline")
    public boolean isOnline;

    @PropertyName("level")
    public int level;

    @PropertyName("password_salt")
    public String password_salt;

    @PropertyName("email")
    public String email;

    public User(){}
    
    public User(int userID, String name, String password, boolean isOnline, String password_salt, String email){
        this.userID = userID;
        this.name = name;
        this.password = password;
        this.isOnline = isOnline;
        this.password_salt = password_salt;
        this.email = email;
    }

    public User(String name, String password, String password_salt){
        this.name = name;
        this.password = password;
        this.password_salt = password_salt;
    }

    public static User getUser(Firestore db, int currentUser){
        String ii = String.format("%05d", currentUser);

        DocumentReference docRef = db.collection("all_users").document(ii);
        
        ApiFuture<DocumentSnapshot> future = docRef.get();

        DocumentSnapshot document = null;

        try {
            document = future.get();
        } catch (Exception e5){

        }

        return document.toObject(User.class);
    }

    public static void main(String[] args){
        Firestore db = new Database().db;
         writeDB(db, 5);
        // System.out.println(getNumberOfUSers(db));
        // for(int i=0; i<2; i++){
        //     System.out.println(User.getUser(db, i).name);
        // }
    }

    private static void writeDB(Firestore db, int numberOfDummyUsers) {

        int last = getNumberOfUSers(db);

        for(int i=last; i<(last + numberOfDummyUsers); i++){
            String randomName = NameGenerator.generateRandomName();
        
            // Generate a salt
            String salt = BCrypt.gensalt();
                
            String ii = String.format("%05d", i);
    
            DocumentReference addedDocRef = db.collection("all_users").document(ii);
            
            User data = new User(i, randomName, encryptPassword("DummyPWsdasdasd"+ii, salt), false, salt, randomName.replace(" ","").toLowerCase()+"@gmail.com");           
        
            ApiFuture<WriteResult> writeResult = addedDocRef.set(data);
        }
        
    }

    public static int getNumberOfUSers(Firestore db){
        int count = 0;
        try {
           // asynchronously retrieve multiple documents
            ApiFuture<QuerySnapshot> future = db.collection("all_users").get();
            // future.get() blocks on response
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            if (documents.isEmpty()){
                return 0;
            }

            for (DocumentSnapshot document : documents) {
              count++;
              //System.out.println(document.getId() + " => " + document.toObject(User.class));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    public static String encryptPassword(String password, String salt){
        
        // Hash the password with the salt
        String hashedPassword = BCrypt.hashpw(password, salt);

        //System.out.println("Original password: " + password);
        //System.out.println("Hashed password: " + hashedPassword);
        
        return hashedPassword;
    }

    private static boolean isPasswordCorrect(String password, String salt, String hashedPassword2){
        String hashedPassword1 = BCrypt.hashpw(password, salt);

        return hashedPassword1.equals(hashedPassword2);
    }

    public static String getEncryptedPassword(String password, String salt) {
        return encryptPassword(password, salt);
    }

    public static int getLevel(Firestore db, int currentUser) throws Exception{              
        String ii = String.format("%05d", currentUser);

        DocumentReference docRef = db.collection("all_users").document(ii);
        // asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        // ...
        // future.get() blocks on response
        DocumentSnapshot document = future.get();
        if (document.exists()) {
          System.out.println("Document data: " + document.getData());
        } else {
          System.out.println("No such document!");
          return 0;
        }

        return document.toObject(User.class).level;
    }

    public static void increaseLevel(Firestore db, int currentUser) throws Exception{              
        String ii = String.format("%05d", currentUser);

        DocumentReference docRef = db.collection("all_users").document(ii);
        // asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        // ...
        // future.get() blocks on response
        DocumentSnapshot document = future.get();
        if (document.exists()) {
          System.out.println("Document data: " + document.getData());
        } else {
          System.out.println("No such document!");
          return;
        }

        int level = document.toObject(User.class).level;
        level++;

        // Update an existing document
        // (async) Update one field
        ApiFuture<WriteResult> future1 = docRef.update("level", level);

        // ...
        WriteResult result = future1.get();
      //  System.out.println("Write result: " + result);
    }



}
