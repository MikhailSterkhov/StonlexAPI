package ru.stonlex.bukkit.utility;

import lombok.experimental.UtilityClass;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;
import ru.stonlex.bukkit.BukkitAPI;

@UtilityClass
public class MetadataUtil {

    private final Plugin plugin = BukkitAPI.getInstance();


    /**
     * Установить какие-то данные объекту
     *
     * @param metadatableObject - сам объект, которому устанавливать данные
     * @param metadataName - наименование данных
     * @param object - сам объект, что мы устанавливаем
     */
    public void setMetadata(Metadatable metadatableObject, String metadataName, Object object) {
        metadatableObject.setMetadata(metadataName, new FixedMetadataValue(plugin, object));
    }

    /**
     * Удалить какие-то данные объекта
     *
     * @param metadatableObject - сам объект, которому удалить данные
     * @param metadataName - наименование данных
     */
    public void removeMetadata(Metadatable metadatableObject, String metadataName) {
        metadatableObject.removeMetadata(metadataName, plugin);
    }

    /**
     * Получить какие-то данные из объекта
     *
     * @param metadatableObject - сам объект, которому устанавливать данные
     * @param metadataName - наименование данных
     * @param objectClass - класс объекта, что мы получаем
     */
    public <T> T getMetadata(Metadatable metadatableObject, String metadataName, Class<T> objectClass) {
        return (T) getMetadata(metadatableObject, metadataName);
    }

    /**
     * Получить какие-то данные из объекта
     *
     * @param metadatableObject - сам объект, которому устанавливать данные
     * @param metadataName - наименование данных
     */
    public Object getMetadata(Metadatable metadatableObject, String metadataName) {
        return metadatableObject.getMetadata(metadataName)
                .stream()
                .filter(metadataValue -> metadataValue.getOwningPlugin().equals(plugin))
                .findFirst()
                .orElse(null).value();
    }

    /**
     * Проверить, существует ли метадата под указанным именем
     *
     * @param metadatableObject - сам объект, которому устанавливать данные
     * @param metadataName - наименование данных
     */
    public boolean hasMetadata(Metadatable metadatableObject, String metadataName) {
        return metadatableObject.hasMetadata(metadataName);
    }

}
