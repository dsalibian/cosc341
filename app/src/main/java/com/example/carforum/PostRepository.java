package com.example.carforum;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple in-memory repository so activities can share the same post list.
 */
public final class PostRepository {

    private static final ArrayList<Post> POSTS = new ArrayList<>();

    private PostRepository() { }

    public static void ensureSeeded() {
        if (!POSTS.isEmpty()) {
            return;
        }

        Post welcome = new Post("Welcome to Car Forum", "Admin",
                "Share your builds, ask for help, and show off new parts.");
        welcome.addTag("welcome");
        welcome.addTag("builds");
        welcome.addComment("Drop your ride photos below!");
        POSTS.add(welcome);

        Post oil = new Post("DIY: How to change car oil", "GarageGuru",
                "Step-by-step guide with tools, oil weights, and torque specs. Great for first-timers.");
        oil.addTag("maintenance");
        oil.addTag("oil");
        oil.addComment("Remember to replace the crush washer!");
        POSTS.add(oil);

        Post buying = new Post("What car should I buy?", "CarEnthusiast92",
                "Looking for a fun daily under $15k, good on gas but still engaging. Suggestions?");
        buying.addTag("buying");
        buying.addTag("advice");
        buying.addComment("Mazda3 hatch with the 2.5 is a solid pick.");
        buying.addComment("Check insurance on GTI before committing.");
        POSTS.add(buying);

        Post winter = new Post("Winter tire recommendations needed", "KelownaDriver",
                "Snow is coming early this year. Need tire suggestions for a 2018 Civic in BC.");
        winter.addTag("tires");
        winter.addTag("winter");
        POSTS.add(winter);

        Post compact = new Post("Best compact cars of the year", "AutoCritic",
                "Rundown of the top compacts by fuel economy, safety, and tech. Ranking inside.");
        compact.addTag("reviews");
        compact.addTag("compact");
        POSTS.add(compact);

        Post tesla = new Post("Tesla Model 3 vs Model Y comparison", "EVFan",
                "Real-world range, cargo space, ride quality, and heat pump performance compared.");
        tesla.addTag("ev");
        tesla.addTag("tesla");
        POSTS.add(tesla);
    }

    public static ArrayList<Post> getAll() {
        return new ArrayList<>(POSTS);
    }

    public static Post getById(String id) {
        for (Post p : POSTS) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public static void add(Post post) {
        POSTS.add(0, post);
    }

    public static boolean update(String id, String title, ArrayList<String> tags, String content) {
        Post target = getById(id);
        if (target == null) {
            return false;
        }
        target.setTitle(title);
        target.setContent(content);
        target.setTags(tags);
        return true;
    }

    public static boolean delete(String id) {
        Post target = getById(id);
        if (target == null) {
            return false;
        }
        return POSTS.remove(target);
    }
}
