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

public class BaybayinTransliteratorGUI {

    private static JFrame frame;
    private static JTextField textInput;
    private static JLabel textOutput;

    public BaybayinTransliteratorGUI() {
        frame = new JFrame("Baybayin Transliterator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try{
            URI path = Thread.currentThread().getContextClassLoader().getResource("TintangBaybayin.otf").toURI();
            Font fixed;
            File myFontFile = new File(path);
            fixed = Font.createFont(Font.TRUETYPE_FONT, myFontFile);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fixed);
            Map attributes = fixed.getAttributes();
            attributes.put(TextAttribute.SIZE, 24   );
            attributes.put(TextAttribute.LIGATURES, TextAttribute.LIGATURES_ON);
            fixed = fixed.deriveFont(attributes);
            textInput = new JTextField();
            textOutput = new JLabel("");
            textOutput.setFont(fixed);
        } catch (FontFormatException | IOException e) {
          e.printStackTrace();
        } catch (URISyntaxException e1) {
         e1.printStackTrace();
        }



        JButton transliterateButton = new JButton("Transliterate");
        transliterateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textInput.getText();
                String baybayin = translate(text);
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

    static Map<String, String> tagalogToBaybayin = new HashMap<>();
    {

        //consonants + vowels

        tagalogToBaybayin.put("nga", "\u1705");
        tagalogToBaybayin.put("ngi", "\u1705\u1712");
        tagalogToBaybayin.put("ngu", "\u1705\u1713");
        tagalogToBaybayin.put("ng", "\u1705\u1714");

        tagalogToBaybayin.put("ka", "\u1703");
        tagalogToBaybayin.put("ga", "\u1704");
        tagalogToBaybayin.put("ta", "\u1706");
        tagalogToBaybayin.put("da", "\u1707");
        tagalogToBaybayin.put("na", "\u1708");
        tagalogToBaybayin.put("pa", "\u1709");
        tagalogToBaybayin.put("fa", "\u1709");
        tagalogToBaybayin.put("ba", "\u170A");
        tagalogToBaybayin.put("ma", "\u170B");
        tagalogToBaybayin.put("ya", "\u170C");
        tagalogToBaybayin.put("ra", "\u1707");
        tagalogToBaybayin.put("la", "\u170E");
        tagalogToBaybayin.put("wa", "\u170F");
        tagalogToBaybayin.put("sa", "\u1710");
        tagalogToBaybayin.put("ha", "\u1711");

        tagalogToBaybayin.put("ki", "\u1703\u1712");
        tagalogToBaybayin.put("gi", "\u1704\u1712");
        tagalogToBaybayin.put("ti", "\u1706\u1712");
        tagalogToBaybayin.put("di", "\u1707\u1712");
        tagalogToBaybayin.put("ni", "\u1708\u1712");
        tagalogToBaybayin.put("pi", "\u1709\u1712");
        tagalogToBaybayin.put("bi", "\u170A\u1712");
        tagalogToBaybayin.put("mi", "\u170B\u1712");
        tagalogToBaybayin.put("yi", "\u170C\u1712");
        tagalogToBaybayin.put("ri", "\u1707\u1712");
        tagalogToBaybayin.put("li", "\u170E\u1712");
        tagalogToBaybayin.put("wi", "\u170F\u1712");
        tagalogToBaybayin.put("si", "\u1710\u1712");
        tagalogToBaybayin.put("hi", "\u1711\u1712");

        tagalogToBaybayin.put("ku", "\u1703\u1713");
        tagalogToBaybayin.put("gu", "\u1704\u1713");
        tagalogToBaybayin.put("tu", "\u1706\u1713");
        tagalogToBaybayin.put("du", "\u1707\u1713");
        tagalogToBaybayin.put("nu", "\u1708\u1713");
        tagalogToBaybayin.put("pu", "\u1709\u1713");
        tagalogToBaybayin.put("bu", "\u170A\u1713");
        tagalogToBaybayin.put("mu", "\u170B\u1713");
        tagalogToBaybayin.put("yu", "\u170C\u1713");
        tagalogToBaybayin.put("ru", "\u1707\u1713");
        tagalogToBaybayin.put("lu", "\u170E\u1713");
        tagalogToBaybayin.put("wu", "\u170F\u1713");
        tagalogToBaybayin.put("su", "\u1710\u1713");
        tagalogToBaybayin.put("hu", "\u1711\u1713");

        //vowels
        tagalogToBaybayin.put("a", "\u1700");
        tagalogToBaybayin.put("e", "\u1701");
        tagalogToBaybayin.put("i", "\u1701");
        tagalogToBaybayin.put("o", "\u1702");
        tagalogToBaybayin.put("u", "\u1702");

        //consonants
        tagalogToBaybayin.put("k", "\u1703\u1714");
        tagalogToBaybayin.put("c", "\u1703\u1714");
        tagalogToBaybayin.put("q", "\u1703\u1714");
        tagalogToBaybayin.put("g", "\u1704\u1714");
        tagalogToBaybayin.put("t", "\u1706\u1714");
        tagalogToBaybayin.put("d", "\u1707\u1714");
        tagalogToBaybayin.put("n", "\u1708\u1714");
        tagalogToBaybayin.put("p", "\u1709\u1714");
        tagalogToBaybayin.put("f", "\u1709\u1714");
        tagalogToBaybayin.put("v", "\u170a\u1714");
        tagalogToBaybayin.put("b", "\u170a\u1714");
        tagalogToBaybayin.put("m", "\u170b\u1714");
        tagalogToBaybayin.put("y", "\u170c\u1714");
        tagalogToBaybayin.put("r", "\u1707\u1714");
        tagalogToBaybayin.put("l", "\u170e\u1714");
        tagalogToBaybayin.put("w", "\u170f\u1714");
        tagalogToBaybayin.put("s", "\u1710\u1714");
        tagalogToBaybayin.put("x", "\u1710\u1714");
        tagalogToBaybayin.put("z", "\u1710\u1714");
        tagalogToBaybayin.put("h", "\u1711\u1714");
        tagalogToBaybayin.put("z", "\u1711\u1714");

        tagalogToBaybayin.put("  ", "\u0020");


    }
    //public static String transliterate(String text) {
        //StringBuilder baybayinText = new StringBuilder();
        //int index = 0;
        ///while (index < text.length()) {
            // Try to match longer substrings first
            //for (int len = Math.min(3, text.length() - index); len > 0; len--) {
              //  String substring = text.substring(index, index + len);
               // if (tagalogToBaybayin.containsKey(substring)) {
                //    baybayinText.append(tagalogToBaybayin.get(substring));
                //    index += len;
                //    break;
               // }
          //  }
           // if (index < text.length()) {
             //    If no match is found, append the current character as is
            //    baybayinText.append(text.charAt(index));
            //    index++;
           // }
       // }
      //  return baybayinText.toString();
    //}

    public static String translate(String rawText) {
        rawText = rawText.toLowerCase();

        // Replace "e" with "i"
        rawText = rawText.replace("e", "i");

        // Replace "o" with "u"
        rawText = rawText.replace("o", "u");
        StringBuilder baybayinText = new StringBuilder();
        int index = 0;

        while (index < rawText.length()) {
            boolean matchFound = false;
            // Try to match longer substrings first
            // Try to match substrings of length 3
            if (index + 3 <= rawText.length()) {
                String substring3 = rawText.substring(index, index + 3);
                if (tagalogToBaybayin.containsKey(substring3)) {
                    baybayinText.append(tagalogToBaybayin.get(substring3));
                    index += 3;
                    matchFound = true;
                }
            }

            // Try to match substrings of length 2
            if (!matchFound && index + 2 <= rawText.length()) {
                String substring2 = rawText.substring(index, index + 2);
                if (tagalogToBaybayin.containsKey(substring2)) {
                    baybayinText.append(tagalogToBaybayin.get(substring2));
                    index += 2;
                    matchFound = true;
                }
            }

            // Try to match substrings of length 1
            if (!matchFound) {
                String substring1 = rawText.substring(index, index + 1);
                if (tagalogToBaybayin.containsKey(substring1)) {
                    baybayinText.append(tagalogToBaybayin.get(substring1));
                    index += 1;
                } else {
                    baybayinText.append(substring1);
                    index += 1;
                }
            }
        }

        // Replace Tagalog substrings with Baybayin characters

        return baybayinText.toString();
    }

    public static void main(String[] args) {
        new BaybayinTransliteratorGUI();
    }
}