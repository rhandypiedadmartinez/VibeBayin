package org.alibaby.Model;

import com.google.cloud.firestore.DocumentChange;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreException;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.database.annotations.Nullable;

public class MessageListener {
    public MessageListener(Database database){
        Firestore db = database.db;

        db.collection("all_messages")
        .addSnapshotListener( new EventListener<QuerySnapshot>() {
          @Override
          public void onEvent(
              @Nullable QuerySnapshot snapshots, @Nullable FirestoreException e) {
            if (e != null) {
              System.err.println("Listen failed: " + e);
              return;
            }

            for (DocumentChange dc : snapshots.getDocumentChanges()) {
              switch (dc.getType()) {
                case ADDED:
                  System.out.println("New message: " + dc.getDocument().getData());
                  break;
                case MODIFIED:
                  System.out.println("Modified message: " + dc.getDocument().getData());
                  break;
                case REMOVED:
                  System.out.println("Removed message: " + dc.getDocument().getData());
                  break;
                default:
                  break;
              }
            }
          }
        });
     }
     public static void main(String[] args) {
        MessageListener listener = new MessageListener(new Database());
        while(true);
     }
}

