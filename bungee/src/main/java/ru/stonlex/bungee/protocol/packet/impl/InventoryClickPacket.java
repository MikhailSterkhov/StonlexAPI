package ru.stonlex.bungee.protocol.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import ru.stonlex.bungee.event.InventoryClickEvent;
import ru.stonlex.bungee.inventory.BungeeStonlexMenu;
import ru.stonlex.bungee.inventory.button.applicable.ButtonApplicable;
import ru.stonlex.bungee.inventory.cache.BungeeInventoryCache;
import ru.stonlex.bungee.protocol.packet.MessagingPacket;

@AllArgsConstructor
@NoArgsConstructor
public class InventoryClickPacket implements MessagingPacket {

    private String inventoryName;
    private ProxiedPlayer player;

    private int itemSlot;


    @Override
    public void writePacket(ByteArrayDataOutput dataOutput) {
        // ноуп
    }

    @Override
    public void readPacket(ByteArrayDataInput dataInput) {
        this.inventoryName = dataInput.readUTF();
        this.player = BungeeCord.getInstance().getPlayer(dataInput.readUTF());

        this.itemSlot = dataInput.readInt();
    }

    @Override
    public void handle() {
        BungeeInventoryCache.getInstance().getInventoryCache().cleanUp();
        BungeeStonlexMenu bungeeMenu = BungeeInventoryCache.getInstance().getInventoryCache().asMap().get(inventoryName.toLowerCase());

        if (bungeeMenu == null) {
            player.sendMessage("§cОшибка, Вы долгое время находились в данном GUI, из-за чего он принял AFK-режим.\n" +
                    "§cПриносим извинения за неудобства, но Вам необходимо переоткрыть его для дальнешего использования");
            return;
        }

        BungeeCord.getInstance().getPluginManager().callEvent(new InventoryClickEvent(bungeeMenu, itemSlot));

        ButtonApplicable buttonApplicable = bungeeMenu.getButtonMap().get(itemSlot).getButtonApplicable();

        if (buttonApplicable == null) {
            return;
        }

        buttonApplicable.onButtonClick(player);
    }

}
