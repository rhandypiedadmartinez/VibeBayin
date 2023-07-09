package org.alibaby.View;

import javax.swing.*;
import java.awt.*;

import org.alibaby.Model.Question;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import org.alibaby.Controller.Utilities.BaybayinUtil;
import org.alibaby.Model.User;

import org.alibaby.Model.Kislap;


import com.google.cloud.firestore.Firestore;


public class QuizGUI  implements ActionListener {
    
    public BaybayinUtil bUtil;
    public Font fixed;
    public JPanel panel;
    public Firestore db;
    public JButton button1;
    public JButton button2;
    public JButton button3;
    public String ch1;
    public String ch2;
    public String ch3;
    public String true_ans;
    public InnerChatBoxPane window;
    public int currentUser;


    public QuizGUI(InnerChatBoxPane window, ArrayList<Question> qz, int currentUser, Firestore db) {
    
        this.currentUser = currentUser;
        this.window = window;
        this.db = db;

        int level = 0;

        try {
            level = User.getLevel(db, currentUser);
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("level" + level);
        ch1 = qz.get(level).choice1;
        ch2 = qz.get(level).choice2;
        ch3 = qz.get(level).choice3;
        true_ans = qz.get(level).answer;

        //setTitle("Swing Example");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(300, 200);
        //setLocationRelativeTo(null);
        this.panel = new JPanel(new GridBagLayout());

        bUtil = new BaybayinUtil();
        fixed = bUtil.fixed;
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fixed);
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        // First label
        JLabel label1 = new JLabel();
        label1.setText(qz.get(level).question);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        panel.add(label1, constraints);

        // Second label
        JLabel label2 = new JLabel();
        label2.setFont(fixed);
        label2.setText(bUtil.translate(qz.get(level).diagram));
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(label2, constraints);

        // Button 1
        button1 = new JButton();

        button1.setText(ch1);
        
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(button1, constraints);

        // Button 2
        button2 = new JButton();
        button2.setText(ch2);
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(button2, constraints);

        // Button 3
        button3 = new JButton();
        button3.setText(ch3);
        constraints.gridx = 2;
        constraints.gridy = 2;
        panel.add(button3, constraints);

        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
      
//        add(panel);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        
        String command = e.getActionCommand();
        System.out.println("Button clicked: " + command);
        
        String my_ans = "";

        if (command.equals(ch1)){
            my_ans = ch1;

        }
        
        if (command.equals(ch2)){
            my_ans = ch2;
        }

        if (command.equals(ch3)){
            my_ans = ch3; 
        }

        if (my_ans.equals(true_ans)){
            Kislap.kislapSendMessage(this.db, this.currentUser, "Ikaw ay Tama!");
            try {
                User.increaseLevel(this.db, this.currentUser);
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        } else {
            Kislap.kislapSendMessage(this.db, this.currentUser, "Ang iyong sagot ay mali!");
        }

        Thread a = new Thread(()->{
            try {
                Thread.sleep(500);
            } catch (InterruptedException e1){

            }  

            window.addUserOptions();
        });

        a.start();

    }

    public static void main(String[] args) {
       //  int level = 1;
       //  Quiz qz = new Quiz();
       //  QuizGUI example = new QuizGUI(qz.quizzes, level);
       // // example.setVisible(true);
    }
}
