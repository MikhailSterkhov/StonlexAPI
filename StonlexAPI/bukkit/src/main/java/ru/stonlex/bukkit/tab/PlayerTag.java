package ru.stonlex.bukkit.tab;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.protocol.packet.scoreboard.WrapperPlayServerScoreboardTeam;

import java.util.Collections;

@RequiredArgsConstructor
public class PlayerTag {

    @Getter
    private final Player player;

    @Getter
    private final String prefix, suffix, teamName;


    private WrapperPlayServerScoreboardTeam scoreboardTeamPacket = new WrapperPlayServerScoreboardTeam();


    /**
     * Отправить пакет игроку
     *
     * @param receiver - получатель пакета
     * @param mode - тип пакета
     */
    public void sendPacket(Player receiver, int mode) {
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

}
