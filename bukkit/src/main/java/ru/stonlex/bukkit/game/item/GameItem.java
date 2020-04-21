package ru.stonlex.bukkit.game.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.game.player.GamePlayer;
import ru.stonlex.bukkit.utility.ItemUtil;
import ru.stonlex.global.utility.NumberUtil;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public abstract class GameItem {

    @Setter
    private int typeId;

    private final int itemCost;

    private final Material baseMaterial;

    private final String itemName;
    private final String[] itemDescription;


    /**
     * Воспроизвести систему предмета игроку
     *
     * @param player - игрок
     */
    public abstract void applyItem(Player player);


    /**
     * Преобразовать в баккитовский ItemStack
     * по настройкам указанного игрока
     *
     * @param gamePlayer - игрок
     */
    public ItemStack toBukkitItem(GamePlayer gamePlayer) {
        List<String> loreList = new ArrayList<>();

        for (String descriptionLore : itemDescription) {
            loreList.add(ChatColor.GRAY + descriptionLore);
        }

        loreList.add("");
        loreList.add("§7Цена предмета: §6" + NumberUtil.spaced(itemCost) + " монет");
        loreList.add("");


        if (gamePlayer.isItemBought(this)) {
            loreList.add("§eНажмите, чтобы выбрать!");
        } else if (!gamePlayer.getVaultPlayer().hasMoney(itemCost)) {
            loreList.add("§cУ Вас недостаточно средств для покупки!");
        } else {
            loreList.add("§aНажмите, чтобы купить предмет!");
        }


        Material itemMaterial = Material.STAINED_GLASS_PANE;
        int itemDurability = 14;

        if (gamePlayer.getVaultPlayer().hasMoney(itemCost)) {
            itemDurability = 4;
        }

        if (gamePlayer.isItemBought(this)) {
            itemMaterial = baseMaterial;
            itemDurability = 0;
        }


        String itemDisplayName = ChatColor.RED + itemName;

        if (gamePlayer.getVaultPlayer().hasMoney(itemCost)) {
            itemDisplayName = ChatColor.YELLOW + itemName;
        }

        if (gamePlayer.isItemBought(this)) {
            itemDisplayName = ChatColor.GREEN + itemName;
        }

        return ItemUtil.newBuilder(itemMaterial)

                .setName(itemDisplayName)
                .setLore(loreList)

                .setDurability(itemDurability)
                .build();
    }
}
