package com.shuai.weather.service;


import com.shuai.weather.pojo.QueryWeather;
import net.sf.json.JSONObject;

public interface AliyunWeatherService {
    public String dayweather(String latitude, String longitude);
}
