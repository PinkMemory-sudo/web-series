package com.pk.springboot.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.lang.Nullable;

/**
 * 通用接口返回体
 */
@Data
public class RetBody<T> {
    // 状态码
    private int status;
    // 消息
    private String msg;
    // 返回数据
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> RetBody<T> ok(@Nullable T t) {
        RetBody<T> retBody = new RetBody<>();
        retBody.setStatus(1);
        retBody.setMsg("success");
        retBody.setData(t);
        return retBody;
    }

    public static RetBody bad(String msg){
        RetBody retBody = new RetBody<>();
        retBody.setStatus(0);
        retBody.setMsg(msg);
        return retBody;
    }
}
