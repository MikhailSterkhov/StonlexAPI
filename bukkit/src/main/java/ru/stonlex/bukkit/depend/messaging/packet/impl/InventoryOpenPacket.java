package ru.stonlex.bukkit.depend.messaging.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.inventory.impl.StonlexInventory;
import ru.stonlex.bukkit.depend.messaging.inventory.cache.InventoryCache;
import ru.stonlex.bukkit.depend.messaging.packet.MessagingPacket;

@AllArgsConstructor
@NoArgsConstructor
public class InventoryOpenPacket implements MessagingPacket {

    private String inventoryName;
    private Player player;

    @Override
    public void writePacket(ByteArrayDataOutput dataOutput) {
        // ноуп
    }

    @Override
    public void readPacket(ByteArrayDataInput dataInput) {
        this.inventoryName = dataInput.readUTF();
        this.player = Bukkit.getPlayer(dataInput.readUTF());
    }

    @Override
    public void handle() {
        InventoryCache.getInstance().getInventoryCache().cleanUp();
        StonlexInventory customMenu = InventoryCache.getInstance().getInventoryCache().asMap().get(inventoryName.toLowerCase());

        customMenu.openInventory(player);
    }
}
