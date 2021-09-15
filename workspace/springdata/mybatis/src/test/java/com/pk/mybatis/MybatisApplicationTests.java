package com.pk.mybatis;

import com.pk.mybatis.dao.mapper.PersonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MybatisApplicationTests {

    @Autowired
    private PersonMapper personDao;

    @Test
    void contextLoads() {
    }

}
