package org.alibaby.View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.alibaby.Model.Database;
import org.alibaby.Model.MessageListener;

import com.google.cloud.firestore.DocumentChange;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreException;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.database.annotations.Nullable;

public class ChatBox {

    JPanel panel;
    Dimension dim;
    Font fixed; 
    Firestore db;
    
    public ChatBox () {
        db = new Database().db;
        panel = new JPanel();

        JFrame frame = new JFrame("Custom Font Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
                frame.setSize(420, 690);
                panel.setLayout(new FlowLayout());

                JLabel nameLabel = new JLabel("Input:");
                JTextField nameField = new JTextField();
                nameField.setPreferredSize(dim);

                JLabel emailLabel = new JLabel("Output:");
                JTextField emailField = new JTextField();
                emailField.setPreferredSize(dim);

                emailField.setFont(fixed);

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






                nameField.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        emailField.setText(nameField.getText());
                        String message = nameField.getText();
                        addMessage(message);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        emailField.setText(nameField.getText());
                        String message = nameField.getText();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                    }
                });

                panel.add(nameLabel);
                panel.add(nameField);
                panel.add(emailLabel);
                panel.add(emailField);

                frame.getContentPane().add(panel);

                frame.setVisible(true);


            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }


        }

    public void addMessage(String message){
        JLabel label = new JLabel(message);
        label.setFont(fixed);
        label.setPreferredSize(dim);
        panel.add(label);
        panel.revalidate();
        panel.repaint();
    }

}

