package ru.stonlex.global.utility.query;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Supplier;

@UtilityClass
public class AsyncUtil {

    /**
     * scheduled для того, чтобы чуть что можно было
     * какой-нибудь шедулер заебошить, ну так, по фану )0)))0)
     */
    public final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();


    /**
     * Пропидорить раннебл в аснхронном потоке
     *
     * @param command - асихронная команда
     */
    public void submitAsync(Runnable command) {
        EXECUTOR_SERVICE.submit(command);
    }

    /**
     * Пропидорить раннебл в аснхронном потоке
     *
     * @param throwableAsynchronousCommand - асихронная команда
     */
    public void submitThrowsAsync(ThrowableAsynchronousCommand throwableAsynchronousCommand) {
        submitAsync(() -> {
            try {
                throwableAsynchronousCommand.submitCommand();
            }

            catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    /**
     * Создать объект в асинхронном потобке
     *
     * @param futureSupplier - обработчик создания объекта
     */
    @SneakyThrows
    public <T> T supplyAsyncFuture(Supplier<T> futureSupplier) {
        CompletableFuture<T> completableFuture
                = CompletableFuture.supplyAsync(futureSupplier);

        return completableFuture.get();
    }

    public interface ThrowableAsynchronousCommand {

        void submitCommand() throws Exception;
    }

}
