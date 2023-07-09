package org.alibaby.View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import org.alibaby.Controller.UserOptions;
import org.alibaby.Controller.Utilities.BaybayinUtil;
import org.alibaby.Model.*;


import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentChange;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreException;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.database.annotations.Nullable;

public class InnerChatBoxPane {

    public ArrayList<JLabel> lblMessages;
    public ArrayList<Message> messages;
    
    public JPanel panel;
    public Dimension dim;
    public Font fixed; 
    public Firestore db;
    public JLabel txtFrom;
    public Font arial;
    public BaybayinUtil bUtil;
    public JScrollPane scrollPane;
    public int currentUser;

    public InnerChatBoxPane (Firestore db, int currentUser, int kausap) {
        this.currentUser = currentUser;

        this.db = db;


        arial = new Font("Century Gothic", Font.PLAIN, 14);
        bUtil = new BaybayinUtil();
        fixed = bUtil.fixed;

        panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        lblMessages = new ArrayList<>();
        messages = new ArrayList<>();

        //JPanel bottomPanel = new QuizGUI(qz.quizzes, level).panel;

                         

        //JFrame frame = new JFrame("VibeBayin");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scrollPane = new JScrollPane(panel);

        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fixed);

            try {
                dim = new Dimension(200, 30);
                //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                //frame.setSize(400, 800);
                JLabel lblFrom = new JLabel("From :");
                txtFrom = new JLabel(String.valueOf(currentUser));
                txtFrom.setPreferredSize(dim);
                
                JLabel lblTo = new JLabel("To:");
                JLabel txtTo = new JLabel(String.valueOf(kausap));
                txtTo.setPreferredSize(dim);

                JLabel lblMessage = new JLabel("Input:");
                JTextField txtMessage = new JTextField();
                txtMessage.setPreferredSize(dim);

                JLabel lblOutput = new JLabel("Output:");
                JTextField txtOutput = new JTextField("");
                txtOutput.setPreferredSize(dim);
                txtOutput.setFont(fixed);

                JButton btnSend = new JButton("Send");
                btnSend.setPreferredSize(dim);      

                //panel.add( new QuizGUI(qz.quizzes, level).panel, BorderLayout.SOUTH);               

                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                db.collection("all_messages")
                .whereIn("from", Arrays.asList(new Integer[]{currentUser, kausap}))
                .whereIn("to", Arrays.asList(new Integer[]{currentUser, kausap}))
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
                            //System.out.println("New message: " + dc.getDocument().getData());
                            //if (message.)
                            Message msg2 = dc.getDocument().toObject(Message.class);
                            addMessage(msg2);
                           
                        break;
                        case MODIFIED:
                        //System.out.println("Modified message: " + dc.getDocument().getData());

                        break;
                        case REMOVED:
                        //System.out.println("Removed message: " + dc.getDocument().getData());
                            // Message msg3 = dc.getDocument().toObject(Message.class);
                            // int index = messages.indexOf(msg3);
                            // lblMessages.remove(index);
                            // messages.remove(index);
                            // panel = new JPanel();

                            // panel.revalidate();
                            // panel.repaint();
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
                        ApiFuture<WriteResult> future = db.collection("all_messages").document(timestamp.toString()).set(message);
                        try {
                            //System.out.println("Added document with ID: " +  future.get().getId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        };
                    }
                });;


                txtMessage.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        txtOutput.setText(bUtil.translate(txtMessage.getText()));

                        String message = txtMessage.getText();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        txtOutput.setText(bUtil.translate(txtMessage.getText()));

                        String message = txtMessage.getText();

                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {

                    }
                });


                //panel.add(lblFrom);
                //panel.add(txtFrom);
                //panel.add(lblTo);
                //panel.add(txtTo);
                //panel.add(lblMessage);
                //panel.add(txtMessage);
                //panel.add(lblOutput);
                //panel.add(txtOutput);
                
                //panel.add(btnSend);


                // frame.getContentPane().add(scrollPane);

                // frame.setVisible(true);


            } catch (Exception e1) {
                e1.printStackTrace();
            } 

            Thread a = new Thread(()->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                addUserOptions();
            });

            if (kausap==0){
                a.start();
            }
        }

    public void addMessage(Message message){
        JPanel newPanel = new JPanel();

        System.out.println(message.message);
        JLabel lblUser = new JLabel("User: " + message.from);
        lblUser.setPreferredSize(dim);
        JLabel lblMessage = new JLabel(message.message);

        lblMessage.setPreferredSize(dim);


        int currentUser = Integer.valueOf(txtFrom.getText());
        if ((currentUser == message.from) || (currentUser == message.to)){
            newPanel.add(lblUser);
            newPanel.add(lblMessage);
            newPanel.setPreferredSize(new Dimension(200, 100));

            panel.add(newPanel);

            lblMessages.add(lblMessage);
            messages.add(message);

            panel.revalidate();
            panel.repaint();
        } else {
        
        }

        //addUserOptions();
        
        updatefromLabels();
    }

    public void addUserOptions(){
        UserOptions opt = new UserOptions(this, this.currentUser, this.db);
        JPanel newPanel = opt.panel;
        newPanel.setPreferredSize(new Dimension(200, 100));
        panel.add(newPanel);
            
        panel.revalidate();
        panel.repaint();

    }

    public void addQuiz(){

        int level = 1;
        Quiz qz = new Quiz();
        QuizGUI quizGUI = new QuizGUI(this, qz.quizzes, this.currentUser, this.db);
        
        JPanel newPanel = quizGUI.panel;
        newPanel.setPreferredSize(new Dimension(200, 100));
        panel.add(newPanel);
        
        panel.revalidate();
        panel.repaint();

    }
    
    public void updatefromLabels(){
        try {
            
            int currentUser = Integer.valueOf(txtFrom.getText());

            for(int i=0; i<messages.size(); i++){

                if (0 == messages.get(i).from){
                    lblMessages.get(i).setFont(arial);
                    continue;
                }

                if (currentUser == messages.get(i).to){
                    lblMessages.get(i).setText(bUtil.translate(messages.get(i).message));
                    lblMessages.get(i).setFont(fixed);
                } else {
                    lblMessages.get(i).setText(messages.get(i).message);
                    lblMessages.get(i).setFont(arial);
                }

                if (currentUser == messages.get(i).from){
                    lblMessages.get(i).setText(messages.get(i).message);
                    lblMessages.get(i).setFont(arial);
                } else {
                    lblMessages.get(i).setText(bUtil.translate(messages.get(i).message));
                    lblMessages.get(i).setFont(fixed);
                }

            }
        } catch (Exception e){

        }
    }
}

