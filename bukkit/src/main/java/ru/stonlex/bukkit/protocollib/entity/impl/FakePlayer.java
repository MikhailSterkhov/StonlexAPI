package ru.stonlex.bukkit.protocollib.entity.impl;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.utility.MinecraftProtocolVersion;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.utility.MinecraftVersion;
import com.comphenix.protocol.wrappers.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.stonlex.bukkit.StonlexBukkitApiPlugin;
import ru.stonlex.bukkit.protocollib.entity.FakeBaseEntityLiving;
import ru.stonlex.bukkit.protocollib.packet.entity.WrapperPlayServerNamedEntitySpawn;
import ru.stonlex.bukkit.protocollib.packet.entity.WrapperPlayServerPlayerInfo;
import ru.stonlex.bukkit.protocollib.packet.scoreboard.WrapperPlayServerScoreboardTeam;
import ru.stonlex.bukkit.utility.mojang.MojangSkin;
import ru.stonlex.bukkit.utility.mojang.MojangUtil;
import ru.stonlex.global.utility.NumberUtil;

import java.util.Collections;
import java.util.UUID;

@Getter
public class FakePlayer extends FakeBaseEntityLiving {

    private final UUID uuid;
    private final String name;

    private MojangSkin mojangSkin;
    private WrappedGameProfile wrappedGameProfile;


    public FakePlayer(@NonNull MojangSkin skin, @NonNull Location location) {
        super(EntityType.PLAYER, location);

        this.name = String.format("§8NPC [%s]", NumberUtil.randomInt(0, 99999));
        this.uuid = UUID.randomUUID();

        this.mojangSkin = skin;

        updateSkinPart(PlayerSkinPart.TOTAL);
    }

    public FakePlayer(@NonNull String skin, @NonNull Location location) {
        this(MojangUtil.getMojangSkin(skin), location);
    }

    public FakePlayer(@NonNull Location location) {
        this("Steve", location);
    }


    public synchronized void updateSkinPart(byte skinParts) {
        int beeVersion = MinecraftProtocolVersion.getVersion(MinecraftVersion.BEE_UPDATE);
        int currentVersion = MinecraftProtocolVersion.getVersion(ProtocolLibrary.getProtocolManager().getMinecraftVersion());

        broadcastDataWatcherObject(currentVersion >= beeVersion ? 16 : 13, BYTE_SERIALIZER, skinParts);
    }

    public synchronized void updateSkinPart(@NonNull PlayerSkinPart... playerSkinParts) {
        byte skinParts = 0x00;

        for (PlayerSkinPart playerSkinPart : playerSkinParts) {
            skinParts += playerSkinPart.mask;
        }

        updateSkinPart(skinParts);
    }

    public synchronized void setSkin(@NonNull Player player, @NonNull MojangSkin mojangSkin) {
        this.mojangSkin = mojangSkin;
        sendPlayerInfoPacket(EnumWrappers.PlayerInfoAction.ADD_PLAYER, player);

        new BukkitRunnable() {

            @Override
            public void run() {
                sendPlayerInfoPacket(EnumWrappers.PlayerInfoAction.REMOVE_PLAYER, player);
            }

        }.runTaskLater(StonlexBukkitApiPlugin.getProvidingPlugin(StonlexBukkitApiPlugin.class), 30);
    }

    public synchronized void setSkin(@NonNull MojangSkin mojangSkin) {
        this.mojangSkin = mojangSkin;

        for (Player receiver : Bukkit.getOnlinePlayers())
            sendPlayerInfoPacket(EnumWrappers.PlayerInfoAction.ADD_PLAYER, receiver);

        new BukkitRunnable() {

            @Override
            public void run() {

                for (Player receiver : Bukkit.getOnlinePlayers())
                    sendPlayerInfoPacket(EnumWrappers.PlayerInfoAction.REMOVE_PLAYER, receiver);
            }

        }.runTaskLater(StonlexBukkitApiPlugin.getProvidingPlugin(StonlexBukkitApiPlugin.class), 30);
    }

    public synchronized void setSkin(@NonNull String skinName) {
        setSkin(MojangUtil.getMojangSkin(skinName));
    }

    @Override
    public synchronized void sendSpawnPackets(Player player) {
        String teamName = getTeamName();

        sendPlayerInfoPacket(EnumWrappers.PlayerInfoAction.ADD_PLAYER, player);
        sendTeamPacket(teamName, player, WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED);

        int beeVersion = MinecraftProtocolVersion.getVersion(MinecraftVersion.BEE_UPDATE);
        int version = ProtocolLibrary.getProtocolManager().getProtocolVersion(player);


        WrapperPlayServerNamedEntitySpawn spawned = new WrapperPlayServerNamedEntitySpawn();

        spawned.setEntityID(getEntityId());
        spawned.setPosition(getLocation().toVector());
        spawned.setPlayerUUID(uuid);

        spawned.setPitch(getLocation().getPitch());
        spawned.setYaw(getLocation().getYaw());

        if (version < beeVersion) {
            spawned.setMetadata(getDataWatcher());
        }

        spawned.sendPacket(player);

        sendEntityLookPacket(player);
        sendHeadRotationPacket(player);

        sendTeamPacket(teamName, player, WrapperPlayServerScoreboardTeam.Mode.PLAYERS_ADDED);

        new BukkitRunnable() {

            @Override
            public void run() {
                sendDataWatcherPacket(player);

                sendPlayerInfoPacket(EnumWrappers.PlayerInfoAction.REMOVE_PLAYER, player);
            }

        }.runTaskLater(StonlexBukkitApiPlugin.getProvidingPlugin(StonlexBukkitApiPlugin.class), 100);
    }

    @Override
    public synchronized void sendDestroyPackets(@NonNull Player player) {
        super.sendDestroyPackets(player);

        sendTeamPacket(getTeamName(), player, WrapperPlayServerScoreboardTeam.Mode.TEAM_REMOVED);
    }

    private synchronized void sendPlayerInfoPacket(EnumWrappers.PlayerInfoAction action, Player player) {
        WrapperPlayServerPlayerInfo playerInfoPacket = new WrapperPlayServerPlayerInfo();

        this.wrappedGameProfile = new WrappedGameProfile(uuid, name);

        if (mojangSkin != null) {
            wrappedGameProfile.getProperties().put("textures",
                    new WrappedSignedProperty("textures", mojangSkin.getValue(), mojangSkin.getSignature()));
        }

        PlayerInfoData playerInfoData = new PlayerInfoData(wrappedGameProfile, 0,
                EnumWrappers.NativeGameMode.ADVENTURE, WrappedChatComponent.fromText(name));

        playerInfoPacket.setAction(action);
        playerInfoPacket.setData(Collections.singletonList(playerInfoData));

        playerInfoPacket.sendPacket(player);
    }

    private synchronized void sendTeamPacket(String teamName, Player player, int mode) {
        int aquaticVersion = MinecraftProtocolVersion.getVersion(MinecraftVersion.AQUATIC_UPDATE);
        int version = ProtocolLibrary.getProtocolManager().getProtocolVersion(player);

        WrapperPlayServerScoreboardTeam scoreboardTeam = new WrapperPlayServerScoreboardTeam();
        scoreboardTeam.setName(teamName);

        if (version >= aquaticVersion) {
            scoreboardTeam.getHandle().getIntegers().write(0, mode);

            scoreboardTeam.getHandle().getStrings().write(2, "never");
            scoreboardTeam.getHandle().getStrings().write(1, "never");

            if (mode == WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED || mode == WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED) {
                scoreboardTeam.getHandle().getChatComponents().write(0, WrappedChatComponent.fromText(ChatColor.stripColor(teamName)));
                scoreboardTeam.getHandle().getChatComponents().write(1, WrappedChatComponent.fromText(glowingColor == null ? "§8" : glowingColor.toString()));

                scoreboardTeam.getHandle().getEnumModifier(ChatColor.class, MinecraftReflection.getMinecraftClass("EnumChatFormat")).write(0, glowingColor == null ? ChatColor.RESET : glowingColor);

                scoreboardTeam.getHandle().getIntegers().write(1, 0);

            } else {

                scoreboardTeam.setPlayers(Collections.singletonList(name));
            }

        } else {

            scoreboardTeam.setMode(mode);

            scoreboardTeam.setCollisionRule("never");
            scoreboardTeam.setNameTagVisibility("never");

            if (mode == WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED || mode == WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED) {
                scoreboardTeam.setDisplayName(teamName);

                scoreboardTeam.setPrefix(glowingColor == null ? "§8" : glowingColor.toString());
                scoreboardTeam.setColor(glowingColor == null ? 0 : glowingColor.ordinal());

                scoreboardTeam.setPackOptionData(0);

            } else {
                scoreboardTeam.setPlayers(Collections.singletonList(name));
            }
        }

        scoreboardTeam.sendPacket(player);
    }

    @Override
    public synchronized void setGlowingColor(ChatColor glowingColor) {
        FakePlayer.super.setGlowingColor(glowingColor);

        getViewerCollection().forEach(receiver ->
                sendTeamPacket(getTeamName(), receiver, WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED));
    }

    protected synchronized String getTeamName() {
        String teamName = (name + "_TEAM");

        if (teamName.length() > 16) {
            teamName = teamName.substring(0, 16);
        }

        return teamName;
    }

    @RequiredArgsConstructor
    public enum PlayerSkinPart {

        CAPE((byte) 0x01),

        JACKET((byte) 0x02),

        RIGHT_HAND((byte) 0x04),
        LEFT_HAND((byte) 0x08),

        RIGHT_LEG((byte) 0x10),
        LEFT_LEG((byte) 0x20),

        HAT((byte) 0x40),
        UNUSED((byte) 0x80),

        TOTAL(
                (byte) (CAPE.getMask() + JACKET.getMask() +

                        RIGHT_HAND.getMask() + LEFT_HAND.getMask() +
                        RIGHT_LEG.getMask() + LEFT_LEG.getMask() +

                        HAT.getMask()
                ));


        @Getter
        private final byte mask;
    }
}
