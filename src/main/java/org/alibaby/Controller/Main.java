package org.alibaby.Controller;

import org.alibaby.Model.Database;
import org.alibaby.View.VibeBayinMain;

import com.google.cloud.firestore.Firestore;

public class Main {
    int currentUser;
    static Firestore db;
    public static void main(String[] args) {

        Main main = new Main(0);
        
        db = new Database().db;

        int currentUser = 1;

        // OPEN ACCOUNT ID=0 VB ACCOUNT
        Thread th_1 = new Thread(()-> {
            VibeBayinMain vb = new VibeBayinMain(db, currentUser);
            vb.setVisible(true);

        });
        
        // OPEN ACCOUNT ID=1 VB ACCOUNT
        Thread th_2 = new Thread(()-> {
            VibeBayinMain vb = new VibeBayinMain(db, 2);
            vb.setVisible(true);

        });

        // // OPEN ACCOUNT ID=2 VB ACCOUNT
        // Thread th_3 = new Thread(()-> {
        //     VibeBayinMain vb = new VibeBayinMain(db, 2);
        //     vb.setVisible(true);
        // });

        th_1.start();
        th_2.start();
        //th_3.start();


    }

    Main(int currentUser){
        this.currentUser = currentUser;
    }
}
