package ru.stonlex.bukkit.game.event;

import lombok.Getter;
import org.bukkit.event.Listener;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.game.GameManager;

public abstract class GameListener implements Listener {

    @Getter
    protected final GameManager gameManager = BukkitAPI.getInstance().getGameManager();
}
