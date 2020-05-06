package ru.stonlex.example.command;

import org.bukkit.command.ConsoleCommandSender;
import ru.stonlex.bukkit.command.StonlexCommand;

public class ConsoleCommand extends StonlexCommand<ConsoleCommandSender> {

    @Override
    public void execute(ConsoleCommandSender console, String[] args) {
        // command logic...
    }

}
