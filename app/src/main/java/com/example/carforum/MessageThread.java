package com.example.carforum;

import java.util.ArrayList;

public class MessageThread {
    private final String id;
    private final String contact;
    private final ArrayList<Message> messages;

    public MessageThread(String id, String contact) {
        this.id = id;
        this.contact = contact;
        this.messages = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getContact() { return contact; }
    public ArrayList<Message> getMessages() { return messages; }

    public void addMessage(Message message) {
        messages.add(message);
    }
}
