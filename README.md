# MiniWeatherProject
微信天气小程序


Q:这是关于什么的代码？
A:这是一项结合Java编写的微信天气小程序代码

Q:Java使用了哪些东西？
A:SpringBoot+Mybatis-Plus（主要封装了查询城市以及城市天气的API接口）、MySQL5.7（数据库存储了城市对应代码等相关信息）

Q:整个流程？
A:由小程序前端获取用户位置经纬度，将经纬度信息传给后台，Java接收前端传来的经纬度信息通过相关API接口查询返回当前用户所在的城市地区，再通过城市地区信息查询天气信息。将所得城市天气信息经过处理后返回给前端小程序，小程序提取天气信息展现在页面上。
（1）、解析经纬度所用的API接口：https://api.ahfi.cn/api/mapjx?lat=latitude&lng=longtitude;
（2）、高德天气API接口：https://restapi.amap.com/v3/weather/weatherInfo?city=&key=1&extensions=base&output=JSON
（3）、阿里天气API接口：https://jisutqybmf.market.alicloudapi.com
小程序前端编译效果图片：http://82.156.30.3:8082/pic/0.jpg
