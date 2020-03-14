package ru.stonlex.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import ru.stonlex.bungee.messaging.MessagingListener;

public final class BungeeAPI extends Plugin {

    public static final String PLUGIN_MESSAGE_TAG = "StonlexChannel";


    @Override
    public void onEnable() {
        getProxy().registerChannel( PLUGIN_MESSAGE_TAG );
        getProxy().getPluginManager().registerListener(this, new MessagingListener());
    }

}
