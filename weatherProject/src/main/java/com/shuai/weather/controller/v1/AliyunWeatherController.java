package com.shuai.weather.controller.v1;

import com.shuai.weather.api.AliyunWeatherControllerApi;
import com.shuai.weather.service.AliyunWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/weather")
public class AliyunWeatherController implements AliyunWeatherControllerApi {

    @Autowired
    private AliyunWeatherService aliyunWeatherService;

    @GetMapping("/dayweather")
    @Override
    public String dayweather(@RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude) {
        String dayweather = aliyunWeatherService.dayweather(latitude, longitude);
        //String result = jsonString.replace("\\","");
        //System.out.println("整日天气结果：" + dayweather);
        return dayweather ;


    }
}
