package com.example.carforum;

public final class UserSession {

    private static String currentUser = "Guest";

    private UserSession() { }

    public static void setCurrentUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            currentUser = "Guest";
        } else {
            currentUser = username.trim();
        }
    }

    public static String getCurrentUser() {
        return currentUser;
    }
}
