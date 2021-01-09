package ru.stonlex.bukkit.scoreboard;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public enum BaseScoreboardScope {

    /**
     * Скорбоард с данной видимостью будет
     * выдан всем игрокам в сети, и даже новым,
     * таким образом его не нужно будет устанавливать
     * вручную, создавая новые листенеры
     */
    PUBLIC,

    /**
     * Скорборд с прототипной видимостью
     * необходимо выдавать вручную, самостоятельно
     * он устанавливаться никому не будет :(
     */
    PROTOTYPE;


    public static final Multimap<BaseScoreboardScope, BaseScoreboard> SCOPING_SCOREBOARD_MAP
            = HashMultimap.create();
}
