package ru.stonlex.bungee.messaging;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

@Getter(AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
@RequiredArgsConstructor
public abstract class BungeeMessagingIncomingAdapter
        implements Listener {

    @NonNull String tag;
    @NonNull String channel;

    @EventHandler
    public void onReceive(PluginMessageEvent event) {

        if (event.getTag().equals(tag)) {

            ByteArrayDataInput input = ByteStreams.newDataInput(event.getData());
            String channel = input.readUTF();

            if (channel.equals(this.channel)) {
                onReceive(input);
            }
        }
    }

    /**
     * Основной процесс обработки сообщения
     * @param input  - обработчик байтов сообщения
     */
    protected abstract void onReceive(@NonNull ByteArrayDataInput input);

}
