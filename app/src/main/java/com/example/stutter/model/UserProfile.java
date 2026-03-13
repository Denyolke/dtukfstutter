package com.example.stutter.model;

public class UserProfile {
    public String userId;
    public String username;
    public String email;
    public int totalXP;
    public int streak;
    public int completedLessons;
    public long lastActivityDate; // Timestamp of last quiz completion (for streak tracking)

    public UserProfile() {
        // Empty constructor needed for Firestore deserialization
    }

    public UserProfile(String userId, String username, String email, int totalXP, int streak, int completedLessons) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.totalXP = totalXP;
        this.streak = streak;
        this.completedLessons = completedLessons;
        this.lastActivityDate = 0; // Initialize to 0
    }
}
