package org.alibaby.Controller;

import org.alibaby.Model.Database;
import org.alibaby.View.ChatBox;

import com.google.cloud.firestore.Firestore;

public class Main {
    int currentUser;
    static Firestore db;
    public static void main(String[] args) {

        Main main = new Main(0);
        
        Database database = new Database();
        db = database.db;

        int currentUser = 1;

        Thread th_1 = new Thread(()-> {
            ChatBox chatbox1 = new ChatBox(db, currentUser, 0);
        });
        
        Thread th_2 = new Thread(()-> {
            ChatBox chatbox2 = new ChatBox(db, 2, currentUser);
        });

        th_1.start();
        th_2.start();


    }

    Main(int currentUser){
        this.currentUser = currentUser;
    }
}
