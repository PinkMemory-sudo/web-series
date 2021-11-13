package com.pk.springboot.model;

import lombok.Data;

/**
 * 账号管理中，账号对应的单个的区域Item，区域Item列表组成了账号对应的全部的区域
 * 使用时，遍历每个区域Item，按照区（县）->市->省的顺序，最小的不为空的项目，即为过滤需要的区域条件
 */
@Data
public class Area {
    private String province;

    private String city;

    private String district;

    /**
     * 便于前端展示历史记录
     *
     * @return
     */
    @Override
    public String toString() {
        return province + city + district;
    }
}
