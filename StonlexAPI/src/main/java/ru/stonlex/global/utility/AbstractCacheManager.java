package ru.stonlex.global.utility;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractCacheManager<T> {

    protected final Map<String, T> cacheMap = new HashMap<>();


    /**
     * Загрузка данных в кеш.
     */
    protected void cache(String dataName, T cache) {
        cacheMap.put(dataName.toLowerCase(), cache);
    }

    /**
     * Получение данных из кеша.
     */
    protected T get(String dataName) {
        return cacheMap.get(dataName.toLowerCase());
    }

    /**
     * Получение из кеша.
     *
     * Если в кеше нет, записываем в мапу новый объект
     *  и возвращаем его.
     */
    protected T get(String dataName, Function<? super String, ? extends T> mappingFunction) {
        return cacheMap.computeIfAbsent(dataName.toLowerCase(), mappingFunction);
    }

}
