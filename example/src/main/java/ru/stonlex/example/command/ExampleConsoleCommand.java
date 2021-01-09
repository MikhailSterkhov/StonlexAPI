package ru.stonlex.example.command;

import org.bukkit.command.ConsoleCommandSender;
import ru.stonlex.bukkit.command.BaseCommand;

public class ExampleConsoleCommand extends BaseCommand<ConsoleCommandSender> {

    public ExampleConsoleCommand(boolean constructorRegister) {
        super(constructorRegister, "console", "testcmd");
    }

    @Override
    protected void executeCommand(ConsoleCommandSender console, String[] args) {
        // command logic...
    }

}
