package ru.stonlex.bukkit.board.line;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.ChatColor;
import ru.stonlex.bukkit.board.MoonSidebar;
import ru.stonlex.bukkit.protocol.packet.AbstractPacket;
import ru.stonlex.bukkit.protocol.packet.scoreboard.WrapperPlayServerScoreboardScore;
import ru.stonlex.bukkit.protocol.packet.scoreboard.WrapperPlayServerScoreboardTeam;

import java.util.Collections;
import java.util.Iterator;

@Getter
public class SidebarLine {

    private final int index;
    private String text;
    private final MoonSidebar sidebar;

    /**
     * Конструктор линии.
     *
     * @param index   - индекс линию, чем выше индекс, тем выше линия
     * @param text    - текст линии
     * @param sidebar - текущий скорборд
     */
    public SidebarLine(int index, @NonNull String text, @NonNull MoonSidebar sidebar) {
        this.index = index;
        this.text = text;
        this.sidebar = sidebar;
        Preconditions.checkArgument(sidebar.getObjective() != null, "Objective cannot be null.");
        show();
    }

    /**
     * Установка текста линии.
     *
     * @param text - строка
     */
    public void setText(@NonNull String text) {
        this.text = text;
        AbstractPacket teamPacket = getTeamPacket(WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED);
        AbstractPacket scorePacket = getScorePacket(EnumWrappers.ScoreboardAction.CHANGE);
        sidebar.broadcastPacket(teamPacket);
        sidebar.broadcastPacket(scorePacket);
    }

    /**
     * Спрятать линию
     */
    public void hide() {
        AbstractPacket teamPacket = getTeamPacket(WrapperPlayServerScoreboardTeam.Mode.TEAM_REMOVED);
        AbstractPacket scorePacket = getScorePacket(EnumWrappers.ScoreboardAction.REMOVE);
        sidebar.broadcastPacket(teamPacket);
        sidebar.broadcastPacket(scorePacket);
    }

    /**
     * Показать линию
     */
    public void show() {
        AbstractPacket teamPacket = getTeamPacket(WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED);
        AbstractPacket scorePacket = getScorePacket(EnumWrappers.ScoreboardAction.CHANGE);
        sidebar.broadcastPacket(teamPacket);
        sidebar.broadcastPacket(scorePacket);
    }

    /**
     * Особая фишка "не мигания", которая заключается в следующуем:
     * В скорбордах есть команды (тимы), как правило у них есть префикс и суффикс,
     * Так же у них есть массив игроков. Для лайфхака нужно добавить игрока в тиму,
     * в качестве игрока выступает ChatColor, который также выступает в качестве ключа.
     * Эта вся магия позволяет использовать максимум 32 символа, так как остальные 16 заняты
     * этим самым ключем. Ну что поделать, ради non-flicker'a нужно идти на такие жертвы :(
     * Ну я думаю 32 символа будет вполне чем достаточно.
     *
     * @param mode - число, обозначающее ID действия {@link WrapperPlayServerScoreboardTeam.Mode}
     * @return подготовленный пакет
     */
    public AbstractPacket getTeamPacket(int mode) {
        Preconditions.checkNotNull(text, "Text cannot be null");
        Preconditions.checkArgument(text.length() <= 32, "Text length must be <= 32, no otherwise.");
        //Чтобы не дубрировались строки
        String result = ChatColor.values()[index].toString();
        WrapperPlayServerScoreboardTeam team = new WrapperPlayServerScoreboardTeam();
        Iterator<String> iterator = Splitter.fixedLength(16).split(text).iterator();
        String prefix = iterator.next();

        team.setName("text-" + index);
        team.setMode(mode);
        team.setPrefix(prefix);

        team.setPlayers(Collections.singletonList(result));

        //Магия, не трогать
        if (text.length() > 16) {
            String prefixColor = ChatColor.getLastColors(prefix);
            String suffix = iterator.next();

            if (prefix.endsWith(String.valueOf(ChatColor.COLOR_CHAR))) {
                prefix = prefix.substring(0, prefix.length() - 1);
                team.setPrefix(prefix);
                prefixColor = ChatColor.getByChar(suffix.charAt(0)).toString();
                suffix = suffix.substring(1);
            }

            if (prefixColor == null)
                prefixColor = "";

            if (suffix.length() > 16) {
                suffix = suffix.substring(0, (13 - prefixColor.length()));
            }

            team.setSuffix((prefixColor.equals("") ? ChatColor.RESET : prefixColor) + suffix);
        }

        return team;
    }

    public AbstractPacket getScorePacket(EnumWrappers.ScoreboardAction action) {
        WrapperPlayServerScoreboardScore score = new WrapperPlayServerScoreboardScore();
        //Прказываем/удаляем линию
        score.setObjectiveName(sidebar.getObjective().getName());
        score.setScoreboardAction(action);
        score.setValue(index);
        score.setScoreName(ChatColor.values()[index].toString());

        return score;
    }


}