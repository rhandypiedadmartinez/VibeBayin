package org.alibaby.Playground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaybayinTransliteratorGUI {

    private static JFrame frame;
    private static JTextField textInput;
    private static JLabel textOutput;

    public BaybayinTransliteratorGUI() {
        frame = new JFrame("Baybayin Transliterator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //try{
            //URI path = Thread.currentThread().getContextClassLoader().getResource("TagDoc93.ttf").toURI();
            //Font fixed;
            //File myFontFile = new File(path);
            //fixed = Font.createFont(Font.TRUETYPE_FONT, myFontFile);
            //GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fixed);
            //Map attributes = fixed.getAttributes();
           // attributes.put(TextAttribute.SIZE, 24   );
           // attributes.put(TextAttribute.LIGATURES, TextAttribute.LIGATURES_ON);
           // fixed = fixed.deriveFont(attributes);
           //
           // textOutput.setFont(fixed);
        //} catch (FontFormatException | IOException e) {
          //  e.printStackTrace();
        //} catch (URISyntaxException e1) {
           // e1.printStackTrace();
       //}

        textInput = new JTextField();
        textOutput = new JLabel("");

        JButton transliterateButton = new JButton("Transliterate");
        transliterateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textInput.getText();
                String baybayin = transliterate(text);
                textOutput.setText(baybayin);
            }
        });

        Box box = Box.createVerticalBox();
        box.add(textInput);
        box.add(transliterateButton);
        box.add(textOutput);

        frame.add(box);
        frame.pack();
        frame.setVisible(true);
    }

    static Map<Character, String> tagalogToBaybayin = new HashMap<>();
    {
        tagalogToBaybayin.put('a', "\u1700");
        tagalogToBaybayin.put('b', "\u170A");
        tagalogToBaybayin.put('c', "\u1703");
        tagalogToBaybayin.put('d', "\u1707\u1712");
        tagalogToBaybayin.put('e', "\u1701");
        tagalogToBaybayin.put('f', "\u1709");
        tagalogToBaybayin.put('g', "\u1704");
        tagalogToBaybayin.put('h', "\u1711");
        tagalogToBaybayin.put('i', "\u1701");
        tagalogToBaybayin.put('j', "j");
        tagalogToBaybayin.put('k', "\u1703");
        tagalogToBaybayin.put('l', "\u170e");
        tagalogToBaybayin.put('m', "\u170b");
        tagalogToBaybayin.put('n', "\u1708");
        tagalogToBaybayin.put('o', "o");
        tagalogToBaybayin.put('p', "\u1709");
        tagalogToBaybayin.put('q', "q");
        tagalogToBaybayin.put('r', "\u170D");
        tagalogToBaybayin.put('s', "\u1710");
        tagalogToBaybayin.put('t', "\u1706");
        tagalogToBaybayin.put('u', "\u1702");
        tagalogToBaybayin.put('v', "\u170a");
        tagalogToBaybayin.put('w', "\u170f");
        tagalogToBaybayin.put('x', "x");
        tagalogToBaybayin.put('y', "\u170c");
        tagalogToBaybayin.put('z', "\u1710");
    }
    private static String transliterate(String text) {
        StringBuilder tagalog = new StringBuilder();
        for (char c : text.toCharArray()) {
            tagalog.append(BaybayinTransliteratorGUI.tagalogToBaybayin.get(c));
        }
        return tagalog.toString();
    }

    public static void main(String[] args) {
        new BaybayinTransliteratorGUI();
    }
}