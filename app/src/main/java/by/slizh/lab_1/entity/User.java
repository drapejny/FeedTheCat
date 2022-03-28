package by.slizh.lab_1.entity;

import java.util.List;

public class User {

    private String id;
    private String name;
    private int highScore;
    private List<Achievement> achievements;

    public User(String id, String name, int highScore, List<Achievement> achievements) {
        this.id = id;
        this.name = name;
        this.highScore = highScore;
        this.achievements = achievements;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", highScore=" + highScore +
                ", achievements=" + achievements +
                '}';
    }
}
