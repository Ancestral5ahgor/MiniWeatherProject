package com.shuai.weather.pojo;

import lombok.Data;

@Data
public class QueryWeather {
    private String city;
    private String citycode;
    private String cityid;
    private String ip;
    private String location;
}
