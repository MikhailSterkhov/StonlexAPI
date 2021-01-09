package ru.stonlex.example.game;

import lombok.NonNull;
import ru.stonlex.bukkit.gaming.GameCountdownProcess;
import ru.stonlex.bukkit.gaming.GameProcess;

public class ExampleGameCountdown extends GameCountdownProcess {

    public ExampleGameCountdown(@NonNull GameProcess gameProcess, int playersCountToStart) {
        super(gameProcess, playersCountToStart);
    }

    @Override
    protected void onCountdownTick(int currentCountdown, int startCountdown,
                                   @NonNull GameProcess gameProcess) {

        gameProcess.alert("Игра начнется через " + currentCountdown);
    }

    @Override
    protected void onCountdownTaskStop(int currentCountdown,
                                       @NonNull GameProcess gameProcess) {

        gameProcess.alert("Таймер был принудитльно остановлен!");
    }

}
