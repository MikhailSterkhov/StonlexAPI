package ru.stonlex.bukkit.inventory.button.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.inventory.button.BaseInventoryButton;
import ru.stonlex.bukkit.inventory.button.action.impl.DraggableButtonAction;

@Getter
@RequiredArgsConstructor
public class DraggableInventoryButton implements BaseInventoryButton {

    private final ItemStack itemStack;

    private final DraggableButtonAction buttonAction;

}
