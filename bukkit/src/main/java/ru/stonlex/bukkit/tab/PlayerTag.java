package ru.stonlex.bukkit.tab;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.module.protocol.packet.scoreboard.WrapperPlayServerScoreboardTeam;

import java.util.*;

@AllArgsConstructor
@Getter
public class PlayerTag {

    private final Player player;

    @Setter
    private String prefix, suffix, teamName;


    /**
     * Отправить пакет игроку
     *
     * @param receiver - получатель пакета
     * @param mode - тип пакета
     */
    public void sendPacket(Player receiver, int mode) {
        WrapperPlayServerScoreboardTeam scoreboardTeamPacket = new WrapperPlayServerScoreboardTeam();
        mode = checkTeam(mode);

        scoreboardTeamPacket.setName(teamName);
        scoreboardTeamPacket.setMode(mode);

        scoreboardTeamPacket.setCollisionRule("always");
        scoreboardTeamPacket.setNameTagVisibility("always");

        if (mode == WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED || mode == WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED) {
            scoreboardTeamPacket.setDisplayName(player.getName());
            scoreboardTeamPacket.setPrefix(prefix);
            scoreboardTeamPacket.setSuffix(suffix);
            scoreboardTeamPacket.setPackOptionData(0);
            scoreboardTeamPacket.setColor(0);
        } else {
            scoreboardTeamPacket.setPlayers( BukkitAPI.getTagManager().getTeamCacheMap().get(teamName) );
        }

        scoreboardTeamPacket.sendPacket(receiver);
    }

    /**
     * Отправить пакет всем игрокам онлайн
     *
     * @param mode - тип пакета
     */
    public void broadcastPacket(int mode) {
        Bukkit.getOnlinePlayers().forEach(receiver -> sendPacket(receiver, mode));
    }

    /**
     * Костыль для того, чтобы тима норм работала
     *
     * @param mode - тип пакета
     */
    private int checkTeam(int mode) {
        Map<String, List<String>> teamCacheMap = BukkitAPI.getTagManager().getTeamCacheMap();

        if (teamCacheMap.containsKey(teamName) && mode == 0) {
            mode = WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED;
        }

        List<String> teamPlayers = teamCacheMap.computeIfAbsent(teamName, f -> new ArrayList<>());
        teamPlayers.add(player.getName());

        teamCacheMap.put(teamName, teamPlayers);

        return mode;
    }

}
