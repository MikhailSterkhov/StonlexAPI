package ru.stonlex.bukkit.tag.manager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.protocollib.packet.scoreboard.WrapperPlayServerScoreboardTeam;
import ru.stonlex.bukkit.tag.PlayerTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TagManager {

    @Getter
    private final Map<String, PlayerTag> playerTagMap = new HashMap<>();

    @Getter // тут будем кешировать тимы
    private final Map<String, List<String>> teamCacheMap = new HashMap<>();


    public static final TagManager INSTANCE = new TagManager();


    /**
     * Получить PlayerTag игрока
     *
     * @param playerName - ник игрока
     */
    public PlayerTag getPlayerTag(String playerName) {
        return playerTagMap.get(playerName.toLowerCase());
    }

    /**
     * Получить PlayerTag игрока
     *
     * @param player - игрок
     */
    public PlayerTag getPlayerTag(Player player) {
        return getPlayerTag(player.getName());
    }


    /**
     * Установить тег в табе для игрока
     *
     * @param player - игрок, которому установить тег
     * @param receiver - получатель пакета
     * @param teamName - имя тимы для тега
     * @param prefix - префикс
     * @param suffix - суффикс
     */
    public void setTagToPlayer(Player player, Player receiver, String teamName, String prefix, String suffix) {
        PlayerTag playerTag = getPlayerTag(player);

        if (playerTag == null) {
            playerTag = new PlayerTag(player, prefix, suffix, teamName);

            playerTag.sendPacket(receiver, WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED);
            playerTag.sendPacket(receiver, WrapperPlayServerScoreboardTeam.Mode.PLAYERS_ADDED);

            playerTagMap.put(player.getName().toLowerCase(), playerTag);
            return;
        }

        playerTag.setTeamName(teamName);
        playerTag.setPrefix(prefix);
        playerTag.setSuffix(suffix);

        playerTag.sendPacket(receiver, WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED);
    }

    /**
     * Установить тег в табе для игрока
     *
     * @param player - игрок, которому установить тег
     * @param receiver - получатель пакета
     * @param prefix - префикс
     * @param suffix - суффикс
     */
    public void setTagToPlayer(Player player, Player receiver, String prefix, String suffix) {
        setTagToPlayer(player, receiver, player.getName(), prefix, suffix);
    }

    /**
     * Установить префикс в табе для игрока
     *
     * @param player игрок, которому установить префикс
     * @param receiver - получатель пакета
     * @param prefix - префикс
     */
    public void setPrefixToPlayer(Player player, Player receiver, String prefix) {
        setTagToPlayer(player, receiver, prefix, "");
    }

    /**
     * Установить суффикс в табе для игрока
     *
     * @param player игрок, которому установить суффикс
     * @param receiver - получатель пакета
     * @param suffix - суффикс
     */
    public void setSuffixToPlayer(Player player, Player receiver, String suffix) {
        setTagToPlayer(player, receiver, "", suffix);
    }

    /**
     * Установить тег в табе для игрока
     *
     * @param player - игрок, которому установить тег
     * @param teamName - имя тимы для тега
     * @param prefix - префикс
     * @param suffix - суффикс
     */
    public void setTag(Player player, String teamName, String prefix, String suffix) {
        Bukkit.getOnlinePlayers().forEach(
                receiver -> setTagToPlayer(player, receiver, teamName, prefix, suffix));
    }

    /**
     * Установить тег в табе для игрока
     *
     * @param player - игрок, которому установить тег
     * @param prefix - префикс
     * @param suffix - суффикс
     */
    public void setTag(Player player, String prefix, String suffix) {
        Bukkit.getOnlinePlayers().forEach(
                receiver -> setTagToPlayer(player, receiver, prefix, suffix));
    }

    /**
     * Установить префикс в табе для игрока
     *
     * @param player игрок, которому установить префикс
     * @param prefix - префикс
     */
    public void setPrefix(Player player, String prefix) {
        Bukkit.getOnlinePlayers().forEach(
                receiver -> setPrefixToPlayer(player, receiver, prefix));
    }

    /**
     * Установить суффикс в табе для игрока
     *
     * @param player игрок, которому установить суффикс
     * @param suffix - суффикс
     */
    public void setSuffix(Player player, String suffix) {
        Bukkit.getOnlinePlayers().forEach(
                receiver -> setSuffixToPlayer(player, receiver, suffix));
    }

}
