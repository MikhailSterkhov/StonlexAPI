package ru.stonlex.bukkit.hologram;

import org.bukkit.Location;
import ru.stonlex.global.Applicable;
import ru.stonlex.global.utility.AbstractCacheManager;

public final class HologramManager extends AbstractCacheManager<MoonHologram> {

    /**
     * Кеширование голограммы в мапу по ее имени.
     */
    public void cacheHologram(String hologramName, MoonHologram hologram) {
        cache(hologramName.toLowerCase(), hologram);
    }

    /**
     * Получение голограммы из кеша по ее имени.
     */
    public MoonHologram getHologram(String hologramName) {
        return get(hologramName.toLowerCase());
    }

    /**
     * Создание голограммы и ее получение.
     */
    public MoonHologram createHologram(Location location) {
        return new MoonHologram(location);
    }

    /**
     * Создание голограммы без использования абстракции.
     *
     * Все действия можно проводить через специальный для этого
     * Applicable, что указан в аргументах.
     */
    public void createHologram(String hologramName, Location location, Applicable<MoonHologram> hologramApplicable) {
        MoonHologram hologram = createHologram(location);

        cacheHologram(hologramName, hologram);

        hologramApplicable.apply(hologram);
    }

}
