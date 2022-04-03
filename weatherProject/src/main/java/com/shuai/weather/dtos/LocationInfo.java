package com.shuai.weather.dtos;

import lombok.Data;

@Data
public class LocationInfo {

    private String latitude;
    private String longitude;

    private String status;
    private String code;
    private String message;
    private boolean result;
    private String timestamp;
    private String version;
    private String country;
    private String countrycode;
    private String province;
    private String provinceadcode;
    private String city;
    private String cityadcode;
    private String tel;
    private String areacode;
    private String district;
    private String districtadcode;
    private String adcode;
    private String desc;

}
