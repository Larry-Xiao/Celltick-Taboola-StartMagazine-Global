package com.celltick.apac.news.model;

import java.util.List;

public class CricketInfo {

    private String matchId;
    private String matchFile;
    private String startTime;
    private String endTime;
    private String status;
    private String type;
    private String stage;
    private String competition;
    private String matchNumber;
    private String gamePageURL;
    private String result;
    private String winMargin;
    private String tossElected;
    private boolean isLive;

    private List<CricketTeamInfo> teams;
    private List<CricketScoreInfo> scores;


    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getMatchFile() {
        return matchFile;
    }

    public void setMatchFile(String matchFile) {
        this.matchFile = matchFile;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public String getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(String matchNumber) {
        this.matchNumber = matchNumber;
    }

    public String getGamePageURL() {
        return gamePageURL;
    }

    public void setGamePageURL(String gamePageURL) {
        this.gamePageURL = gamePageURL;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getWinMargin() {
        return winMargin;
    }

    public void setWinMargin(String winMargin) {
        this.winMargin = winMargin;
    }

    public String getTossElected() {
        return tossElected;
    }

    public void setTossElected(String tossElected) {
        this.tossElected = tossElected;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public List<CricketTeamInfo> getTeams() {
        return teams;
    }

    public void setTeams(List<CricketTeamInfo> teams) {
        this.teams = teams;
    }

    public List<CricketScoreInfo> getScores() {
        return scores;
    }

    public void setScores(List<CricketScoreInfo> scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "CricketInfo{" +
                "matchId='" + matchId + '\'' +
                ", matchFile='" + matchFile + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", stage='" + stage + '\'' +
                ", competition='" + competition + '\'' +
                ", matchNumber='" + matchNumber + '\'' +
                ", gamePageURL='" + gamePageURL + '\'' +
                ", result='" + result + '\'' +
                ", winMargin='" + winMargin + '\'' +
                ", tossElected='" + tossElected + '\'' +
                ", isLive=" + isLive +
                ", teams=" + teams +
                ", scores=" + scores +
                '}';
    }
}