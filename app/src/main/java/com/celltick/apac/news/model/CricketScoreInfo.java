package com.celltick.apac.news.model;

public class CricketScoreInfo {

    private int teamId;
    private String score;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "CricketScoreInfo{" +
                "teamId=" + teamId +
                ", score='" + score + '\'' +
                '}';
    }
}
