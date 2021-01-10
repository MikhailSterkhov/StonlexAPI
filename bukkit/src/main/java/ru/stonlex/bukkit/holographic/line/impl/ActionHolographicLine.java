package ru.stonlex.bukkit.holographic.line.impl;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.holographic.ProtocolHolographic;
import ru.stonlex.bukkit.holographic.line.ProtocolHolographicLine;
import ru.stonlex.bukkit.protocollib.entity.impl.FakeArmorStand;
import ru.stonlex.bukkit.protocollib.entity.impl.FakeSilverfish;

import java.util.function.Consumer;

@Getter
public class ActionHolographicLine implements ProtocolHolographicLine {

    protected final int lineIndex;

    protected final ProtocolHolographic holographic;

    private final Consumer<Player> clickAction;

    protected Location location;


    @Setter
    protected String lineText;


    @Setter
    private FakeArmorStand fakeArmorStand;

    @Setter
    private FakeSilverfish clickableSilverfish;


    public ActionHolographicLine(int lineIndex, String lineText, ProtocolHolographic holographic, Consumer<Player> clickAction) {
        this.lineIndex = lineIndex;
        this.clickAction = clickAction;
        this.holographic = holographic;
        this.lineText = lineText;
        this.location = holographic.getLocation();

        this.create();
    }


    @Override
    public void create() {
        //armor stand holographic
        setFakeArmorStand(new FakeArmorStand(getLocation().clone().add(0, -(0.25 * lineIndex), 0)));

        fakeArmorStand.setInvisible(true);
        fakeArmorStand.setBasePlate(false);
        fakeArmorStand.setCustomNameVisible(true);
        fakeArmorStand.setCustomName(lineText);

        //clickable silverfish
        setClickableSilverfish(new FakeSilverfish(fakeArmorStand.getLocation().clone().add(0, 2.2, 0)));

        clickableSilverfish.setInvisible(true);
        clickableSilverfish.setClickAction(clickAction);
    }

    @Override
    public void update() {
        fakeArmorStand.setCustomName(lineText);
    }

    @Override
    public void remove() {
        fakeArmorStand.remove();
        clickableSilverfish.remove();
    }

    @Override
    public void teleport(Location location) {
        this.location = location.clone().add(0, -(0.25 * lineIndex), 0);

        fakeArmorStand.teleport(this.location);
    }

    @Override
    public boolean isSpawnedToPlayer(Player player) {
        return clickableSilverfish.hasViewer(player) && fakeArmorStand.hasViewer(player);
    }

    @Override
    public void showToPlayer(Player player) {
        fakeArmorStand.addViewers(player);
        clickableSilverfish.addViewers(player);
    }

    @Override
    public void hideToPlayer(Player player) {
        fakeArmorStand.removeViewers(player);
        clickableSilverfish.removeViewers(player);
    }

}
