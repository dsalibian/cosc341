package com.example.carforum;

import java.util.ArrayList;
import java.util.UUID;

public final class MessageRepository {

    private static final ArrayList<MessageThread> THREADS = new ArrayList<>();

    private MessageRepository() { }

    public static void ensureSeeded() {
        if (!THREADS.isEmpty()) return;

        MessageThread alex = new MessageThread(UUID.randomUUID().toString(), "Alex");
        alex.addMessage(new Message("Alex", "Hey, did you see the new coilovers post?", false));
        alex.addMessage(new Message("You", "Yep, adding it to my watch list.", true));
        alex.addMessage(new Message("Alex", "Cool, let's chat later about install tips.", false));
        THREADS.add(alex);

        MessageThread sara = new MessageThread(UUID.randomUUID().toString(), "Sara");
        sara.addMessage(new Message("Sara", "Loved your build thread!", false));
        sara.addMessage(new Message("You", "Thanks! Still tweaking suspension.", true));
        THREADS.add(sara);

        MessageThread parts = new MessageThread(UUID.randomUUID().toString(), "PartsSeller");
        parts.addMessage(new Message("PartsSeller", "I can ship the intake by Friday.", false));
        THREADS.add(parts);
    }

    public static ArrayList<MessageThread> getThreads() {
        return new ArrayList<>(THREADS);
    }

    public static MessageThread getThreadById(String id) {
        for (MessageThread t : THREADS) {
            if (t.getId().equals(id)) return t;
        }
        return null;
    }

    public static MessageThread createOrGetThread(String contact) {
        for (MessageThread t : THREADS) {
            if (t.getContact().equalsIgnoreCase(contact)) return t;
        }
        MessageThread t = new MessageThread(UUID.randomUUID().toString(), contact);
        THREADS.add(t);
        return t;
    }

    public static void addMessage(String threadId, Message message) {
        MessageThread thread = getThreadById(threadId);
        if (thread != null) {
            thread.addMessage(message);
        }
    }
}
