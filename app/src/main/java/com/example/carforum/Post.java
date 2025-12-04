package com.example.carforum;

import java.util.ArrayList;

public class Post {
    private final String id;
    private String title, author, content;
    private ArrayList<String> tags, comments;
    private int score;
    private boolean upvoted = false;
    private boolean downvoted = false;

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
    public void setScore(int score) { this.score = score; }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getContent() { return content; }
    public ArrayList<String> getTags() { return tags; }
    public ArrayList<String> getComments() { return comments; }
    public int getScore() { return score; }

    public void upvote() {
        if (upvoted) {
            score--;
            upvoted = false;
        } else {
            score++;
            upvoted = true;
            if (downvoted) {
                score++;
                downvoted = false;
            }
        }
    }

    public void downvote() {
        if (downvoted) {
            score++;
            downvoted = false;
        } else {
            score--;
            downvoted = true;
            if (upvoted) {
                score--;
                upvoted = false;
            }
        }
    }

    public Post(String title, String author, String content) {
        this.id       = java.util.UUID.randomUUID().toString();
        this.title    = title;
        this.author   = author;
        this.content  = content;
        this.tags     = new ArrayList<String>();
        this.comments = new ArrayList<String>();
        this.score    = 0;
    }
}
