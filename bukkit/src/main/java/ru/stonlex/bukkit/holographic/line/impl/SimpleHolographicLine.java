package ru.stonlex.bukkit.holographic.line.impl;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.holographic.ProtocolHolographic;
import ru.stonlex.bukkit.holographic.line.ProtocolHolographicLine;
import ru.stonlex.bukkit.protocollib.entity.impl.FakeArmorStand;

@Getter
public class SimpleHolographicLine implements ProtocolHolographicLine {

    protected final int lineIndex;

    protected final ProtocolHolographic holographic;

    protected Location location;


    @Setter
    protected String lineText;


    @Setter
    private FakeArmorStand fakeArmorStand;


    public SimpleHolographicLine(int lineIndex, String lineText, ProtocolHolographic holographic) {
        this.lineIndex = lineIndex;
        this.holographic = holographic;
        this.lineText = lineText;
        this.location = holographic.getLocation();

        this.create();
    }


    @Override
    public void create() {
        setFakeArmorStand(new FakeArmorStand(getLocation().clone().add(0, -(0.25 * lineIndex), 0)));

        getFakeArmorStand().setInvisible(true);
        getFakeArmorStand().setBasePlate(false);
        getFakeArmorStand().setCustomNameVisible(true);
        getFakeArmorStand().setCustomName(lineText);
    }

    @Override
    public void update() {
        fakeArmorStand.setCustomName(lineText);
    }

    @Override
    public void remove() {
        fakeArmorStand.remove();
    }

    @Override
    public void teleport(Location location) {
        this.location = location.clone().add(0, -(0.25 * lineIndex), 0);

        fakeArmorStand.teleport(this.location);
    }

    @Override
    public boolean isSpawnedToPlayer(Player player) {
        return fakeArmorStand.hasViewer(player);
    }

    @Override
    public void showToPlayer(Player player) {
        fakeArmorStand.addViewers(player);
    }

    @Override
    public void hideToPlayer(Player player) {
        fakeArmorStand.removeViewers(player);
    }

}
