package com.example.carforum;

public class NotificationItem {
    private final String title;
    private final String subtitle;
    private final String timeAgo;
    private final int iconRes;
    private final int dotColor;

    public NotificationItem(String title, String subtitle, String timeAgo, int iconRes, int dotColor) {
        this.title = title;
        this.subtitle = subtitle;
        this.timeAgo = timeAgo;
        this.iconRes = iconRes;
        this.dotColor = dotColor;
    }

    public String getTitle() { return title; }
    public String getSubtitle() { return subtitle; }
    public String getTimeAgo() { return timeAgo; }
    public int getIconRes() { return iconRes; }
    public int getDotColor() { return dotColor; }
}
