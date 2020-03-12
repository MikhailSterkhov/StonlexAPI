package ru.stonlex.bungee.messaging;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import ru.stonlex.bungee.BungeeAPI;

public class MessagingListener implements Listener {

    @EventHandler
    public void onMessagingReceived(PluginMessageEvent event) {
        ByteArrayDataInput dataInput = ByteStreams.newDataInput( event.getData() );

        if ( !event.getTag().equals(BungeeAPI.PLUGIN_MESSAGE_TAG) ) {
            return;
        }

        String channel = dataInput.readLine();

        switch (channel) {

        }
    }

}
