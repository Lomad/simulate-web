package com.winning.bigscreen.simulation.Utils;

import com.alibaba.fastjson.JSON;

/**
 * @Author Lemod
 * @Version 2018/2/5
 */
public class JSONUtils {

    @SuppressWarnings("unchecked")
    public static <T> T formatJsonString(String jsonString, T t) {
        return JSON.parseObject(jsonString, (Class<T>) t.getClass());
    }
}
