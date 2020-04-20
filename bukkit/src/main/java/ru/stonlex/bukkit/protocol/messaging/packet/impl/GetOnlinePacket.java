package ru.stonlex.bukkit.protocol.messaging.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.protocol.messaging.packet.MessagingPacket;

public class GetOnlinePacket implements MessagingPacket {

    @Override
    public void writePacket(ByteArrayDataOutput dataOutput) {
        //ноуп
    }

    @Override
    public void readPacket(ByteArrayDataInput dataInput) {
        int globalOnline = dataInput.readInt();
        int serversCount = dataInput.readInt();

        for (int i = 0 ; i < serversCount ; i++) {
            String serverName = dataInput.readUTF();
            int serverOnline = dataInput.readInt();

            BukkitAPI.getServerOnlineMap().put(serverName.toLowerCase(), serverOnline);
        }

        BukkitAPI.getServerOnlineMap().put("GLOBAL", globalOnline);
    }

    @Override
    public void handle() {
        //ноуп
    }
}
