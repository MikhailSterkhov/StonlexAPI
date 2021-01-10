package ru.stonlex.bukkit.holographic.line.impl;

import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.holographic.ProtocolHolographic;
import ru.stonlex.bukkit.holographic.line.ProtocolHolographicLine;
import ru.stonlex.bukkit.protocollib.entity.impl.FakeArmorStand;
import ru.stonlex.bukkit.utility.ItemUtil;

@Getter
public class HeadHolographicLine implements ProtocolHolographicLine {

    protected final int lineIndex;

    protected final boolean small;

    protected final ProtocolHolographic holographic;

    protected Location location;


    @Setter
    protected String lineText;


    @Setter
    private FakeArmorStand fakeArmorStand;


    public HeadHolographicLine(int lineIndex, String skullTexture, boolean small, ProtocolHolographic holographic) {
        this.lineIndex = lineIndex;
        this.holographic = holographic;
        this.small = small;
        this.lineText = skullTexture;
        this.location = holographic.getLocation();

        this.create();
    }


    @Override
    public void create() {
        setFakeArmorStand(new FakeArmorStand(getLocation().clone().add(0, -(0.2 * lineIndex), 0)));

        if (lineText.length() > 20) {
            fakeArmorStand.getEntityEquipment()
                    .setEquipment(EnumWrappers.ItemSlot.HEAD, ItemUtil.getSkullByTexture(lineText));
        } else {
            fakeArmorStand.getEntityEquipment()
                    .setEquipment(EnumWrappers.ItemSlot.HEAD, ItemUtil.getSkull(lineText));
        }

        fakeArmorStand.setSmall(small);
        fakeArmorStand.setInvisible(true);
        fakeArmorStand.setBasePlate(false);

        //для расстояния между головой и новыми строками
        holographic.addEmptyHolographicLine();
        holographic.addEmptyHolographicLine();

        if (!fakeArmorStand.isSmall()) {
            holographic.addEmptyHolographicLine();
        }
    }

    @Override
    public void update() {
        fakeArmorStand.getEntityEquipment()
                .setEquipment(EnumWrappers.ItemSlot.HEAD, ItemUtil.getSkullByTexture(lineText));
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
