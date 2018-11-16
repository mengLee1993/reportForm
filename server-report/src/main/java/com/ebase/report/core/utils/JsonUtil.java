package com.ebase.report.core.utils;


import com.alibaba.fastjson.JSONObject;
import com.ebase.report.core.json.JsonRequest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * ClassName: ExcelAttribute1
 * Description:
 * Author: wangyu
 * Date: 2018/4/12 10:05
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    public static final ObjectMapper OM = new ObjectMapper();

    static {
        OM.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public static JavaType assignList(Class<? extends Collection> collection, Class<? extends Object> object) {
        return JsonUtil.OM.getTypeFactory().constructParametricType(collection, object);
    }

    public static <T> List<T> readValuesAsArrayList(String key, Class<T> object) {
        List<T> list = Collections.emptyList();
        try {
            if (isEmpty(key)) {
                return list;
            }
            list = OM.readValue(key, assignList(ArrayList.class, object));
        } catch (JsonParseException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }


    public static String toJson(Object obj) {
        try {
            return OM.writeValueAsString(obj);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T fromJson(SimpleDateFormat sdf, String json, Class<T> clazz) {
        try {
            OM.setDateFormat(sdf);
            T t = OM.readValue(json, clazz);
            OM.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            return t;
        } catch (JsonParseException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }


    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return OM.readValue(json, clazz);
        } catch (JsonParseException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static String toJson(Object obj, String callback) {
        if (obj instanceof String) {
            return (String) obj;
        }
        String rs = null;
        try {
            JsonUtil.OM.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            rs = JsonUtil.OM.writeValueAsString(obj);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        if (rs == null) {
            rs = "{\"rc\":-1}";//解析JSON异常/IO异常
        }
        if (isNotEmpty(callback)) {
            rs = String.format("%s(%s)", callback, rs);
        }
        return rs;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static <T> JsonRequest<T> stringToJsonResponse(String json, Class<T> clazz){
        JsonRequest resutlData = (JsonRequest) JSONObject.parseObject(json, JsonRequest.class);
        T resBody = JSONObject.toJavaObject((JSONObject) resutlData.getReqBody(), clazz);
        resutlData.setReqBody(resBody);
        return resutlData;
    }
}
