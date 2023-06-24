package org.alibaby.Model;

import com.google.cloud.Timestamp;

public class Message {
    public int to;
    public int from;
    public String message;
    public Timestamp timestamp;
    
    public Message(int from, int to, String message, Timestamp timestamp){
        this.to = to;
        this.from = from;
        this.message = message;
        this.timestamp = timestamp;   
    }
}
