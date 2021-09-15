package com.pk.springboot.controller;

import com.pk.springboot.config.PersonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/read_property")
public class ReadProperty {

    @Autowired
    private PersonConfig personConfig;

    @GetMapping("/person")
    public PersonConfig test() {
        int i = 1 / 0;
        return personConfig;
    }
}
