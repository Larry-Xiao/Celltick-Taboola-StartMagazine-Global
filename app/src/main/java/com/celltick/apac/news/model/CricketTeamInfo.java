package com.celltick.apac.news.model;

public class CricketTeamInfo {

    private int id;
    private String name;
    private String logoURL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    @Override
    public String toString() {
        return "CricketTeamInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", logoURL='" + logoURL + '\'' +
                '}';
    }
}
