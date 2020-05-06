package ru.stonlex.example.command;

import org.bukkit.entity.Player;
import ru.stonlex.bukkit.command.StonlexCommand;

public class PlayerCommand extends StonlexCommand<Player> {

    public PlayerCommand() {
        super("player", "player-alias");
    }

    @Override
    public void execute(Player player, String[] args) {
        // command logic...
    }

}
