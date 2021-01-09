package ru.stonlex.bukkit.gaming.item;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import ru.stonlex.bukkit.gaming.event.GameItemAcceptEvent;
import ru.stonlex.bukkit.gaming.event.GameItemCancelEvent;
import ru.stonlex.bukkit.gaming.player.GamingPlayer;
import ru.stonlex.bukkit.utility.ItemUtil;

@Getter
public abstract class GamingItem {

    protected int id;
    protected @NonNull double itemCost;

    protected @NonNull String itemName;

    protected @NonNull MaterialData materialData;
    protected @NonNull GamingItemCategory itemCategory;


    public GamingItem(double itemCost,

                      @NonNull String itemName,
                      @NonNull MaterialData materialData,
                      @NonNull GamingItemCategory itemCategory) {

        this.itemCost = itemCost;

        this.itemName = itemName;

        this.materialData = materialData;
        this.itemCategory = itemCategory;

        itemCategory.addItemToCategory(this);
    }


    protected abstract void onAccept(@NonNull GamingPlayer gamingPlayer);
    protected abstract void onCancel(@NonNull GamingPlayer gamingPlayer);

    public void accept(@NonNull GamingPlayer gamingPlayer) {
        GameItemAcceptEvent gameItemAcceptEvent = new GameItemAcceptEvent(gamingPlayer, this);
        Bukkit.getPluginManager().callEvent(gameItemAcceptEvent);

        if (!gameItemAcceptEvent.isCancelled()) {
            onAccept(gamingPlayer);
        }
    }

    public void cancel(@NonNull GamingPlayer gamingPlayer) {
        GameItemCancelEvent gameItemCancelEvent = new GameItemCancelEvent(gamingPlayer, this);
        Bukkit.getPluginManager().callEvent(gameItemCancelEvent);

        if (!gameItemCancelEvent.isCancelled()) {
            onCancel(gamingPlayer);
        }
    }


    public ItemStack toBukkitItem(@NonNull GamingPlayer gamingPlayer) {
        ChatColor displayColor = ChatColor.RED;

        if (gamingPlayer.isItemBought(this)) {
            displayColor = ChatColor.GREEN;
        }

        else if (gamingPlayer.isItemSelected(this)) {
            displayColor = ChatColor.YELLOW;
        }


        ItemUtil.ItemBuilder itemBuilder = ItemUtil.newBuilder(materialData);
        itemBuilder.setName(displayColor + itemName);

        // TODO
        itemBuilder.addLore("...");

        itemBuilder.setGlowing(gamingPlayer.isItemSelected(this));
        return itemBuilder.build();
    }

}
