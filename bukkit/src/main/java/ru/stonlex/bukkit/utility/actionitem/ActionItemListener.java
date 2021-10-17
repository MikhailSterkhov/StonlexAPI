package ru.stonlex.bukkit.utility.actionitem;

import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

public final class ActionItemListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        handleItem(event.getPlayer(), event.getItemDrop().getItemStack(), (player, actionItem) -> {

            if (actionItem.getDropHandler() != null) {
                actionItem.getDropHandler().handleEvent(event);
            }
        });
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        handleItem(event.getPlayer(), event.getItem().getItemStack(), (player, actionItem) -> {

            if (actionItem.getPickupHandler() != null) {
                actionItem.getPickupHandler().handleEvent(event);
            }
        });
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        handleMainHand((Player) event.getDamager(), (player, actionItem) -> {

            if (actionItem.getAttackHandler() != null) {
                actionItem.getAttackHandler().handleEvent(event);
            }
        });
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.hasItem()) {
            return;
        }

        handleItem(event.getPlayer(), event.getItem(), (player, actionItem) -> {

            if (actionItem.getInteractHandler() != null) {
                actionItem.getInteractHandler().handleEvent(event);
            }
        });
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        handleMainHand(event.getPlayer(), (player, actionItem) -> {

            if (actionItem.getPlaceHandler() != null) {
                actionItem.getPlaceHandler().handleEvent(event);
            }
        });
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        handleMainHand(event.getPlayer(), (player, actionItem) -> {

            if (actionItem.getBreakHandler() != null) {
                actionItem.getBreakHandler().handleEvent(event);
            }
        });
    }

    @EventHandler
    public void onWorldChanged(PlayerChangedWorldEvent event) {
        handleMainHand(event.getPlayer(), (player, actionItem) -> {

            if (actionItem.getWorldChangedHandler() != null) {
                actionItem.getWorldChangedHandler().handleEvent(event);
            }
        });
    }

    @EventHandler
    public void onShootBow(EntityShootBowEvent event) {
        handleItem((Player) event.getEntity(), event.getBow(), (player, actionItem) -> {

            if (actionItem.getShootBowHandler() != null) {
                actionItem.getShootBowHandler().handleEvent(event);
            }
        });
    }

    @EventHandler
    public void onShootArrow(EntityShootBowEvent event) {
        handleItem((Player) event.getEntity(), event.getArrowItem(), (player, actionItem) -> {

            if (actionItem.getShootBowHandler() != null) {
                actionItem.getShootBowHandler().handleEvent(event);
            }
        });
    }

    private void handleItem(@NonNull Player player, @NonNull ItemStack itemStack,
                            @NonNull BiConsumer<Player, ActionItem> itemConsumer) {

        if (ActionItem.hasActionItem(itemStack)) {
            itemConsumer.accept(player, ActionItem.fromItem(itemStack));
        }
    }

    private void handleMainHand(@NonNull Player player, @NonNull BiConsumer<Player, ActionItem> itemConsumer) {
        ItemStack mainHandItem = player.getInventory().getItemInMainHand();

        if (mainHandItem == null) {
            return;
        }

        if (ActionItem.hasActionItem(mainHandItem)) {
            itemConsumer.accept(player, ActionItem.fromItem(mainHandItem));
        }
    }

}
