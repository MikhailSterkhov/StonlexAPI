package ru.stonlex.example.localize;

import lombok.NonNull;
import ru.stonlex.global.localtization.LocalizationResource;

public class Lang {

    public static final LocalizationResource RU_LOCALIZATION_RESOURCE = new LocalizationResource("https://github.com/ItzStonlex/StonlexAPI/blob/master/example/src/main/resources/ru_lang.yml").initResources();
    public static final LocalizationResource EN_LOCALIZATION_RESOURCE = new LocalizationResource("https://github.com/ItzStonlex/StonlexAPI/blob/master/example/src/main/resources/en_lang.yml").initResources();


    public static String of(@NonNull LanguageType languageType, @NonNull String key) {
        switch (languageType) {

            case RUSSIAN:
                return RU_LOCALIZATION_RESOURCE.getMessage(key);

            case ENGLISH:
                return EN_LOCALIZATION_RESOURCE.getMessage(key);
        }

        return null;
    }
}
