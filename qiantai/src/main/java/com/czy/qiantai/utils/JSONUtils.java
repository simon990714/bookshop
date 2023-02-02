package com.czy.qiantai.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 对象转成JSON
     * @param obj
     * @return
     */
    public static String createJson(Object obj){
        try {
            String json = objectMapper.writeValueAsString(obj);
            return json ;
        }catch (Exception e){
            System.out.println("对象转成JSON失败!");
            e.printStackTrace();
        }
        return "" ;
    }


    public static <T> T parseJson(String json ,Class<T> clazz){
        try {
            T t = objectMapper.readValue(json, clazz);
            return t ;
        }catch (Exception e){
            System.out.println("JSON转成对象失败!");
            e.printStackTrace();
        }
        return null;
    }


}

