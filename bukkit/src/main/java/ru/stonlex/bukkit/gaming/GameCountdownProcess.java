package ru.stonlex.bukkit.gaming;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import ru.stonlex.bukkit.StonlexBukkitApiPlugin;
import ru.stonlex.bukkit.gaming.event.GameCountdownStartEvent;
import ru.stonlex.bukkit.gaming.event.GameCountdownStopEvent;
import ru.stonlex.bukkit.gaming.event.GameCountdownUpdateEvent;
import ru.stonlex.bukkit.gaming.setting.GamingSettingType;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Getter
public abstract class GameCountdownProcess {

    protected final @NonNull GameProcess gameProcess;
    private final int playersCountToStart;

    private int currentCountdown;

// ============================================== // PROTECTED COUNTDOWN ABSTRACT // ============================================== //

    /**
     * Абстрактный передающийся метод
     * <p>
     * Вызывается тогда, когда тикает таймер
     * до запуска самой игры
     *
     * @param currentCountdown - текущее количество секунд
     * @param startCountdown   - стартовое количество секунд
     * @param gameProcess      - обработчик игрового процесса
     */
    protected abstract void onCountdownTick
    (int currentCountdown, int startCountdown, @NonNull GameProcess gameProcess);

    /**
     * Внезапная остановка таймера
     *
     * @param currentCountdown - количество секунд, которое было до остановки
     * @param gameProcess      - обработчик игрового процесса
     */
    protected void onCountdownTaskStop(int currentCountdown, @NonNull GameProcess gameProcess) { }

// ============================================== // PROTECTED COUNTDOWN ABSTRACT // ============================================== //


    /**
     * Запустить работоспособность игрового
     * таймера
     */
    public void enableCountdown() {
        gameProcess.setSetting(GamingSettingType.GAME_STATUS, GamingStatus.WAIT_PLAYERS);
        int startCountdown = gameProcess.getSetting(GamingSettingType.COUNTDOWN_START_SECONDS, int.class);

        runTask0(StonlexBukkitApiPlugin.getInstance(), bukkitRunnable -> onCountdownTick(currentCountdown, startCountdown, gameProcess));
    }

    /**
     * Отменить задачу таймера и
     * запустить процесс игры
     */
    public void startGame() {
        cancelTask0();

        gameProcess.setSetting(GamingSettingType.COUNTDOWN_CURRENT_SECONDS, gameProcess.getSetting(GamingSettingType.COUNTDOWN_START_SECONDS, int.class));
        gameProcess.setSetting(GamingSettingType.GAME_STATUS, GamingStatus.IN_GAME);

        gameProcess.onStart();
    }

    /**
     * Установить текущее количество секунд
     * для игрового таймера
     *
     * @param currentCountdown - количество секунд
     */
    public void setCurrentCountdown(int currentCountdown) {
        if (this.currentCountdown == currentCountdown) {
            return;
        }

        int startCountdown = gameProcess.getSetting(GamingSettingType.COUNTDOWN_START_SECONDS, int.class);

        Preconditions.checkArgument(currentCountdown >= 0 && currentCountdown <= startCountdown, "incorrect current countdown! (0 <= countdown <= startCountdown)");
        this.currentCountdown = currentCountdown;

        gameProcess.setSetting(GamingSettingType.COUNTDOWN_CURRENT_SECONDS, currentCountdown);
    }


// ============================================== // PRIVATE COUNTDOWN TASK // ============================================== //

    private BukkitRunnable countdownTask = null;

    /**
     * Запустить задачу обновления игрового таймера
     *
     * @param plugin            - плагин
     * @param runnableConsumer- обработчик таймера
     */
    private void runTask0(@NonNull Plugin plugin, @NonNull Consumer<BukkitRunnable> runnableConsumer) {
        GameCountdownStartEvent countdownStartEvent = new GameCountdownStartEvent(this);
        Bukkit.getPluginManager().callEvent(countdownStartEvent);

        if (countdownStartEvent.isCancelled()) {
            return;
        }

        int startCountdown = gameProcess.getSetting(GamingSettingType.COUNTDOWN_START_SECONDS, int.class);

        this.countdownTask = new BukkitRunnable() {

            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().size() < playersCountToStart) {
                    if (currentCountdown != startCountdown) {

                        GameCountdownStopEvent countdownStopEvent = new GameCountdownStopEvent(GameCountdownProcess.this);
                        Bukkit.getPluginManager().callEvent(countdownStopEvent);

                        if (countdownStopEvent.isCancelled()) {
                            return;
                        }

                        onCountdownTaskStop(currentCountdown, gameProcess);
                    }

                    setCurrentCountdown(startCountdown);
                    return;
                }

                if (currentCountdown < 1) {
                    startGame();

                    return;
                }

                GameCountdownUpdateEvent countdownUpdateEvent = new GameCountdownUpdateEvent(GameCountdownProcess.this, currentCountdown);
                Bukkit.getPluginManager().callEvent(countdownUpdateEvent);

                if (countdownUpdateEvent.isCancelled()) {
                    return;
                }

                runnableConsumer.accept(this);
                setCurrentCountdown(currentCountdown - 1);
            }
        };

        setCurrentCountdown(startCountdown);
        countdownTask.runTaskTimer(plugin, 20, 20);
    }

    /**
     * Отменить текущую задачу обновления
     * таймера
     */
    private void cancelTask0() {
        GameCountdownStopEvent countdownStopEvent = new GameCountdownStopEvent(this);
        Bukkit.getPluginManager().callEvent(countdownStopEvent);

        if (countdownStopEvent.isCancelled()) {
            return;
        }

        if (countdownTask != null) {
            countdownTask.cancel();
        }

        onCountdownTaskStop(currentCountdown, gameProcess);
    }

// ============================================== // PRIVATE COUNTDOWN TASK // ============================================== //

}
