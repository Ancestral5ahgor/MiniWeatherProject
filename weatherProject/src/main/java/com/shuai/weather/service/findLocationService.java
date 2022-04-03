package com.shuai.weather.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shuai.weather.pojo.CityCode;
import com.shuai.weather.dtos.LocationInfo;

public interface findLocationService extends IService<CityCode> {
    public LocationInfo findLocation(String latitude, String longitude);
}
