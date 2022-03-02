package com.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static String toJson(Object obj) {
        String result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            logger.error("Failed to serialize Object", ex);
        }
        return result;
    }

    public static <T> T toObject(String json, Class<T> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        T object = null;
        try {
            object = mapper.readValue(json, valueType);
        } catch (Exception ex) {
            logger.error("Failed to deserialize Object", ex);
        }
        return object;
    }
}
