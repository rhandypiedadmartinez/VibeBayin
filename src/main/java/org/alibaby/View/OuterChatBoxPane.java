package org.alibaby.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JScrollPane;

import org.alibaby.Model.Database;
import org.alibaby.View.InnerChatBoxPane;

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
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.alibaby.Controller.Utilities.BaybayinUtil;
import org.alibaby.Model.City;
import org.alibaby.Model.Database;
import org.alibaby.Model.Message;
import org.alibaby.Model.MessageListener;


import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentChange;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreException;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.annotations.Nullable;

public class OuterChatBoxPane extends JFrame {

    public Firestore db;
    private JTextArea chatBox;
    private JTextArea messageInput;
    private JButton sendButton;
    public InnerChatBoxPane cb;
    public int user;
    public int kausap;
    public JPanel panel;

    public OuterChatBoxPane(Firestore db, int user, int kausap) {
        this.panel = new JPanel();
        this.user = user;
        this.kausap = kausap;
        this.db = db;
        this.cb = new InnerChatBoxPane(db, user, kausap);
        
        setTitle("Messaging App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the message display area with scroll pane
        chatBox = new JTextArea();
        chatBox.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(cb.panel);
        scrollPane.setPreferredSize(new Dimension(500,550));
       scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       // add(scrollPane, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create the input panel at the bottom
        JPanel inputPanel = new JPanel(new BorderLayout());

        messageInput = new JTextArea(3, 40);
        messageInput.setLineWrap(true);

        messageInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) {
                    sendMessage();
                }
            }
        });

        sendButton = new JButton("Send");

        //sendButton.setPreferredSize(new Dimension(200,30));
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        inputPanel.add(messageInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
         panel.add(inputPanel, BorderLayout.SOUTH);
    //    pack();
      //  setVisible(true);
    }

    private void sendMessage() {
        int from = this.user;
        int to = this.kausap;
        String messageBody = messageInput.getText();
        Timestamp timestamp = Timestamp.now();
        Message message = new Message(from, to, messageBody, timestamp);
        ApiFuture<WriteResult> future = db.collection("all_messages").document(timestamp.toString()).set(message);
        try {
            //System.out.println("Added document with ID: " +  future.get().getId());
        } catch (Exception e) {
            e.printStackTrace();
        };
        messageInput.setText("");

        // String message = messageInput.getText().trim();
        // if (!message.isEmpty()) {
        //     chatBox.append("Me: " + message + "\n");
        //     messageInput.setText("");
        // }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OuterChatBoxPane(new Database().db,1,2).setVisible(true);
            }
        });
    }
}

