package ru.stonlex.bukkit.protocollib.team;

import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.protocollib.packet.scoreboard.WrapperPlayServerScoreboardTeam;

import java.util.*;
import java.util.function.Consumer;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProtocolTeam {

    public static final int TEAM_CREATED = 0;
    public static final int TEAM_REMOVED = 1;
    public static final int TEAM_UPDATED = 2;
    public static final int PLAYERS_ADDED = 3;
    public static final int PLAYERS_REMOVED = 4;

    public static final ProtocolTeamListener TEAM_LISTENER = new ProtocolTeamListener();
    public static final Map<String, ProtocolTeam> TEAM_CACHE = new HashMap<>();


    /**
     * Создать (если не существует) и получить тиму
     * по ее наименованию
     *
     * @param name - имя тимы
     */
    public static ProtocolTeam get(@NonNull String name) {
        return get(name, null);
    }

    /**
     * Создать (если не существует) и получить тиму
     * по ее наименованию, инициализируя префикс
     *
     * @param name   - имя тимы
     * @param prefix - префикс
     */
    public static ProtocolTeam get(@NonNull String name, String prefix) {
        return get(name, prefix, null);
    }

    /**
     * Создать (если не существует) и получить тиму
     * по ее наименованию, инициализируя префикс и суффикс
     *
     * @param name   - имя тимы
     * @param prefix - префикс
     * @param suffix - суффикс
     */
    public static ProtocolTeam get(@NonNull String name, String prefix, String suffix) {
        return TEAM_CACHE.computeIfAbsent(name, f -> new ProtocolTeam(name, prefix, suffix, null));
    }


    /**
     * Найти тиму, в списке которой есть игрок,
     * как entry
     *
     * @param player - игрок
     */
    public static ProtocolTeam findEntry(@NonNull Player player) {
        return TEAM_CACHE.values().stream().filter(protocolTeam -> protocolTeam.hasPlayerEntry(player)).findFirst().orElse(null);
    }

    /**
     * Найти тиму, в списке которой есть игрок,
     * как receiver
     *
     * @param player - игрок
     */
    public static ProtocolTeam findReceiver(@NonNull Player player) {
        return TEAM_CACHE.values().stream().filter(protocolTeam -> protocolTeam.hasReceiver(player)).findFirst().orElse(null);
    }


    /**
     * Полностью удалить и очистить тиму
     * по ее названию (если такая существует)
     *
     * @param name- имя тимы
     */
    public static void remove(@NonNull String name) {
        ProtocolTeam protocolTeam = TEAM_CACHE.remove(name);

        if (protocolTeam != null) {
            protocolTeam.clear();
        }
    }

    /**
     * Полностью удалить и очистить все
     * когда-либо созданные и кешированные тимы
     */
    public static void removeAll() {
        for (ProtocolTeam protocolTeam : TEAM_CACHE.values()) {
            protocolTeam.clear();
        }

        TEAM_CACHE.clear();
    }


    private final String name;

    private String prefix;
    private String suffix;

    private final List<String> entryList = new ArrayList<>();
    private final Set<Player> receiversSet = new HashSet<>();


    @Setter
    @Getter
    private Consumer<WrapperPlayServerScoreboardTeam> packetHandler;

    private synchronized WrapperPlayServerScoreboardTeam getTeamPacket(int mode) {
        WrapperPlayServerScoreboardTeam scoreboardTeam = new WrapperPlayServerScoreboardTeam();

        scoreboardTeam.setName(name);
        scoreboardTeam.setMode(mode);

        scoreboardTeam.setCollisionRule("never");
        scoreboardTeam.setNameTagVisibility("always");

        if (mode == WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED || mode == WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED) {

            scoreboardTeam.setPrefix(prefix != null ? fixLength(16, prefix) : "");
            scoreboardTeam.setSuffix(suffix != null ? fixLength(16, suffix) : "");

            scoreboardTeam.setPackOptionData(0);
            scoreboardTeam.setColor(ChatColor.getByChar(prefix.replace(" ", "").charAt(1)).ordinal());

        } else {

            scoreboardTeam.setPlayers(entryList);
        }

        if (packetHandler != null)
            packetHandler.accept(scoreboardTeam);

        return scoreboardTeam;
    }


    public synchronized boolean isInitialized() {
        return (prefix != null || suffix != null) && !entryList.isEmpty();
    }

    public synchronized void sendPacket(int mode, @NonNull Player receiver) {
        getTeamPacket(mode).sendPacket(receiver);
    }

    public synchronized void broadcastPacket(int mode) {
        for (Player receiver : getReceiversSet())
            sendPacket(mode, receiver);
    }


    public synchronized void addReceiver(@NonNull Player player) {
        if (receiversSet.add(player)) {

            sendPacket(TEAM_CREATED, player);
            sendPacket(PLAYERS_ADDED, player);
        }
    }

    public synchronized void removeReceiver(@NonNull Player player) {
        if (receiversSet.remove(player)) {

            sendPacket(PLAYERS_REMOVED, player);
            sendPacket(TEAM_REMOVED, player);
        }
    }

    public synchronized boolean hasReceiver(@NonNull Player player) {
        return receiversSet.contains(player);
    }


    public synchronized void addPlayerEntry(@NonNull Player player) {
        entryList.add(player.getName());

        addReceiver(player);
        broadcastPacket(PLAYERS_ADDED);
    }

    public synchronized void removePlayerEntry(@NonNull Player player) {
        entryList.remove(player.getName());

        broadcastPacket(PLAYERS_REMOVED);

        sendPacket(TEAM_REMOVED, player);
        receiversSet.remove(player);
    }

    public synchronized boolean hasPlayerEntry(@NonNull Player player) {
        return entryList.contains(player.getName());
    }

    public synchronized boolean hasPlayerEntry(@NonNull String playerName) {
        return entryList.contains(playerName);
    }


    public synchronized void addAutoReceived() {
        broadcast();

        TEAM_LISTENER.addTeam(this);
    }

    public synchronized void removeAutoReceived() {
        TEAM_LISTENER.removeTeam(this);
    }

    public synchronized boolean hasAutoReceived() {
        return TEAM_LISTENER.hasTeam(this);
    }


    public synchronized void setPrefix(String prefix) {
        this.prefix = prefix.length() > 16 ? prefix.substring(0, 16) : prefix;

        broadcastPacket(TEAM_UPDATED);
    }

    public synchronized void setSuffix(String suffix) {
        this.suffix = suffix.length() > 16 ? suffix.substring(0, 16) : suffix;

        broadcastPacket(TEAM_UPDATED);
    }


    public synchronized void clear() {
        entryList.clear();

        broadcastPacket(PLAYERS_REMOVED);
        broadcastPacket(TEAM_REMOVED);
        receiversSet.clear();

        removeAutoReceived();
    }

    public synchronized void broadcast() {
        for (Player player : Bukkit.getOnlinePlayers())
            addReceiver(player);
    }

    private String fixLength(int length, String string) {
        return string.length() > length ? string.substring(0, length) : string;
    }

}
