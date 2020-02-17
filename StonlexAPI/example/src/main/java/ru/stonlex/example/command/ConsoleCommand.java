package ru.stonlex.example.command;

import org.bukkit.command.ConsoleCommandSender;
import ru.stonlex.bukkit.command.CommandInfo;
import ru.stonlex.bukkit.command.StonlexCommand;

public class ConsoleCommand extends StonlexCommand<ConsoleCommandSender> {

    @Override
    public void execute(ConsoleCommandSender console, CommandInfo commandInfo) {
        // command logic...
    }

}
