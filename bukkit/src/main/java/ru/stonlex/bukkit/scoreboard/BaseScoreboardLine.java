package ru.stonlex.bukkit.scoreboard;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.utility.MinecraftProtocolVersion;
import com.comphenix.protocol.utility.MinecraftVersion;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.google.common.base.Splitter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.protocollib.packet.scoreboard.WrapperPlayServerScoreboardScore;
import ru.stonlex.bukkit.protocollib.packet.scoreboard.WrapperPlayServerScoreboardTeam;

import java.util.*;

@AllArgsConstructor
public class BaseScoreboardLine {

    @Getter
    private final int scoreIndex;

    @Getter
    private String scoreText;

    public static final int TEAM_CREATED    = 0;
    public static final int TEAM_REMOVED    = 1;
    public static final int TEAM_UPDATED    = 2;
    public static final int PLAYERS_ADDED   = 3;
    public static final int PLAYERS_REMOVED = 4;

    private static final ChatColor[] COLORS = ChatColor.values();
    private static final Splitter SPLITTER  = Splitter.fixedLength(16);


    public void create(@NonNull BaseScoreboard baseScoreboard, @NonNull Player player) {
        getTeamPacket(player, WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED).sendPacket(player);
        getScorePacket(baseScoreboard, EnumWrappers.ScoreboardAction.CHANGE).sendPacket(player);
    }

    public void remove(@NonNull BaseScoreboard baseScoreboard, @NonNull Player player) {
        getTeamPacket(player, WrapperPlayServerScoreboardTeam.Mode.TEAM_REMOVED).sendPacket(player);
        getScorePacket(baseScoreboard, EnumWrappers.ScoreboardAction.REMOVE).sendPacket(player);
    }

    public void modify(@NonNull BaseScoreboard baseScoreboard, @NonNull String scoreText, @NonNull Player player) {
        this.scoreText = scoreText;

        getTeamPacket(player, WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED).sendPacket(player);
        getScorePacket(baseScoreboard, EnumWrappers.ScoreboardAction.CHANGE).sendPacket(player);
    }


    protected WrapperPlayServerScoreboardScore getScorePacket(@NonNull BaseScoreboard baseScoreboard,
                                                              @NonNull EnumWrappers.ScoreboardAction scoreboardAction) {

        WrapperPlayServerScoreboardScore scorePacket = new WrapperPlayServerScoreboardScore();

        scorePacket.getHandle().getStrings().write(0, COLORS[scoreIndex].toString());
        scorePacket.getHandle().getStrings().write(1, baseScoreboard.getScoreboardName());

        scorePacket.getHandle().getScoreboardActions().write(0, scoreboardAction);

        scorePacket.getHandle().getIntegers().write(0, scoreIndex);
        return scorePacket;
    }

    protected WrapperPlayServerScoreboardTeam getTeamPacket(@NonNull Player player, int teamMode) {
        String teamEntry = COLORS[scoreIndex].toString();
        WrapperPlayServerScoreboardTeam teamPacket = new WrapperPlayServerScoreboardTeam();

        teamPacket.getHandle().getStrings().write(0, teamEntry);

        int aquaticVersion = MinecraftProtocolVersion.getVersion(MinecraftVersion.AQUATIC_UPDATE);
        int currentVersion = MinecraftProtocolVersion.getVersion(ProtocolLibrary.getProtocolManager().getMinecraftVersion());

        if (currentVersion >= aquaticVersion) {
            teamPacket.getHandle().getIntegers().write(0, teamMode);

        } else {

            teamPacket.getHandle().getIntegers().write(1, teamMode);
        }

        if (teamMode == TEAM_REMOVED) {
            return teamPacket;
        }

        int version = ProtocolLibrary.getProtocolManager().getProtocolVersion(player);

        teamPacket.getHandle().getSpecificModifier(Collection.class).write(0, Collections.singletonList(teamEntry));

        // Since 1.13 character limit for prefix/suffix was removed
        if (version >= aquaticVersion) {
            if (!scoreText.isEmpty() && scoreText.charAt(0) != ChatColor.COLOR_CHAR) {
                scoreText = ChatColor.RESET + scoreText;
            }

            if (currentVersion >= aquaticVersion) {
                teamPacket.getHandle().getChatComponents().write(1,
                        WrappedChatComponent.fromText(scoreText)); // prefix

                teamPacket.getHandle().getChatComponents().write(2,
                        WrappedChatComponent.fromText(ChatColor.RESET.toString())); // suffix
            } else {

                teamPacket.getHandle().getStrings().write(2, scoreText); // prefix
                teamPacket.getHandle().getStrings().write(3, ChatColor.RESET.toString()); // suffix
            }

            return teamPacket;
        }

        Iterator<String> iterator = SPLITTER.split(scoreText).iterator();
        String prefix = iterator.next();

        teamPacket.getHandle().getStrings().write(2, prefix);

        if (scoreText.length() > 16) {
            String prefixColor = ChatColor.getLastColors(prefix);
            String suffix = iterator.next();

            if (prefix.endsWith(String.valueOf(ChatColor.COLOR_CHAR))) {
                prefix = prefix.substring(0, prefix.length() - 1);

                teamPacket.getHandle().getStrings().write(2, prefix);

                prefixColor = ChatColor.getByChar(suffix.charAt(0)).toString();
                suffix = suffix.substring(1);
            }

            if (prefixColor == null) {
                prefixColor = "";
            }

            suffix = ((prefixColor.equals("") ? ChatColor.RESET : prefixColor) + suffix);

            if (suffix.length() > 16) {
                suffix = suffix.substring(0, 13) + "...";
            }

            teamPacket.getHandle().getStrings().write(3, suffix);
        }

        return teamPacket;
    }

}