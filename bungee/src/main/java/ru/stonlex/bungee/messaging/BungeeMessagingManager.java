package ru.stonlex.bungee.messaging;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.function.Consumer;

public final class BungeeMessagingManager {

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    public static class OutgoingRequest {

        @NonNull String tag;
        @NonNull byte[] bytes;

        public void sendData(@NonNull ServerInfo serverInfo) {
            serverInfo.sendData(tag, bytes);
        }

        public void sendData(@NonNull ProxiedPlayer proxiedPlayer) {
            proxiedPlayer.sendData(tag, bytes);
        }
    }


    public OutgoingRequest newOutputRequest(@NonNull String tag, @NonNull String channel,
                                               @NonNull Consumer<ByteArrayDataOutput> outputConsumer) {

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF(channel);

        outputConsumer.accept(output);

        return new OutgoingRequest(tag, output.toByteArray());
    }

    public void registerIncomingListener(@NonNull Plugin plugin, @NonNull BungeeMessagingIncomingAdapter incomingAdapter) {
        plugin.getProxy().getPluginManager().registerListener(plugin, incomingAdapter);
    }

}
