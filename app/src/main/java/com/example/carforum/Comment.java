package com.example.carforum;

/**
 * Simple value object representing a comment and its author.
 */
public class Comment {
    private final String author;
    private final String body;

    public Comment(String author, String body) {
        this.author = author == null || author.trim().isEmpty() ? "Anonymous" : author.trim();
        this.body = body == null ? "" : body;
    }

    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }
}
