package com.celltick.apac.news.model;

public class SoccerTeamInfo {

    private String teamId;
    private String teamName;
    private String teamLogo;
    private String sportTypeId;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamLogo() {
        return teamLogo;
    }

    public void setTeamLogo(String teamLogo) {
        this.teamLogo = teamLogo;
    }

    public String getSportTypeId() {
        return sportTypeId;
    }

    public void setSportTypeId(String sportTypeId) {
        this.sportTypeId = sportTypeId;
    }

    @Override
    public String toString() {
        return "SoccerTeamInfo{" +
                "teamId='" + teamId + '\'' +
                ", teamName='" + teamName + '\'' +
                ", teamLogo='" + teamLogo + '\'' +
                ", sportTypeId='" + sportTypeId + '\'' +
                '}';
    }
}
