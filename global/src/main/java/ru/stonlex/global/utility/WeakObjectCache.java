package ru.stonlex.global.utility;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeakObjectCache {

    @Getter
    private final Map<String, WeakReference<?>> weakObjectMap = new HashMap<>();


// ================================================================================================================== //

    /**
     * Создать хранилище пакетных данных
     * для его обработки
     */
    public static WeakObjectCache create() {
        return new WeakObjectCache();
    }

// ================================================================================================================== //


    /**
     * Получить хранилище объекта, который хранится
     * по определенному имени данных
     *
     * @param objectType     - тип объекта, который хотим получить
     * @param objectName - имя данных для объекта
     */
    public <V> WeakReference<V> getWeakObject(@NonNull Class<V> objectType,
                                              @NonNull String objectName) {

        return (WeakReference<V>) weakObjectMap.get(objectName.toLowerCase());
    }

    /**
     * Получить объект из хранилища, который хранится
     * по определенному имени данных
     *
     * @param objectType     - тип объекта, который хотим получить
     * @param objectName - имя данных для объекта
     */
    public <V> V getObject(@NonNull Class<V> objectType,
                           @NonNull String objectName) {

        return getWeakObject(objectType, objectName).get();
    }

    /**
     * Получить объект из хранилища, который хранится
     * по определенному имени данных
     *
     * @param objectName - имя данных для объекта
     */
    public ValidatedObject getValidatedObject(@NonNull String objectName) {
        return new ValidatedObject(getObject(Object.class, objectName));
    }

    /**
     * Добавить данные в хранилище
     *
     * @param objectName  - имя данных
     * @param weakObject - значение данных
     */
    public void addObject(@NonNull String objectName,
                          @NonNull Object weakObject) {

        weakObjectMap.put(objectName.toLowerCase(), new WeakReference<>(weakObject));
    }

}