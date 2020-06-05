package ru.stonlex.bukkit.depend.messaging.packet.manager;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.depend.messaging.packet.MessagingPacket;
import ru.stonlex.bukkit.depend.messaging.reference.ProtocolReference;

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
     * Отправить пакет
     *
     * @param messagingPacket - пакет
     */
    public void sendPacket(MessagingPacket messagingPacket) {
        Plugin plugin = BukkitAPI.getInstance();
        String messageTag = ProtocolReference.MESSAGE_TAG;

        ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
        dataOutput.writeUTF(messagingPacket.getClass().getSimpleName());

        messagingPacket.writePacket(dataOutput);

        Bukkit.getServer().sendPluginMessage(plugin, messageTag, dataOutput.toByteArray());
    }

}
