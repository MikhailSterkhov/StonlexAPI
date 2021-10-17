package ru.stonlex.bukkit.utility.actionitem;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public abstract class AbstractActionItem {

    public static void addItem(@NonNull AbstractActionItem abstractActionItem, @NonNull Player... players) {

        for (Player player : players) {
            player.getInventory().addItem(abstractActionItem.getActionItem().getItemStack());
        }
    }

    private final ActionItem actionItem;

    public AbstractActionItem(@NonNull ItemStack itemStack) {
        this.actionItem = ActionItem.create(itemStack);

        handle(actionItem);
    }

    public abstract void handle(@NonNull ActionItem actionItem);

}
