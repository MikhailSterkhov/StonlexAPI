package ru.stonlex.bukkit.utility;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;

@RequiredArgsConstructor
@Getter
public enum Rarity {

    NONE(0, 0, 0, ChatColor.DARK_GRAY),
    COMMON(1, 1, 0, ChatColor.YELLOW),
    RARE( 2, 0.27, 0.02, ChatColor.AQUA),
    EPIC(3, 0.07, 0.03, ChatColor.DARK_PURPLE),
    LEGENDARY(4, 0.025, 0.025, ChatColor.GOLD);

    private final int rarity;
    private final double chance;
    private final double changeChance;
    private final ChatColor color;

}
