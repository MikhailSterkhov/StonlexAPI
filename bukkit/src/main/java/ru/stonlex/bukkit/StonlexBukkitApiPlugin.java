package ru.stonlex.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.stonlex.bukkit.command.manager.CommandManager;
import ru.stonlex.bukkit.protocollib.entity.listener.FakeEntityClickListener;
import ru.stonlex.bukkit.inventory.listener.SimpleInventoryListener;
import ru.stonlex.bukkit.inventory.manager.BukkitInventoryManager;
import ru.stonlex.bukkit.listener.PlayerListener;
import ru.stonlex.bukkit.protocollib.entity.listener.FakeEntityScopeListener;
import ru.stonlex.bukkit.protocollib.entity.listener.FakeEntityTrackListener;
import ru.stonlex.bukkit.scoreboard.listener.BaseScoreboardListener;
import ru.stonlex.bukkit.tag.listener.TagListener;
import ru.stonlex.bukkit.test.TestCommand;
import ru.stonlex.bukkit.vault.VaultManager;

import java.util.HashMap;
import java.util.Map;

@Getter
public final class StonlexBukkitApiPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        registerFakeEntityClicker();

        //events
        getServer().getPluginManager().registerEvents(new SimpleInventoryListener(), this);
        getServer().getPluginManager().registerEvents(new TagListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new BaseScoreboardListener(), this);

        getServer().getPluginManager().registerEvents(StonlexBukkitApi.HOLOGRAPHIC_MANAGER, this);

        //vault
        VaultManager.INSTANCE.getChatProvider().registerProvider(this);
        VaultManager.INSTANCE.getEconomyProvider().registerProvider(this);
        VaultManager.INSTANCE.getPermissionProvider().registerProvider(this);

        //inventories
        BukkitInventoryManager.INSTANCE.startInventoryUpdaters();

        //test
        StonlexBukkitApi.registerCommand(new TestCommand(), "test");
    }


    /**
     * Регистрация листенера для фейковых Entity
     * (иначе говоря, пакетных, созданных на основе ProtocolLib)
     */
    private void registerFakeEntityClicker() {
        FakeEntityClickListener entityClickListener = new FakeEntityClickListener(this);
        ProtocolLibrary.getProtocolManager().addPacketListener(entityClickListener);

        getServer().getPluginManager().registerEvents(new FakeEntityScopeListener(), this);
        getServer().getPluginManager().registerEvents(new FakeEntityTrackListener(), this);
    }

}
