package org.alibaby.Playground;

import javax.swing.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class Test2 {
    public static void main(String[] args) throws URISyntaxException, FontFormatException, IOException {
        // WORKING PATTERN
        URI path = Thread.currentThread().getContextClassLoader().getResource("BayBayinNiwangUno.otf").toURI();
                
        File myFontFile = new File(path);
        Font fixed = Font.createFont(Font.TRUETYPE_FONT, myFontFile);
        
        Map attributes = fixed.getAttributes();
        attributes.put(TextAttribute.SIZE, 76);
        attributes.put(TextAttribute.LIGATURES, TextAttribute.LIGATURES_ON);
        fixed = fixed.deriveFont(attributes);


        // Create a JTextArea to display the Baybayin text
        
        JTextArea textArea = new JTextArea();
        textArea.setFont(fixed);
        //textArea.setText("ka"); // Replace with the Baybayin text you want to display
        

        // This is to correctly show the word kailangan
        textArea.setText("kI+laNn+");

        // Create a JFrame to hold the JTextArea
        JFrame frame = new JFrame();
        frame.setTitle("Baybayin Translator");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(textArea);
        frame.setVisible(true);
    }
}
