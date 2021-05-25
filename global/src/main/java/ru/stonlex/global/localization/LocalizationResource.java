package ru.stonlex.global.localization;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class LocalizationResource {

    public static LocalizationResource create() {
        return new LocalizationResource();
    }

    private final Map<String, Object> localizationMessages = new LinkedHashMap<>();


    /**
     * Создать конструкцию YAML конфигураций
     * для чтения локализированных сообщений
     * в данном формате со сторонних ресурсов
     */
    private synchronized Yaml newYaml() {
        Constructor constructor = new Constructor();

        PropertyUtils propertyUtils = new PropertyUtils();
        propertyUtils.setSkipMissingProperties(true);

        constructor.setPropertyUtils(propertyUtils);
        return new Yaml(constructor);
    }

    /**
     * Инициализировать локализированные сообщения
     * со стороннего ресурса в формате YAML
     */
    @SneakyThrows
    public synchronized LocalizationResource init(@NonNull InputStream inputStream) {
        localizationMessages.clear();
        localizationMessages.putAll(newYaml().loadAs(inputStream, LinkedHashMap.class));

        inputStream.close();
        return this;
    }

    /**
     * Инициализировать локализированные сообщения
     * со стороннего ресурса в формате YAML
     */
    @SneakyThrows
    public synchronized LocalizationResource initResources(@NonNull String resourceUrl) {
        localizationMessages.clear();

        try (InputStreamReader inputStream = new InputStreamReader(new URL(resourceUrl).openStream())) {
            localizationMessages.putAll(newYaml().loadAs(inputStream, LinkedHashMap.class));
        }

        return this;
    }

    /**
     * Инициализировать локализированные сообщения
     * со стороннего ресурса в формате YAML
     */
    @SneakyThrows
    public synchronized LocalizationResource initYaml(@NonNull File file) {
        return init(new FileInputStream(file));
    }

    /**
     * Добавить кастомное локализированное сообщение
     * вручную
     *
     * @param messageKey - ключ локализированного сообщения
     * @param message    - локализированное сообщение
     */
    public synchronized LocalizationResource addMessage(@NonNull String messageKey, @NonNull Object message) {
        localizationMessages.put(messageKey, message);
        return this;
    }

    /**
     * Получить локализированное сообщение, преобразованное
     * в строку по ключу
     *
     * @param messageKey - ключ локализированного сообщения
     */
    public synchronized String getText(@NonNull String messageKey) {
        return localizationMessages.getOrDefault(messageKey, messageKey).toString();
    }

    /**
     * Получить локализированное сообщение, преобразованное
     * в список строк по ключу
     *
     * @param messageKey - ключ локализированного сообщения
     */
    public synchronized List<String> getTextList(@NonNull String messageKey) {
        return ((List<String>) localizationMessages.getOrDefault(messageKey, Collections.singletonList(messageKey)));
    }

    /**
     * Получить локализированное сообщение
     *
     * @param messageKey - ключ локализированного сообщения
     */
    public synchronized LocalizedMessage getMessage(@NonNull String messageKey) {
        return LocalizedMessage.create(this, messageKey);
    }


    /**
     * Проверить наличие локализированного сообщения
     * в списке загруженных
     *
     * @param messageKey - ключ локализированного сообщения
     */
    public synchronized boolean hasMessage(@NonNull String messageKey) {
        return localizationMessages.containsKey(messageKey);
    }

    /**
     * Проверить наличие локализированного сообщения
     * в списке загруженных
     *
     * @param messageKey - ключ локализированного сообщения
     */
    public synchronized boolean isText(@NonNull String messageKey) {
        return hasMessage(messageKey) && (localizationMessages.get(messageKey) instanceof String);
    }

    /**
     * Проверить наличие локализированного сообщения
     * в списке загруженных
     *
     * @param messageKey - ключ локализированного сообщения
     */
    public  synchronized boolean isList(@NonNull String messageKey) {
        return hasMessage(messageKey) && (localizationMessages.get(messageKey) instanceof List);
    }

}
