package ru.stonlex.bukkit.inventory.button.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.inventory.button.IBukkitInventoryButton;

@RequiredArgsConstructor
@Getter
public class OriginalStonlexInventoryButton implements IBukkitInventoryButton {

    private final ItemStack itemStack;

    @Override
    public IInventoryButtonAction getButtonAction() {
        return null;
    }
}
