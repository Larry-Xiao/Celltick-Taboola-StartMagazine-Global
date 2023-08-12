package com.celltick.apac.news.model;

import java.util.List;

public class SoccerInfo {

    private String gameID;
    private String leageName;
    private String season;
    private String stage;
    private String status;
    private String gameTime;
    private String gamePageURL;

    private List<SoccerTeamInfo> teams;
    private List<SoccerScoreInfo> scores;

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getLeageName() {
        return leageName;
    }

    public void setLeageName(String leageName) {
        this.leageName = leageName;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    public String getGamePageURL() {
        return gamePageURL;
    }

    public void setGamePageURL(String gamePageURL) {
        this.gamePageURL = gamePageURL;
    }

    public List<SoccerTeamInfo> getTeams() {
        return teams;
    }

    public void setTeams(List<SoccerTeamInfo> teams) {
        this.teams = teams;
    }

    public List<SoccerScoreInfo> getScores() {
        return scores;
    }

    public void setScores(List<SoccerScoreInfo> scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "SoccerInfo{" +
                "gameID='" + gameID + '\'' +
                ", leageName='" + leageName + '\'' +
                ", season='" + season + '\'' +
                ", stage='" + stage + '\'' +
                ", status='" + status + '\'' +
                ", gameTime='" + gameTime + '\'' +
                ", gamePageURL='" + gamePageURL + '\'' +
                ", teams=" + teams +
                ", scores=" + scores +
                '}';
    }
}
