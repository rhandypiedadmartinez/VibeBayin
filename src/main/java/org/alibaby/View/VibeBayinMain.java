package org.alibaby.View;

import javax.swing.*;

import org.alibaby.Model.Database;
import org.alibaby.View.ChatBoxPane;

import com.google.cloud.firestore.Firestore;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class VibeBayinMain extends JFrame {
    public ArrayList<JScrollPane> chatboxes;
    public ArrayList<Thread> threads;
    private Map<String, JTextArea> chatPanes;
    private JTabbedPane tabbedPane;
    public Firestore db;
    public int currentUser;
    public ArrayList<Integer> friends;
    
    public VibeBayinMain(Firestore db, int currentUser) {
        setTitle("VibeBayin App of USER: " + currentUser);
        friends = new ArrayList<>();
        chatboxes = new ArrayList<>();
        this.db = db;
        this.currentUser = currentUser;
        threads = new ArrayList<>();

        for(int i=0; i<=3; i++){

            if (currentUser==i){
                continue;
            }
            
            int finalI = i;
            friends.add(finalI);

            threads.add(new Thread(()->{
                ChatBoxPane chatBoxPane = new ChatBoxPane(db, currentUser, finalI);
                chatboxes.add(chatBoxPane.scrollPane);
            }));

            threads.get(threads.size()-1).start();
            
        }


        

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Create the sidebar panel
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setPreferredSize(new Dimension(200, 600));
        sidebarPanel.setBackground(Color.LIGHT_GRAY);
        sidebarPanel.setLayout(new GridLayout(0, 1));

        // Create friend panels
        JPanel friendPanel1 = createFriendPanel("Friend " + friends.get(0), loadImage("friend1.jpg"));
        JPanel friendPanel2 = createFriendPanel("Friend " + friends.get(1), loadImage("friend2.jpg"));
        JPanel friendPanel3 = createFriendPanel("Friend " + friends.get(2), loadImage("friend3.jpg"));

        // Add friend panels to the sidebar
        sidebarPanel.add(friendPanel1);
        sidebarPanel.add(friendPanel2);
        sidebarPanel.add(friendPanel3);

        // Create the chatbox panel using JTabbedPane
        tabbedPane = new JTabbedPane();
        chatPanes = new HashMap<>();

        // Add mouse listener to friend panels
        friendPanel1.addMouseListener(new FriendPanelMouseListener("Friend " + friends.get(0)));
        friendPanel2.addMouseListener(new FriendPanelMouseListener("Friend " + friends.get(1)));
        friendPanel3.addMouseListener(new FriendPanelMouseListener("Friend " + friends.get(2)));

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

        public FriendPanelMouseListener(String friendName) {
            this.friendName = friendName;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (chatPanes.containsKey(friendName)) {
                int index = tabbedPane.indexOfTab(friendName);
                tabbedPane.setSelectedIndex(index);
            } else {
                if (chatPanes.size() < 3) {
                    JTextArea chatTextArea = new JTextArea();
                    //JScrollPane chatScrollPane = new JScrollPane(chatTextArea);
                    int ID = Integer.valueOf(this.friendName.replace("Friend","").trim());
                    tabbedPane.addTab(friendName, chatboxes.get(ID));
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
