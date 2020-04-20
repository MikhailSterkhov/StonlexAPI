package ru.stonlex.bungee.protocol.packet.manager;

import com.google.common.collect.HashBasedTable;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import ru.stonlex.bungee.protocol.packet.MessagingPacket;
import ru.stonlex.bungee.protocol.reference.ProtocolReference;

import java.util.HashMap;
import java.util.Map;

public final class PacketManager {

    @Getter
    private final Map<String, Class<? extends MessagingPacket>> packetMap = new HashMap<>();


    /**
     * Зарегистрировать пакет в протоколе
     *
     * @param messagingPacket - класс пакета
     */
    public void registerPacket(Class<? extends MessagingPacket> messagingPacket) {
        System.out.println("[StonlexAPI Protocol] Packet " + messagingPacket.getSimpleName() + " has been registered");

        packetMap.put(messagingPacket.getSimpleName().toLowerCase(), messagingPacket);
    }

    /**
     * Получить и создать пакет
     *
     * @param packetName - имя пакета
     */
    public MessagingPacket getMessagingPacket(String packetName) {
        try {
            return packetMap.get(packetName.toLowerCase()).getConstructor().newInstance();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * Отправить пакет на Bukkit сервера
     *
     * @param messagingPacket - пакет
     */
    public void sendPacket(MessagingPacket messagingPacket) {
        String messageTag = ProtocolReference.MESSAGE_TAG;

        ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
        dataOutput.writeUTF(messagingPacket.getClass().getSimpleName());

        messagingPacket.writePacket(dataOutput);

        for (ServerInfo serverInfo : BungeeCord.getInstance().getServers().values()) {
            serverInfo.sendData(messageTag, dataOutput.toByteArray());
        }
    }

    /**
     * Отправить пакет на указанный сервер
     *
     * @param serverInfo - сервер
     * @param messagingPacket - пакет
     */
    public void sendPacket(ServerInfo serverInfo, MessagingPacket messagingPacket) {
        String messageTag = ProtocolReference.MESSAGE_TAG;

        ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
        dataOutput.writeUTF(messagingPacket.getClass().getSimpleName());

        messagingPacket.writePacket(dataOutput);

        serverInfo.sendData(messageTag, dataOutput.toByteArray());
    }

}
