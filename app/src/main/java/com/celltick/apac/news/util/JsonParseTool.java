package com.celltick.apac.news.util;

import android.util.Log;

import com.celltick.apac.news.model.ArticleBean;
import com.celltick.apac.news.model.CategoryBean;
import com.celltick.apac.news.model.CricketInfo;
import com.celltick.apac.news.model.CricketScoreInfo;
import com.celltick.apac.news.model.CricketTeamInfo;
import com.celltick.apac.news.model.SoccerInfo;
import com.celltick.apac.news.model.SoccerScoreInfo;
import com.celltick.apac.news.model.SoccerTeamInfo;
import com.celltick.apac.news.model.VideoBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Larryx on 3/7/2018.
 */
public class JsonParseTool {

    private static final String TAG = "JsonParseTool";

    public List<ArticleBean> parseJuheJson(String jsonData){
        List<ArticleBean> mArticles = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                ArticleBean articleInfo = new ArticleBean();

                String mArticleURL  = jsonObject1.getString("url");
                String mCategoryName = jsonObject1.getString("category");
                String mPubDate = jsonObject1.getString("date");
                String mArticleTitle = jsonObject1.getString("title");
                String mArticleID = jsonObject1.getString("uniquekey");
                String mPublisherName = jsonObject1.getString("author_name");

                articleInfo.setArticleURL(mArticleURL);
                articleInfo.setCategoryName(mCategoryName);
                articleInfo.setPublishedDate(mPubDate);
                articleInfo.setTitle(mArticleTitle);
                articleInfo.setUniqueKey(mArticleID);
                articleInfo.setPublisherName(mPublisherName);

                List<String> imageURLs = new ArrayList<>();
                if (jsonObject1.has("thumbnail_pic_s")){
                    imageURLs.add(jsonObject1.getString("thumbnail_pic_s"));
                }
                if (jsonObject1.has("thumbnail_pic_s02")){
                    imageURLs.add(jsonObject1.getString("thumbnail_pic_s02"));
                }
                if (jsonObject1.has("thumbnail_pic_s03")){
                    imageURLs.add(jsonObject1.getString("thumbnail_pic_s03"));
                }
                articleInfo.setImageUrls(imageURLs);

                mArticles.add(articleInfo);


                Log.d(TAG, "URL:" + mArticleURL);
                Log.d(TAG, "category:" + mCategoryName);
                Log.d(TAG, "date:" + mPubDate);
                Log.d(TAG, "title:" + mArticleTitle);
                Log.d(TAG, "uniquekey:" + mArticleID);
                for (int j=0; j<imageURLs.size(); j++){
                    Log.d(TAG, "imageURL" + j+": "+imageURLs.get(j));
                }
                Log.d(TAG, "---------------------------------\n");

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
            return mArticles;
        }


    String first_id = "";
    String last_id = "";

    public List<ArticleBean> parseSHUWENJson(String jsonData){
        List<ArticleBean> mArticles = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("news");

            first_id = jsonObject.getJSONObject("data").getString("first_id");
            last_id = jsonObject.getJSONObject("data").getString("last_id");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                ArticleBean articleInfo = new ArticleBean();

                String mArticleURL  = jsonObject1.getString("url");
                String mCategoryName = jsonObject1.getString("category");
                String mPubDate = jsonObject1.getString("gmt_publish");
                String mArticleTitle = jsonObject1.getString("title");
                String mArticleID = jsonObject1.getString("news_id");
                String mPublisherName = jsonObject1.getString("source");

                articleInfo.setArticleURL(mArticleURL);
                articleInfo.setCategoryName(mCategoryName);
                articleInfo.setPublishedDate(mPubDate);
                articleInfo.setTitle(mArticleTitle);
                articleInfo.setUniqueKey(mArticleID);
                articleInfo.setPublisherName(mPublisherName);

                List<String> imageURLs = new ArrayList<>();
                JSONArray images = jsonObject1.getJSONArray("thumbnail_img");
                for (int j = 0; j < images.length(); j++) {
                    imageURLs.add(images.getString(j));
                }
                articleInfo.setImageUrls(imageURLs);
                mArticles.add(articleInfo);

            }


        }catch (JSONException e) {
            e.printStackTrace();
        }
        return mArticles;
    }

    public String getFirstId(){
        return first_id;
    }
    public String getLastId(){
        return last_id;
    }

    public List<ArticleBean> parseStartMagazineJson(String jsonData){
        List<ArticleBean> mArticles = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getInt("totalItems") == 0) {
                return mArticles;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("content");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                ArticleBean articleInfo = new ArticleBean();

                String mArticleURL  = jsonObject1.getString("contentURL");
//                String mCategoryName = jsonObject1.getString("category");
                String mPubDate = jsonObject1.getString("publishedAt");
                String mArticleTitle = jsonObject1.getString("title");
                String mArticleID = jsonObject1.getString("contentId");
                String mPublisherName = jsonObject1.getString("contentSourceDisplay");
                String mCPLogoURL = jsonObject1.getString("contentSourceLogo");
                int views = jsonObject1.getInt("views");

                articleInfo.setArticleURL(mArticleURL);
//                articleInfo.setCategoryName(mCategoryName);
                articleInfo.setViews(views);
                articleInfo.setPublishedDate(mPubDate);
                articleInfo.setTitle(mArticleTitle);
                articleInfo.setUniqueKey(mArticleID);
                articleInfo.setPublisherName(mPublisherName);
                articleInfo.setCpLogoURL(mCPLogoURL);
                if (i == 0) {
                    articleInfo.setPriviousFirstArticleDate(mPubDate);
                }


                JSONObject json_images_obj = jsonArray.getJSONObject(i).getJSONObject("images");
                String mainImage = json_images_obj.getJSONObject("mainImage").getString("url");
                articleInfo.setMainImageURL(mainImage);
                String mainImageThumbnail = json_images_obj.getJSONObject("mainImageThumbnail").getString("url");
                articleInfo.setMainImageThumbnailURL(mainImageThumbnail);
                List<String> additionalImages = new ArrayList<>();
                JSONArray images = json_images_obj.getJSONArray("additionalImages");
                for (int j = 0; j < images.length(); j++) {
                    String addtionalImageURL = images.getJSONObject(j).getString("url");
                    additionalImages.add(addtionalImageURL);
                }
//                if(mainImage !=null){
//                    additionalImages.add(mainImage);
//                }
//                if(mainImageThumbnail !=null){
//                    additionalImages.add(mainImageThumbnail);
//                }
                articleInfo.setAdditionalImages(additionalImages);
                mArticles.add(articleInfo);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return mArticles;
    }

    public List<ArticleBean> parseSCJson(String jsonData){
        List<ArticleBean> mArticles = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getInt("totalItems") == 0) {
                return mArticles;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("content");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                ArticleBean articleInfo = new ArticleBean();

                String mArticleID = jsonObject1.getString("contentId");
                String mArticleTitle = jsonObject1.getString("title");
                String mArticleURL  = jsonObject1.getString("actionUri");
                String mPubDate = jsonObject1.getString("promotedText");
                String mPublisherName = jsonObject1.getString("contentSourceDisplay");
                String mCPLogoURL = jsonObject1.getString("imageUrl");

                articleInfo.setArticleURL(mArticleURL);
                articleInfo.setPublishedDate(mPubDate);
                articleInfo.setTitle(mArticleTitle);
                articleInfo.setUniqueKey(mArticleID);
                articleInfo.setPublisherName(mPublisherName);
                articleInfo.setCpLogoURL(mCPLogoURL);
                List<String> additionalImages = new ArrayList<>();
                articleInfo.setAdditionalImages(additionalImages);

                mArticles.add(articleInfo);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return mArticles;
    }

    public List<CricketInfo> parseSportCricketJson(String jsonData){
        List<CricketInfo> mCricketGames = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getJSONArray("games").length() == 0) {
                return mCricketGames;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("games");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                CricketInfo cricketInfo = new CricketInfo();
                String matchId  = jsonObject1.getString("matchId");
                String matchFile = jsonObject1.getString("matchFile");
                String startTime = jsonObject1.getString("startTime");
                String endTime = jsonObject1.getString("endTime");
                String status = jsonObject1.getString("status");
                String result;
                if (jsonObject1.has("result")){
                    result = jsonObject1.getString("result");
                }else {
                    result = "";
                }

//                String winMargin = jsonObject1.getString("winMargin");
//                String tossElected = jsonObject1.getString("tossElected");
                String type = jsonObject1.getString("type");
                String stage = jsonObject1.getString("stage");
                String competition = jsonObject1.getString("competition");
                String gamePageURL = jsonObject1.getString("gamePageURL");
                Boolean isLive = jsonObject1.getBoolean("isLive");

                JSONArray teams = jsonObject1.getJSONArray("teams");
                JSONObject teamA_Obj = teams.getJSONObject(0);
                CricketTeamInfo teamA = new CricketTeamInfo();
                int teamA_Id = teamA_Obj.getInt("id");
                String teamA_name = teamA_Obj.getString("name");
                String teamA_logo = teamA_Obj.getString("logoURL");
                teamA.setId(teamA_Id);
                teamA.setLogoURL(teamA_logo);
                teamA.setName(teamA_name);
                JSONObject teamB_Obj = teams.getJSONObject(1);
                CricketTeamInfo teamB = new CricketTeamInfo();
                int teamB_Id = teamB_Obj.getInt("id");
                String teamB_name = teamB_Obj.getString("name");
                String teamB_logo = teamB_Obj.getString("logoURL");
                teamB.setId(teamB_Id);
                teamB.setLogoURL(teamB_logo);
                teamB.setName(teamB_name);
                List<CricketTeamInfo> teamAB = new ArrayList<>();
                teamAB.add(teamA);
                teamAB.add(teamB);

                if ((status.equals("Ended") && (jsonObject1.has("result") && !jsonObject1.getString("result").equals("Match Abandoned")))
                        || status.equals("Live")){
                    JSONArray scores = jsonObject1.getJSONArray("scores");
                    JSONObject scoreA_Obj = scores.getJSONObject(0);
                    CricketScoreInfo scoreA = new CricketScoreInfo();
                    int score_teamA_Id = scoreA_Obj.getInt("teamId");
                    String score_teamA_name = scoreA_Obj.getString("score");
                    scoreA.setScore(score_teamA_name);
                    scoreA.setTeamId(score_teamA_Id);
                    JSONObject scoreB_Obj = scores.getJSONObject(1);
                    CricketScoreInfo scoreB = new CricketScoreInfo();
                    int score_teamB_Id = scoreB_Obj.getInt("teamId");
                    String score_teamB_name = scoreB_Obj.getString("score");
                    scoreB.setScore(score_teamB_name);
                    scoreB.setTeamId(score_teamB_Id);
                    List<CricketScoreInfo> scoreAB = new ArrayList<>();
                    scoreAB.add(scoreA);
                    scoreAB.add(scoreB);

                    cricketInfo.setScores(scoreAB);
                }

                cricketInfo.setMatchId(matchId);
                cricketInfo.setMatchFile(matchFile);
                cricketInfo.setStartTime(startTime);
                cricketInfo.setEndTime(endTime);
                cricketInfo.setCompetition(competition);
                cricketInfo.setGamePageURL(gamePageURL);
                cricketInfo.setLive(isLive);
                cricketInfo.setResult(result);
                cricketInfo.setStage(stage);
                cricketInfo.setStatus(status);
                cricketInfo.setType(type);
//                cricketInfo.setWinMargin(winMargin);

                cricketInfo.setTeams(teamAB);

                mCricketGames.add(cricketInfo);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return mCricketGames;
    }

    public List<SoccerInfo> parseSportSoccerJson(String jsonData){
        List<SoccerInfo> mSoccerGames = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getJSONArray("games").length() == 0) {
                Log.d(TAG,"Soccer game = 0  ");
                return mSoccerGames;
            }

            JSONArray jsonArray = jsonObject.getJSONArray("games");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                SoccerInfo soccerInfo = new SoccerInfo();
                String gameId  = jsonObject1.getString("gameId");
                String leageName = jsonObject1.getString("leageName");
//                String season = jsonObject1.getString("season");
                String stage = jsonObject1.getString("stage");
                String status = jsonObject1.getString("status");
                String type = jsonObject1.getString("type");
                String gameTime = jsonObject1.getString("gameTime");
                String gamePageURL = jsonObject1.getString("gamePageURL");

                JSONArray teams = jsonObject1.getJSONArray("teams");
                JSONObject teamA_Obj = teams.getJSONObject(0);
                SoccerTeamInfo teamA = new SoccerTeamInfo();
                String teamA_Id = teamA_Obj.getString("teamId");
                String teamA_name = teamA_Obj.getString("teamName");
                String teamA_logo = teamA_Obj.getString("teamLogo");
                teamA.setTeamId(teamA_Id);
                teamA.setTeamLogo(teamA_logo);
                teamA.setTeamName(teamA_name);
                JSONObject teamB_Obj = teams.getJSONObject(1);
                SoccerTeamInfo teamB = new SoccerTeamInfo();
                String teamB_Id = teamB_Obj.getString("teamId");
                String teamB_name = teamB_Obj.getString("teamName");
                String teamB_logo = teamB_Obj.getString("teamLogo");
                teamB.setTeamId(teamB_Id);
                teamB.setTeamLogo(teamB_logo);
                teamB.setTeamName(teamB_name);
                List<SoccerTeamInfo> teamAB = new ArrayList<>();
                teamAB.add(teamA);
                teamAB.add(teamB);

                JSONArray scores = jsonObject1.getJSONArray("scores");
                JSONObject scoreA_Obj = scores.getJSONObject(0);
                SoccerScoreInfo scoreA = new SoccerScoreInfo();
                String score_teamA_Id = scoreA_Obj.getString("teamId");
                String score_teamA_name = scoreA_Obj.getString("scr");
                scoreA.setScr(score_teamA_name);
                scoreA.setTeamId(score_teamA_Id);
                JSONObject scoreB_Obj = scores.getJSONObject(1);
                SoccerScoreInfo scoreB = new SoccerScoreInfo();
                String score_teamB_Id = scoreB_Obj.getString("teamId");
                String score_teamB_name = scoreB_Obj.getString("scr");
                scoreB.setScr(score_teamB_name);
                scoreB.setTeamId(score_teamB_Id);
                List<SoccerScoreInfo> scoreAB = new ArrayList<>();
                scoreAB.add(scoreA);
                scoreAB.add(scoreB);

                soccerInfo.setScores(scoreAB);
                soccerInfo.setGameID(gameId);
                soccerInfo.setGamePageURL(gamePageURL);
                soccerInfo.setGameTime(gameTime);
                soccerInfo.setLeageName(leageName);
//                soccerInfo.setSeason(season);
                soccerInfo.setStage(stage);
                soccerInfo.setStatus(status);
                soccerInfo.setTeams(teamAB);

                mSoccerGames.add(soccerInfo);
            }
        }catch (JSONException e) {
            Log.d(TAG,"Soccer error： " + e.getMessage());
            e.printStackTrace();
        }
        return mSoccerGames;
    }

    public List<ArticleBean> parseFWCJson(String jsonData){
        List<ArticleBean> mArticles = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getInt("totalItems") == 0) {
                return mArticles;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("content");
            String mNextContentItem = jsonObject.getString("nextContentItem");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                ArticleBean articleInfo = new ArticleBean();

                String mFWCURL  = jsonObject1.getString("contentUrl");
                String mPubDate = jsonObject1.getString("publishTime");
                String mArticleTitle = jsonObject1.getString("title");
                String mArticleID = jsonObject1.getString("id");
                String mPublisherName = jsonObject1.getString("contentSource");
                String mImageURL = jsonObject1.getString("imgUrl");

                articleInfo.setArticleURL(mFWCURL);
                articleInfo.setPublishedDate(mPubDate);
                articleInfo.setTitle(mArticleTitle);
                articleInfo.setUniqueKey(mArticleID);
                articleInfo.setPublisherName(mPublisherName);
                articleInfo.setMainImageThumbnailURL(mImageURL);
                articleInfo.setNextContentItem(mNextContentItem);
                if (i == 0) {
                    articleInfo.setPriviousFirstArticleDate(mPubDate);
                }
                mArticles.add(articleInfo);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return mArticles;
    }

    public List<VideoBean> parseVideoJson(String jsonData){
        List<VideoBean> mVideos = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getInt("totalItems") == 0) {
                return mVideos;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("content");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                VideoBean videoInfo = new VideoBean();

                String mVideoID = jsonObject1.getString("contentId");
                String mFWCVideoURL  = jsonObject1.getString("contentURL");
                String mVideoTitle = jsonObject1.getString("title");
                String mPublisherName = jsonObject1.getString("contentSourceDisplay");
                String mImageURL = jsonObject1.getString("thumbnail");
                String mPubDate = jsonObject1.getString("publishedAt");
                String mViews = jsonObject1.getString("totalViews");
                String mLength = jsonObject1.getString("length");
                String videoVendorLogoURL = jsonObject1.getString("contentSourceLogo");

                videoInfo.setmVideoURL(mFWCVideoURL);
                videoInfo.setVideoVendorLogoURL(videoVendorLogoURL);
                videoInfo.setmPubDate(mPubDate);
                videoInfo.setmTitle(mVideoTitle);
                videoInfo.setmID(mVideoID);
                videoInfo.setmSource(mPublisherName);
                videoInfo.setmImgURL(mImageURL);
                videoInfo.setLength(mLength);
                videoInfo.setViews(mViews);
                if (i == 0) {
                    videoInfo.setmFirstItemDate(mPubDate);
                }

                mVideos.add(videoInfo);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return mVideos;
    }

    public List<CategoryBean> parseCategoryListJson(String jsonData){
        List<CategoryBean> mCategoryList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getInt("totalItems") == 0) {
                return mCategoryList;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("categories");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                CategoryBean categoryInfo = new CategoryBean();

                String mCategoryId = jsonObject1.getString("categoryId");
                String mCategoryName  = jsonObject1.getString("categoryName");
                String mTranslatedName = jsonObject1.getString("translatedName");
                String mImageUrl = jsonObject1.getString("imageUrl");

                categoryInfo.setCategoryId(mCategoryId);
                categoryInfo.setCategoryName(mCategoryName);
                categoryInfo.setTranslatedName(mTranslatedName);
                categoryInfo.setImageUrl(mImageUrl);

                mCategoryList.add(categoryInfo);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return mCategoryList;
    }

}