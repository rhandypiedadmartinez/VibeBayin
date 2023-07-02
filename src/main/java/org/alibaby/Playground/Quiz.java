package org.alibaby.Playground;

import org.alibaby.Model.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.*;

import java.net.*;

public class Quiz {
    public ArrayList<Question> quizzes;

    public Quiz(){
        quizzes = new ArrayList<Question>();

        URI path = null;
        File csvFile = null;
            
        try {
            path = Thread.currentThread().getContextClassLoader().getResource("quiz.csv").toURI();
            csvFile = new File(path);
        } catch (URISyntaxException e){

        }
        
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(csvSplitBy);

                quizzes.add(Question.fromCSVToQuestion(line));

                for (String value : values) {
                    System.out.print(value + " ");
                }
                
                System.out.println();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
       Quiz qz = new Quiz();
       for(Question q: qz.quizzes){
            Question.printQuestion(q);
       }
    }
}
