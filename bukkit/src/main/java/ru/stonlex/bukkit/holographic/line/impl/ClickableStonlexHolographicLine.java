package ru.stonlex.bukkit.holographic.line.impl;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.holographic.IProtocolHolographic;
import ru.stonlex.bukkit.holographic.line.IProtocolHolographicLine;
import ru.stonlex.bukkit.depend.protocol.entity.impl.FakeArmorStand;
import ru.stonlex.bukkit.depend.protocol.entity.impl.FakeSilverfish;

import java.util.function.Consumer;

@Getter
public class ClickableStonlexHolographicLine implements IProtocolHolographicLine {

    protected final int lineIndex;

    protected final IProtocolHolographic holographic;

    private final Consumer<Player> clickAction;

    protected Location location;


    @Setter
    protected String lineText;


    @Setter
    private FakeArmorStand fakeArmorStand;

    @Setter
    private FakeSilverfish clickableSilverfish;


    public ClickableStonlexHolographicLine(int lineIndex, String lineText, IProtocolHolographic holographic, Consumer<Player> clickAction) {
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

        fakeArmorStand.teleport(location);
    }

    @Override
    public boolean isSpawnedToPlayer(Player player) {
        return clickableSilverfish.hasSpawnedToPlayer(player) && fakeArmorStand.hasSpawnedToPlayer(player);
    }

    @Override
    public void showToPlayer(Player player) {
        fakeArmorStand.spawnToPlayer(player);
        clickableSilverfish.spawnToPlayer(player);
    }

    @Override
    public void hideToPlayer(Player player) {
        fakeArmorStand.removeToPlayer(player);
        clickableSilverfish.removeToPlayer(player);
    }

}
