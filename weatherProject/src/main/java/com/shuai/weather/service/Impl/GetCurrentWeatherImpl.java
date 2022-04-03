package com.shuai.weather.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shuai.weather.dtos.AmapWeather;
import com.shuai.weather.dtos.Amapdemo;

import com.shuai.weather.mapper.FindLocationMapper;
import com.shuai.weather.pojo.CityCode;
import com.shuai.weather.dtos.LocationInfo;
import com.shuai.weather.service.AliyunLoactionService;
import com.shuai.weather.service.GetCurrentWeatherService;
import com.shuai.weather.service.findLocationService;
import com.shuai.weather.util.GetPostUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.shuai.weather.constant.APIConfig.*;

@Service
public class GetCurrentWeatherImpl implements GetCurrentWeatherService {

    @Autowired
    private findLocationService findLocationService;

    @Autowired
    private FindLocationMapper findLocationMapper;

    @Autowired
    private AliyunLoactionService aliyunLoactionService;

    @Override
    public String currentweather(String latitude, String longitude) {
        //调用location接口，查询所在城市
        LocationInfo location = findLocationService.findLocation(latitude, longitude);
        String district = location.getDistrict();
        String city = location.getCity();
        String province = location.getProvince();
        //根据所得城市名称查询数据库得城市code
        LambdaQueryWrapper<CityCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CityCode::getCityname, district);
        List<CityCode> cityCodes = findLocationMapper.selectList(queryWrapper);
        LambdaQueryWrapper<CityCode> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(CityCode::getCityname, city);
        List<CityCode> cityCodes2 = findLocationMapper.selectList(queryWrapper2);
        String thecode = null;
        for (CityCode cityCode : cityCodes2) {
            thecode = cityCode.getCitycode();
        }

        Amapdemo amapdemo = new Amapdemo();
        for (CityCode cityCode : cityCodes) {
            String citycode = cityCode.getCitycode();
            if (citycode.equals(thecode)) {
                amapdemo.setCity(cityCode.getAbcode());
                amapdemo.setKey(API_KEY);
                amapdemo.setExtensions(API_EXTENSIONS);
                amapdemo.setOutput(API_OUTPUT);
            }
        }

        //高德API封装，查询城市天气
        AmapWeather amapWeather = CheckWeather(amapdemo);
        String jsonString = JSON.toJSONString(amapWeather);
        return jsonString;

    }



    /**
     * 高德天气API
     * @param amapdemo
     * @return
     */
    private AmapWeather CheckWeather(Amapdemo amapdemo) {
        //发送get请求，获取天气数据
        //https://restapi.amap.com/v3/weather/weatherInfo?city=440309&key=9b9cd45a69a597df5440cb3ff4190091&extensions=base&output=JSON
        String url = CURRENT_WEATHER + "city=" + amapdemo.getCity() + "&key=" + amapdemo.getKey() + "&extensions=" + amapdemo.getExtensions() + "&output=" + amapdemo.getOutput();
        String result = GetPostUtil.sendGet(url);
        //将所得信息转化为JsonObject对象
        JSONObject jsonObject = JSON.parseObject(result);
        //获取JSONObject对象的数组
        JSONArray results = jsonObject.getJSONArray("lives");
        //新建对象
        AmapWeather amapWeather = new AmapWeather();
        //得到object对象
        JSONObject jsonObject1 = results.getJSONObject(0);
        String province = jsonObject1.getString("province");
        String city = jsonObject1.getString("city");
        String adcode = jsonObject1.getString("adcode");
        String weather = jsonObject1.getString("weather");
        String temperature = jsonObject1.getString("temperature");
        String winddirection = jsonObject1.getString("winddirection");
        String windpower = jsonObject1.getString("windpower");
        String humidity = jsonObject1.getString("humidity");
        String reporttime = jsonObject1.getString("reporttime");
        amapWeather.setProvince(province);
        amapWeather.setCity(city);
        amapWeather.setAdcode(adcode);
        amapWeather.setWeather(weather);
        amapWeather.setTemperature(temperature);
        amapWeather.setWinddirection(winddirection);
        amapWeather.setWindpower(windpower);
        amapWeather.setHumidity(humidity);
        amapWeather.setReporttime(reporttime);
        return amapWeather;
    }
}
