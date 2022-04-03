package com.shuai.weather.constant;

public class APIConfig {

    //心知天气API
    // 生活指数请求地址 https://api.seniverse.com/v3/life/suggestion.json?key=your_api_key&location=shanghai&language=zh-Hans

    public static final String TIANQI_API_SECRET_KEY = "SQZUkckHDe5YbavFB";
    public static final String TIANQI_API_USER_ID= "PEMv8o5OMg3WSt4OL";

    //高德地图API常量
    public static final String API_KEY = "9b9cd45a69a597df5440cb3ff4190091";
    //API_EXTENSIONS
    public static final String API_EXTENSIONS = "base";
    //API_OUTPUT
    public static final String API_OUTPUT = "JSON";

    //https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=<用户key>  CURRENT_WEATHER
    public static final String CURRENT_WEATHER= "https://restapi.amap.com/v3/weather/weatherInfo?";
    public static final String TIANQI_DAILY_WEATHER_URL= "https://api.ahfi.cn/api/mapjx?";
    //LONGITUDE
    public static final String LONGITUDE = "lng";
    //LATITUDE
    public static final String LATITUDE = "lat";


    //阿里云天气接口
    public static final String HOST = "https://jisutqybmf.market.alicloudapi.com";
    public static final String CITY_PATH = "/weather/city";
    public static final String WEATHER_PATH = "/weather/query";
    public static final String METHOD = "GET";
    public static final String APPCODE = "1a78a32205cc466aaff172e2a4c3c947";


}
