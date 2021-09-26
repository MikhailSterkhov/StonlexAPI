package ru.stonlex.bukkit.messaging;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

@Getter(AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
@RequiredArgsConstructor
public abstract class BukkitMessagingIncomingAdapter
        implements PluginMessageListener {

    @NonNull String tag;
    @NonNull String channel;

    protected abstract void onReceive(Player player, @NonNull String tag, @NonNull String channel, @NonNull ByteArrayDataInput input);

    @Override
    public void onPluginMessageReceived(String tag, Player player, byte[] bytes) {

        if (tag.equals(this.tag)) {

            ByteArrayDataInput input = ByteStreams.newDataInput(bytes);
            String channel = input.readUTF();

            if (channel.equals(this.channel)) {
                onReceive(player, tag, channel, input);
            }
        }
    }

}
