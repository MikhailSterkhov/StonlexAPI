package ru.stonlex.bukkit.game.item.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.game.item.GameItem;
import ru.stonlex.bukkit.game.player.GamePlayer;
import ru.stonlex.bukkit.utility.ItemUtil;
import ru.stonlex.global.utility.NumberUtil;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class ItemType {

    private final int typeId;

    private final Material baseMaterial;

    private final String typeName;
    private final String[] typeDescription;

    private final List<GameItem> itemList = new ArrayList<>();


    public ItemStack toBukkitItem(GamePlayer gamePlayer) {
        List<String> loreList = new ArrayList<>();

        for (String descriptionLore : typeDescription) {
            loreList.add(ChatColor.GRAY + descriptionLore);
        }

        //todo: add item percents

        loreList.add("");
        loreList.add("§eНажмите, чтобы выбрать!");

        return ItemUtil.newBuilder(baseMaterial)

                .setName(ChatColor.GREEN + typeName)
                .setLore(loreList)

                //.setDurability(itemDurability)
                .build();
    }

}
