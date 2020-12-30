package com.aitao.myblog.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author : AiTao
 * Date : 2020/11/5
 * Time : 9:51
 * Information : 响应状态码工具类
 */
@Component
public class ResponseCode {
    static Map<String, Object> map = new HashMap<String, Object>();

    public static Map<String, Object> ok() {
        map.put("code", 200);
        map.put("message", "OK");
        map.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return map;
    }

    public static Map<String, Object> ok(Object data) {
        map.put("code", 200);
        map.put("message", data);
        map.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return map;
    }

    public static <T> Map<String, Object> ok(Collection<T> collection) {
        map.put("code", 200);
        map.put("message", collection);
        map.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return map;
    }

    public static Map<String, Object> error(Object data) {
        map.put("code", 500);
        map.put("message", data);
        map.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return map;
    }

    public static Map<String, Object> paramIsNull() {
        map.put("code", 500);
        map.put("message", "参数不完整");
        map.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return map;
    }


    public static Map<String, Object> idIsNotNull() {
        map.put("code", 500);
        map.put("message", "编号不能为空");
        map.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return map;
    }

    public static Map<String, Object> idIsNotExists(Integer id) {
        map.put("code", 500);
        map.put("message", id + "不存在");
        map.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return map;
    }
}
