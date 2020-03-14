package ru.stonlex.bukkit.tab;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.protocol.packet.scoreboard.WrapperPlayServerScoreboardTeam;

import java.util.Collections;

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
            scoreboardTeamPacket.setPlayers(Collections.singletonList(player.getName()));
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

}
