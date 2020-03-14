package ru.stonlex.bukkit.menu.button;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.menu.button.applicable.ButtonApplicable;

@RequiredArgsConstructor
@Getter
public class InventoryButton {

    private final ItemStack itemStack;
    private final ButtonApplicable buttonApplicable;
}
