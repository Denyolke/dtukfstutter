package com.example.stutter.model;

public class DailyQuest {
    public String questId;
    public String title;
    public String description;
    public int target;
    public int progress;
    public int rewardCoins;
    public boolean completed;
    public String dateKey;   // "YYYY-MM-DD"
    public String type;      // "complete_levels" | "earn_xp" | "answer_correct" | "complete_topic" | "login_streak" | "complete_quiz_perfect"

    public DailyQuest() {}

    public DailyQuest(String questId, String title, String description,
                      int target, int rewardCoins, String type, String dateKey) {
        this.questId     = questId;
        this.title       = title;
        this.description = description;
        this.target      = target;
        this.progress    = 0;
        this.rewardCoins = rewardCoins;
        this.completed   = false;
        this.type        = type;
        this.dateKey     = dateKey;
    }
}
