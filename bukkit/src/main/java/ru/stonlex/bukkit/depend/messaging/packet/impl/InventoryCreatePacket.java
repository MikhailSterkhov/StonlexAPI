package ru.stonlex.bukkit.depend.messaging.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.depend.messaging.inventory.CustomMenu;
import ru.stonlex.bukkit.depend.messaging.inventory.cache.InventoryCache;
import ru.stonlex.bukkit.depend.messaging.packet.MessagingPacket;
import ru.stonlex.bukkit.depend.messaging.reference.ProtocolReference;
import ru.stonlex.bukkit.utility.ItemUtil;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class InventoryCreatePacket implements MessagingPacket {

    private String inventoryName;
    private String inventoryTitle;

    private int inventoryRows;

    private TIntObjectMap<ItemStack> buttonMap = new TIntObjectHashMap<>();


    @Override
    public void writePacket(ByteArrayDataOutput dataOutput) {
        // ноуп
    }

    @Override
    public void readPacket(ByteArrayDataInput dataInput) {
        this.inventoryName = dataInput.readUTF();
        this.inventoryTitle = dataInput.readUTF();

        this.inventoryRows = dataInput.readInt();
        int buttonCount = dataInput.readInt();

        for (int i = 0 ; i < buttonCount ; i ++) {
            int itemSlot = dataInput.readInt();

            int itemId = dataInput.readInt();
            int itemDurability = dataInput.readInt();
            int itemAmount = dataInput.readInt();

            Material itemMaterial = Material.getMaterial(itemId);

            String playerSkull = null;
            String displayName = null;

            List<String> displayLore = null;

            if (itemMaterial == Material.SKULL_ITEM) {
                playerSkull = dataInput.readUTF();
            }

            if (dataInput.readBoolean()) {
                displayName = dataInput.readUTF();
            }

            if (dataInput.readBoolean()) {
                displayLore = new ArrayList<>();

                int loreSize = dataInput.readInt();
                for (int a = 0 ; a < loreSize ; a++) {
                    displayLore.add(dataInput.readUTF());
                }
            }

            buttonMap.put(itemSlot, ItemUtil.newBuilder(itemMaterial)
                    .setDurability(itemDurability)
                    .setAmount(itemAmount)
                    .setName(displayName)
                    .setLore(displayLore)
                    .setPlayerSkull(playerSkull != null && playerSkull.length() > 20 ? null : playerSkull)
                    .setTextureValue(playerSkull != null && playerSkull.length() > 20 ? playerSkull : null)
                    .build());
        }
    }

    @Override
    public void handle() {
        CustomMenu customMenu = new CustomMenu(inventoryTitle, inventoryRows, (player, menu) -> buttonMap.forEachEntry((itemSlot, menuButton) -> {
            InventoryClickPacket inventoryClickPacket = new InventoryClickPacket(
                    inventoryName, player.getName(), itemSlot
            );

            menu.setClickItem(itemSlot, menuButton,
                    (player1, event) -> ProtocolReference.PACKET_MANAGER.sendPacket(inventoryClickPacket));

            return true;
        }));

        InventoryCache.getInstance().getInventoryCache().cleanUp();
        InventoryCache.getInstance().getInventoryCache().put(inventoryName.toLowerCase(), customMenu);
    }

}
