package ru.stonlex.bukkit.holographic;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public interface ProtocolHolographic extends ProtocolHolographicSpawnable {

    @NonNull Location getLocation();

    @NonNull Set<Player> getViewers();

    @NonNull Set<Player> getReceivers();

    @NonNull List<ProtocolHolographicLine> getHolographicLines();

    ProtocolHolographicUpdater getHolographicUpdater();

    ProtocolHolographicLine getHolographicLine(int lineIndex);

    void setHolographicLine(int lineIndex, @NonNull ProtocolHolographicLine holographicLine);

    void setTextLine(int lineIndex, @NonNull String holographicLine);

    void setClickLine(int lineIndex, @NonNull String holographicLine, @NonNull Consumer<Player> clickAction);

    void setSkullLine(int lineIndex, @NonNull String headTexture, boolean small);

    void setDropLine(int lineIndex, @NonNull ItemStack itemStack);

    void setEmptyLine(int lineIndex);

    void addHolographicLine(ProtocolHolographicLine holographicLine);

    void addTextLine(@NonNull String holographicLine);

    void addClickLine(@NonNull String holographicLine, @NonNull Consumer<Player> clickAction);

    void addSkullLine(@NonNull String headTexture, boolean small);

    void addDropLine(@NonNull ItemStack itemStack);

    void addEmptyLine();

    void teleport(@NonNull Location location);

    void setFullClickAction(@NonNull Consumer<Player> clickAction);

    void setUpdater(long updateTicks, ProtocolHolographicUpdater holographicUpdater);
}
