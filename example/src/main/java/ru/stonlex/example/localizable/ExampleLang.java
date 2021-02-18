package ru.stonlex.example.localizable;

import lombok.NonNull;
import ru.stonlex.global.localtization.LanguageType;
import ru.stonlex.global.localtization.LocalizedMessage;

public class ExampleLang {

    public static final LanguageType RU_LANGUAGE = LanguageType.create(0, "ru", "Русский", "https://raw.githubusercontent.com/ItzStonlex/StonlexAPI/master/example/src/main/resources/ru_lang.yml");
    public static final LanguageType EN_LANGUAGE = LanguageType.create(1, "en", "English", "https://raw.githubusercontent.com/ItzStonlex/StonlexAPI/master/example/src/main/resources/en_lang.yml");

    /**
     * Получить локализированный текст
     * из загруженной YAML конфигурации
     * по URL.
     *
     * @param languageType - тип языка
     * @param key          - ключ к локализированному сообщению
     */
    public static String of(@NonNull LanguageType languageType, @NonNull String key) {
        return languageType.getLocalizationResource().getText(key);
    }

    /**
     * Получить локализированный сообщение
     * из загруженной YAML конфигурации
     * по URL.
     *
     * @param languageType - тип языка
     * @param key          - ключ к локализированному сообщению
     */
    public static LocalizedMessage message(@NonNull LanguageType languageType, @NonNull String key) {
        return languageType.getLocalizationResource().getMessage(key);
    }

}