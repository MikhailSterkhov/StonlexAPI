package ru.stonlex.bukkit.utility;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;

@Getter
@RequiredArgsConstructor
public class ProgressBar {

    private final double current, max;
    private final int count;
    private final String yesColor, noColor, symbol;

    public ProgressBar(double current, double max, int symbolCount) {
        this (current, max, symbolCount, "§a", "§7", "•");
    }

    /**
     * Получение полосы прогресса
     */
    public String getProgressBar() {
        double tenPercent = (current / max) * count;

        int percent = (int) Math.round(tenPercent);

        String bar = yesColor + StringUtils.repeat(symbol, percent);
        bar += noColor + StringUtils.repeat(symbol, (count - percent));

        return bar;
    }

    /**
     * Получение процента
     */
    public String getPercent() {
        double allPercent = (current / max) * 100;

        return ((int) Math.round(allPercent)) + "%";
    }

}