package ru.stonlex.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.stonlex.bukkit.command.manager.CommandManager;
import ru.stonlex.bukkit.protocollib.entity.listener.FakeEntityClickListener;
import ru.stonlex.bukkit.holographic.manager.ProtocolHolographicManager;
import ru.stonlex.bukkit.inventory.listener.StonlexInventoryListener;
import ru.stonlex.bukkit.inventory.manager.BukkitInventoryManager;
import ru.stonlex.bukkit.listener.PlayerListener;
import ru.stonlex.bukkit.protocollib.entity.listener.FakeEntityScopeListener;
import ru.stonlex.bukkit.protocollib.entity.listener.FakeEntityTrackListener;
import ru.stonlex.bukkit.scoreboard.listener.BaseScoreboardListener;
import ru.stonlex.bukkit.tag.listener.TagListener;
import ru.stonlex.bukkit.tag.manager.TagManager;
import ru.stonlex.bukkit.test.TestCommand;
import ru.stonlex.bukkit.vault.VaultManager;

import java.util.HashMap;
import java.util.Map;

public final class StonlexBukkitApiPlugin extends JavaPlugin {

    @Getter
    private final ProtocolHolographicManager holographicManager = new ProtocolHolographicManager();

    @Getter
    private final TagManager tagManager = new TagManager();


    @Getter
    private static StonlexBukkitApiPlugin instance; {
        instance = this;
    }


    @Getter
    private final Map<String, Integer> serverOnlineMap = new HashMap<>();


    @Override
    public void onEnable() {
        registerFakeEntityClicker();

        //events
        getServer().getPluginManager().registerEvents(new StonlexInventoryListener(), this);
        getServer().getPluginManager().registerEvents(new TagListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new BaseScoreboardListener(), this);

        getServer().getPluginManager().registerEvents(holographicManager, this);

        //vault
        VaultManager.INSTANCE.getChatProvider().registerProvider(this);
        VaultManager.INSTANCE.getEconomyProvider().registerProvider(this);
        VaultManager.INSTANCE.getPermissionProvider().registerProvider(this);

        //inventories
        BukkitInventoryManager.INSTANCE.startInventoryUpdaters();

        //test
        CommandManager.INSTANCE.registerCommand(this, new TestCommand(), "test");
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


    public int getServerOnline(String serverName) {
        return serverOnlineMap.get(serverName.toLowerCase());
    }

    public int getGlobalOnline() {
        return serverOnlineMap.get("GLOBAL");
    }

}
