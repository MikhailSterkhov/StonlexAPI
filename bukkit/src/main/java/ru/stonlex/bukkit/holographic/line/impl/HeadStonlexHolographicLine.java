package ru.stonlex.bukkit.holographic.line.impl;

import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.holographic.IProtocolHolographic;
import ru.stonlex.bukkit.holographic.line.IProtocolHolographicLine;
import ru.stonlex.bukkit.depend.protocol.entity.impl.FakeArmorStand;
import ru.stonlex.bukkit.utility.ItemUtil;

@Getter
public class HeadStonlexHolographicLine implements IProtocolHolographicLine {

    protected final int lineIndex;

    protected final boolean small;

    protected final IProtocolHolographic holographic;

    protected Location location;


    @Setter
    protected String lineText;


    @Setter
    private FakeArmorStand fakeArmorStand;


    public HeadStonlexHolographicLine(int lineIndex, String skullTexture, boolean small, IProtocolHolographic holographic) {
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
                    .setEquipment(EnumWrappers.ItemSlot.HEAD, ItemUtil.getTextureSkull(lineText));
        } else {
            fakeArmorStand.getEntityEquipment()
                    .setEquipment(EnumWrappers.ItemSlot.HEAD, ItemUtil.getPlayerSkull(lineText));
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
                .setEquipment(EnumWrappers.ItemSlot.HEAD, ItemUtil.getTextureSkull(lineText));
    }

    @Override
    public void remove() {
        fakeArmorStand.remove();
    }

    @Override
    public void teleport(Location location) {
        this.location = location.clone().add(0, -(0.25 * lineIndex), 0);

        fakeArmorStand.teleport(location);
    }

    @Override
    public boolean isSpawnedToPlayer(Player player) {
        return fakeArmorStand.hasSpawnedToPlayer(player);
    }

    @Override
    public void showToPlayer(Player player) {
        fakeArmorStand.spawnToPlayer(player);
    }

    @Override
    public void hideToPlayer(Player player) {
        fakeArmorStand.removeToPlayer(player);
    }

}
