package ru.stonlex.global.utility;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtil {

    public final Gson GSON               = new Gson();
    public final JsonParser JSON_PARSER  = new JsonParser();


    /**
     * Преобразовать объект в JSON
     *
     * @param object - объект
     */
    public String toJson(Object object) {
        return GSON.toJson(object);
    }

    /**
     * Преобразовать JSON обратно в объект
     *
     * @param json - JSON
     * @param clazz - класс объекта
     */
    public <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    public JsonElement parse(@NonNull String json) {
        return JSON_PARSER.parse(json);
    }

}
