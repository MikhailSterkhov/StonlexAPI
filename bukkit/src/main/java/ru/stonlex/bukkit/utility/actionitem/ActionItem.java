package ru.stonlex.bukkit.utility.actionitem;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public final class ActionItem {

    public static final Map<ItemStack, ActionItem> ACTION_ITEM_MAP
            = new HashMap<>();

    public static boolean hasActionItem(@NonNull ItemStack itemStack) {
        return ACTION_ITEM_MAP.containsKey(itemStack);
    }

    public static ActionItem fromItem(@NonNull ItemStack itemStack) {
        if (!ActionItem.hasActionItem(itemStack))
            return null;

        return ACTION_ITEM_MAP.get(itemStack);
    }

    public static ActionItem create(@NonNull ItemStack itemStack, Player... playersForGive) {
        for (Player player : playersForGive) {
            player.getInventory().addItem(itemStack);
        }

        ActionItem actionItem = ActionItem.hasActionItem(itemStack) ? ActionItem.fromItem(itemStack) : new ActionItem(itemStack);

        if (!ActionItem.hasActionItem(itemStack)) {
            ACTION_ITEM_MAP.put(itemStack, actionItem);
        }

        return actionItem;
    }


    private final ItemStack itemStack;


    private ActionItemHandler<PlayerDropItemEvent> dropHandler;

    private ActionItemHandler<PlayerPickupItemEvent> pickupHandler;

    private ActionItemHandler<EntityDamageByEntityEvent> attackHandler;

    private ActionItemHandler<PlayerInteractEvent> interactHandler;

    private ActionItemHandler<BlockPlaceEvent> placeHandler;

    private ActionItemHandler<BlockBreakEvent> breakHandler;

    private ActionItemHandler<EntityShootBowEvent> shootBowHandler;

    private ActionItemHandler<PlayerChangedWorldEvent> worldChangedHandler;


    public ActionItem setDropHandler(ActionItemHandler<PlayerDropItemEvent> dropHandler) {
        this.dropHandler = dropHandler;
        return this;
    }

    public ActionItem setPickupHandler(ActionItemHandler<PlayerPickupItemEvent> pickupHandler) {
        this.pickupHandler = pickupHandler;
        return this;
    }

    public ActionItem setAttackHandler(ActionItemHandler<EntityDamageByEntityEvent> attackHandler) {
        this.attackHandler = attackHandler;
        return this;
    }

    public ActionItem setInteractHandler(ActionItemHandler<PlayerInteractEvent> interactHandler) {
        this.interactHandler = interactHandler;
        return this;
    }

    public ActionItem setPlaceHandler(ActionItemHandler<BlockPlaceEvent> placeHandler) {
        this.placeHandler = placeHandler;
        return this;
    }

    public ActionItem setBreakHandler(ActionItemHandler<BlockBreakEvent> breakHandler) {
        this.breakHandler = breakHandler;
        return this;
    }

    public ActionItem setShootBowHandler(ActionItemHandler<EntityShootBowEvent> shootBowHandler) {
        this.shootBowHandler = shootBowHandler;
        return this;
    }

    public ActionItem setWorldChangedHandler(ActionItemHandler<PlayerChangedWorldEvent> worldChangedHandler) {
        this.worldChangedHandler = worldChangedHandler;
        return this;
    }
}
