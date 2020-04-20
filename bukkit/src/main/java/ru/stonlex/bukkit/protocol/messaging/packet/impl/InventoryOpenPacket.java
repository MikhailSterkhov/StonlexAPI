package ru.stonlex.bukkit.protocol.messaging.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.menu.StonlexMenu;
import ru.stonlex.bukkit.protocol.messaging.inventory.cache.InventoryCache;
import ru.stonlex.bukkit.protocol.messaging.packet.MessagingPacket;

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
        StonlexMenu customMenu = InventoryCache.getInstance().getInventoryCache().asMap().get(inventoryName.toLowerCase());

        customMenu.openInventory(player);
    }
}
