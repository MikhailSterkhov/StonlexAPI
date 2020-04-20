package ru.stonlex.bungee.protocol.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import ru.stonlex.bungee.protocol.packet.MessagingPacket;
import ru.stonlex.bungee.protocol.reference.ProtocolReference;

public class ProtocolListener implements Listener {

    @EventHandler
    public void onMessagingReceived(PluginMessageEvent event) {
        ByteArrayDataInput dataInput = ByteStreams.newDataInput(event.getData());

        if (!event.getTag().equals(ProtocolReference.MESSAGE_TAG)) {
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
