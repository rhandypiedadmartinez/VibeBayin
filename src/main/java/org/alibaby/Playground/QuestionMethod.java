package org.alibaby.Playground;

import org.alibaby.Model.Question;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class QuestionMethod extends Question {
    public QuestionMethod(String question, String choice1, String choice2, String choice3, String answer) {
        super(question, choice1, choice2, choice3, answer);
    }

    public static void main(String[] args) {

        String csvFile = "resources\\javafx-sdk-20.0.1\\lib\\quiz.csv";
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        while ((line = br.readLine()) != null) {
         String[] values = line.split(csvSplitBy);

        // Process the values as needed
        // For example, print them
         for (String value : values) {
           System.out.print(value + " ");
            }
          System.out.println();
              }
          } catch (IOException e) {
               e.printStackTrace();
          }
    }
}