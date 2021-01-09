package ru.stonlex.example.game;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import ru.stonlex.bukkit.gaming.item.GamingItem;
import ru.stonlex.bukkit.gaming.item.GamingItemCategory;
import ru.stonlex.bukkit.gaming.player.GamingPlayer;

public class ExampleGameItem extends GamingItem {

    public static final GamingItemCategory EXAMPLE_ITEM_CATEGORY
            = GamingItemCategory.registerItemCategory(1, new GamingItemCategory("Example", new MaterialData(Material.WOOD, (byte) 1)));


    public ExampleGameItem(double itemCost, @NonNull String itemName, @NonNull MaterialData materialData) {
        super(itemCost, itemName, materialData, EXAMPLE_ITEM_CATEGORY);
    }

    @Override
    protected void onAccept(@NonNull GamingPlayer gamingPlayer) {
        gamingPlayer.toBukkit().sendMessage("§aВы воспользователись " + getItemName());
    }

    @Override
    protected void onCancel(@NonNull GamingPlayer gamingPlayer) {
        gamingPlayer.toBukkit().sendMessage("§cИгровой предмет " + getItemName() + " был отклонен");
    }

}
