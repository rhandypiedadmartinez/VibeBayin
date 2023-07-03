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


import org.alibaby.Model.*;
import org.alibaby.Controller.*;
import org.alibaby.Controller.Utilities.*;
import org.alibaby.View.*;

import java.util.*;


public class FriendsList {


    @PropertyName("userID")
    public int userID;

    @PropertyName("friendsList")
    public ArrayList<Integer> friendsList;

    public FriendsList(){

    }

    public FriendsList(int userID, ArrayList<Integer> friendsList){
        this.userID = userID;
        this.friendsList = friendsList;
    }

    public static void main(String[] args) throws Exception {
        Firestore db = new Database().db;

        for(int i=0; i < User.getNumberOfUSers(db) ;i++){
            FriendsUtil fu = new FriendsUtil(db, i);
            FriendsList fr_list = new FriendsList(i, fu.friendsList);
            fr_list.storeFriends(db);
        }


    }

    public void initializeFriendList(Firestore db) throws Exception {
        for(int i=0; i < User.getNumberOfUSers(db) ;i++){
            FriendsUtil fu = new FriendsUtil(db, i);
            FriendsList fr_list = new FriendsList(i, fu.friendsList);
            fr_list.storeFriends(db);
        }

    }

    // private static void writeDB(Firestore db, int numberOfDummyUsers) {

    //     int last = getNumberOfUSers(db);

    //     for(int i=last; i<(last + numberOfDummyUsers); i++){
    //         String randomName = NameGenerator.generateRandomName();
        
    //         // Generate a salt
    //         String salt = BCrypt.gensalt();
                
    //         String ii = String.format("%05d", i);
    
    //         DocumentReference addedDocRef = db.collection("all_users").document(ii);
            
    //         User data = new User(i, randomName, encryptPassword("DummyPWsdasdasd"+ii, salt), false, salt, randomName.replace(" ","").toLowerCase()+"@gmail.com");           
        
    //         ApiFuture<WriteResult> writeResult = addedDocRef.set(data);
    //     }
        
    // }

    // public static int getNumberOfUSers(Firestore db){
    //     int count = 0;
    //     try {
    //        // asynchronously retrieve multiple documents
    //         ApiFuture<QuerySnapshot> future = db.collection("all_users").get();
    //         // future.get() blocks on response
    //         List<QueryDocumentSnapshot> documents = future.get().getDocuments();

    //         if (documents.isEmpty()){
    //             return 0;
    //         }

    //         for (DocumentSnapshot document : documents) {
    //           count++;
    //           System.out.println(document.getId() + " => " + document.toObject(User.class));
    //         }
    //     } catch (Exception e){
    //         e.printStackTrace();
    //     }
    //     return count;
    // }

    public void storeFriends(Firestore db) throws Exception {   
            
        String ii = String.format("%05d", this.userID);

        String str = Arrays.deepToString(this.friendsList.toArray());   
    
       // System.out.print(this.userID + " " + str);



        try {
            db.collection("all_friends_list").document(ii).set(this);
        } catch (Exception e2){
//            e2.printStackTrace();
        }
    }



}
