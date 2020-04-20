package ru.stonlex.bungee.inventory;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.apache.commons.lang3.RandomStringUtils;
import ru.stonlex.bungee.inventory.button.BungeeMenuButton;
import ru.stonlex.bungee.inventory.button.applicable.ButtonApplicable;
import ru.stonlex.bungee.inventory.cache.BungeeInventoryCache;
import ru.stonlex.bungee.inventory.item.BungeeItem;
import ru.stonlex.bungee.protocol.packet.impl.InventoryCreatePacket;
import ru.stonlex.bungee.protocol.packet.impl.InventoryOpenPacket;
import ru.stonlex.bungee.protocol.reference.ProtocolReference;

public abstract class BungeeStonlexMenu {

    @Getter
    private final String inventoryName;

    @Getter
    private final String inventoryTitle;

    @Getter
    private final int inventoryRows;

    @Getter
    private final TIntObjectMap<BungeeMenuButton> buttonMap = new TIntObjectHashMap<>();


    /**
     * Инициализация инвентаря
     *
     * @param inventoryTitle - название инвентаря
     * @param inventoryRows - количество линий в разметке инвентаря
     */
    public BungeeStonlexMenu(String inventoryTitle, int inventoryRows) {
        this.inventoryName = RandomStringUtils.randomAlphabetic(64);

        this.inventoryTitle = inventoryTitle;
        this.inventoryRows = inventoryRows;

        BungeeInventoryCache.getInstance().getInventoryCache().put(inventoryName.toLowerCase(), this);
    }

    /**
     * Передающийся метод, вызывается при открытии инвентаря
     */
    public void onOpen() { }

    /**
     * Передающийся метод, вызывается при закрытии инвентаря
     */
    public void onClose() { }

    /**
     * Создать и нарисовать инвентарь
     *
     * @param player - игрок, для которого адаптировать инвентарь
     */
    public abstract void drawInventory(ProxiedPlayer player);

    /**
     * Создать и инициализировать инвентарь
     * на Bukkit сервере при помощи отправки пакета
     */
    private void createInventory() {
        InventoryCreatePacket inventoryCreatePacket = new InventoryCreatePacket(
                inventoryName, inventoryTitle, inventoryRows, buttonMap
        );

        ProtocolReference.PACKET_MANAGER.sendPacket(inventoryCreatePacket);
    }

    /**
     * Открыть инвентарь игроку
     *
     * @param proxiedPlayer - игрок
     */
    public void openInventory(ProxiedPlayer proxiedPlayer) {
        drawInventory(proxiedPlayer);
        createInventory();

        InventoryOpenPacket inventoryOpenPacket = new InventoryOpenPacket(
                inventoryName, proxiedPlayer.getName()
        );

        ProtocolReference.PACKET_MANAGER.sendPacket(inventoryOpenPacket);
    }

    /**
     * Создать и занести в инветарь
     *
     * @param itemSlot - слот
     * @param bungeeItem - предмет
     * @param buttonApplicable - кликабельность
     */
    public void setItem(int itemSlot, BungeeItem bungeeItem, ButtonApplicable buttonApplicable) {
        BungeeMenuButton menuButton = new BungeeMenuButton(bungeeItem, buttonApplicable);

        buttonMap.put(itemSlot, menuButton);
    }

    /**
     * Создать и занести в инветарь
     *
     * @param itemSlot - слот
     * @param bungeeItem - предмет
     */
    public void setItem(int itemSlot, BungeeItem bungeeItem) {
        setItem(itemSlot, bungeeItem, null);
    }

}
