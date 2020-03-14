package ru.stonlex.bukkit.game.player;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GamePlayer {

    @Getter
    private final String name;

    @Getter
    private final Player player;


    @Getter
    private boolean spectate = false;


    public GamePlayer(String playerName) {
        this.name = playerName;
        this.player = Bukkit.getPlayer(playerName);
    }


    /**
     * Установить игрока наблюдателем игры
     */
    public void setSpectate() {
        this.spectate = true;

        if (player == null) {
            return;
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 3));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));

        player.setAllowFlight(true);
        player.setFlying(true);

        player.getInventory().clear();
    }

}
