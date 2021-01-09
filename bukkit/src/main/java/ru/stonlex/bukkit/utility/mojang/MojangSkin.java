package ru.stonlex.bukkit.utility.mojang;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Getter
public class MojangSkin {

    private final String skinName;
    private final String playerUUID;
    private final String value;
    private final String signature;
    private final long timestamp;

    public boolean isExpired() {
        return System.currentTimeMillis() - this.timestamp > TimeUnit.HOURS.toMillis(5);
    }
}