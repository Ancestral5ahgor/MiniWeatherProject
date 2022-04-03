package com.shuai.weather.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuai.weather.dtos.AliCity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FindAlicityMapper extends BaseMapper<AliCity> {
    public List<String> findAllcity();
}
