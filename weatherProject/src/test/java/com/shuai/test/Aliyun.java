package com.shuai.test;


import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;
import com.shuai.weather.util.HttpUtils;

public class Aliyun {
    public static void main(String[] args) {
        String host = "https://jisutqybmf.market.alicloudapi.com";
        String path = "/weather/query";
        String method = "GET";//GET/POST 任意
        String appcode = "1a78a32205cc466aaff172e2a4c3c947";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("city", "深圳");
        querys.put("citycode", "citycode");
        querys.put("cityid", "cityid");
        querys.put("ip", "ip");
        querys.put("location", "22.72174,114.06031");


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
            //System.out.println(response.toString());
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
            //JSONObject json = JSONObject.fromObject(result);
            //System.out.println(json);
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity(),"utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
