package ru.stonlex.bungee.utility;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.stonlex.bungee.inventory.item.BungeeItem;
import ru.stonlex.bungee.inventory.item.material.BungeeMaterial;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class BungeeItemBuilder {

    private final BungeeMaterial material;

    private String playerSkull;
    private String displayName;

    private List<String> displayLore;

    private int durability, amount;


    /**
     * Инициализировать предмету настройку PlayerSkull
     *
     * @param playerSkull - текстура головы предмета
     */
    public BungeeItemBuilder setPlayerSkull(String playerSkull) {
        this.playerSkull = playerSkull;

        return this;
    }

    /**
     * Инициализировать предмету настройку DisplayName
     *
     * @param displayName - показываемое имя предмета
     */
    public BungeeItemBuilder setDisplayName(String displayName) {
        this.displayName = displayName;

        return this;
    }

    /**
     * Инициализировать предмету настройку DisplayLore
     *
     * @param displayLore - показываемые линии предмета
     */
    public BungeeItemBuilder setDisplayLore(List<String> displayLore) {
        this.displayLore = displayLore;

        return this;
    }

    /**
     * Инициализировать предмету настройку DisplayLore
     *
     * @param displayLore - показываемые линии предмета
     */
    public BungeeItemBuilder setDisplayLore(String... displayLore) {
        this.displayLore = Arrays.asList(displayLore);

        return this;
    }

    /**
     * Инициализировать предмету настройку Durability
     *
     * @param durability - данные предмета через знак двоеточия
     */
    public BungeeItemBuilder setDurability(int durability) {
        this.durability = durability;

        return this;
    }

    /**
     * Инициализировать предмету настройку Amount
     *
     * @param amount - количество предмета
     */
    public BungeeItemBuilder setAmount(int amount) {
        this.amount = amount;

        return this;
    }

    /**
     * Создать и инициализировать предмет по тем настройкам,
     * что были установлены для него
     */
    public BungeeItem build() {
        BungeeItem bungeeItem = new BungeeItem(material, durability, amount);

        bungeeItem.setDisplayName(displayName);
        bungeeItem.setDisplayLore(displayLore);

        bungeeItem.setPlayerSkull(playerSkull);

        return bungeeItem;
    }


    /**
     * Создать билдер для предмета по его типу материала
     *
     * @param material - тип материала предмета
     */
    public static BungeeItemBuilder newBuilder(BungeeMaterial material) {
        return new BungeeItemBuilder(material);
    }

}
