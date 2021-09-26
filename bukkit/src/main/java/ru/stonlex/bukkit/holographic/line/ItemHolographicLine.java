package ru.stonlex.bukkit.holographic.line;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.holographic.ProtocolHolographic;
import ru.stonlex.bukkit.holographic.ProtocolHolographicLine;
import ru.stonlex.bukkit.protocollib.entity.impl.FakeArmorStand;
import ru.stonlex.bukkit.protocollib.entity.impl.FakeDroppedItem;

@Getter
public class ItemHolographicLine implements ProtocolHolographicLine {

    protected final int lineIndex;
    protected final ProtocolHolographic holographic;

    protected Location location;

    @Setter
    private FakeDroppedItem fakeDroppedItem;

    @Setter
    protected ItemStack itemStack;


    public ItemHolographicLine(int lineIndex, ItemStack itemStack, ProtocolHolographic holographic) {
        this.lineIndex = lineIndex;
        this.itemStack = itemStack;
        this.holographic = holographic;

        this.location = holographic.getLocation();
    }


    @Override
    public String getLineText() {
        return "";
    }

    @Override
    public void setLineText(String lineText) { }


    @Override
    public void initialize() {
        fakeDroppedItem = new FakeDroppedItem(itemStack, normalizeLocation(getLocation()));
        fakeDroppedItem.setNoGravity(true);
    }

    @Override
    public void update() {
        fakeDroppedItem.setItemStack(itemStack);
    }

    @Override
    public boolean hasReceiver(@NonNull Player player) {
        return fakeDroppedItem.hasReceiver(player);
    }

    @Override
    public void addReceivers(@NonNull Player... receivers) {
        fakeDroppedItem.addReceivers(receivers);
    }

    @Override
    public void removeReceivers(@NonNull Player... receivers) {
        fakeDroppedItem.removeReceivers(receivers);
    }

    @Override
    public boolean hasViewer(@NonNull Player player) {
        return fakeDroppedItem.hasViewer(player);
    }

    @Override
    public void addViewers(@NonNull Player... viewers) {
        fakeDroppedItem.addViewers(viewers);
    }

    @Override
    public void removeViewers(@NonNull Player... viewers) {
        fakeDroppedItem.removeViewers(viewers);
    }

    @Override
    public void spawn() {
        fakeDroppedItem.spawn();
    }

    @Override
    public void remove() {
        fakeDroppedItem.remove();
    }

    @Override
    public void teleport(Location location) {
        this.location = normalizeLocation(location);

        fakeDroppedItem.teleport(this.location);
    }

    @Override
    public double getLineDistance() {
        return 0.72D;
    }

    @Override
    public Location modifyNormalizeLocation(@NonNull Location normalizedLocation) {
        return normalizedLocation.add(0, getLineDistance() + 1.5D, 0);
    }

    @Override
    public FakeArmorStand getFakeArmorStand() {
        return null;
    }

}
