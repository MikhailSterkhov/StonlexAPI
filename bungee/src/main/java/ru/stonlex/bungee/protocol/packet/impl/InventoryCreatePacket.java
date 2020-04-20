package ru.stonlex.bungee.protocol.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import gnu.trove.map.TIntObjectMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.stonlex.bungee.inventory.button.BungeeMenuButton;
import ru.stonlex.bungee.inventory.item.BungeeItem;
import ru.stonlex.bungee.inventory.item.material.BungeeMaterial;
import ru.stonlex.bungee.protocol.packet.MessagingPacket;

@AllArgsConstructor
@NoArgsConstructor
public class InventoryCreatePacket implements MessagingPacket {

    private String inventoryName;
    private String inventoryTitle;

    private int inventoryRows;

    private TIntObjectMap<BungeeMenuButton> buttonMap;


    @Override
    public void writePacket(ByteArrayDataOutput dataOutput) {
        dataOutput.writeUTF(inventoryName);
        dataOutput.writeUTF(inventoryTitle);

        dataOutput.writeInt(inventoryRows);
        dataOutput.writeInt(buttonMap.size());

        buttonMap.forEachEntry((itemSlot, menuButton) -> {
            BungeeItem bungeeItem = menuButton.getBungeeItem();

            dataOutput.writeInt(itemSlot);

            dataOutput.writeInt(bungeeItem.getMaterial().getId());
            dataOutput.writeInt(bungeeItem.getDurability());
            dataOutput.writeInt(bungeeItem.getAmount());

            if (bungeeItem.getMaterial() == BungeeMaterial.SKULL_ITEM && bungeeItem.getDurability() == 3) {
                dataOutput.writeUTF(bungeeItem.getPlayerSkull());
            }

            //display name
            boolean hasName = bungeeItem.getDisplayName() != null;
            dataOutput.writeBoolean(hasName);

            if (hasName) {
                dataOutput.writeUTF(bungeeItem.getDisplayName());
            }

            //display lore
            boolean hasLore = bungeeItem.getDisplayLore() != null;
            dataOutput.writeBoolean(hasLore);

            if (hasLore) {
                dataOutput.writeInt(bungeeItem.getDisplayLore().size());

                for (String loreElement : bungeeItem.getDisplayLore()) {
                    dataOutput.writeUTF(loreElement);
                }
            }

            return true;
        });
    }

    @Override
    public void readPacket(ByteArrayDataInput dataInput) {
        // ноуп
    }

    @Override
    public void handle() {
        // ноуп
    }

}
