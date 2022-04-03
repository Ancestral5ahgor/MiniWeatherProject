package com.shuai.weather.service.Impl;

import com.shuai.weather.dtos.AliCity;
import com.shuai.weather.pojo.QueryWeather;
import com.shuai.weather.service.AliyunLoactionService;
import com.shuai.weather.util.HttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.shuai.weather.constant.APIConfig.*;

@Service
public class AliyunLoactionServiceImpl implements AliyunLoactionService {

    @Override
    public AliCity querycity() {
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + APPCODE);
        Map<String, String> querys = new HashMap<String, String>();

        AliCity aliCity = new AliCity();
        try {
            HttpResponse response = HttpUtils.doGet(HOST, CITY_PATH, METHOD, headers, querys);
            String result = EntityUtils.toString(response.getEntity());
            JSONObject json = JSONObject.fromObject(result);
            JSONArray result1 = json.getJSONArray("result");
            for (int i = 0; i <result1.size() ; i++) {
                JSONObject jsonObject = result1.getJSONObject(i);
                String cityid = jsonObject.getString("cityid");
                String parentid = jsonObject.getString("parentid");
                String citycode = jsonObject.getString("citycode");
                String city = jsonObject.getString("city");
                aliCity.setCity(city);
                aliCity.setCitycode(citycode);
                aliCity.setCityid(cityid);
                aliCity.setParentid(parentid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aliCity;
    }
}
