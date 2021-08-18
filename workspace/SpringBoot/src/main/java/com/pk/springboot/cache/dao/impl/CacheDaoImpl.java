package com.pk.springboot.cache.dao.impl;

import com.pk.springboot.cache.dao.CacheDao;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.concurrent.TimeUnit;

@CacheConfig(cacheNames = "menuCache", keyGenerator = "myKeyGenerator")
public class CacheDaoImpl implements CacheDao {

    @Override
    @Cacheable(key = "#")
    public String getNameById(String id) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Tom";
    }

    @CacheEvict(value = {"emp"}, beforeInvocation = true,key="#id")
    public void deleteEmp(Integer id){
        //int i = 10/0;
    }
}
