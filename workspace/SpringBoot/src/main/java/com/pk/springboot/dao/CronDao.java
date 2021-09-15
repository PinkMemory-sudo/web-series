package com.pk.springboot.dao;

import com.pk.springboot.entity.CronEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CronDao extends JpaRepository<CronEntity, Integer> {

}
