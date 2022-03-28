package by.slizh.lab_1.entity;

import java.time.LocalDateTime;

public class Result {

    private LocalDateTime dateTime;
    private String userId;
    private String userName;
    private int score;

    public Result(LocalDateTime dateTime, String userId, String userName, int score) {
        this.dateTime = dateTime;
        this.userId = userId;
        this.userName = userName;
        this.score = score;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Result{" +
                "dateTime=" + dateTime +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", score=" + score +
                '}';
    }
}
