package ru.stonlex.bukkit.protocol.messaging.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.stonlex.bukkit.protocol.messaging.packet.MessagingPacket;

@AllArgsConstructor
@NoArgsConstructor
public class InventoryClickPacket implements MessagingPacket {

    private String inventoryName;
    private String playerName;

    private int itemSlot;


    @Override
    public void writePacket(ByteArrayDataOutput dataOutput) {
        dataOutput.writeUTF(inventoryName);
        dataOutput.writeUTF(playerName);

        dataOutput.writeInt(itemSlot);
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
