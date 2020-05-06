package ru.stonlex.bukkit.module.protocol.messaging.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import ru.stonlex.bukkit.module.protocol.messaging.packet.MessagingPacket;
import ru.stonlex.bukkit.module.protocol.messaging.reference.ProtocolReference;

public class ProtocolListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String tag, Player player, byte[] bytes) {
        ByteArrayDataInput dataInput = ByteStreams.newDataInput(bytes);

        if (!tag.equals(ProtocolReference.MESSAGE_TAG)) {
            return;
        }

        String packetName = dataInput.readUTF();
        MessagingPacket messagingPacket = ProtocolReference.PACKET_MANAGER.getMessagingPacket(packetName);

        if (messagingPacket == null) {
            throw new NullPointerException("packet cannot be equal null");
        }

        messagingPacket.readPacket(dataInput);
        messagingPacket.handle();
    }

}
