package com.example.carforum;

import java.util.ArrayList;

public class Post {
    private String title, author, content;
    private ArrayList<String> tags, comments;
    private int likes;

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String title) { this.author = author; }
    public void setContent(String title) { this.content = content; }
    public void addTag(String tag) { this.tags.add(tag); }
    public void addComment(String comment) { this.comments.add(comment); }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getContent() { return content; }
    public ArrayList<String> getTags() { return tags; }
    public ArrayList<String> getComments() { return comments; }
    public int getLikes() { return likes; }
}
