package com.xjy.graduateweb.bean;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class JsonMessage {
    private Integer code;
    private String message;
    private Map<String,Object> map;

    public JsonMessage add(String key,Object value){
        this.map = new HashMap<String, Object>();
        map.put(key,value);
        return this;
    }

    public static  JsonMessage success() {
        JsonMessage message = new JsonMessage();
        message.setCode(200);
        message.setMessage("成功");
        return message;
    }

    public static JsonMessage fail() {
        JsonMessage message = new JsonMessage();
        message.setCode(500);
        message.setMessage("失败");
        return message;
    }

}
