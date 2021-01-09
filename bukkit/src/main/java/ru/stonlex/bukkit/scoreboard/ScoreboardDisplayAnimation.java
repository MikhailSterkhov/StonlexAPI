package ru.stonlex.bukkit.scoreboard;

import java.util.Collection;

public interface ScoreboardDisplayAnimation {

    Collection<String> getDisplayAnimation();

    String getCurrentDisplay();

    void nextDisplay();
}
