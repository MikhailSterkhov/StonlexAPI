package ru.stonlex.bukkit.protocollib.entity.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.stonlex.bukkit.StonlexBukkitApiPlugin;
import ru.stonlex.bukkit.protocollib.entity.FakeBaseEntity;
import ru.stonlex.bukkit.protocollib.entity.FakeEntityLiving;
import ru.stonlex.bukkit.protocollib.entity.FakeEntityRegistry;
import ru.stonlex.bukkit.protocollib.entity.FakeEntityScope;
import ru.stonlex.bukkit.utility.PlayerCooldownUtil;

import java.util.function.Consumer;

public class FakeEntityListener extends PacketAdapter
        implements Listener {


    private static final long ENTITY_INTERACT_COOLDOWN = 250;

    public FakeEntityListener() {
        super(StonlexBukkitApiPlugin.getPlugin(StonlexBukkitApiPlugin.class),
                PacketType.Play.Client.USE_ENTITY, PacketType.Play.Server.MAP_CHUNK, PacketType.Play.Server.UNLOAD_CHUNK);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketType type = event.getPacketType();
        Player player = event.getPlayer();

        StructureModifier<Integer> integers = event.getPacket().getIntegers();

        int x = integers.read(0);
        int z = integers.read(1);

        if (type == PacketType.Play.Server.UNLOAD_CHUNK) {
            onChunkUnload(player, x, z);
            return;
        }

        onChunkLoad(player, x, z);
    }

    private void onChunkLoad(Player player, int x, int z) {
        getPlugin().getServer().getScheduler().runTaskLater(getPlugin(), () -> {

            for (FakeBaseEntity entity : FakeEntityRegistry.INSTANCE.getReceivableEntities(player)) {
                Chunk chunk = entity.getLocation().getChunk();

                if (chunk.getX() == x && chunk.getZ() == z) {
                    if (entity.hasViewer(player)) {
                        continue;
                    }

                    entity.addViewers(player);
                }
            }

        }, 10L);
    }

    private void onChunkUnload(Player player, int x, int z) {
        for (FakeBaseEntity entity : FakeEntityRegistry.INSTANCE.getReceivableEntities(player)) {
            Chunk chunk = entity.getLocation().getChunk();

            if (chunk.getX() == x && chunk.getZ() == z) {
                if (!entity.hasViewer(player)) {
                    continue;
                }

                entity.removeViewers(player);
            }
        }
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        Player player = event.getPlayer();

        if (PlayerCooldownUtil.hasCooldown("fake_entity_interact", player)) {
            return;
        }

        FakeBaseEntity fakeEntity = FakeEntityRegistry.INSTANCE.getEntityById(event.getPacket().getIntegers().read(0));
        if (!(fakeEntity instanceof FakeEntityLiving)) {
            return;
        }

        EnumWrappers.EntityUseAction entityUseAction = event.getPacket().getEntityUseActions().read(0);
        switch (entityUseAction) {

            case ATTACK: {
                Consumer<Player> attackAction = fakeEntity.getAttackAction();

                if (attackAction != null) {
                    Bukkit.getScheduler().runTask(getPlugin(), () -> attackAction.accept(player));
                }

                break;
            }

            case INTERACT_AT: {
                Consumer<Player> clickAction = fakeEntity.getClickAction();

                if (clickAction != null) {
                    Bukkit.getScheduler().runTask(getPlugin(), () -> clickAction.accept(player));
                }

                break;
            }
        }

        PlayerCooldownUtil.putCooldown("fake_entity_interact", player, ENTITY_INTERACT_COOLDOWN);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        for (FakeBaseEntity fakeEntity : FakeEntityRegistry.INSTANCE.getReceivableEntities(player)) {
            if (fakeEntity == null) {
                continue;
            }

            fakeEntity.removeReceivers(player);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        getPlugin().getServer().getScheduler().runTaskLater(getPlugin(), () -> {

            for (FakeBaseEntity publicEntity : FakeEntityRegistry.INSTANCE.getEntitiesByScope(FakeEntityScope.PUBLIC)) {
                publicEntity.addReceivers(player);

                if (!publicEntity.getLocation().getWorld().equals(player.getWorld()))
                    publicEntity.removeViewers(player);
            }
        }, 20);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        for (FakeBaseEntity fakeBaseEntity : FakeEntityRegistry.INSTANCE.getReceivableEntities(player)) {
            if (!fakeBaseEntity.getLocation().getWorld().equals(player.getWorld())) {

                if (fakeBaseEntity.hasViewer(player)) {
                    fakeBaseEntity.removeViewers(player);
                }

            } else {

                if (!fakeBaseEntity.hasViewer(player)) {
                    fakeBaseEntity.addViewers(player);
                }
            }
        }

    }

}