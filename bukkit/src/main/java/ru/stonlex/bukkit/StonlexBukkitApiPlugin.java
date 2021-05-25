package ru.stonlex.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.stonlex.bukkit.inventory.BaseInventoryListener;
import ru.stonlex.bukkit.listener.PlayerListener;
import ru.stonlex.bukkit.protocollib.entity.listener.FakeEntityListener;
import ru.stonlex.bukkit.protocollib.team.ProtocolTeam;
import ru.stonlex.bukkit.scoreboard.listener.BaseScoreboardListener;
import ru.stonlex.bukkit.vault.VaultManager;

@Getter
public final class StonlexBukkitApiPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        // protocol lib
        registerFakeEntityClicker();

        // events
        getServer().getPluginManager().registerEvents(new BaseInventoryListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new BaseScoreboardListener(), this);

        getServer().getPluginManager().registerEvents(ProtocolTeam.TEAM_LISTENER, this);
        getServer().getPluginManager().registerEvents(StonlexBukkitApi.HOLOGRAPHIC_MANAGER, this);

        // vault
        VaultManager.INSTANCE.getChatProvider().registerProvider(this);
        VaultManager.INSTANCE.getEconomyProvider().registerProvider(this);
        VaultManager.INSTANCE.getPermissionProvider().registerProvider(this);

        // inventories
        StonlexBukkitApi.INVENTORY_MANAGER.startInventoryUpdateTask(this);

        // test
        //StonlexBukkitApi.registerCommand(new TestCommand());
        //StonlexBukkitApi.registerCommand(new TestMegaCommand());
    }


    /**
     * Регистрация листенера для фейковых Entity
     * (иначе говоря, пакетных, созданных на основе ProtocolLib)
     */
    private void registerFakeEntityClicker() {
        FakeEntityListener fakeEntityListener = new FakeEntityListener();

        ProtocolLibrary.getProtocolManager().addPacketListener(fakeEntityListener);
        getServer().getPluginManager().registerEvents(fakeEntityListener, this);
    }

}
