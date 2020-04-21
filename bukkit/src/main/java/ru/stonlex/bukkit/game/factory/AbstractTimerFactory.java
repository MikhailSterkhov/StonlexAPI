package ru.stonlex.bukkit.game.factory;

import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.game.GameManager;
import ru.stonlex.bukkit.game.GameSettings;
import ru.stonlex.bukkit.game.enums.GameEvent;

public abstract class AbstractTimerFactory {

    @Getter //цикличный runnable, благодаря которому тикает таймер
    protected BukkitRunnable updateTimerRunnable;

    protected final GameManager gameManager = BukkitAPI.getInstance().getGameManager();
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
                    AbstractTimerFactory.this.cancel();

                    gameManager.getGameFactory().onStartGame();
                    gameManager.getEventManager().callGameEvent(GameEvent.START_GAME);
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
