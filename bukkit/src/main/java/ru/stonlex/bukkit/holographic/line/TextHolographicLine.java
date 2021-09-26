package ru.stonlex.bukkit.holographic.line;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.holographic.ProtocolHolographic;
import ru.stonlex.bukkit.holographic.ProtocolHolographicLine;
import ru.stonlex.bukkit.protocollib.entity.impl.FakeArmorStand;

@Getter
public class TextHolographicLine implements ProtocolHolographicLine {

    protected final int lineIndex;

    protected final ProtocolHolographic holographic;

    protected Location location;


    @Setter
    protected String lineText;


    @Setter
    private FakeArmorStand fakeArmorStand;


    public TextHolographicLine(int lineIndex, String lineText, ProtocolHolographic holographic) {
        this.lineIndex = lineIndex;
        this.holographic = holographic;
        this.lineText = lineText;

        this.location = holographic.getLocation();
    }

    @Override
    public double getLineDistance() {
        return ProtocolHolographicLine.DEFAULT_LINE_DISTANCE;
    }

    @Override
    public void initialize() {
        setFakeArmorStand(new FakeArmorStand(normalizeLocation(getLocation())));

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
    public boolean hasReceiver(@NonNull Player player) {
        return fakeArmorStand.hasViewer(player);
    }

    @Override
    public void addReceivers(@NonNull Player... receivers) {
        fakeArmorStand.addReceivers(receivers);
    }

    @Override
    public void removeReceivers(@NonNull Player... receivers) {
        fakeArmorStand.removeReceivers(receivers);
    }

    @Override
    public boolean hasViewer(@NonNull Player player) {
        return fakeArmorStand.hasViewer(player);
    }

    @Override
    public void addViewers(@NonNull Player... viewers) {
        fakeArmorStand.addViewers(viewers);
    }

    @Override
    public void removeViewers(@NonNull Player... viewers) {
        fakeArmorStand.removeViewers(viewers);
    }

    @Override
    public void spawn() {
        fakeArmorStand.spawn();
    }

    @Override
    public void remove() {
        fakeArmorStand.remove();
    }

    @Override
    public void teleport(Location location) {
        this.location = normalizeLocation(location);

        fakeArmorStand.teleport(this.location);
    }

}
