package com.example.stutter.model;

public class Topic {
    public String id;
    public String title;
    public String description;
    public String icon;
    public boolean completed;
    public int completedLessons;
    public int totalLessons;

    public Topic() {}

    public Topic(String id, String title, String description, String icon,
                 boolean completed, int completedLessons, int totalLessons) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.completed = completed;
        this.completedLessons = completedLessons;
        this.totalLessons = totalLessons;
    }
}
