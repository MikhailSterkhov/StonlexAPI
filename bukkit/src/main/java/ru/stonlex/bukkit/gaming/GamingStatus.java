package ru.stonlex.bukkit.gaming;

import com.comphenix.protocol.reflect.IntEnum;

public class GamingStatus extends IntEnum {

    public static final int WAIT_PLAYERS = 10;
    public static final int IN_GAME = 20;
    public static final int END_GAME = 30;
    public static final int RESTART_GAME = 40;


    public static boolean isWaitPlayers(int statusLevel) {
        return statusLevel == WAIT_PLAYERS;
    }

    public static boolean isGameIn(int statusLevel) {
        return statusLevel == IN_GAME;
    }

    public static boolean isGameEnd(int statusLevel) {
        return statusLevel == END_GAME;
    }

    public static boolean isGameRestart(int statusLevel) {
        return statusLevel == RESTART_GAME;
    }
}
