package com.shuai.weather.dtos;

import lombok.Data;

@Data
public class Amapdemo {

    private String key;
    private Integer city;
    private String extensions;
    private String output;
}
