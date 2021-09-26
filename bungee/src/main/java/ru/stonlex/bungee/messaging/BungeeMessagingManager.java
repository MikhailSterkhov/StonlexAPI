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

        /**
         * Отправление сообщения на сторону сервера
         * @param serverInfo - сервер
         */
        public void sendData(@NonNull ServerInfo serverInfo) {
            serverInfo.sendData(tag, bytes);
        }

        /**
         * Отправление сообщения на сторону игрока
         * @param proxiedPlayer - игрок
         */
        public void sendData(@NonNull ProxiedPlayer proxiedPlayer) {
            proxiedPlayer.sendData(tag, bytes);
        }
    }


    /**
     * Создание нового Output сообщения
     * для отправления его на определенный
     * Bukkit сервер на обработку.
     *
     * @param tag            - тэг подключения
     * @param channel        - канал/название/основное действие
     * @param outputConsumer - обработчик данных на вход
     */
    public OutgoingRequest newOutputRequest(@NonNull String tag, @NonNull String channel,
                                            @NonNull Consumer<ByteArrayDataOutput> outputConsumer) {

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF(channel);

        outputConsumer.accept(output);

        return new OutgoingRequest(tag, output.toByteArray());
    }

    /**
     * Регистрация Input слушателя сообщений
     * с Bukkit серверов
     *
     * @param plugin          - плагин, на который регистрируем слушатель
     * @param incomingAdapter - адаптер слушателя
     */
    public void registerIncomingListener(@NonNull Plugin plugin, @NonNull BungeeMessagingIncomingAdapter incomingAdapter) {
        plugin.getProxy().getPluginManager().registerListener(plugin, incomingAdapter);
    }

}
