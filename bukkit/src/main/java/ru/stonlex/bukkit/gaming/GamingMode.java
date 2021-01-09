package ru.stonlex.bukkit.gaming;

import com.comphenix.protocol.reflect.IntEnum;
import com.google.common.base.Preconditions;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class GamingMode extends IntEnum {

    public static final int SOLO = 1;
    public static final int DOUBLE = 2;
    public static final int TRIPLE = 3;

    protected static final Map<String, Integer> GAMING_MODE_MAP = new HashMap<String, Integer>() {{

        put("solo", SOLO);
        put("double", DOUBLE);
        put("triple", TRIPLE);
    }};

    public static int getPlayersInMode(@NonNull String gamingMode) {
        return GAMING_MODE_MAP.get(gamingMode.toLowerCase());
    }

    public static void addGamingMode(@NonNull String gamingMode, int playersInTeamCount) {
        Preconditions.checkArgument(playersInTeamCount > 0, "Count of the players in team must be > 0");

        GAMING_MODE_MAP.put(gamingMode.toLowerCase(), playersInTeamCount);
    }
}
