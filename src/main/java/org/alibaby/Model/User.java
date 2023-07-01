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
    
    public User(){}
    
    public User(int userID, String name, String password, boolean isOnline){
        this.userID = userID;
        this.name = name;
        this.password = password;
        this.isOnline = isOnline;   
    }

    public static void main(String[] args){
        Firestore db = new Database().db;
        writeDB(db, 5);
        System.out.println(getNumberOfUSers(db));
    }

    private static void writeDB(Firestore db, int numberOfDummyUsers) {

        int last = getNumberOfUSers(db);

        for(int i=last; i<(last + numberOfDummyUsers); i++){
            String ii = String.valueOf(i);
            DocumentReference addedDocRef = db.collection("all_users").document(ii);
            
            User data = new User(i, "DummyName" + ii , "DummyPassword" + ii, false);           
        
            ApiFuture<WriteResult> writeResult = addedDocRef.set(data);
        }
        
    }

    private static int getNumberOfUSers(Firestore db){
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
              System.out.println(document.getId() + " => " + document.toObject(User.class));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }
}
