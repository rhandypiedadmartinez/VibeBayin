package org.alibaby.Controller.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.alibaby.Model.Database;
import org.alibaby.Model.Message;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;

import org.alibaby.Model.*;
public class FriendsUtil {
    public LinkedHashSet<Integer> friends;
    public ArrayList<Integer> friendsList;
    public Firestore db;
    public int currentUser;

    public FriendsUtil(Firestore db, int currentUser){
        // code to get query all users id that has interacted with current user id        
        this.db = db;
        this.currentUser = currentUser;
        friends = new LinkedHashSet<>();

        Query query = db.collection("all_messages");
        // asynchronously retrieve the document
        ApiFuture<QuerySnapshot> querySnapshot = query.get();



        try {
            for(DocumentSnapshot document: querySnapshot.get().getDocuments()){
                Message msg = document.toObject(Message.class);
                if (msg.from == currentUser){
                    friends.add(msg.to);
                }
                if (msg.to == currentUser){
                    friends.add(msg.from);
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        friendsList = new ArrayList<Integer>(friends);
        if (friendsList.isEmpty()){
            System.out.println("No Friends");
        }
    }

    public static void main(String[] args) {
        System.out.println(new FriendsUtil(new Database().db, 1).friendsList);
    }

    public void connectToNewFriend(){


        LinkedHashSet<Integer> not_friends;
    
        Query query = db.collection("all_users");
        // asynchronously retrieve the document
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        int new_friend = 0;
        try {
            for(DocumentSnapshot document: querySnapshot.get().getDocuments()){

                User user = document.toObject(User.class);
                if (user.userID==this.currentUser || user.userID==0){
                    continue;
                }
                if (!friendsList.contains(user.userID)){
                    new_friend = user.userID;
                    break;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (new_friend==0){
            Kislap.kislapSendMessage(this.db, this.currentUser, "Walang makakalandian");

        } else {
            Kislap.kislapConnect(this.db, this.currentUser, new_friend);
        }
    }

    public void connectToExistingFriend(String name){

       // Kislap.kislapSendMessage(this.db, )
        
    }
 
}
