package com.pk.springboot.controller;

import com.pk.springboot.model.Area;
import com.pk.springboot.model.RetBody;
import com.pk.springboot.model.UserEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest_template")
public class RestTemplateApi {

    /**
     * @param localDate yyyy-MM-dd
     */
    @GetMapping("/get_test")
    public ResponseEntity<?> getTest(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("Tom");
        userEntity.setAge(18);
        Area area = new Area();
        area.setProvince("上海市");
        area.setCity("上海市");
        area.setDistrict("浦东新区");
        List<Area> areaList = new ArrayList<>();
        areaList.add(area);
        userEntity.setAreaList(areaList);
        return ResponseEntity.ok(RetBody.ok(userEntity));
    }

    /**
     * @param localDate yyyy-MM-dd
     */
    @PostMapping("/post_test")
    public ResponseEntity<?> postTest(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("Tom");
        userEntity.setAge(18);
        Area area = new Area();
        area.setProvince("上海市");
        area.setCity("上海市");
        area.setDistrict("浦东新区");
        List<Area> areaList = new ArrayList<>();
        areaList.add(area);
        userEntity.setAreaList(areaList);
        return ResponseEntity.ok(RetBody.ok(userEntity));
    }

    /**
     * @param localDate yyyy-MM-dd
     */
    @PutMapping("/put_test")
    public ResponseEntity<?> putTest(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("Tom");
        userEntity.setAge(18);
        Area area = new Area();
        area.setProvince("上海市");
        area.setCity("上海市");
        area.setDistrict("浦东新区");
        List<Area> areaList = new ArrayList<>();
        areaList.add(area);
        userEntity.setAreaList(areaList);
        return ResponseEntity.ok(RetBody.ok(userEntity));
    }

    /**
     * @param localDate yyyy-MM-dd
     */
    @DeleteMapping("/delete_test")
    public ResponseEntity<?> deleteTest(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("Tom");
        userEntity.setAge(18);
        Area area = new Area();
        area.setProvince("上海市");
        area.setCity("上海市");
        area.setDistrict("浦东新区");
        List<Area> areaList = new ArrayList<>();
        areaList.add(area);
        userEntity.setAreaList(areaList);
        return ResponseEntity.ok(RetBody.ok(userEntity));
    }
}
