package ru.stonlex.global.localization;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalizedMessage {

    public static LocalizedMessage create(@NonNull LocalizationResource localizationResource, @NonNull String messageKey) {
        return new LocalizedMessage(messageKey, localizationResource);
    }


    private final String messageKey;
    private final LocalizationResource localizationResource;


    public synchronized String toText() {
        return localizationResource.getText(messageKey);
    }

    public synchronized List<String> toList() {
        return localizationResource.getTextList(messageKey);
    }


    public synchronized LocalizedMessage replace(@NonNull String placeholder, @NonNull Object value) {
        Object handle = localizationResource.getLocalizationMessages().get(messageKey);

        if (handle instanceof String) {
            handle = toText().replace(placeholder, value.toString());

        } else {

            handle = toList().stream().map(line -> line.replace(placeholder, value.toString())).collect(Collectors.toList());
        }

        localizationResource.addMessage(messageKey, handle.toString());
        return this;
    }

}
