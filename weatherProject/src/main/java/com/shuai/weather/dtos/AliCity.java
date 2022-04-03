package com.shuai.weather.dtos;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("alicity")
public class AliCity implements Serializable {
    @TableId(value = "cityid")
    private String cityid;
    @TableField("parentid")
    private String parentid;
    @TableField("citycode")
    private String citycode;
    @TableField("city")
    private String city;

}
