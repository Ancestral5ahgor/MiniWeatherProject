package com.shuai.test;

import com.shuai.weather.WeatherApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.test.context.junit4.SpringRunner;

/*@SpringBootTest(classes = WeatherApplication.class)
@RunWith(SpringRunner.class)*/
public class teststr {


    @Test
    public void wenzi(){
        String city = "临沂市";
        char c = city.charAt(0);
        char c1 = city.charAt(1);
        String value = String.valueOf(c);
        String value1 = String.valueOf(c1);
        String code = value + value1;
        System.out.println(code);

        for (int i = 0; i <city.length() ; i++) {
            char c3 = city.charAt(i);
            System.out.println(c3);
        }

    }
}
