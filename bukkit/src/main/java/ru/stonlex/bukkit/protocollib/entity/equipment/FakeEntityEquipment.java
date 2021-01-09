package ru.stonlex.bukkit.protocollib.entity.equipment;

import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.protocollib.entity.FakeBaseEntity;
import ru.stonlex.bukkit.protocollib.packet.entity.WrapperPlayServerEntityEquipment;

import java.util.EnumMap;
import java.util.Map;

@RequiredArgsConstructor
public class FakeEntityEquipment {

    private final FakeBaseEntity fakeBaseEntity;

    private final EnumMap<EnumWrappers.ItemSlot, ItemStack> equipmentMap = new EnumMap<>(EnumWrappers.ItemSlot.class);

    public ItemStack getEquipment(EnumWrappers.ItemSlot itemSlot) {
        return equipmentMap.get(itemSlot);
    }

    public void setEquipment(EnumWrappers.ItemSlot itemSlot, ItemStack itemStack) {
        equipmentMap.put(itemSlot, itemStack);

        fakeBaseEntity.getViewerCollection().forEach(receiver -> sendEquipmentPacket(itemSlot, itemStack, receiver));
    }

    public void sendEquipmentPacket(EnumWrappers.ItemSlot itemSlot, ItemStack itemStack, Player player) {
        WrapperPlayServerEntityEquipment entityEquipment = new WrapperPlayServerEntityEquipment();

        entityEquipment.setEntityID(fakeBaseEntity.getEntityId());
        entityEquipment.setSlot(itemSlot);
        entityEquipment.setItem(itemStack);

        entityEquipment.sendPacket(player);
    }

    public void updateEquipmentPacket(Player player) {
        for (Map.Entry<EnumWrappers.ItemSlot, ItemStack> equipmentEntry : equipmentMap.entrySet()) {
            sendEquipmentPacket(equipmentEntry.getKey(), equipmentEntry.getValue(), player);
        }
    }

}
