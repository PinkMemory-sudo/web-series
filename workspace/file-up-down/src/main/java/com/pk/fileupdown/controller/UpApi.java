package com.pk.fileupdown.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/up")
@Slf4j
public class UpApi {

    @Value("${up.dir}")
    private String upDir;

    @PostMapping("simple")
    public String simpleIp(MultipartFile file) {
        if (null == file) {
            return "上传文件为空";
        }
        File dest = new File(upDir, file.getOriginalFilename());
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            log.error("保存上传文件失败", e);
        }
        return "上传成功";
    }

}
