package com.pk.mybatis.dao;

import com.pk.mybatis.dao.mapper.PersonMapper;
import com.pk.mybatis.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PersonMapperTest {

    @Autowired
    private PersonMapper personMapper;

    @Test
    public void findOneTest(){
        Person people = personMapper.findById(1);
        System.out.println(people);
    }

    @Test
    public void findPersonAndPhoneByIdTest(){
        Person person = personMapper.findPersonAndPhoneById(1);
        System.out.println(person);
    }
}
