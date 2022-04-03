package com.shuai.weather.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuai.weather.mapper.FindLocationMapper;
import com.shuai.weather.pojo.CityCode;
import com.shuai.weather.dtos.LocationInfo;
import com.shuai.weather.service.findLocationService;
import com.shuai.weather.util.GetPostUtil;
import org.springframework.stereotype.Service;

import static com.shuai.weather.constant.APIConfig.*;

@Service
public class findLocationServiceImpl extends ServiceImpl<FindLocationMapper, CityCode> implements findLocationService {


    @Override
    public LocationInfo findLocation(String latitude, String longitude) {

        //https://api.ahfi.cn/api/mapjx? + lat + "=" + latitude + "&" + lng + "=" + longtitude;
        String url = TIANQI_DAILY_WEATHER_URL + LATITUDE + "=" + latitude + "&" + LONGITUDE + "=" + longitude;
        String result = GetPostUtil.sendGet(url);
        String replace = result.replace(result.substring(result.length() - 5, result.length()), "");

        LocationInfo locationInfo = new LocationInfo();
        //将所得信息转化为JsonObject对象
        JSONObject jsonObject = JSON.parseObject(replace);

        //获取JSONObject对象：data
        JSONObject data = jsonObject.getJSONObject("data");
        String country = data.getString("country");
        locationInfo.setCountry(country);
        String countrycode = data.getString("countrycode");
        locationInfo.setCountrycode(countrycode);
        String province = data.getString("province");
        locationInfo.setProvince(province);
        String city = data.getString("city");
        locationInfo.setCity(city);
        String cityadcode = data.getString("cityadcode");
        locationInfo.setCityadcode(cityadcode);
        String district = data.getString("district");
        locationInfo.setDistrict(district);
        String desc = data.getString("desc");
        locationInfo.setDesc(desc);
        locationInfo.setLatitude(latitude);
        locationInfo.setLongitude(longitude);

        return locationInfo;
    }


}
