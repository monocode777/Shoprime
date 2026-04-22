package com.example.shoprime;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private String senderEmail;
    private String message;
    private long timestamp;

    public ChatMessage(String senderEmail, String message, long timestamp) {
        this.senderEmail = senderEmail;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getSenderEmail() { return senderEmail; }
    public String getMessage() { return message; }
    public long getTimestamp() { return timestamp; }
}