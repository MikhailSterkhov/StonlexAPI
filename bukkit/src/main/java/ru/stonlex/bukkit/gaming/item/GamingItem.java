package ru.stonlex.bukkit.gaming.item;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
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


    public abstract void accept(@NonNull GamingPlayer gamingPlayer);
    public abstract void cancel(@NonNull GamingPlayer gamingPlayer);


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
