package com.pk.fileupdown.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/down")
@Slf4j
public class DownFile implements ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/body")
    public ResponseEntity simpleDown() {
        /**
         * 1. 设置响应头
         * 2. 设置ContentType
         * 3. 返回文件流
         */
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment;filename=" + "body.txt");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 假设file是你调用service，dao查询数据库后生成的
        Resource resource = resourceLoader.getResource("file:./body.txt");
        log.info("body.txt是否存在:{}",resource.exists());
        return new ResponseEntity(resourceLoader.getResource("file:./body.txt"), headers, HttpStatus.OK);
    }

    @GetMapping("/response_stream")
    public void resOpsDownload(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            FileInputStream fin = new FileInputStream("./response.txt");
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = fin.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + "response.txt");
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity all(HttpServletResponse response) {

        try {
            ServletOutputStream out = response.getOutputStream();
            FileInputStream fin = new FileInputStream("./response.txt");
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = fin.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + "response.txt");
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment;filename=" + "body.txt");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 假设file是你调用service，dao查询数据库后生成的
        return new ResponseEntity(resourceLoader.getResource("file: ./body.txt"), headers, HttpStatus.OK);
    }
}
