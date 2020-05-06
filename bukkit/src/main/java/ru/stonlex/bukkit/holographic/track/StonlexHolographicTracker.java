package ru.stonlex.bukkit.holographic.track;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.holographic.IProtocolHolographic;
import ru.stonlex.bukkit.holographic.addon.IProtocolHolographicTracker;

@RequiredArgsConstructor
public class StonlexHolographicTracker implements IProtocolHolographicTracker {

    @Getter
    private final IProtocolHolographic holographic;

    @Getter
    private final int trackDistance;


    @Override
    public void onHolographicShow(Player player) {
        //todo: При приближении на расстоянии this.getTrackDistance() блоков голограмма показывается
    }

    @Override
    public void onHolographicHide(Player player) {
        //todo: При уходе на расстояние this.getTrackDistance() блоков голограмма скрывается
    }

}
