package org.alibaby.Model;

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

public class Kislap {
    public int currentUser;
    public Firestore db;

    public Kislap( Firestore db, int currentUser){        
        this.db = db;
        this.currentUser = currentUser;

        isInitialize();
    }

    void isInitialize(){

        Query query = db.collection("all_messages")
        .whereIn("from", Arrays.asList(new Integer[]{currentUser, 0}))
        .whereIn("to", Arrays.asList(new Integer[]{currentUser, 0}));

        // asynchronously retrieve the document
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        try {
            if (querySnapshot.get().getDocuments().isEmpty()){
                initializeKislap();
            } 

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    
        
    } 

    private void initializeKislap() {
        CollectionReference convoWithKislap = db.collection("all_messages");
        List<ApiFuture<WriteResult>> futures = new ArrayList<>();
        futures.add(
            convoWithKislap.document(Timestamp.now().toString()).set(
                new Message(0,currentUser,"Kamusta ka! Ako si Kislap",Timestamp.now())));
    }

    private void writeDB(int from, int to) {
        CollectionReference convoWithKislap = db.collection("all_messages");
        List<ApiFuture<WriteResult>> futures = new ArrayList<>();
        futures.add(
            convoWithKislap.document(Timestamp.now().toString()).set(
                new Message(from, to,"Test lang",Timestamp.now())));
    }

    public static void main(String[] args) {
        Kislap ks = new Kislap(new Database().db, 1);
        
        for(int i=4; i<=20; i++){
          ks.writeDB(i,1);
 
        }
    }   
    
}
