package com.pk.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) {
        File tmpFile = new File("./fileupload.test");
        try {
            file.transferTo(tmpFile);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return e.getMessage();
        }
        return "s!";
    }
}
