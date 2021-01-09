package ru.stonlex.bukkit.protocollib.entity.impl;

import com.comphenix.protocol.utility.MinecraftProtocolVersion;
import com.comphenix.protocol.wrappers.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
    private MojangSkin mojangSkin;

    private final String name;

    private WrappedGameProfile wrappedGameProfile;


    public FakePlayer(String skin, Location location) {
        super(EntityType.PLAYER, location);

        this.name = String.format("§8NPC [%s]", NumberUtil.randomInt(0, 999_999));
        this.uuid = UUID.randomUUID();

        this.mojangSkin = MojangUtil.getMojangSkin(skin);

        updateSkinPart(PlayerSkinPart.TOTAL);
    }

    public synchronized void updateSkinPart(byte skinParts) {
        int currentVersion = MinecraftProtocolVersion.getCurrentVersion();

        int dataWatcherIndex1_8 = 10;
        int dataWatcherIndex1_12 = 13;
        int dataWatcherIndex1_15 = 16;

        int dataWatcherIndex = currentVersion >= 393 ? dataWatcherIndex1_15 :
                currentVersion <= 47 ? dataWatcherIndex1_8 : dataWatcherIndex1_12;

        broadcastDataWatcherObject(dataWatcherIndex, BYTE_SERIALIZER, skinParts);
    }

    public synchronized void updateSkinPart(@NonNull PlayerSkinPart... playerSkinParts) {
        byte skinParts = 0x00;

        for (PlayerSkinPart playerSkinPart : playerSkinParts) {
            skinParts += playerSkinPart.mask;
        }

        updateSkinPart(skinParts);
    }

    @Override
    protected synchronized void sendSpawnPackets(Player player) {
        String teamName = getTeamName();

        sendPlayerInfoPacket(EnumWrappers.PlayerInfoAction.ADD_PLAYER, player);
        sendTeamPacket(teamName, player, WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED);

        WrapperPlayServerNamedEntitySpawn spawned = new WrapperPlayServerNamedEntitySpawn();

        spawned.setEntityID(getEntityId());
        spawned.setPosition(getLocation().toVector());
        spawned.setPlayerUUID(uuid);

        spawned.setPitch(getLocation().getPitch());
        spawned.setYaw(getLocation().getYaw());

        spawned.setMetadata(getDataWatcher());
        spawned.sendPacket(player);

        sendEntityLookPacket(player);
        sendHeadRotationPacket(player);

        sendTeamPacket(teamName, player, WrapperPlayServerScoreboardTeam.Mode.PLAYERS_ADDED);

        new BukkitRunnable() {

            @Override
            public void run() {
                sendPlayerInfoPacket(EnumWrappers.PlayerInfoAction.REMOVE_PLAYER, player);
            }

        }.runTaskLater(StonlexBukkitApiPlugin.getInstance(), 30);
    }

    private synchronized void sendPlayerInfoPacket(EnumWrappers.PlayerInfoAction action, Player player) {
        WrapperPlayServerPlayerInfo playerInfoPacket = new WrapperPlayServerPlayerInfo();

        this.wrappedGameProfile = new WrappedGameProfile(uuid, name);

        if (mojangSkin != null) {
            wrappedGameProfile.getProperties().put("textures", new WrappedSignedProperty("textures", mojangSkin.getValue(), mojangSkin.getSignature()));
        }

        PlayerInfoData playerInfoData = new PlayerInfoData(wrappedGameProfile, 0,
                EnumWrappers.NativeGameMode.ADVENTURE, WrappedChatComponent.fromText(name));

        playerInfoPacket.setAction(action);
        playerInfoPacket.setData(Collections.singletonList(playerInfoData));

        playerInfoPacket.sendPacket(player);
    }

    private synchronized void sendTeamPacket(String teamName, Player player, int mode) {
        WrapperPlayServerScoreboardTeam scoreboardTeam = new WrapperPlayServerScoreboardTeam();

        scoreboardTeam.setName(teamName);
        scoreboardTeam.setMode(mode);

        scoreboardTeam.setCollisionRule("never");
        scoreboardTeam.setNameTagVisibility("never");

        if (mode == WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED || mode == WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED) {
            scoreboardTeam.setDisplayName(teamName);
            scoreboardTeam.setPrefix(getGlowingColor() == null ? "§8" : getGlowingColor().toString());
            scoreboardTeam.setPackOptionData(0);
            scoreboardTeam.setColor(0);
        } else {
            scoreboardTeam.setPlayers(Collections.singletonList(name));
        }

        scoreboardTeam.sendPacket(player);
    }

    @Override
    public synchronized void setGlowingColor(ChatColor glowingColor) {
        new BukkitRunnable() {

            @Override
            public void run() {
                FakePlayer.super.setGlowingColor(glowingColor);

                getViewerCollection().forEach(receiver ->
                        sendTeamPacket(getTeamName(), receiver, WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED));
            }

        }.runTaskLater(StonlexBukkitApiPlugin.getInstance(), 20);
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
