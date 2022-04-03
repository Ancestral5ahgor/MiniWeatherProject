package com.shuai.weather.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("citycode")
public class CityCode implements Serializable {

    @TableField("city_name")
    private String cityname;
    @TableField("abcode")
    private Integer abcode;
    @TableField("city_code")
    private String citycode;

}
