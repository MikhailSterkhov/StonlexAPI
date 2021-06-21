package ru.stonlex.global.localization;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalizedMessage {

    public static LocalizedMessage create(@NonNull LocalizationResource localizationResource, @NonNull String messageKey) {
        return new LocalizedMessage(messageKey, localizationResource);
    }


    private final String messageKey;
    private final LocalizationResource localizationResource;

    private Object handle;


    public synchronized String toText() {
        if (handle != null)
            return handle.toString();

        return localizationResource.getText(messageKey);
    }

    public synchronized List<String> toList() {
        if (handle != null)
            return ((List<String>) handle);

        return localizationResource.getTextList(messageKey);
    }


    public synchronized LocalizedMessage replace(@NonNull String placeholder, @NonNull Object value) {
        if (handle == null) {

            if (localizationResource.isText(messageKey)) {
                handle = new String(toText().getBytes());
            }

            if (localizationResource.isList(messageKey)) {
                handle = new ArrayList<>(toList());
            }
        }

        if (handle instanceof String) {
            handle = toText().replace(placeholder, value.toString());

        } else {

            handle = toList().stream().map(line -> line.replace(placeholder, value.toString())).collect(Collectors.toList());
        }

        return this;
    }

}
