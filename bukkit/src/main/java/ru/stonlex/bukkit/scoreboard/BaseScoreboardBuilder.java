package ru.stonlex.bukkit.scoreboard;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import ru.stonlex.bukkit.scoreboard.animation.ScoreboardDisplayStaticAnimation;

@RequiredArgsConstructor
@Getter
public final class BaseScoreboardBuilder {

    private final String scoreboardName;

    private ScoreboardDisplayAnimation scoreboardDisplay;
    private BaseScoreboardScope scoreboardScope = BaseScoreboardScope.PROTOTYPE;

    private long durationTick;
    private ScoreboardUpdater scoreboardUpdater;

    private TIntObjectMap<BaseScoreboardLine> scoreboardLineMap = new TIntObjectHashMap<>();


    public static BaseScoreboardBuilder newScoreboardBuilder() {
        return new BaseScoreboardBuilder(RandomStringUtils.randomAlphanumeric(12));
    }

    public static BaseScoreboardBuilder newScoreboardBuilder(@NonNull String scoreboardName) {
        return new BaseScoreboardBuilder(scoreboardName);
    }


    /**
     * Установить титульное имя скорбоарду
     *
     * @param scoreboardDisplay - титульное имя
     */
    public BaseScoreboardBuilder scoreboardDisplay(@NonNull String scoreboardDisplay) {
        this.scoreboardDisplay = new ScoreboardDisplayStaticAnimation(scoreboardDisplay);
        return this;
    }
    /**
     * Установить титульное имя скорбоарду
     *
     * @param displayAnimation - титульное имя
     */
    public BaseScoreboardBuilder scoreboardDisplay(@NonNull ScoreboardDisplayAnimation displayAnimation) {
        this.scoreboardDisplay = displayAnimation;
        return this;
    }


    /**
     * Добавить строку в скорбоард
     *
     * @param baseScoreboardLine - строка
     */
    public BaseScoreboardBuilder scoreboardLine(@NonNull BaseScoreboardLine baseScoreboardLine) {
        scoreboardLineMap.put(baseScoreboardLine.getScoreIndex(), baseScoreboardLine);
        return this;
    }

    /**
     * Добавить строку в скорбоард по
     * определенному индексу
     *
     * @param scoreIndex - индекс строки
     * @param scoreText - текст строки
     */
    public BaseScoreboardBuilder scoreboardLine(int scoreIndex, String scoreText) {
        return scoreboardLine(new BaseScoreboardLine(scoreIndex, scoreText));
    }

    /**
     * Добавить несколько строк сразу,
     * найдя для них свои индексы
     *
     * @param scoresTextArray - строки скорборда
     */
    public BaseScoreboardBuilder scoreboardLine(@NonNull String... scoresTextArray) {
        int length = Math.min(scoresTextArray.length, 15);

        for (int scoreIndex = length; scoreIndex > 0; scoreIndex--) {
            if (scoresTextArray.length < scoreIndex) {
                continue;
            }

            String scoreText = scoresTextArray[length - scoreIndex];
            scoreboardLine(scoreIndex, scoreText);
        }

        return this;
    }

    /**
     * Установить скорбоарду тип видимости
     *
     * @param scoreboardScope - тип видимости
     */
    public BaseScoreboardBuilder scoreboardScope(@NonNull BaseScoreboardScope scoreboardScope) {
        this.scoreboardScope = scoreboardScope;
        return this;
    }

    /**
     * Установить скорбоарду режим автообновления
     */
    public BaseScoreboardBuilder scoreboardUpdater(@NonNull ScoreboardUpdater scoreboardUpdater,
                                                   long durationTick) {

        this.scoreboardUpdater = scoreboardUpdater;
        this.durationTick = durationTick;

        return this;
    }

    /**
     * Преобразовать все инициализированные
     * данные скоарборда в {@link BaseScoreboard}
     */
    public BaseScoreboard build() {
        BaseScoreboard baseScoreboard = new BaseScoreboard(scoreboardName, scoreboardDisplay, scoreboardLineMap);
        baseScoreboard.setScoreboardScope(scoreboardScope);

        if (scoreboardUpdater != null) {
            baseScoreboard.addScoreboardUpdater(scoreboardUpdater, durationTick);
        }

        return baseScoreboard;
    }

}
