package com.shuai.weather.controller.v1;

import com.alibaba.fastjson.JSON;
import com.shuai.weather.api.WeatherControllerApi;
import com.shuai.weather.service.GetCurrentWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/weather")
public class HelloWorldController implements WeatherControllerApi {

    @Autowired
    private GetCurrentWeatherService getCurrentWeatherService;


    @GetMapping("/currentWeather")
    @Override
    public String currentweather(@RequestParam("latitude") String latitude,@RequestParam("longitude") String longitude) {
        String currentweather = getCurrentWeatherService.currentweather(latitude, longitude);
        String jsonString = JSON.toJSONString(currentweather);
        //String result = jsonString.replace("\\","");
        //System.out.println("实时天气结果：" + jsonString);
        return jsonString;
    }
}