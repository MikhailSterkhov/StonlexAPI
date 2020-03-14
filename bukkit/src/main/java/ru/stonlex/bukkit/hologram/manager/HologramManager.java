package ru.stonlex.bukkit.hologram.manager;

import org.bukkit.Location;
import ru.stonlex.bukkit.hologram.StonlexHologram;
import ru.stonlex.global.Applicable;
import ru.stonlex.global.utility.AbstractCacheManager;

public final class HologramManager extends AbstractCacheManager<StonlexHologram> {

    /**
     * Кеширование голограммы в мапу по ее имени.
     */
    public void cacheHologram(String hologramName, StonlexHologram hologram) {
        cache(hologramName.toLowerCase(), hologram);
    }

    /**
     * Получение голограммы из кеша по ее имени.
     */
    public StonlexHologram getHologram(String hologramName) {
        return get(hologramName.toLowerCase());
    }

    /**
     * Создание голограммы и ее получение.
     */
    public StonlexHologram createHologram(Location location) {
        return new StonlexHologram(location);
    }

    /**
     * Создание голограммы без использования абстракции.
     *
     * Все действия можно проводить через специальный для этого
     * Applicable, что указан в аргументах.
     */
    public void createHologram(String hologramName, Location location, Applicable<StonlexHologram> hologramApplicable) {
        StonlexHologram hologram = createHologram(location);

        cacheHologram(hologramName, hologram);

        hologramApplicable.apply(hologram);
    }

}
