package ru.stonlex.example.localization;

import ru.stonlex.global.localization.LanguageType;
import ru.stonlex.global.localization.LocalizationResource;

public class ExampleLang {

    public static final LanguageType RU_LANGUAGE = LanguageType.create(0, "ru", "Русский", LocalizationResource.create().initResources("https://raw.githubusercontent.com/ItzStonlex/StonlexAPI/master/example/src/main/resources/ru_lang.yml"));
    public static final LanguageType EN_LANGUAGE = LanguageType.create(1, "en", "English", LocalizationResource.create().initResources("https://raw.githubusercontent.com/ItzStonlex/StonlexAPI/master/example/src/main/resources/en_lang.yml"));
}