package org.alibaby.Model;

import java.time.LocalDateTime;

public class Message {
    LocalDateTime localDateTime;
    String sentTo;
    String sendFrom;

    Message(String sentTo, String sendFrom){
        localDateTime = LocalDateTime.now();
        this.sentTo = sentTo;
        this.sendFrom = sendFrom;
    }
}
