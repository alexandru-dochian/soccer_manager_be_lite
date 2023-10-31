package forktex.SoccerManagerBELite.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;


public class JsonObjectMapper<T> {
    public static <T> T getObjectFromJsonString(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }

    public static String getJsonStringFromObject(Object object) {
        return new Gson().toJson(object);
    }
}

