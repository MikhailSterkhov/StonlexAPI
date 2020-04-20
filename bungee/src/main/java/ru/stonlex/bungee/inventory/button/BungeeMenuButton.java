package ru.stonlex.bungee.inventory.button;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.stonlex.bungee.inventory.button.applicable.ButtonApplicable;
import ru.stonlex.bungee.inventory.item.BungeeItem;

@Getter
@RequiredArgsConstructor
public class BungeeMenuButton {

    private final BungeeItem bungeeItem;
    private final ButtonApplicable buttonApplicable;
}
