package org.alibaby.View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.alibaby.Model.City;
import org.alibaby.Model.Database;
import org.alibaby.Model.Message;
import org.alibaby.Model.MessageListener;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentChange;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreException;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.database.annotations.Nullable;

public class ChatBox {

    ArrayList<JLabel> lblMessages;
    ArrayList<Integer> lblUsers;
    
    JPanel panel;
    Dimension dim;
    Font fixed; 
    Firestore db;
    JTextField txtFrom;
    
    public ChatBox () {
        db = new Database().db;
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
 
        JFrame frame = new JFrame("VibeBayin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane scrollPane = new JScrollPane(panel);

            try {
                // WORKING PATTERN
                URI path = Thread.currentThread().getContextClassLoader().getResource("BayBayinNiwangUno.otf").toURI();
                        
                File myFontFile = new File(path);
                fixed = Font.createFont(Font.TRUETYPE_FONT, myFontFile);
                
                Map attributes = fixed.getAttributes();
                attributes.put(TextAttribute.SIZE, 24   );
                attributes.put(TextAttribute.LIGATURES, TextAttribute.LIGATURES_ON);
                fixed = fixed.deriveFont(attributes);

                dim = new Dimension(420,25);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(300, 690);
                //panel.setLayout(new FlowLayout());

                JLabel lblFrom = new JLabel("From :");
                txtFrom = new JTextField("0");
                txtFrom.setPreferredSize(dim);
                
                JLabel lblTo = new JLabel("To:");
                JTextField txtTo = new JTextField("1");
                txtTo.setPreferredSize(dim);

                JLabel lblMessage = new JLabel("Input:");
                JTextField txtMessage = new JTextField();
                txtMessage.setPreferredSize(dim);

                JLabel lblOutput = new JLabel("Output:");
                JTextField txtOutput = new JTextField("1");
                txtOutput.setPreferredSize(dim);
                txtOutput.setFont(fixed);

                JButton btnSend = new JButton("Send");
                btnSend.setPreferredSize(dim);      

                db.collection("all_messages")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                            addMessage(dc.getDocument().get("message", String.class), dc.getDocument().get("from", Integer.class) );
                            Integer a = 0;
                            System.out.println(a.getClass());

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


                btnSend.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        int from = Integer.valueOf(txtFrom.getText());
                        int to = Integer.valueOf(txtTo.getText());
                        String messageBody = txtMessage.getText();
                        Timestamp timestamp = Timestamp.now();
                        Message message = new Message(from, to, messageBody, timestamp);
                        ApiFuture<DocumentReference> future = db.collection("all_messages").add(message);
                        try {
                            System.out.println("Added document with ID: " +  future.get().getId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        };
                    }
                });;


                // txtFrom.getDocument().addDocumentListener(new DocumentListener() {
                //     @Override
                //     public void insertUpdate(DocumentEvent e) {
                //     // updatefromLabels();   
                //     }

                //     @Override
                //     public void removeUpdate(DocumentEvent e) {
                //     //updatefromLabels();
                //     }

                //     @Override
                //     public void changedUpdate(DocumentEvent e) {
                        
                //     }

                //     public void updatefromLabels(){
                //         try {

                //             for(int i=0; i<lblUsers.size(); i++){
                //                 if (Integer.valueOf(txtFrom.getText())==lblUsers.get(i))
                //                     lblMessage.setFont(new Font("Arial", Font.PLAIN, 24));
                //             }
                //         } catch (Exception e){

                //         }
 
                //     }
                // });

                txtMessage.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        txtOutput.setText(txtMessage.getText());
                        String message = txtMessage.getText();
                     //   addMessage(message);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        txtOutput.setText(txtMessage.getText());
                        String message = txtMessage.getText();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                    }
                });


                panel.add(lblFrom);
                panel.add(txtFrom);
                panel.add(lblTo);
                panel.add(txtTo);
                panel.add(lblMessage);
                panel.add(txtMessage);
                panel.add(lblOutput);
                panel.add(txtOutput);
                
                panel.add(btnSend);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                frame.getContentPane().add(scrollPane);

                frame.setVisible(true);


            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }


        }

    public void addMessage(String message, int user){
        JLabel lblUser = new JLabel("User: " + user);
        lblUser.setPreferredSize(dim);
        
        JLabel lblMessage = new JLabel(message);

        if (user != Integer.valueOf(txtFrom.getText())){
          lblMessage.setFont(fixed);
        }

        lblMessage.setPreferredSize(dim);
        
      //  lblMessages.add(lblMessage);
      //  lblUsers.add(user);

        panel.add(lblUser);
        panel.add(lblMessage);
        panel.revalidate();
        panel.repaint();
    }

}

