package com.celltick.apac.news.util;

public class Constant {

    public static final int COUNT_IMAGE = 965;

    // 控制还剩几项新闻的时候加载更多
    public static final int VISIBLE_THRESHOLD = 3;
    public static final String LOGGER_TAG = "LoggerTag";
    public static final String SINA_ERROR_INFO = "您所访问的网站发生故障";


    //加载Header 或 Bottom的状态码
    public static final int HEADER_LOAD = 0;
    public static final int BOTTOM_LOAD = 1;

    //错误码
    public static final int SUCCESS = 0;//成功返回数据
    public static final int SC_HEADER_SUCCESS = 1;//成功返回SC数据 - 顶部
    public static final int SC_BOTTOM_SUCCESS = 2;//成功返回SC数据 - 底部
    public static final int SC_HEADER_FAILED = -5;//失败返回SC数据 - 顶部
    public static final int SC_BOTTOM_FAILED = -6;//失败返回SC数据 - 底部
    public static final int ERR_RETURN = -1;//返回数据失败的统一标识
    public static final int ERR_RETURN_NO_CONNECTION = -2;//返回数据失败 - no connection
    public static final int ERR_RETURN_TIMEOUT = -3;//返回数据失败 - timeout
    public static final int ERR_RETURN_UNKNOWN = -99;//返回数据失败 - 未知错误
    public static final int ERR_BANNER_DISMISS = -4;//隐藏banner提示

    public static final int STATUS_CATEGORY_LIST_REQ_START= 10001;//request category list start
    public static final int STATUS_CATEGORY_LIST_REQ_END= 10002;//request category list end


    //体育状态码
    // - cricket
    public static final int CRICKET_SUCCESS = 100;
    public static final int CRICKET_ERR_RETURN_NO_CONNECTION = -101;
    public static final int CRICKET_ERR_RETURN_TIMEOUT = -102;
    public static final int CRICKET_ERR_RETURN_UNKNOWN = -103;

    // - soccer
    public static final int SOCCER_SUCCESS = 200;
    public static final int SOCCER_ERR_RETURN_NO_CONNECTION = -201;
    public static final int SOCCER_ERR_RETURN_TIMEOUT = -202;
    public static final int SOCCER_ERR_RETURN_UNKNOWN = -203;

    // - nba
    public static final int NBA_SUCCESS = 300;
    public static final int NBA_ERR_RETURN_NO_CONNECTION = -301;
    public static final int NBA_ERR_RETURN_TIMEOUT = -302;
    public static final int NBA_ERR_RETURN_UNKNOWN = -303;



    /**
        Start Magazine ~~~~
     */

//    public static final String PUBLISHERID = "publisherId=xiaolei10086-NEWS";
//    public static final String KEY = "&key=DdJwjBciDrPzQvxJhqbga84ndKCBViG0";
    public static final String PUBLISHERID = "publisherId=Magazine_from_Starter";
    public static final String KEY = "&key=XE4n3Zt72XKP1ciSw1gZC77t9Gf0bkOp";

    public static final int NUM_SM_1ST_REQUEST = 20;
    public static final int NUM_SM_NORMAL_REQUEST = 10;

    //Start Magazine api (SM)
//    public static final String SM_BASIC_URL = "https://contentapi.celltick.com/mediaApi/v1.0/content?publisherId=Magazine-Test&key=x08O9Sycd17NR71y193SuI6Y55p6sc4u&thumbImgWidth=540&thumbImgQuality=50";
//    public static final String SM_BASIC_URL = "https://contentapi.celltick.com/mediaApi/v1.0/content?publisherId=VIVO-Web&key=WW5CCec3QsLyx2tGjEPHT0ptUKbqbif7";
//    public static final String SM_BASIC_URL = "https://contentapi.celltick.com/mediaApi/v1.0/personal/content?publisherId=VIVO-Web&key=WW5CCec3QsLyx2tGjEPHT0ptUKbqbif7";
    public static final String SM_BASIC_URL = "https://contentapi.celltick.com/mediaApi/v1.0/personal/content?"+PUBLISHERID+KEY;
    public static final String SM_BASIC_SEARCH_URL = "https://contentapi.celltick.com/mediaApi/v1.0/content?"+PUBLISHERID+KEY;
    public static final String SM_CRICKET_CURRENT_SCHEDULE_URL = "https://sportapi.celltick.com/mediaApi/v1.0/sport/cricket?"+PUBLISHERID+KEY;
    public static final String SM_SOCCER_CURRENT_SCHEDULE_URL = "https://sportapi.celltick.com/mediaApi/v1.0/sport/games?"+PUBLISHERID+KEY;
    public static final String SM_SC_BASIC_URL = "https://contentapi.celltick.com/mediaApi/v1.0/mid/promoted/?"+PUBLISHERID+KEY;

    public static final String SM_WORLD = SM_BASIC_URL + "&category=4,13" ;
    public static final String SM_SPORT = SM_BASIC_URL + "&category=7,39" ;
    public static final String SM_ENTERTAINMENT = SM_BASIC_URL + "&category=12,8" ;
    public static final String SM_TECH = SM_BASIC_URL + "&category=9,14" ;
    public static final String SM_LIFE = SM_BASIC_URL + "&category=42,16,65,75,19,85,24" ;
    public static final String SM_CELEBRITY = SM_BASIC_URL + "&category=17,18,91" ;
    public static final String SM_FINANCE = SM_BASIC_URL + "&category=48" ;

    public static final String SM_WORLDCUP = "https://sportapi.celltick.com/mediaApi/v1.0/sport/content?publisherId=Magazine-Test&key=x08O9Sycd17NR71y193SuI6Y55p6sc4u&userId=111222555&language=en&competitions=5930&countryCode=US";
    public static final String SM_WORLDCUP_XIAOMI = "https://sportapi.celltick.com/mediaApi/v1.0/sport/content?publisherId=Xiaomi&key=77xOEwXq1Fp5tb96QuQA65AesCeJ7PDb&userId=123123&language=ru&countryCode=RU&competitions=5930";

    public static final String FWC_URL = "https://www.thestartmagazine.com/feed/summary?isDesktop=false&publisherId=360-Web&key=9rp8SdD83A1ovLSQOYTd4t8XeZurSiAp&vendor=365scores&genericDimension=FWC&countryCode=MX&language=es";


    public static final String SM_FWC_VIDEO = "https://contentapi.celltick.com/mediaApi/v1.0/video?publisherId=Magazine-Test&key=x08O9Sycd17NR71y193SuI6Y55p6sc4u&userId=123e-3345-6678-7777&countryCode=US&language=en&tag=fwc18";
    public static final String SM_VIDEO = "https://contentapi.celltick.com/mediaApi/v1.0/video?"+PUBLISHERID+KEY;

    public static final String SM_HOLLYWOOD_VIDEO = "https://contentapi.celltick.com/mediaApi/v1.0/video?key=XE4n3Zt72XKP1ciSw1gZC77t9Gf0bkOp&publisherId=Magazine_from_Starter&userId=123e-3345-6678-7777&countryCode=US&tag=epus-movies";
    public static final String SM_BOLLYWOOD_VIDEO = "https://contentapi.celltick.com/mediaApi/v1.0/video?key=XE4n3Zt72XKP1ciSw1gZC77t9Gf0bkOp&publisherId=Magazine_from_Starter&userId=123e-3345-6678-7777&countryCode=IN&tag=epin_en-movies";

    public static final String SM_CATEGORY_LIST = "https://contentapi.celltick.com/mediaApi/v1.0/category?"+PUBLISHERID+KEY;


    //各种超时时间定义 - 秒
    public static final long CONNECT_TIMEOUT = 10;
    public static final long WRITE_TIMEOUT = 10;
    public static final long READ_TIMEOUT = 15;

    //define the exit & restart time
    public static final long EXIT_SURPASS_TIME = 2*1000;
    public static final long RESTART_SURPASS_TIME = 10*60*1000;

    //define refresh time
    public static final long CACHE_SURPASS_TIME = 20*60*1000;

    //SharedPreference Key Names
    public static final String UUID = "uuid";
    public static final String COUNTRY_CODE = "countryCode";
    public static final String LANGUAGE_CODE = "languageCode";
    public static final String CATEGOTY_LIST = "categoryListStr";
    public static final String NIGHT_MODE = "night_mode";
    public static final String CACHE_SAVE_TIME = "save_time";
    public static final String TEMPLATE = "template";
    public static final String IS_FIRST_RUN = "isFirstRun";




    //packages urls
    //soccer
    public static final String SOCCER = "https://www.thestartmagazine.com/feed/summary?publisherId=xiaolei10086-NEWS&key=DdJwjBciDrPzQvxJhqbga84ndKCBViG0&vendor=365scores&genericDimension=Soccer&locale=auto";
    //cricket
    public static final String CRICKET = "https://www.thestartmagazine.com/feed/summary?isDesktop=false&publisherId=xiaolei10086-NEWS&key=DdJwjBciDrPzQvxJhqbga84ndKCBViG0&countryCode=IN&langauge=en&tag=cricket";
    //Celebrities
    public static final String CELEBRITY = "https://www.thestartmagazine.com/feed/summary?isDesktop=false&publisherId=xiaolei10086-NEWS&key=DdJwjBciDrPzQvxJhqbga84ndKCBViG0&language=en&tag=yoga";
    //BEAUTY
    public static final String BEAUTY = "https://www.thestartmagazine.com/feed/summary?isDesktop=false&publisherId=xiaolei10086-NEWS&key=DdJwjBciDrPzQvxJhqbga84ndKCBViG0&countryCode=US&langauge=en&category=beauty";
    //HEALTH
    public static final String HEALTH = "https://www.thestartmagazine.com/feed/summary?isDesktop=false&publisherId=xiaolei10086-NEWS&key=DdJwjBciDrPzQvxJhqbga84ndKCBViG0&genericDimension=novideo&language=en&tag=fitness";
    //FOOD
    public static final String FOOD = "https://www.thestartmagazine.com/feed/summary?isDesktop=false&publisherId=xiaolei10086-NEWS&key=DdJwjBciDrPzQvxJhqbga84ndKCBViG0&language=en&category=food";


}
