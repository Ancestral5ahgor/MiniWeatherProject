package com.shuai.test;

import com.shuai.weather.dtos.AliCity;
import com.shuai.weather.util.HttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AiliyunMap {
    public static void main(String[] args) {
        String host = "https://jisutqybmf.market.alicloudapi.com";
        String path = "/weather/city";
        String method = "GET";
        String appcode = "1a78a32205cc466aaff172e2a4c3c947";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());
            String result = EntityUtils.toString(response.getEntity());
            JSONObject json = JSONObject.fromObject(result);
            JSONArray result1 = json.getJSONArray("result");
            List<AliCity> aliCityList = new ArrayList<>();
            for (int i = 0; i < result1.size(); i++) {
                AliCity aliCity = new AliCity();
                JSONObject jsonObject = result1.getJSONObject(i);

                String cityid = jsonObject.getString("cityid");
                String parentid = jsonObject.getString("parentid");
                String citycode = jsonObject.getString("citycode");
                String city = jsonObject.getString("city");
                aliCity.setCity(city);
                aliCity.setCityid(cityid);
                aliCity.setCitycode(citycode);
                aliCity.setParentid(parentid);
                aliCityList.add(aliCity);
            }
            //System.out.println(aliCityList);
            JSONArray jsonArray = JSONArray.fromObject(aliCityList);
            System.out.println(jsonArray);
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity(),"utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
