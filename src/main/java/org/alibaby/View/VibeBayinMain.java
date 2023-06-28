package org.alibaby.View;

import javax.swing.*;

import org.alibaby.Controller.Utilities.FriendsUtil;
import org.alibaby.Model.Database;
import org.alibaby.Model.Kislap;
import org.alibaby.View.ChatBoxPane;

import com.google.cloud.firestore.Firestore;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VibeBayinMain extends JFrame {
    public HashMap<Integer, JScrollPane> chatboxes;
    public ArrayList<Thread> threads;
    private Map<String, JTextArea> chatPanes;
    private JTabbedPane tabbedPane;
    public Firestore db;
    public int currentUser;
    public ArrayList<Integer> friends;
    
    public VibeBayinMain(Firestore db, int currentUser) {
        setTitle("VibeBayin App of USER: " + currentUser);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());


        // Create the sidebar panel
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setPreferredSize(new Dimension(200, 600));
        sidebarPanel.setBackground(Color.LIGHT_GRAY);
        sidebarPanel.setLayout(new GridLayout(0, 1));

        // Create friend panels
        ArrayList<JPanel> friendPanels = new ArrayList<>();

        this.db = db;
        this.currentUser = currentUser;
        
        Kislap kislap = new Kislap(db, currentUser);
        FriendsUtil friendsUtil = new FriendsUtil(db, currentUser);
        friends = friendsUtil.friendsList;
        chatboxes = new HashMap<>();

        Thread th_1 = new Thread(()-> {
            for(int i=0; i<friends.size()/2; i++){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                int friendID = friends.get(i);
                ChatBoxPane chatBoxPane = new ChatBoxPane(db, currentUser, friendID);
                chatboxes.put(friendID , chatBoxPane.scrollPane);
                JPanel friendPanel = createFriendPanel("Friend " + friendID, loadImage("friend1.jpg"));
                friendPanel.addMouseListener(new FriendPanelMouseListener("Friend " + friends.get(i),  friends.get(i)));
                sidebarPanel.add(friendPanel);
            }
        });

        Thread th_2 = new Thread(()-> {
            for(int i=friends.size()/2; i<friends.size(); i++){
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                int friendID = friends.get(i);
                ChatBoxPane chatBoxPane = new ChatBoxPane(db, currentUser, friendID);
                chatboxes.put(friendID , chatBoxPane.scrollPane);
                JPanel friendPanel = createFriendPanel("Friend " + friendID, loadImage("friend1.jpg"));
                friendPanel.addMouseListener(new FriendPanelMouseListener("Friend " + friends.get(i),  friends.get(i)));

                sidebarPanel.add(friendPanel);
            }
        });

        th_1.start();
        th_2.start();


        try {

            th_2.join();
            th_1.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // Create the chatbox panel using JTabbedPane
        tabbedPane = new JTabbedPane();
        chatPanes = new HashMap<>();

        // Add the sidebar and tabbed pane to the main frame
        add(sidebarPanel, BorderLayout.WEST);
        add(tabbedPane, BorderLayout.CENTER);
            

        

        
    }

    private JPanel createFriendPanel(String friendName, Image image) {
        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new BorderLayout());

        // Create the image label
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        friendPanel.add(imageLabel, BorderLayout.CENTER);

        // Create the name label
        JLabel nameLabel = new JLabel(friendName);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        friendPanel.add(nameLabel, BorderLayout.SOUTH);

        // Add a border to the friend panel
        friendPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        return friendPanel;
    }

    private Image loadImage(String fileName) {
        // Load and return the image using any method you prefer
        // Here, we'll create a placeholder image
        int size = 100;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, size, size);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, size - 1, size - 1);
        graphics.dispose();
        return image;
    }

    private class FriendPanelMouseListener extends MouseAdapter {
        private String friendName;
        private int friendID;

        public FriendPanelMouseListener(String friendName, int friendID) {
            this.friendName = friendName;
            this.friendID = friendID;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (chatPanes.containsKey(friendName)) {
                int index = tabbedPane.indexOfTab(friendName);
                tabbedPane.setSelectedIndex(index);
            } else {
                if (chatPanes.size() <= 4) {
                    JTextArea chatTextArea = new JTextArea();

                    tabbedPane.addTab(friendName, chatboxes.get(this.friendID));
                    chatPanes.put(friendName, chatTextArea);
                } else {
                    int selectedIndex = tabbedPane.getSelectedIndex();
                    String currentFriend = tabbedPane.getTitleAt(selectedIndex);
                    chatPanes.remove(currentFriend);

                    JTextArea chatTextArea = new JTextArea();
                    JScrollPane chatScrollPane = new JScrollPane(chatTextArea);
                    tabbedPane.setComponentAt(selectedIndex, chatScrollPane);
                    tabbedPane.setTitleAt(selectedIndex, friendName);
                    chatPanes.put(friendName, chatTextArea);
                }
            }
        }
    }

    //  public static void main(String[] args) {
    //      SwingUtilities.invokeLater(() -> {
    //          VibeBayinMain messengerApp = new VibeBayinMain(new Database().db, 0);
    //          messengerApp.setVisible(true);
    //      });
    //  }
}
