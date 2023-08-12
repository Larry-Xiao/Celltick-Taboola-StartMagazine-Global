package com.celltick.apac.news.util;

public class ApiUrl {

    public static String getMagazineBaseURL() { return Constant.SM_BASIC_URL;}
    public static String getSCBaseURL() { return Constant.SM_SC_BASIC_URL;}
    public static String getMagazineSearchBaseURL() { return Constant.SM_BASIC_SEARCH_URL;}
    public static String getCricketCurrentScheduleURL() { return Constant.SM_CRICKET_CURRENT_SCHEDULE_URL;}
    public static String getSoccerCurrentScheduleURL() { return Constant.SM_SOCCER_CURRENT_SCHEDULE_URL;}
    public static String getVideoBaseURL() {
        return Constant.SM_VIDEO;
    }

    public static String getVideoURL(String title){
        String URL = "";
        switch (title){
            case "002":
                URL = Constant.SM_VIDEO;
                break;
            case "SecondFragment":
                URL = Constant.SM_VIDEO;
                break;
            case "Top":
                URL = Constant.SM_VIDEO;
                break;
            case "News":
                URL = Constant.SM_VIDEO + "&category="+13;
                break;
            case "Celebrities":
                URL = Constant.SM_VIDEO + "&category="+17;
                break;
            case "Entertainment":
                URL = Constant.SM_VIDEO + "&category="+12;
                break;
            case "Sports":
                URL = Constant.SM_VIDEO + "&category="+7;
                break;
            case "Health":
                URL = Constant.SM_VIDEO + "&category="+24;
                break;
            case "Finance":
                URL = Constant.SM_VIDEO + "&category="+48;
                break;
            case "Hobby":
                URL = Constant.SM_VIDEO + "&category="+47;
                break;
            case "Lifestyle":
                URL = Constant.SM_VIDEO + "&category="+42;
                break;
            case "Astrology":
                URL = Constant.SM_VIDEO + "&category="+73;
                break;
            case "Hollywood":
                URL = Constant.SM_VIDEO + "&tag=epus-movies";
                break;
            case "Bollywood":
                URL = Constant.SM_VIDEO + "&tag=epin_en-movies";
                break;
            case "Food":
                URL = Constant.SM_VIDEO + "&category="+77;
                break;
            case "Society":
                URL = Constant.SM_VIDEO + "&category="+15;
                break;
            case "Women's guide":
                URL = Constant.SM_VIDEO + "&category="+30;
                break;
            case "Technology":
                URL = Constant.SM_VIDEO + "&category="+9;
                break;
            case "Viral":
                URL = Constant.SM_VIDEO + "&category="+92;
                break;
            case "Humor":
                URL = Constant.SM_VIDEO + "&category="+71;
                break;
            case "Tourism":
                URL = Constant.SM_VIDEO + "&category="+85;
                break;
            case "Cars":
                URL = Constant.SM_VIDEO + "&category="+61;
                break;
        }

        return URL;
    }

    public static String getVideoBottomRequestURL(String title, int offset){
        return getVideoURL(title)+ "&limit=" + Constant.NUM_SM_NORMAL_REQUEST + "&offset=" + offset;
    }


    public static String getMagazineBaseURL(String title){
        String URL = "";
        switch (title){
            case "Top":
                URL = Constant.SM_BASIC_URL;
                break;
            case "World Cup":
                URL = Constant.SM_WORLDCUP;
                break;
            case "FWC Video":
                URL = Constant.SM_FWC_VIDEO;
                break;
            case "Video":
                URL = Constant.SM_VIDEO;
                break;
            case "Hollywood Video":
                URL = Constant.SM_HOLLYWOOD_VIDEO;
                break;
            case "Bollywood Video":
                URL = Constant.SM_BOLLYWOOD_VIDEO;
                break;
            case "World":
                URL = Constant.SM_WORLD;
                break;
            case "Sport":
                URL = Constant.SM_SPORT;
                break;
            case "Entertainment":
                URL = Constant.SM_ENTERTAINMENT;
                break;
            case "Tech":
                URL = Constant.SM_TECH;
                break;
            case "Celebrities":
                URL = Constant.SM_CELEBRITY;
                break;
            case "Life":
                URL = Constant.SM_LIFE;
                break;
            case "Finance":
                URL = Constant.SM_FINANCE;
                break;
            case "Holidays":
//                URL = Constant.JUHE_CAIJING;
//                URL = getURL_SW_ALL_FINANCE();
                break;
            case "Fashion":
//                URL = Constant.JUHE_SHISHANG;
//                URL = getURL_SW_ALL_LIFE();
                break;
        }

        return URL;
    }

    public static String getBottomRequestURL(String title, int offset){

        if (title.equals("Top")) {
            return Constant.SM_BASIC_URL + "&limit=" + Constant.NUM_SM_NORMAL_REQUEST + "&offset=" + offset;
        } else if (title.equals("World")) {
            return Constant.SM_WORLD + "&limit=" + Constant.NUM_SM_NORMAL_REQUEST + "&offset=" + offset;
        } else if (title.equals("FWC Video")){
            return Constant.SM_FWC_VIDEO + "&limit=" + Constant.NUM_SM_NORMAL_REQUEST + "&offset=" + offset;
        } else if (title.equals("Video")) {
            return Constant.SM_VIDEO + "&limit=" + Constant.NUM_SM_NORMAL_REQUEST + "&offset=" + offset;
        } else if (title.equals("Hollywood Video")) {
            return Constant.SM_HOLLYWOOD_VIDEO + "&limit=" + Constant.NUM_SM_NORMAL_REQUEST + "&offset=" + offset;
        } else if (title.equals("Bollywood Video")) {
            return Constant.SM_BOLLYWOOD_VIDEO + "&limit=" + Constant.NUM_SM_NORMAL_REQUEST + "&offset=" + offset;
        } else if (title.equals("Sport")) {
            return Constant.SM_SPORT + "&limit=" + Constant.NUM_SM_NORMAL_REQUEST + "&offset=" + offset;
        } else if (title.equals("Entertainment")) {
            return Constant.SM_ENTERTAINMENT + "&limit=" + Constant.NUM_SM_NORMAL_REQUEST + "&offset=" + offset;
        } else if (title.equals("Tech")) {
            return Constant.SM_TECH + "&limit=" + Constant.NUM_SM_NORMAL_REQUEST + "&offset=" + offset;
        } else if (title.equals("Celebrities")) {
            return Constant.SM_CELEBRITY + "&limit=" + Constant.NUM_SM_NORMAL_REQUEST + "&offset=" + offset;
        } else if (title.equals("Life")) {
            return Constant.SM_LIFE + "&limit=" + Constant.NUM_SM_NORMAL_REQUEST + "&offset=" + offset;
        } else if (title.equals("Finance")) {
            return Constant.SM_FINANCE + "&limit=" + Constant.NUM_SM_NORMAL_REQUEST + "&offset=" + offset;
        }

        return "";

    }

    public static String getFWCContentURL() {return Constant.SM_WORLDCUP;}

    public static String getCategoryListURL() {return Constant.SM_CATEGORY_LIST;}


}
