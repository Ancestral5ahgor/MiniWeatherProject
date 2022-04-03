package com.shuai.test;


import com.alibaba.fastjson.JSON;
import com.shuai.weather.WeatherApplication;
import com.shuai.weather.pojo.Location;
import com.shuai.weather.service.AliyunWeatherService;
import com.shuai.weather.service.GetCurrentWeatherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = WeatherApplication.class)
@RunWith(SpringRunner.class)
public class test {

    @Autowired
    private GetCurrentWeatherService getCurrentWeatherService;

    @Autowired
    private AliyunWeatherService aliyunWeatherService;

    @Test
    public void test() {

        Location location = new Location();
        location.setLatitude("27.846725");
        location.setLongitude("112.925083");

        /*String currentweather = getCurrentWeatherService.currentweather(location.getLatitude(), location.getLongitude());
        System.out.println("currentweather" + currentweather);
        String jsonString = JSON.toJSONString(currentweather);
        String result = jsonString.replace("\\","");
        System.out.println("result:" + result);*/

        String dayweather = aliyunWeatherService.dayweather(location.getLatitude(), location.getLongitude());
        //String jsonStrings = JSON.toJSONString(dayweather);
        System.out.println("未转换格式：" + dayweather);
        //System.out.println("result1:" + jsonStrings);
    }

}