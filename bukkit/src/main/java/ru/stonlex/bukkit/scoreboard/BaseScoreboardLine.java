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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class BaseScoreboardLine {

    @Getter
    private final int scoreIndex;

    @Getter
    private String scoreText;

    public static final int TEAM_CREATED    = (0);
    public static final int TEAM_REMOVED    = (1);
    public static final int TEAM_UPDATED    = (2);
    public static final int PLAYERS_ADDED   = (3);
    public static final int PLAYERS_REMOVED = (4);

    private static final ChatColor[] COLORS = ChatColor.values();
    private static final Splitter SPLITTER  = Splitter.fixedLength(16);


    public void create(@NonNull BaseScoreboard baseScoreboard, @NonNull Player player) {
        getTeamPacket(player, TEAM_CREATED).sendPacket(player);
        getScorePacket(baseScoreboard, EnumWrappers.ScoreboardAction.CHANGE).sendPacket(player);
    }

    public void remove(@NonNull BaseScoreboard baseScoreboard, @NonNull Player player) {
        getTeamPacket(player, TEAM_REMOVED).sendPacket(player);
        getScorePacket(baseScoreboard, EnumWrappers.ScoreboardAction.REMOVE).sendPacket(player);
    }

    public void modify(@NonNull BaseScoreboard baseScoreboard, @NonNull String scoreText, @NonNull Player player) {
        this.scoreText = scoreText;

        getTeamPacket(player, TEAM_UPDATED).sendPacket(player);
        getScorePacket(baseScoreboard, EnumWrappers.ScoreboardAction.CHANGE).sendPacket(player);
    }

    protected WrapperPlayServerScoreboardScore getScorePacket(@NonNull BaseScoreboard baseScoreboard,
                                                              @NonNull EnumWrappers.ScoreboardAction scoreboardAction) {

        WrapperPlayServerScoreboardScore scoreboardScorePacket = new WrapperPlayServerScoreboardScore();

        scoreboardScorePacket.setScoreName(getTeamName());
        scoreboardScorePacket.setObjectiveName(baseScoreboard.getScoreboardName());

        scoreboardScorePacket.setScoreboardAction(scoreboardAction);
        scoreboardScorePacket.setValue(scoreIndex);

        return scoreboardScorePacket;
    }

    protected WrapperPlayServerScoreboardTeam getTeamPacket(@NonNull Player player, int teamMode) {
        WrapperPlayServerScoreboardTeam teamPacket = new WrapperPlayServerScoreboardTeam();

        teamPacket.setName(COLORS[scoreIndex].toString());
        teamPacket.setMode(teamMode);

        if (teamMode == TEAM_REMOVED) {
            return teamPacket;
        }

        int aquaticVersion = MinecraftProtocolVersion.getVersion(MinecraftVersion.AQUATIC_UPDATE);
        int currentVersion = MinecraftProtocolVersion.getVersion(ProtocolLibrary.getProtocolManager().getMinecraftVersion());
        int playerVersion = ProtocolLibrary.getProtocolManager().getProtocolVersion(player);


        // Since 1.13 character limit for prefix/suffix was removed
        if (playerVersion >= aquaticVersion) {
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

        String[] splittingText = getSplittingText();

        teamPacket.setPrefix(splittingText[0]);
        teamPacket.setSuffix(splittingText[2]);

        teamPacket.setPlayers(Collections.singletonList(getTeamName()));

        return teamPacket;
    }

    @SuppressWarnings("all")
    protected String[] getSplittingText() {
        String text = (scoreText);

        if (text.length() > 48) {
            text = text.substring(0, 48);
        }

        List<String> modifierList = SPLITTER.splitToList(text);
        return Arrays.stream( modifierList.toArray(new String[3]) )

                .map(element -> (element == null ? ("") : element))
                .toArray(value -> new String[3]);
    }

    protected String getTeamName() {
        String name = getSplittingText()[1];

        return (!ChatColor.stripColor(name.trim()).isEmpty() ? name : COLORS[scoreIndex].toString());
    }

}