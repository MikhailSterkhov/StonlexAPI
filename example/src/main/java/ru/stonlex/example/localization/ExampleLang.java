package ru.stonlex.example.localization;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.stonlex.global.localization.LocalizationResource;

@RequiredArgsConstructor
@Getter
public enum ExampleLang {

    RU_LANGUAGE(0, "ru", "Русский", LocalizationResource.create().initResources("https://raw.githubusercontent.com/ItzStonlex/StonlexAPI/master/example/src/main/resources/ru_lang.yml")),
    EN_LANGUAGE(1, "en", "English", LocalizationResource.create().initResources("https://raw.githubusercontent.com/ItzStonlex/StonlexAPI/master/example/src/main/resources/en_lang.yml"));

    private final int langId;

    private final String langCode;
    private final String langName;

    private final LocalizationResource resource;


    public String of(@NonNull String key) {
        return resource.getText(key);
    }
}