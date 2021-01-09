package ru.stonlex.bukkit.protocollib.entity.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ru.stonlex.bukkit.protocollib.entity.FakeBaseEntity;
import ru.stonlex.bukkit.protocollib.entity.FakeEntityRegistry;
import ru.stonlex.bukkit.utility.PlayerCooldownUtil;

public final class FakeEntityClickListener extends PacketAdapter {

    public FakeEntityClickListener(Plugin plugin) {
        super(plugin, PacketType.Play.Client.USE_ENTITY);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        Player player = event.getPlayer();

        if (PlayerCooldownUtil.hasCooldown("fake-entity_click", player)) {
            return;
        }

        int entityId = event.getPacket().getIntegers().read(0);
        FakeBaseEntity fakeBaseEntity = FakeEntityRegistry.INSTANCE.getEntityById(entityId);

        if (fakeBaseEntity == null || fakeBaseEntity.getClickAction() == null) {
            return;
        }

        fakeBaseEntity.getClickAction().accept(player);
        PlayerCooldownUtil.putCooldown("fake-entity_click", player, 200);
    }
}
