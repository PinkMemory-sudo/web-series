package com.pk.mybatis.dao.mapper;

import com.pk.mybatis.entity.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PersonMapper {

    Person findById(int id);

    // 一对多映射
    Person findPersonAndPhoneById(int id);


}
