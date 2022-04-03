package com.shuai.weather.pojo;

import lombok.Data;

@Data
public class Daily {

    String date;
    String week;
    String weather;
    String high;
    String sunrise;
    String sunset;
    String low;
    String img;
    String wind_direction;
    String wind_scale;


}
