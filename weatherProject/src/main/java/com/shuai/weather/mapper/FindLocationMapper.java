package com.shuai.weather.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuai.weather.pojo.CityCode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FindLocationMapper extends BaseMapper<CityCode> {

}
