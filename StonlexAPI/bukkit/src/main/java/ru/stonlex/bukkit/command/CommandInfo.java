package ru.stonlex.bukkit.command;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;

@RequiredArgsConstructor
public class CommandInfo {

    public final Command command;

    public final String label;
    public final String[] args;
}
