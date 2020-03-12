package ru.stonlex.bukkit.game.factory;

import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.game.GameManager;
import ru.stonlex.bukkit.game.GameSettings;

@Getter
public abstract class AbstractGameTimer {

    //цикличный runnable, блягодаря которому тикает таймер
    protected BukkitRunnable updateTimerRunnable;

    protected final GameManager gameManager = BukkitAPI.getGameManager();
    protected final GameSettings gameSettings = gameManager.getGameSettings();


    /**
     * Запустить таймер
     */
    public void start(Plugin plugin) {
        this.updateTimerRunnable = new BukkitRunnable() {
            int secondsLeft = gameSettings.LOBBY_TIMER_START_SECONDS;

            @Override
            public void run() {
                if (secondsLeft <= 0) {
                    AbstractGameTimer.this.cancel();

                    gameManager.getGameFactory().onStartGame();
                    return;
                }

                onTick(secondsLeft);

                this.secondsLeft--;
            }
        };

        this.updateTimerRunnable.runTaskTimer(plugin, 0, 20);
    }

    /**
     * Остановить таймер
     */
    public void cancel() {
        if (updateTimerRunnable == null) {
            return;
        }

        this.updateTimerRunnable.cancel();
        this.updateTimerRunnable = null;
    }

    /**
     * Возвращает boolean, который говорит о том, запущен
     * ли таймер до начала игры
     */
    public boolean isTimerTick() {
        return updateTimerRunnable != null;
    }



    /**
     * Передающийся метод из-за абстракции,
     * вызывается в том случае, когда секунды таймера тикают
     *
     * @param secondsLeft - секунды таймера
     */
    public abstract void onTick(int secondsLeft);

}
