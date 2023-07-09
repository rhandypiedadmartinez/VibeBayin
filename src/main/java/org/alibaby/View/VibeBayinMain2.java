package org.alibaby.View;

import com.google.cloud.firestore.*;
import com.google.firebase.database.annotations.Nullable;
import org.alibaby.Controller.Utilities.FriendsUtil;
import org.alibaby.Model.FriendsList;
import org.alibaby.Model.Kislap;
import org.alibaby.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class VibeBayinMain2 extends JFrame {
    public HashMap<Integer, JPanel> chatboxes;
    public ArrayList<Thread> threads;
    private Map<String, JTextArea> chatPanes;
    private JTabbedPane tabbedPane;
    public Firestore db;
    public int currentUser;
    public ArrayList<Integer> friends;
    public JPanel sidebarPanel;
    public ArrayList<JPanel> friendPanels;
    public ArrayList<String> friendNames;

    public VibeBayinMain2(Firestore db, int currentUser) {

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 700);
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();
        chatPanes = new HashMap<>();

        // Create the sidebar panel
        sidebarPanel = new JPanel();
        sidebarPanel.setPreferredSize(new Dimension(150, 550));
        sidebarPanel.setBackground(Color.LIGHT_GRAY);
        sidebarPanel.setLayout(new GridLayout(0, 1));

        // Create friend panels
        friendPanels = new ArrayList<>();
        this.friendNames = new ArrayList<String>();

        this.db = db;
        this.currentUser = currentUser;

        Kislap kislap = new Kislap(db, currentUser);


        FriendsUtil friendsUtil = new FriendsUtil(db, currentUser);
        FriendsList fr_list3 = new FriendsList(currentUser, friendsUtil.friendsList);

        setTitle("VibeBayin App of USER: " + User.getUser(db, currentUser).name);

        try {
            fr_list3.initializeFriendList(db);
        } catch (Exception e7){

        }
        friends = friendsUtil.friendsList;

        chatboxes = new HashMap<>();


        db.collection("all_friends_list")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(

                            @Nullable QuerySnapshot snapshots, @Nullable FirestoreException e) {

                        if (e != null) {
                            System.err.println("Listen failed: " + e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            FriendsList fr_list = dc.getDocument().toObject(FriendsList.class);


                            switch (dc.getType()) {
                                case ADDED:
                                    if (fr_list.userID == currentUser){
                                        addChatBox(fr_list.friendsList);
                                    }

                                    break;
                                case MODIFIED:
                                    if (fr_list.userID == currentUser){
                                        addChatBox(fr_list.friendsList);
                                    }


                                    break;
                                case REMOVED:
                                    if (fr_list.userID == currentUser) {
                                        addChatBox(fr_list.friendsList);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                });



        // Add the sidebar and tabbed pane to the main frame
        add(sidebarPanel, BorderLayout.WEST);
        add(tabbedPane, BorderLayout.CENTER);

    }

    public void addChatBox(ArrayList<Integer> fr){
        sidebarPanel.removeAll();
        for(int i=0; i<fr.size(); i++){
            int friendID = fr.get(i);
            String friendName = User.getUser(db, fr.get(i)).name;
            OuterChatBoxPane chatBoxPane = new OuterChatBoxPane(db, currentUser, friendID);
            chatboxes.put(friendID , chatBoxPane.panel);
            JPanel friendPanel = createFriendPanel(friendName);
            friendPanel.addMouseListener(new FriendPanelMouseListener(friendName,  fr.get(i)));
            sidebarPanel.add(friendPanel);
        }

        sidebarPanel.revalidate();
        sidebarPanel.repaint();
        tabbedPane.revalidate();
        tabbedPane.repaint();

    }

    private JPanel createFriendPanel(String friendName) {
        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new BorderLayout());


        // Create the name label
        JLabel nameLabel = new JLabel(friendName);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        friendPanel.add(nameLabel, BorderLayout.SOUTH);

        // Add a border to the friend panel
        friendPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        return friendPanel;
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
                if (chatPanes.size() < 3) {
                    JTextArea chatTextArea = new JTextArea();
                    tabbedPane.addTab(friendName, chatboxes.get(this.friendID));
                    chatPanes.put(friendName, chatTextArea);
                } else {
                    int selectedIndex = tabbedPane.getSelectedIndex();
                    String currentFriend = tabbedPane.getTitleAt(selectedIndex);
                    chatPanes.remove(currentFriend);
                    JTextArea chatTextArea = new JTextArea();
                    JScrollPane chatScrollPane = new JScrollPane(chatTextArea);
                    tabbedPane.setComponentAt(selectedIndex, chatboxes.get(this.friendID));
                    tabbedPane.setTitleAt(selectedIndex, this.friendName);
                    chatPanes.put(friendName, chatTextArea);
                }
            }
        }
    }


    public static ArrayList<Integer> getElementsNotInList(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        ArrayList<Integer> result = new ArrayList<>();
        for (Integer element : list2) {
            if (!list1.contains(element)) {
                result.add(element);
            }
        }
        return result;
    }
}
