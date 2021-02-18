package ru.stonlex.global.localization;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.stonlex.global.utility.map.MultikeyHashMap;
import ru.stonlex.global.utility.map.MultikeyMap;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LanguageType {

    public static final MultikeyMap<LanguageType> LANGUAGE_TYPES = new MultikeyHashMap<LanguageType>()
            .register(String.class, LanguageType::getLangName)
            .register(int.class,    LanguageType::getIndex);


    /**
     * Создать языковой тип для новых
     * локализированных сообщений под одинаковые
     * ключи
     *
     * @param index                - индекс типа языка
     * @param langName             - индексальное название типа языка
     * @param displayName          - выводимое имя типа языка
     * @param localizationResource - ресурсы для локализированных сообщений языка
     */
    public static LanguageType create(int index,
                                      @NonNull String langName, @NonNull String displayName,
                                      @NonNull LocalizationResource localizationResource) {

        LanguageType languageType = new LanguageType(index, langName, displayName, localizationResource);
        LANGUAGE_TYPES.put(languageType);

        return languageType;
    }

    /**
     * Получить уже когда-то созданный
     * и кешированный тип языка для
     * локализированных сообщений по его индексу
     *
     * @param langIndex - индекс языка
     */
    public static LanguageType of(int langIndex) {
        return LANGUAGE_TYPES.get(int.class, langIndex);
    }

    /**
     * Получить уже когда-то созданный
     * и кешированный тип языка для
     * локализированных сообщений по его
     * индексальному названию
     *
     * @param langName - индексальное название языка
     */
    public static LanguageType of(@NonNull String langName) {
        return LANGUAGE_TYPES.get(String.class, langName);
    }


    private final int index;

    private final String langName;
    private final String displayName;

    private final LocalizationResource localizationResource;


    /**
     * Получить локализированное сообщение, преобразованное
     * в строку по ключу
     *
     * @param messageKey - ключ локализированного сообщения
     */
    public synchronized String getText(@NonNull String messageKey) {
        return localizationResource.getText(messageKey);
    }

    /**
     * Получить локализированное сообщение, преобразованное
     * в список строк по ключу
     *
     * @param messageKey - ключ локализированного сообщения
     */
    public synchronized List<String> getTextList(@NonNull String messageKey) {
        return localizationResource.getTextList(messageKey);
    }

    /**
     * Получить локализированное сообщение
     *
     * @param messageKey - ключ локализированного сообщения
     */
    public synchronized LocalizedMessage getMessage(@NonNull String messageKey) {
        return localizationResource.getMessage(messageKey);
    }


    /**
     * Проверить наличие локализированного сообщения
     * в списке загруженных
     *
     * @param messageKey - ключ локализированного сообщения
     */
    public synchronized boolean hasMessage(@NonNull String messageKey) {
        return localizationResource.hasMessage(messageKey);
    }


    /**
     * Проверить наличие локализированного сообщения
     * в списке загруженных
     *
     * @param messageKey - ключ локализированного сообщения
     */
    public synchronized boolean isText(@NonNull String messageKey) {
        return localizationResource.isText(messageKey);
    }

    /**
     * Проверить наличие локализированного сообщения
     * в списке загруженных
     *
     * @param messageKey - ключ локализированного сообщения
     */
    public  synchronized boolean isList(@NonNull String messageKey) {
        return localizationResource.isList(messageKey);
    }

}
