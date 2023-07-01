package org.alibaby.View;
import javax.swing.*;
import java.awt.*;

public class MultilineLabel extends JFrame {

    public MultilineLabel() {
        setTitle("Multiline Label Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Create a JLabel with long text
        String labelText = "This is a long text that will wrap to the next line when it exceeds the label's width. This is a long text that will wrap to the next line when it exceeds the label's width.";
        JLabel label = new JLabel(labelText);

        // Set line wrap and vertical alignment properties
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setPreferredSize(new Dimension(200, 100));
        label.setMaximumSize(new Dimension(200, 100));
        label.setOpaque(true);
        label.setBackground(Color.YELLOW);

        add(label);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MultilineLabel().setVisible(true);
            }
        });
    }
}
