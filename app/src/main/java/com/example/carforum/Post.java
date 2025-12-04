package com.example.carforum;

import java.util.ArrayList;

public class Post {
    private final String id;
    private String title, author, content;
    private ArrayList<String> tags, comments;
    private int likes;

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setContent(String content) { this.content = content; }
    public void setTags(ArrayList<String> tags) {
        this.tags.clear();
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }
    public void addTag(String tag) { this.tags.add(tag); }
    public void setComments(ArrayList<String> comments) {
        this.comments.clear();
        if (comments != null) {
            this.comments.addAll(comments);
        }
    }
    public void addComment(String comment) { this.comments.add(comment); }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getContent() { return content; }
    public ArrayList<String> getTags() { return tags; }
    public ArrayList<String> getComments() { return comments; }
    public int getLikes() { return likes; }

    public Post(String title, String author, String content) {
        this.id       = java.util.UUID.randomUUID().toString();
        this.title    = title;
        this.author   = author;
        this.content  = content;
        this.tags     = new ArrayList<String>();
        this.comments = new ArrayList<String>();
        this.likes    = 0;
    }
}
