package com.shuai.weather.api;

import com.shuai.weather.dtos.LocationInfo;


public interface LocationControllerApi {

    public LocationInfo findLocation(String latitude, String longtitude);

}
