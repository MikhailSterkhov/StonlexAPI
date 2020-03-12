package ru.stonlex.bukkit.utility;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

import java.util.Objects;

@UtilityClass
public class PlayerCooldownUtil {

    private final Table<String, String, Long> cooldownTable = HashBasedTable.create();

    /**
     * Создание и добавление задержки
     */
    public void putCooldown(String cooldownName, Player player, long mills) {
        Objects.requireNonNull(cooldownName, "Name of cooldown == null");
        Objects.requireNonNull(player, "player == null");

        cooldownTable.put(cooldownName, player.getName(), System.currentTimeMillis() + mills);
    }

    /**
     * Получение оставшегося времени у задержки
     */
    public long getCooldown(String cooldownName, Player player) {
        Objects.requireNonNull(cooldownName, "Name of cooldown == null");
        Objects.requireNonNull(player, "player == null");

        Long playerCooldown = cooldownTable.get(cooldownName, player.getName());

        return playerCooldown == null ? 0 :
                (hasCooldown(cooldownName, player) ? playerCooldown - System.currentTimeMillis() : 0);
    }

    /**
     * Удаление задержки
     */
    public void removeCooldown(String cooldownName, Player player) {
        Objects.requireNonNull(cooldownName, "Name of cooldown == null");
        Objects.requireNonNull(player, "player == null");

        cooldownTable.remove(cooldownName, player.getName());
    }

    /**
     * Возвращает boolean, говорящий о том, действует ли
     * еще эта задержка.
     */
    public boolean hasCooldown(String cooldownName, Player player) {
        Objects.requireNonNull(cooldownName, "Name of cooldown == null");
        Objects.requireNonNull(player, "player == null");

        Long playerCooldown = cooldownTable.get(cooldownName, player.getName());

        return playerCooldown != null && (playerCooldown - System.currentTimeMillis()) > 0;
    }

}
