package org.alibaby.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.alibaby.Controller.Utilities.*;

import org.alibaby.View.InnerChatBoxPane;

import org.alibaby.Controller.Utilities.BaybayinUtil;
import org.alibaby.Model.*;
import org.alibaby.Model.Message;


import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

public class UserOptions implements ActionListener {
    
    public BaybayinUtil bUtil;
    public Font fixed;
    public JPanel panel;
    public int currentUser;
    public Firestore db;
    public JButton button1;
    public JButton button2;
    public JButton button3;
    public JButton button4;

    public InnerChatBoxPane window;

    public UserOptions(InnerChatBoxPane window, int currentUser, Firestore db) {
        //setTitle("Swing Example");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(300, 200);
        //setLocationRelativeTo(null);
        this.window = window;
        this.currentUser =  currentUser;
        this.panel = new JPanel(new GridBagLayout());
        this.db = db;


        bUtil = new BaybayinUtil();
        fixed = bUtil.fixed;
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fixed);
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(1, 1, 1, 1);

        // First label
        JLabel label1 = new JLabel();
        label1.setText("Anong maipaglilingkod ko?");

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        panel.add(label1, constraints);

        // Button 1
        button1 = new JButton();
        button1.setText("Matuto");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        button1.addActionListener(this);
        button1.setBackground(Color.RED);
        button1.setForeground(Color.WHITE);

        panel.add(button1, constraints);

        button2 = new JButton();
        button2.setText("Magsulit");
        constraints.gridx = 1;
        constraints.gridy = 2;
        button2.addActionListener(this);
        button2.setBackground(Color.GREEN);
        
        panel.add(button2, constraints);

        button3 = new JButton();
        button3.setText("Kumaibigan");
        constraints.gridx = 2;
        constraints.gridy = 2;
        button3.setBackground(Color.BLUE);
        button3.setForeground(Color.WHITE);

        button3.addActionListener(this);
        
        panel.add(button3, constraints);

        // button4 = new JButton();
        // button4.setText("Kumonekta");
        // button4.addActionListener(this);
        
        // constraints.gridx = 3;
        // constraints.gridy = 2;
        // panel.add(button4, constraints);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
//        button4.setEnabled(false);

        String command = e.getActionCommand();
        //System.out.println("Button clicked: " + command);
            
        Thread b = new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1){

            }  

            window.addUserOptions();
        });

        if (command.equals("Matuto")){

            int from = 0;
            int to = this.currentUser;

            String messageBody = "https://www.baybayin.com/";
            Timestamp timestamp = Timestamp.now();

            Message message = new Message(from, to, messageBody, timestamp);
            ApiFuture<WriteResult> future = db.collection("all_messages").document(timestamp.toString()).set(message);

            b.start();
        }

        if (command.equals("Magsulit")){
            Thread a = new Thread(()->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e2){

                }  
                
                window.addQuiz();
            });

            a.start(); 
        }

        if (command.equals("Kumaibigan")){
            FriendsUtil fu = new FriendsUtil(this.db, this.currentUser);
            fu.connectToNewFriend();
            
            FriendsList fr_list = new FriendsList(this.currentUser, fu.friendsList);
            try {
                fr_list.storeFriends(db);
            } catch (Exception e5){

            }
            b.start();
        }

        if (command.equals("Kumonekta")){
      //      Kislap.kislapSendMessage(this.db, this.currentUser, "Isulat ang \"\"");
        }

    }


}
