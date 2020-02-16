package ru.stonlex.global.utility;

import lombok.experimental.UtilityClass;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@UtilityClass
public final class AsyncUtil {

    /**
     * scheduled для того, чтобы чуть что можно было
     * какой-нибудь шедулер заебошить, ну так, по фану )0)))0)
     */
    private final ScheduledExecutorService THREAD_POOL = Executors.newSingleThreadScheduledExecutor();


    /**
     * Пропидорить раннебл в аснхронном потоке
     *
     * @param command - раннебл
     */
    public void runAsync(Runnable command) {
        THREAD_POOL.submit( command );
    }

}
