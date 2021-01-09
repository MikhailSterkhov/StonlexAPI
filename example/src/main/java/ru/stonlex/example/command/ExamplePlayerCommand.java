package ru.stonlex.example.command;

import org.bukkit.entity.Player;
import ru.stonlex.bukkit.command.BaseCommand;

public class ExamplePlayerCommand extends BaseCommand<Player> {

    public ExamplePlayerCommand(boolean constructorRegister) {
        super(constructorRegister, "player", "player-alias");
    }

    @Override
    protected void executeCommand(Player player, String[] args) {
        // command logic...
    }

}
