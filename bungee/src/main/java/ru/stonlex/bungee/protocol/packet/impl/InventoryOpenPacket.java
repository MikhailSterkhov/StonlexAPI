package ru.stonlex.bungee.protocol.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.stonlex.bungee.protocol.packet.MessagingPacket;

@AllArgsConstructor
@NoArgsConstructor
public class InventoryOpenPacket implements MessagingPacket {

    private String inventoryName;
    private String playerName;

    @Override
    public void writePacket(ByteArrayDataOutput dataOutput) {
        dataOutput.writeUTF(inventoryName);
        dataOutput.writeUTF(playerName);
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
