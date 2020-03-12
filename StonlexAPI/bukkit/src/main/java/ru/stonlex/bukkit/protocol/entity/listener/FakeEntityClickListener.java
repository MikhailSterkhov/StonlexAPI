package ru.stonlex.bukkit.protocol.entity.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ru.stonlex.bukkit.protocol.entity.StonlexFakeEntity;
import ru.stonlex.bukkit.utility.PlayerCooldownUtil;

public class FakeEntityClickListener extends PacketAdapter {

    public FakeEntityClickListener(Plugin plugin) {
        super(plugin, PacketType.Play.Client.USE_ENTITY);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        Player player = event.getPlayer();

        if (PlayerCooldownUtil.hasCooldown("fake-entity_click", player)) {
            return;
        }

        StonlexFakeEntity fakeEntity = StonlexFakeEntity.getEntityById(event.getPacket().getIntegers().read(0));

        if (fakeEntity == null || fakeEntity.getClickAction() == null) {
            return;
        }

        fakeEntity.getClickAction().accept(player);

        PlayerCooldownUtil.putCooldown("fake-entity_click", player, 1000);
    }
}
