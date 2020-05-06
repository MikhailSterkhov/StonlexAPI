package ru.stonlex.bukkit.inventory.button.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.inventory.button.IBukkitInventoryButton;
import ru.stonlex.bukkit.inventory.button.action.impl.DraggableButtonAction;

@Getter
@RequiredArgsConstructor
public class DraggableStonlexInventoryButton implements IBukkitInventoryButton {

    private final ItemStack itemStack;

    private final DraggableButtonAction buttonAction;

}
