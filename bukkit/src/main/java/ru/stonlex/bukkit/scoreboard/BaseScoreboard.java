package ru.stonlex.bukkit.scoreboard;

import gnu.trove.map.TIntObjectMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import ru.stonlex.bukkit.StonlexBukkitApiPlugin;
import ru.stonlex.bukkit.protocollib.packet.scoreboard.WrapperPlayServerScoreboardDisplayObjective;
import ru.stonlex.bukkit.protocollib.packet.scoreboard.WrapperPlayServerScoreboardObjective;
import ru.stonlex.bukkit.utility.MetadataUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BaseScoreboard {

    @Getter
    private final @NonNull String scoreboardName;
    @Getter
    private final @NonNull ScoreboardDisplayAnimation scoreboardDisplay;

    @Getter
    private final @NonNull TIntObjectMap<BaseScoreboardLine> scoreboardLineMap;

    @Getter
    private BaseScoreboardScope scoreboardScope;


    @Getter private final Collection<Player> playerReceiverCollection      = new ArrayList<>();
    @Getter private final Map<Player, BaseScoreboard> scoreboardQueueMap   = new ConcurrentHashMap<>();

    private final Collection<BukkitTask> scoreboardUpdaterCollection       = new ArrayList<>();

    /**
     * Установить скорбоард для игрока
     *
     * @param player - игрок
     */
    public void setScoreboardToPlayer(@NonNull Player player) {
        BaseScoreboard previousScoreboard = getFirstCachedPlayerScoreboard(player);
        if (previousScoreboard != null) {

            if (equals(previousScoreboard)) {
                return;
            }

            if (previousScoreboard.scoreboardScope.equals(BaseScoreboardScope.PUBLIC)
                    && previousScoreboard.scoreboardScope.equals(scoreboardScope)) {
                return;
            }

            previousScoreboard.removeScoreboardToPlayer(player);
            if (!previousScoreboard.scoreboardQueueMap.containsKey(player)) {

                // Только глобальные борды имеют право ожидать игрока обратно
                if (previousScoreboard.scoreboardScope.equals(BaseScoreboardScope.PUBLIC)) {
                    previousScoreboard.scoreboardQueueMap.put(player, this);
                }
            }
        }

        // Если игрок до этого менял свой борд
        BaseScoreboard scoreboardQueue = scoreboardQueueMap.get(player);
        if (scoreboardQueue != null) {

            scoreboardQueue.removeScoreboardToPlayer(player);
            scoreboardQueueMap.remove(player);
        }

        // Не, ну а вдруг не инициализировался...
        if (scoreboardDisplay.getCurrentDisplay() == null) {
            scoreboardDisplay.nextDisplay();
        }

        // Инициализируем и отправляем борд игроку
        getObjectivePacket(WrapperPlayServerScoreboardObjective.Mode.ADD_OBJECTIVE)
                .sendPacket(player);

        for (BaseScoreboardLine scoreboardLine : scoreboardLineMap.valueCollection()) {
            scoreboardLine.create(BaseScoreboard.this, player);
        }

        getDisplayObjectivePacket().sendPacket(player);

        // Добавляем игрока в receiver`ов борда
        playerReceiverCollection.add(player);

        // Добавляем борд в метадату игрока
        MetadataUtil.setMetadata(player, PLAYER_METADATA_NAME, this);
    }

    /**
     * Удалить скорбоард для игрока
     *
     * @param player - игрок
     */
    public void removeScoreboardToPlayer(@NonNull Player player) {
        // Удаление борда
        for (BaseScoreboardLine baseScoreboardLine : new ArrayList<>(scoreboardLineMap.valueCollection())) {
            baseScoreboardLine.remove(this, player);
        }

        getObjectivePacket(WrapperPlayServerScoreboardObjective.Mode.REMOVE_OBJECTIVE)
                .sendPacket(player);

        playerReceiverCollection.remove(player);

        // Удаление борда из метадаты игрока
        MetadataUtil.removeMetadata(player, PLAYER_METADATA_NAME);

        // Чекаем наличие бордов в ожидании возвращения игрока
        BaseScoreboard previousScoreboard = scoreboardQueueMap.get(player);

        if (previousScoreboard != null) {
            previousScoreboard.setScoreboardToPlayer(player);
        }
    }

    /**
     * Проверить наличие игрока в скорбоарде,
     * который может принимать от него пакеты
     *
     * @param playerReceiver - игрок, принимаюший пакеты скорбоарда
     */
    public boolean hasPlayerReceiver(@NonNull Player playerReceiver) {
        return playerReceiverCollection.contains(playerReceiver);
    }

    /**
     * Отменить все созданные и запущенные
     * автообновления скорбоарда
     *
     * @param remove - разрешение на полное очищение от потоков
     */
    public void cancelUpdaters(boolean remove) {
        for (BukkitTask bukkitTask : scoreboardUpdaterCollection) {
            bukkitTask.cancel();
        }

        if (remove)
            scoreboardUpdaterCollection.clear();
    }

    /**
     * Добавить задачу автообновления скорбоарду
     *
     * @param scoreboardUpdater - задача автообновления
     * @param durationTick - задержка обновления в тиках
     */
    public void addScoreboardUpdater(@NonNull ScoreboardUpdater scoreboardUpdater,
                                     long durationTick) {

        BukkitTask bukkitTask = new BukkitRunnable() {

            @Override
            public void run() {
                scoreboardDisplay.nextDisplay();

                for (Player playerReceiver : new ArrayList<>(playerReceiverCollection)) {
                    // Чекаем борд из метадаты игрока
                    BaseScoreboard playerScoreboard = MetadataUtil.getMetadata(playerReceiver, PLAYER_METADATA_NAME, BaseScoreboard.class);

                    //Если его не существует, то устанавливаем текущий
                    if (playerScoreboard == null) {
                        MetadataUtil.setMetadata(playerReceiver, PLAYER_METADATA_NAME, this);
                    }

                    scoreboardUpdater.onUpdate(BaseScoreboard.this, playerReceiver);
                    updateDisplayAnimation(playerReceiver);
                }
            }

        }.runTaskTimer(StonlexBukkitApiPlugin.getPlugin(StonlexBukkitApiPlugin.class), durationTick, durationTick);
        scoreboardUpdaterCollection.add(bukkitTask);
    }

    /**
     * Получить кешированную строку скорбоарда
     * по ее индексу
     *
     * @param lineIndex - индекс строки
     */
    public BaseScoreboardLine getScoreboardLine(int lineIndex) {
        return scoreboardLineMap.get(lineIndex);
    }

    /**
     * Обвноить строку скорбоарда по данному
     * индексу для указанного игрока
     *
     * @param lineIndex - индекс строки
     *
     * @param player - игрок
     * @param scoreText - новый текст строки
     */
    public void updateScoreboardLine(int lineIndex, @NonNull Player player, @NonNull String scoreText) {
        BaseScoreboardLine scoreboardLine = getScoreboardLine(lineIndex);

        if (scoreboardLine == null) {
            setScoreboardLine(lineIndex, player, scoreText);
            return;
        }

        scoreboardLine.modify(this, scoreText, player);
    }

    /**
     * Установить/создать строку скорбоарда
     * с данным индексом для указанного игрока
     *
     * @param lineIndex - индекс строки
     *
     * @param player - игрок
     * @param scoreText - новый текст строки
     */
    public void setScoreboardLine(int lineIndex, @NonNull Player player, @NonNull String scoreText) {
        if (!hasPlayerReceiver(player)) {
            return;
        }

        BaseScoreboardLine scoreboardLine = getScoreboardLine(lineIndex);

        if (scoreboardLine != null) {
            updateScoreboardLine(lineIndex, player, scoreText);

            return;
        }

        if (scoreText.length() > 48) {
            scoreText = scoreText.substring(0, 48);
        }

        scoreboardLine = new BaseScoreboardLine(lineIndex, scoreText);
        scoreboardLine.create(this, player);

        scoreboardLineMap.put(lineIndex, scoreboardLine);
    }

    /**
     * Установить новую видимость скорборду
     *
     * @param scoreboardScope - видимость
     */
    public void setScoreboardScope(@NonNull BaseScoreboardScope scoreboardScope) {
        BaseScoreboardScope.SCOPING_SCOREBOARD_MAP.put(
                (this.scoreboardScope = scoreboardScope), this);

        switch (scoreboardScope) {
            case PROTOTYPE:
                break;

            case PUBLIC:
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    setScoreboardToPlayer(onlinePlayer);
                }

                break;
        }
    }

    /**
     * Удалить строку скорбоарда по данному
     * индексу для всех игроков
     *
     * @param lineIndex - индекс строки
     */
    public void removeScoreboardLine(int lineIndex) {
        BaseScoreboardLine scoreboardLine = getScoreboardLine(lineIndex);

        if (scoreboardLine == null) {
            return;
        }

        scoreboardLineMap.remove(lineIndex);

        for (Player player : playerReceiverCollection) {
            scoreboardLine.remove(this, player);
        }
    }

    /**
     * Удалить строку скорбоарда по данному
     * индексу для указанного игрока
     *
     * @param lineIndex - индекс строки
     * @param player - игрок
     */
    public void removeScoreboardLine(int lineIndex, @NonNull Player player) {
        BaseScoreboardLine scoreboardLine = getScoreboardLine(lineIndex);

        if (scoreboardLine == null) {
            return;
        }

        scoreboardLine.remove(this, player);
    }

    /**
     * Обновить титульное имя скорбоарда
     * по {@link ScoreboardDisplayAnimation}
     * для указанного игрока
     *
     * @param playerReceiver - игрок
     */
    public void updateDisplayAnimation(@NonNull Player playerReceiver) {
        getObjectivePacket(WrapperPlayServerScoreboardObjective.Mode.UPDATE_VALUE).sendPacket(playerReceiver);
    }

// ======================================================= // SCOREBOARD PROTOCOL // ======================================================= //

    public WrapperPlayServerScoreboardObjective getObjectivePacket(@NonNull int objectiveMode) {
        String currentDisplay = scoreboardDisplay.getCurrentDisplay();

        if (currentDisplay.length() > 32) {
            currentDisplay = currentDisplay.substring(0, 32);
        }

        WrapperPlayServerScoreboardObjective objectivePacket = new WrapperPlayServerScoreboardObjective();

        objectivePacket.setName(scoreboardName);
        objectivePacket.setDisplayName(currentDisplay);

        objectivePacket.setHealthDisplay(WrapperPlayServerScoreboardObjective.HealthDisplay.INTEGER);
        objectivePacket.setMode(objectiveMode);

        return objectivePacket;
    }

    public WrapperPlayServerScoreboardDisplayObjective getDisplayObjectivePacket() {
        WrapperPlayServerScoreboardDisplayObjective displayObjectivePacket = new WrapperPlayServerScoreboardDisplayObjective();

        displayObjectivePacket.setScoreName(scoreboardName);
        displayObjectivePacket.setPosition(1);

        return displayObjectivePacket;
    }

// ========================================================= // STATIC FACTORY // ========================================================= //

    public static final String PLAYER_METADATA_NAME
            = ("CURRENT_BASE_SCOREBOARD");

    public static BaseScoreboard getFirstCachedPlayerScoreboard(@NonNull Player player) {
        return getCachedPlayerScoreboard(player)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public static Collection<BaseScoreboard> getCachedPlayerScoreboard(@NonNull Player player) {
        return BaseScoreboardScope.SCOPING_SCOREBOARD_MAP.values()
                .stream()
                .filter(scoreboard -> scoreboard.getPlayerReceiverCollection().contains(player))
                .collect(Collectors.toList());
    }

    public static Collection<BaseScoreboard> getQueuePlayerScoreboard(@NonNull Player player) {
        return BaseScoreboardScope.SCOPING_SCOREBOARD_MAP.values()
                .stream()
                .filter(scoreboard -> scoreboard.getScoreboardQueueMap().containsKey(player))
                .collect(Collectors.toList());
    }

    public static BaseScoreboard getCachedScoreboardByName(@NonNull String scoreboardName) {
        return BaseScoreboardScope.SCOPING_SCOREBOARD_MAP.values()
                .stream()
                .filter(scoreboard -> scoreboard.getScoreboardName().equalsIgnoreCase(scoreboardName))
                .findFirst()
                .orElse(null);
    }

// ======================================================================================================================================== //

}