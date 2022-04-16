import * as echarts from '../../ec-canvas/echarts';
var dayChart = '',hourChart=''
var _self
//定义城市、天气、温度、风级、图片、日期参数
var getreporttime,getwindpower,getwinddirection,getweather,gettemperature,gethumidity,getname,getwinddirectiondegree,getwinscale,getspeed,getlanguage,daily,dailys
//定义经纬度
var latitude,longitude
let _page, _this;

//引入项目
function dayChartFun(canvas, width, height, dpr) {
  dayChart = echarts.init(canvas, null, {
    width: width,
    height: height,
    devicePixelRatio: dpr //解决小程序视图模糊的问题，必写
  });
  canvas.setChart(dayChart);

  var option = {
    color: ["#FB7821", "#1B9DFF"],
    grid: {
      containLabel: true,
      x: -11,
      x2: 15,
      top: 14,
      bottom: 15
    },
    tooltip: {
      show: true,
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      show: false,
      axisLabel: {
        interval: 49
      }
    },
    yAxis: {
      min: 'dataMin',
      show: false
    },
    series: [{
      itemStyle: {
        normal: {
          label: {
            show: true,
            position: [-5, -11],
            textStyle: {
              color: 'black'
            },
            formatter: function (params) {
              return params.value + '°'
            }
          }
        }
      },
      type: 'line',
      symbolSize: '4',
      smooth: true,
      data: []
    }, {
      itemStyle: {
        normal: {
          label: {
            show: true,
            position: [-5, 7],
            textStyle: {
              color: 'black'
            },
            formatter: function (params) {
              return params.value + '°'
            }
          }
        }
      },
      type: 'line',
      symbolSize: '4',
      smooth: true,
      data: []
    }]
  };

  dayChart.setOption(option);
  return dayChart;
}
function hourChartFun(canvas, width, height, dpr) {
  // console.log(canvas, width, height)
  hourChart = echarts.init(canvas, null, {
    width: width - 4,
    height: height,
    devicePixelRatio: dpr //解决小程序视图模糊的问题，必写
  });
  canvas.setChart(hourChart);

  var option = {
    color: ["#16C95D"],
    grid: {
      containLabel: true,
      x: -13,
      x2: 15,
      top: 17,
      bottom: 15
    },
    tooltip: {
      show: true,
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      show: false,
      axisLabel: {
        interval: 49
      }
    },
    yAxis: {
      min: 'dataMin',
      show: false,
    },
    series: [{
      itemStyle: {
        normal: {
          label: {
            show: true,
            position: [0, -13],
            textStyle: {
              color: 'black'
            },
            formatter: function (params) {
              return params.value + '°'
            }
          }
        }
      },
      type: 'line',
      // symbol: "none",
      symbolSize: '4',
      smooth: true,
      data: []
    }]
  };

  hourChart.setOption(option);
  return hourChart;
}
let di=0,hi=0



Page({
  /**
     * 页面的初始数据
     */
    data: {
      hourArrs: [],
      dayArrs: [],
      ec: {
        onInit: dayChartFun
      },
      ecH:{
        onInit:hourChartFun
      },
      
    },

    
   


    //经纬度请求阿里天气信息
getaliweather(latitude,longitude){
  //这里填写请求网址
  let url = 'http://ip:端口/api/v1/weather/dayweather?latitude=' + latitude + '&longitude=' + longitude,
  _page = this;
  var that=this;
   wx.request({
   url:url,
  header: {
     'Content-Type': 'application/json'
  },
  success:(res)=>{
    var msg = res.data
    //console.log(msg)  
   var dailys = [msg[1].daily,msg[2].daily,msg[3].daily,msg[4].daily,msg[5].daily]
   var hourlys = [msg[0].hourly,msg[1].hourly,msg[2].hourly,msg[3].hourly,msg[4].hourly,msg[5].hourly,msg[6].hourly,msg[7].hourly]
   var sunset = msg[0].daily.sunset
   var sunrise = msg[1].daily.sunrise

   var d= hourlys.map(v=>{return v.temperature})
   _self.hourLine(d)

   var maxArry = dailys.map(item => {
    return item.high;
  });
  var minArry = dailys.map(item => {
    return item.low;
  });
  _self.dayLine(maxArry,minArry)

    that.setData({
      dayArrs : dailys,
      hourArrs : hourlys,
      sunset : sunset,
      sunrise : sunrise
    })
    
  }
})
},


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      var that = this;
      //this.getLanguage()
      di=0
      hi=0
      _self=this
      _this = this;
      
      wx.getLocation({
       success: function (res) {
         //console.log(res)
        latitude = res.latitude
        longitude = res.longitude
        _this.getAmpWeather(latitude,longitude)
        _this.getaliweather(latitude,longitude)
        that.setData({
          latitude1 : latitude,
          longitude1 : longitude,
        })
      }, 
      
    }) 
     
  },



//经纬度请求高德天气信息
getAmpWeather(latitude,longitude){ 
//这里填写请求地址
let url = 'http://ip:端口/api/v1/weather/currentWeather?latitude=' + latitude + '&longitude=' + longitude,
_page = this;
 wx.request({
 url:url,
 header: {
  'Content-Type': 'application/json'
},
  success:(res)=>{
    var msg = res.data
    var val = JSON.parse(msg)
    
    getname = val['city']
    gethumidity = val['humidity']
    gettemperature = val['temperature']
    getweather = val['weather']
    getwinddirection = val['winddirection']
    getwindpower = val['windpower']
    getreporttime = val['reporttime']
    this.setData({
      city:getname,
      humidity:gethumidity,
      temperature:gettemperature,
      weather:getweather,
      winddirection:getwinddirection,
      windpower:getwindpower,
      reporttime:getreporttime
    })
  }
})
},



    //引入项目
dayWeather(){  
      setTimeout(()=>{
        let dayArrs=[{
          week: "周二",
          date: "08-11",
          high: "32",
          low: "26",
          wind_scale: "2",
          weather: "小雨",
          wind_direction: "北",
          img:"1"
        },
        {
          week: "周一",
          date: "08-12",
          high: "35",
          low: "26",
          wind_scale: "2",
          weather: "多云",
          wind_direction: "东南",
          img:"1"
        },
        {
          week: "周一",
          date: "08-13",
          high: "34",
          low: "25",
          wind_scale: "1",
          weather: "小雨",
          wind_direction: "北",
          img:"1"
        },
        {
          week: "周一",
          date: "08-14",
          high: "31",
          low: "25",
          wind_scale: "1",
          weather: "中雨",
          wind_direction: "北",
          img:"1"
        },
        {
          week: "周一",
          date: "08-15",
          high: "30",
          low: "23",
          wind_scale: "2",
          weather: "小雨",
          wind_direction: "东北",
          img:"1"
        }]
        _self.setData({
          dayArrs
        })
        // var maxArry = dayArrs.map(item => {
        //   return item.high;
        // });
        // var minArry = dayArrs.map(item => {
        //   return item.low;
        // });
        // _self.dayLine(maxArry,minArry)
      },1000)
    },
    dayLine(maxArry,minArry){
      ++di
      setTimeout(function () {
        if (dayChart) {
          var option = dayChart.getOption();
          option.series[0].data = maxArry
          option.series[1].data = minArry
          dayChart.setOption(option, true);
          di=0
        } else {
          // 两秒内没画出来就不调了
          if(di<20) _self.dayLine(maxArry,minArry)
          else console.log('五日天气失败')
        }
      }, 100)
    },

  hourWeather(){
      setTimeout(()=>{
        let hourArrs=[{
          bjTime: "08:00",
          temperature: "32",
          wether: "小雨",
          wind_direction: "北",
          wind:"2级",
          img:"1"
        },{
          bjTime: "08:00",
          temperature: "35",
          wether: "小雨",
          wind_direction: "北",
          wind:"2级",
          img:"1"
        },{
          bjTime: "08:00",
          temperature: "30",
          wether: "小雨",
          wind_direction: "北",
          wind:"2级",
          img:"1"
        },{
          bjTime: "08:00",
          temperature: "28",
          wether: "小雨",
          wind_direction: "北",
          wind:"2级",
          img:"1"
        },{
          bjTime: "08:00",
          temperature: "31",
          wether: "小雨",
          wind_direction: "北",
          wind:"2级",
          img:"1"
        },{
          bjTime: "08:00",
          temperature: "26",
          wether: "小雨",
          wind_direction: "北",
          wind:"2级",
          img:"1"
        },{
          bjTime: "08:00",
          temperature: "29",
          wether: "小雨",
          wind_direction: "北",
          wind:"2级",
          img:"1"
        },{
          bjTime: "08:00",
          temperature: "32",
          wether: "小雨",
          wind_direction: "北",
          wind:"2级",
          img:"1"
        }]
        _self.setData({
          hourArrs
        })
        //var d=hourArrs.map(v=>{return v.temperature})
        //console.log(d)
        //_self.hourLine(d)
      },1000)
    },
  hourLine(d){
      ++hi
      setTimeout(function () {
        if (hourChart) {
          var option = hourChart.getOption();
          option.series[0].data = d
          hourChart.setOption(option, true);
          hi=0
        } else {
          if(hi<20) _self.hourLine(d)
          else console.log('小时天气失败')
        }
      }, 100)
    },


/**
 * 页面相关事件处理函数--监听用户下拉动作
 */
onPullDownRefresh:function(){
    var that = this;
    _self=this
    _this = this;
    setTimeout(()=>{
      // 在此调取接口
      _this.updateBlogs(this.data.latitude1,this.data.longitude1)
  }, 1000) //这里调整刷新多久后调用接口
  wx.stopPullDownRefresh() 
},

  //更新数据
  updateBlogs(latitude,longitude){
    var that = this;
    di=0
    hi=0
    _self=this
    _this = this;
    _this.getAmpWeather(latitude,longitude);
    _this.getaliweather(latitude,longitude);
    
  },

//请求语言信息
getLanguage(){
wx.getSystemInfo({
  success: function(res) {
    console.log(res.language)
  }
})
},
   
/*生命周期函数--监听页面初次渲染完成*/
onReady: function () {

},

/*生命周期函数--监听页面显示*/
onShow: function () {

},

/*生命周期函数--监听页面隐藏*/
onHide: function () {

},

/*生命周期函数--监听页面卸载*/
onUnload: function () {

},

/*页面上拉触底事件的处理函数*/
onReachBottom: function () {

},

/*用户点击右上角分享*/
onShareAppMessage: function () {

}

})