package com.pk.springboot.service.impl;

import com.pk.springboot.dao.CronDao;
import com.pk.springboot.service.CronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CronServiceImpl implements CronService {

    @Autowired
    private CronDao cronDao;

    @Override
    public String getCronById(int id) {
        return cronDao.findById(id).get().getCronStr();
    }
}
