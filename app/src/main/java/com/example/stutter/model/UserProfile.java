package com.example.stutter.model;

public class UserProfile {
    public String userId;
    public String username;
    public String email;
    public int totalXP;
    public int streak;
    public int completedLessons;
    public long lastActivityDate;
    public int pfp; // 1 to 6

    public UserProfile() {
    }

    public UserProfile(String userId, String username, String email, int totalXP, int streak, int completedLessons) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.totalXP = totalXP;
        this.streak = streak;
        this.completedLessons = completedLessons;
        this.lastActivityDate = 0;
        this.pfp = 1; // default profile picture
    }
}