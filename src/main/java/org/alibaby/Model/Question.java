package org.alibaby.Model;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.PropertyName;

public class Question {
    @PropertyName("question")
    public String question;

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

    public Question(String question, String choice1, String choice2, String choice3, String answer) {
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.answer = answer;
    }

    public static Question fromCSV(String csvLine) {
        String[] values = csvLine.split(",");
        String question = values[0];
        String choice1 = values[1];
        String choice2 = values[2];
        String choice3 = values[3];
        String answer = values[4];

        return new Question(question, choice1, choice2, choice3, answer);
    }
}
