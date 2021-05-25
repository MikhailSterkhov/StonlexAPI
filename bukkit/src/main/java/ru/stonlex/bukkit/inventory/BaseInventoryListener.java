package ru.stonlex.bukkit.inventory;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import ru.stonlex.bukkit.StonlexBukkitApi;
import ru.stonlex.bukkit.inventory.handler.impl.BaseInventoryClickHandler;
import ru.stonlex.bukkit.inventory.handler.impl.BaseInventoryDisplayableHandler;
import ru.stonlex.bukkit.inventory.item.BaseInventoryClickItem;
import ru.stonlex.bukkit.inventory.item.BaseInventorySelectItem;
import ru.stonlex.bukkit.utility.ItemUtil;
import ru.stonlex.global.utility.WeakObjectCache;

public class BaseInventoryListener implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent inventoryOpenEvent) {
        Player player = (Player) inventoryOpenEvent.getPlayer();
        BaseInventory baseInventory = StonlexBukkitApi.INVENTORY_MANAGER.getPlayerInventory(player);

        if (baseInventory == null) {
            return;
        }

        WeakObjectCache weakObjectCache = WeakObjectCache.create();

        weakObjectCache.addObject("player", player);
        weakObjectCache.addObject("isOpen", true);
        weakObjectCache.addObject("event", inventoryOpenEvent);

        baseInventory.getInventoryInfo().handleHandlers(BaseInventoryDisplayableHandler.class, weakObjectCache);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent inventoryCloseEvent) {
        Player player = (Player) inventoryCloseEvent.getPlayer();
        BaseInventory baseInventory = StonlexBukkitApi.INVENTORY_MANAGER.getPlayerInventory(player);

        if (baseInventory == null) {
            return;
        }

        StonlexBukkitApi.INVENTORY_MANAGER.removeInventory(player, baseInventory);
        StonlexBukkitApi.INVENTORY_MANAGER.removeInventoryUpdateTask(baseInventory);

        WeakObjectCache weakObjectCache = WeakObjectCache.create();

        weakObjectCache.addObject("player", player);
        weakObjectCache.addObject("isOpen", false);
        weakObjectCache.addObject("event", inventoryCloseEvent);

        baseInventory.getInventoryInfo().handleHandlers(BaseInventoryDisplayableHandler.class, weakObjectCache);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        BaseInventory baseInventory = StonlexBukkitApi.INVENTORY_MANAGER.getPlayerInventory(player);

        if (baseInventory == null) {
            return;
        }

        int itemSlot = (inventoryClickEvent.getSlot() - 1);
        BaseInventoryItem baseInventoryItem = baseInventory.getInventoryInfo().getItem(itemSlot);

        if (inventoryClickEvent.getClickedInventory() instanceof PlayerInventory && baseInventory.getInventorySettings().isUseOnlyCacheItems()) {
            return;
        }

        if (baseInventoryItem == null) {
            inventoryClickEvent.setCancelled(!baseInventory.getInventorySettings().isUseOnlyCacheItems());

        } else {

            inventoryClickEvent.setCancelled(true);
        }

        if (baseInventoryItem instanceof BaseInventoryClickItem) {
            ((BaseInventoryClickItem) baseInventoryItem).getInventoryClickHandler().onClick(baseInventory, inventoryClickEvent);
        }

        if (baseInventoryItem instanceof BaseInventorySelectItem) {
            BaseInventorySelectItem inventorySelectItem = ((BaseInventorySelectItem) baseInventoryItem);

            inventorySelectItem.setSelected(!inventorySelectItem.isSelected());
            inventorySelectItem.getInventoryClickHandler().onClick(baseInventory, inventoryClickEvent);

            if (inventorySelectItem.isEnchanting()) {
                ItemStack itemStack = ItemUtil.newBuilder(inventoryClickEvent.getCurrentItem())
                        .setGlowing(inventorySelectItem.isEnchanting())
                        .build();

                inventoryClickEvent.setCurrentItem(itemStack);
            }
        }

        WeakObjectCache weakObjectCache = WeakObjectCache.create();
        weakObjectCache.addObject("slot", itemSlot);
        weakObjectCache.addObject("player", player);
        weakObjectCache.addObject("event", inventoryClickEvent);

        baseInventory.getInventoryInfo().handleHandlers(BaseInventoryClickHandler.class, weakObjectCache);
    }

}
