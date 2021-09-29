package com.pk.springboot.controller;

import com.pk.springboot.model.RetBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/excel")
public class ExcelTest {

    @PostMapping("/download")
    public String download(){


       return "hello";
    }
}
