package ru.stonlex.global.utility;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public class CooldownUtil {
    
    private final Map<String, Long> cooldownMap = new HashMap<>();

    /**
     * Создание и добавление задержки
     */
    public void putCooldown(String cooldownName, long mills) {
        Objects.requireNonNull(cooldownName, "Name of cooldown == null");

        cooldownMap.put(cooldownName, System.currentTimeMillis() + mills);
    }

    /**
     * Получение оставшегося времени у задержки
     */
    public long getCooldown(String cooldownName) {
        Objects.requireNonNull(cooldownName, "Name of cooldown == null");

        Long playerCooldown = cooldownMap.get(cooldownName);

        return playerCooldown == null ? 0 :
                (hasCooldown(cooldownName) ? playerCooldown - System.currentTimeMillis() : 0);
    }

    /**
     * Удаление задержки
     */
    public void removeCooldown(String cooldownName) {
        Objects.requireNonNull(cooldownName, "Name of cooldown == null");

        cooldownMap.remove(cooldownName);
    }

    /**
     * Возвращает boolean, говорящий о том, действует ли
     * еще эта задержка.
     */
    public boolean hasCooldown(String cooldownName) {
        Objects.requireNonNull(cooldownName, "Name of cooldown == null");

        Long playerCooldown = cooldownMap.get(cooldownName);

        return playerCooldown != null && (playerCooldown - System.currentTimeMillis()) > 0;
    }
    
}
