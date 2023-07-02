package org.alibaby.Model;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.PropertyName;

public class Question {
    @PropertyName("question")
    public String question;

    @PropertyName("diagram")
    public String diagram;

    @PropertyName("choice1")
    public String choice1;

    @PropertyName("choice2")
    public String choice2;

    @PropertyName("choice3")
    public String choice3;

    @PropertyName("answer")
    public String answer;

    public Question() {
    }

    public Question(String question, String diagram, String choice1, String choice2, String choice3, String answer) {
        this.question = question;
        this.diagram = diagram;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.answer = answer;
    }

    public static Question fromCSVToQuestion(String csvLine) {
        String[] values = csvLine.split(",");
        String question = values[0];
        String diagram = values[1];
        String choice1 = values[2];
        String choice2 = values[3];
        String choice3 = values[4];
        String answer = values[5];
        return new Question(question, diagram, choice1, choice2, choice3, answer);
    }

    public static void printQuestion(Question qz){
        System.out.println(qz.question + " " + qz.diagram + " " + qz.choice1 + " "
                            + qz.choice2 + " " + qz.choice3 + " " + qz.answer);
    } 
}
