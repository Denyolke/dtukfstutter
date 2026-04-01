package com.example.stutter.model;

public class Level {
    public String id;
    public String topicId;
    public int levelNumber;
    public String title;
    public int questionsCount;
    public boolean completed;
    public int xpReward;
    public String difficulty;

    public Level() {

    }

    public Level(String id, String topicId, int levelNumber, String title, 
                 int questionsCount, boolean completed, String difficulty) {
        this.id = id;
        this.topicId = topicId;
        this.levelNumber = levelNumber;
        this.title = title;
        this.questionsCount = questionsCount;
        this.completed = completed;
        this.xpReward = 15; // Fixed 15 XP per level
        this.difficulty = difficulty;
    }
}
