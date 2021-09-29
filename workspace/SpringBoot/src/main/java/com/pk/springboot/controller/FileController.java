package com.pk.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController implements ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

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

    /**
     * 将文件放到body中返回
     */
    @GetMapping("/download_body")
    public ResponseEntity<Resource> downloadBody() {
        HttpHeaders httpHeaders = new HttpHeaders();
        Resource resource = resourceLoader.getResource("classpath:test");
        httpHeaders.add("Content-Disposition",
                String.format("attachment; filename=\"%s\"", resource.getFilename()));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

    @GetMapping("/download_response")
    public ResponseEntity<?> downloadResponse(HttpServletResponse response) {
        Resource resource = resourceLoader.getResource("classpath:test1");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;fileName=" + resource.getFilename());
        try (ServletOutputStream outputStream = response.getOutputStream();
             FileInputStream fileInputStream = new FileInputStream(resource.getFile())) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            log.error("下载失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

}
