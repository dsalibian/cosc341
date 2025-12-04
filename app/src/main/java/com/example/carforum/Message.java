package com.example.carforum;

public class Message {
    private final String sender;
    private final String body;
    private final boolean isOwn;

    public Message(String sender, String body, boolean isOwn) {
        this.sender = sender;
        this.body = body;
        this.isOwn = isOwn;
    }

    public String getSender() { return sender; }
    public String getBody() { return body; }
    public boolean isOwn() { return isOwn; }
}
