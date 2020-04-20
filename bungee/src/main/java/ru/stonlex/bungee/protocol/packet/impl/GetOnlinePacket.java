package ru.stonlex.bungee.protocol.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import ru.stonlex.bungee.protocol.packet.MessagingPacket;
import ru.stonlex.bungee.protocol.reference.ProtocolReference;

public class GetOnlinePacket implements MessagingPacket {

    @Override
    public void writePacket(ByteArrayDataOutput dataOutput) {
        dataOutput.writeInt(BungeeCord.getInstance().getOnlineCount());

        dataOutput.writeInt(BungeeCord.getInstance().getServers().size());

        for (ServerInfo serverInfo : BungeeCord.getInstance().getServers().values()) {
            dataOutput.writeUTF(serverInfo.getName());
            dataOutput.writeInt(serverInfo.getPlayers().size());
        }
    }

    @Override
    public void readPacket(ByteArrayDataInput dataInput) {
        // ноуп
    }

    @Override
    public void handle() {
        ProtocolReference.PACKET_MANAGER.sendPacket(this);
    }
}
