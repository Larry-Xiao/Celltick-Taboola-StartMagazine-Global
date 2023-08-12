package com.celltick.apac.news.model;

public class SoccerScoreInfo {

    private String teamId;
    private String scr;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getScr() {
        return scr;
    }

    public void setScr(String scr) {
        this.scr = scr;
    }

    @Override
    public String toString() {
        return "SoccerScoreInfo{" +
                "teamId='" + teamId + '\'' +
                ", scr='" + scr + '\'' +
                '}';
    }
}
