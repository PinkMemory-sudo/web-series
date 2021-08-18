package com.pk.springboot.cache.dao;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

public interface CacheDao {

    String getNameById(String id);
}
