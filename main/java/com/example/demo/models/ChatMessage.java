package com.example.demo.models;

//import java.time.LocalDateTime;

public class ChatMessage {
    private String userId;
    private String message;
    private String timestamp;

    //Default constructor (needed for serialization)
    public ChatMessage() {
    }

    //Constructor matching your usage
    public ChatMessage(String userId, String message, String timestamp) {
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
    }

    //Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
