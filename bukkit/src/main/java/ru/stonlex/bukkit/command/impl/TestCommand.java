package ru.stonlex.bukkit.command.impl;

import org.bukkit.entity.Player;
import ru.stonlex.bukkit.command.CommandInfo;
import ru.stonlex.bukkit.command.StonlexCommand;

public class TestCommand extends StonlexCommand<Player> {

    @Override
    public void execute(Player player, CommandInfo commandInfo) {
        player.sendMessage("execute");
    }

}
