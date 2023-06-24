package org.alibaby.Model;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.PropertyName;

public class Message {
    @PropertyName("to")
    public int to;

    @PropertyName("from")
    public int from;

    @PropertyName("message")
    public String message;

    @PropertyName("timestamp")
    public Timestamp timestamp;
    
    public Message(){}
    
    public Message(int from, int to, String message, Timestamp timestamp){
        this.to = to;
        this.from = from;
        this.message = message;
        this.timestamp = timestamp;   
    }
}
