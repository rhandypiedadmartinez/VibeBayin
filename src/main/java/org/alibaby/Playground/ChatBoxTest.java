// package org.alibaby.Playground;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class ChatBoxTest extends JFrame {
//     private JTextArea chatArea;
//     private JTextField inputField;
//     private JPanel messagesPanel;

//     public ChatBoxTest() {
//         setTitle("Chatbox");
//         setDefaultCloseOperation(EXIT_ON_CLOSE);
//         setLayout(new BorderLayout());

//         chatArea = new JTextArea();
//         chatArea.setEditable(false);
//         JScrollPane scrollPane = new JScrollPane(chatArea);
//         add(scrollPane, BorderLayout.CENTER);

//         messagesPanel = new JPanel();
//         messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));
//         add(messagesPanel, BorderLayout.WEST);

//         inputField = new JTextField();
//         inputField.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 String message = inputField.getText();
//                 if (!message.isEmpty()) {
//                     appendMessage("You: " + message, true);
//                     inputField.setText("");
//                 }
//             }
//         });
//         add(inputField, BorderLayout.SOUTH);

//         pack();
//         setLocationRelativeTo(null);
//     }

//     private void appendMessage(String message, boolean isUser) {
//         JPanel messagePanel = new JPanel();
//         messagePanel.setLayout(new FlowLayout(isUser ? FlowLayout.RIGHT : FlowLayout.LEFT));
//         JLabel messageLabel = new JLabel(message);
//         messagePanel.add(messageLabel);

//         messagesPanel.add(messagePanel);
//         messagesPanel.revalidate();
//         messagesPanel.repaint();

//         chatArea.append(message + "\n");
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(new Runnable() {
//             @Override
//             public void run() {
//                 new ChatBoxTest().setVisible(true);
//             }
//         });
//     }
// }
