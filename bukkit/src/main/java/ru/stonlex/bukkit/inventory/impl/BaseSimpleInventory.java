package ru.stonlex.bukkit.inventory.impl;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.inventory.BaseInventory;
import ru.stonlex.bukkit.inventory.button.BaseInventoryButton;
import ru.stonlex.bukkit.inventory.addon.BaseInventoryUpdater;
import ru.stonlex.bukkit.inventory.button.action.impl.ClickableButtonAction;
import ru.stonlex.bukkit.inventory.button.action.impl.DraggableButtonAction;
import ru.stonlex.bukkit.inventory.button.impl.ActionInventoryButton;
import ru.stonlex.bukkit.inventory.button.impl.DraggableInventoryButton;
import ru.stonlex.bukkit.inventory.button.impl.SimpleInventoryButton;
import ru.stonlex.bukkit.inventory.manager.BukkitInventoryManager;

@Getter
public abstract class BaseSimpleInventory implements BaseInventory {

    protected String inventoryTitle;

    protected int inventoryRows;
    protected int inventorySize;

    protected final TIntObjectMap<BaseInventoryButton> buttons = new TIntObjectHashMap<>();


    protected BaseInventoryUpdater inventoryUpdater;

    protected Inventory bukkitInventory;


    /**
     * Инициализировать данные инвентаря
     *
     * @param inventoryTitle - название инвентаря
     * @param inventoryRows  - количество линий в разметке инвентаря
     */
    public BaseSimpleInventory(String inventoryTitle, int inventoryRows) {
        this.inventoryTitle = inventoryTitle;
        this.inventoryRows = inventoryRows;

        this.inventorySize = inventoryRows * 9;
    }

    @Override
    public void create(Player player, boolean inventoryInitialize) {
        if (inventoryInitialize) {
            this.bukkitInventory = Bukkit.createInventory(player, inventorySize, inventoryTitle);
        }

        if (player == null) {
            return;
        }

        clearInventory(player);
        drawInventory(player);

        buttons.forEachEntry((buttonSlot, inventoryButton) -> {

            bukkitInventory.setItem(buttonSlot - 1, inventoryButton.getItemStack());
            return true;
        });
    }

    @Override
    public void openInventory(@NonNull Player player) {
        player.closeInventory();

        create(player, true);

        BukkitInventoryManager.INSTANCE.addOpenInventoryToPlayer(player, this);
        player.openInventory(getBukkitInventory());
    }

    @Override
    public void updateInventory(@NonNull Player player) {
        create(player, false);

        //player.openInventory(getBukkitInventory());
    }

    @Override
    public void clearInventory(@NonNull Player player) {
        getBukkitInventory().clear();

        getButtons().clear();
    }

    @Override
    public void setInventoryTitle(@NonNull String inventoryTitle) {
        Validate.isTrue(inventoryTitle.length() < 32, "inventory title length cannot be > 32");

        this.inventoryTitle = inventoryTitle;

        create(null, true);
    }

    @Override
    public void setInventoryRows(int inventoryRows) {
        Validate.isTrue(inventoryRows <= 6, "inventory rows length cannot be > 6");

        this.inventoryRows = inventoryRows;
        this.inventorySize = inventoryRows * 9;

        create(null, true);
    }

    @Override
    public void setInventorySize(int inventorySize) {
        Validate.isTrue(inventoryRows % 9 == 0, "Inventory must have a size that is a multiple of 9!");

        this.inventorySize = inventorySize;
        this.inventoryRows = inventorySize / 9;

        create(null, true);
    }

    @Override
    public void setItem(int buttonSlot, @NonNull BaseInventoryButton inventoryButton) {
        buttons.put(buttonSlot, inventoryButton);
    }

    @Override
    public void setOriginalItem(int buttonSlot,
                             ItemStack itemStack) {

        BaseInventoryButton inventoryButton = new SimpleInventoryButton(itemStack);

        setItem(buttonSlot, inventoryButton);
    }

    @Override
    public void setClickItem(int buttonSlot,
                        ItemStack itemStack,
                        ClickableButtonAction buttonAction) {

        BaseInventoryButton inventoryButton = new ActionInventoryButton(
                itemStack, buttonAction
        );

        setItem(buttonSlot, inventoryButton);
    }

    @Override
    public void setDragItem(int buttonSlot,
                            ItemStack itemStack,
                            DraggableButtonAction buttonAction) {

        BaseInventoryButton inventoryButton = new DraggableInventoryButton(
                itemStack, buttonAction
        );

        setItem(buttonSlot, inventoryButton);
    }

    @Override
    public void setInventoryUpdater(long updateTicks, @NonNull BaseInventoryUpdater inventoryUpdater) {
        //check old updater
        if (inventoryUpdater != null) {
            inventoryUpdater.cancelUpdater();
        }

        this.inventoryUpdater = inventoryUpdater;

        getInventoryUpdater().setEnable(true);
        getInventoryUpdater().startUpdater(updateTicks);
    }


    @Override public void onOpen(Player player) { }
    @Override public void onClose(Player player) { }

}
