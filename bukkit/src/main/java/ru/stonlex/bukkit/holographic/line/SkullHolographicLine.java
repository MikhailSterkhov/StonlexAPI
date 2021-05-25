package ru.stonlex.bukkit.holographic.line;

import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.holographic.ProtocolHolographic;
import ru.stonlex.bukkit.holographic.ProtocolHolographicLine;
import ru.stonlex.bukkit.protocollib.entity.impl.FakeArmorStand;
import ru.stonlex.bukkit.utility.ItemUtil;

@Getter
public class SkullHolographicLine implements ProtocolHolographicLine {

    protected final int lineIndex;

    protected final boolean small;

    protected final ProtocolHolographic holographic;

    protected Location location;


    @Setter
    protected String lineText;


    @Setter
    private FakeArmorStand fakeArmorStand;


    public SkullHolographicLine(int lineIndex, String skullTexture, boolean small, ProtocolHolographic holographic) {
        this.lineIndex = lineIndex;
        this.holographic = holographic;
        this.small = small;
        this.lineText = skullTexture;

        this.location = holographic.getLocation();
    }


    @Override
    public void initialize() {
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
        holographic.addEmptyLine();
        holographic.addEmptyLine();

        if (!fakeArmorStand.isSmall()) {
            holographic.addEmptyLine();
        }
    }

    @Override
    public void update() {
        fakeArmorStand.getEntityEquipment()
                .setEquipment(EnumWrappers.ItemSlot.HEAD, ItemUtil.getSkullByTexture(lineText));
    }

    @Override
    public boolean hasReceiver(@NonNull Player player) {
        return fakeArmorStand.hasReceiver(player);
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
        this.location = location.clone().add(0, -(0.25 * lineIndex), 0);

        fakeArmorStand.teleport(this.location);
    }

}
