package com.example.stutter.model;

public class UserProfile {
    public String userId;
    public String username;
    public String email;
    public int totalXP;
    public int streak;
    public int completedLessons;
    public long lastActivityDate;
    public int pfp; // 1–6

    // Economy
    public int coins;
    public int xpBoosterCount;
    public double activeXpMultiplier;
    public String lastQuestDate;      // "yyyy-MM-dd" — gates daily quest generation

    // Streak — the CET date string of the last day a quiz was completed ("yyyy-MM-dd")
    // This replaces the old timestamp-based approach entirely.
    public String lastCoveredDate;

    public UserProfile() {}

    public UserProfile(String userId, String username, String email,
                       int totalXP, int streak, int completedLessons) {
        this.userId            = userId;
        this.username          = username;
        this.email             = email;
        this.totalXP           = totalXP;
        this.streak            = streak;
        this.completedLessons  = completedLessons;
        this.lastActivityDate  = 0;
        this.pfp               = 1;

        this.coins              = 0;
        this.xpBoosterCount     = 0;
        this.activeXpMultiplier = 1.0;
        this.lastQuestDate      = "";
        this.lastCoveredDate    = "";
    }
}
