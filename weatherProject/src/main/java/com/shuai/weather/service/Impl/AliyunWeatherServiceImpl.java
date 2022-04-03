package com.shuai.weather.service.Impl;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shuai.weather.dtos.AliCity;
import com.shuai.weather.dtos.Amapdemo;
import com.shuai.weather.dtos.LocationInfo;
import com.shuai.weather.mapper.FindAlicityMapper;
import com.shuai.weather.mapper.FindLocationMapper;
import com.shuai.weather.pojo.*;
import com.shuai.weather.service.AliyunWeatherService;
import com.shuai.weather.service.findLocationService;
import com.shuai.weather.util.HttpUtils;

import com.shuai.weather.util.MyBeanUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shuai.weather.constant.APIConfig.*;

@Service
public class AliyunWeatherServiceImpl implements AliyunWeatherService {


    @Autowired
    private findLocationService findLocationService;

    @Autowired
    private FindAlicityMapper findAlicityMapper;

    @Autowired
    private FindLocationMapper findLocationMapper;

    @Override
    public String dayweather(String latitude, String longitude) {

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


        //阿里API，查询24小时以及未来几天天气
        //1.1阿里查询城市
        LambdaQueryWrapper<AliCity> querycity = new LambdaQueryWrapper<>();
        char c = city.charAt(0);
        char c1 = city.charAt(1);
        String value = String.valueOf(c);
        String value1 = String.valueOf(c1);
        String code = value + value1;
        querycity.like(AliCity::getCity,code);
        //querycity.eq(AliCity::getCity, city);
        List<AliCity> aliCityList = findAlicityMapper.selectList(querycity);
        String aliweather = null;
        //AliCity querycity = aliyunLoactionService.querycity();
        for (AliCity aliCity1 : aliCityList) {
            AliCity aliCity = new AliCity();
            String city1 = aliCity1.getCity();
            String citycode = aliCity1.getCitycode();
            String cityid = aliCity1.getCityid();
            String parentid = aliCity1.getParentid();
            aliCity.setCity(city1);
            aliCity.setCitycode(citycode);
            aliCity.setCityid(cityid);
            aliCity.setParentid(parentid);
        /*
        判断
         1、如果有这个城市，调用阿里API接口，返回城市天气
         2、如果没有这个城市，例如：无深圳市龙华区，返回深圳市的天气
         */

            if (district.equals(aliCity.getCity())) {
               aliweather = aliweather(aliCity, latitude, longitude);
            }
            if (code.equals(aliCity.getCity())) {
               aliweather = aliweather(aliCity, latitude, longitude);
            }
        }



        JSONObject jsonbject = JSON.parseObject(aliweather);
        if(jsonbject == null){
            return aliweather;
        }

        JSONObject result = jsonbject.getJSONObject("result");
        JSONArray index = result.getJSONArray("index");
        JSONArray daily = result.getJSONArray("daily");
        JSONArray hourly = result.getJSONArray("hourly");

        List<AliWeather> aliWeatherList = new ArrayList<>();
        List<Index> indexList = new ArrayList<>();
        //index数组取值
        for (int i = 0; i < index.size(); i++) {
            Index index1 = new Index();
            JSONObject jsonObject = index.getJSONObject(i);
            String iname = jsonObject.getString("iname");
            String ivalue = jsonObject.getString("ivalue");
            String detail = jsonObject.getString("detail");
            index1.setDetail(detail);
            index1.setIvalue(ivalue);
            index1.setIname(iname);
            indexList.add(index1);
        }

        List<Daily> dayList = new ArrayList<>();
        //daily数组取值
        for (int i = 0; i < daily.size(); i++) {
            Daily daily1 = new Daily();
            JSONObject jsonObject = daily.getJSONObject(i);
            String date = jsonObject.getString("date");
            String week = jsonObject.getString("week");
            String sunrise = jsonObject.getString("sunrise");
            String sunset = jsonObject.getString("sunset");
            JSONObject day = jsonObject.getJSONObject("day");
            String weather = day.getString("weather");
            String temphigh = day.getString("temphigh");
            String img = day.getString("img");
            String winddirect = day.getString("winddirect");
            String windpower = day.getString("windpower");
            JSONObject night = jsonObject.getJSONObject("night");
            String templow = night.getString("templow");
            daily1.setDate(date);
            daily1.setSunset(sunset);
            daily1.setSunrise(sunrise);
            daily1.setWeek(week);
            daily1.setWeather(weather);
            daily1.setHigh(temphigh);
            daily1.setLow(templow);
            daily1.setImg(img);
            daily1.setWind_direction(winddirect);
            daily1.setWind_scale(windpower);
            dayList.add(daily1);

            //aliWeather.setDaily(daily1);
        }

        List<Hourly> hourlyList = new ArrayList<>();
        //hourly数组取值
        for (int i = 0; i < hourly.size(); i++) {
            Hourly hourly1 = new Hourly();
            JSONObject jsonObject = hourly.getJSONObject(i);
            String time = jsonObject.getString("time");
            String weather = jsonObject.getString("weather");
            String temp = jsonObject.getString("temp");
            String img = jsonObject.getString("img");
            hourly1.setImg(img);
            hourly1.setTemperature(temp);
            hourly1.setWeather(weather);
            hourly1.setBjTime(time);
            hourlyList.add(hourly1);

        }


        for (int i = 0; i < indexList.size(); i++) {
            AliWeather aliWeathers = new AliWeather();
            Index index1 = indexList.get(i);
            Daily daily1 = dayList.get(i);
            Hourly hourly1 = hourlyList.get(i);
            aliWeathers.setIndex(index1);
            aliWeathers.setDaily(daily1);
            aliWeathers.setHourly(hourly1);
            aliWeatherList.add(aliWeathers);
        }

        AliWeather aliWeather1 = new AliWeather();
        aliWeather1.setHourly(hourlyList.get(7));
        aliWeather1.setDaily(null);
        aliWeather1.setIndex(null);
        aliWeatherList.add(aliWeather1);

       // System.out.println(aliWeatherList);
        String s = JSON.toJSONString(aliWeatherList).toString();

        //String jsonString = JSON.toJSONString(aliweather);
        return s;

    }

    /**
     * 阿里天气API
     * @param aliCity
     * @param latitude
     * @param longitude
     * @return
     */
    private String aliweather(AliCity aliCity, String latitude, String longitude) {

        Location location = new Location();
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + APPCODE);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("city", aliCity.getCity());
        querys.put("citycode", aliCity.getCitycode());
        querys.put("cityid", aliCity.getCityid());
        querys.put("ip", null);
        querys.put("location", location.toString());


        String result = null;
        try {
            HttpResponse response = HttpUtils.doGet(HOST, WEATHER_PATH, WEATHER_PATH, headers, querys);
            //System.out.println(response.toString());
            result = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
