package ru.stonlex.bungee.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.plugin.Event;
import ru.stonlex.bungee.inventory.BungeeStonlexMenu;

@RequiredArgsConstructor
@Getter
public class InventoryClickEvent extends Event {

    private final BungeeStonlexMenu menu;

    private final int itemSlot;
}
