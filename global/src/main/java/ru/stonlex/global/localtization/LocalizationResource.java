package ru.stonlex.global.localtization;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class LocalizationResource {

    private final String resourceURL;
    private final Map<String, Object> localizationMessages = new LinkedHashMap<>();


    private synchronized Yaml newYaml() {
        Constructor constructor = new Constructor();

        PropertyUtils propertyUtils = new PropertyUtils();
        propertyUtils.setSkipMissingProperties(true);

        constructor.setPropertyUtils(propertyUtils);
        return new Yaml(constructor);
    }

    @SneakyThrows
    public synchronized LocalizationResource initResources() {
        try (InputStreamReader inputStream = new InputStreamReader(new URL(resourceURL).openStream())) {
            localizationMessages.putAll(newYaml().loadAs(inputStream, LinkedHashMap.class));
        }

        return this;
    }

    public synchronized String getMessage(@NonNull String messageKey) {
        return localizationMessages.get(messageKey).toString();
    }

    public synchronized List<String> getMessageList(@NonNull String messageKey) {
        String message = getMessage(messageKey);

        return Arrays.asList(message.substring(1, message.length() - 1).split(", "));
    }

    public synchronized boolean hasMessage(@NonNull String messageKey) {
        return localizationMessages.containsKey(messageKey);
    }

}
