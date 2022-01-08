package ru.stonlex.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import ru.stonlex.bukkit.inventory.BaseInventoryListener;
import ru.stonlex.bukkit.listener.PlayerListener;
import ru.stonlex.bukkit.protocollib.entity.listener.FakeEntityListener;
import ru.stonlex.bukkit.protocollib.team.ProtocolTeam;
import ru.stonlex.bukkit.scoreboard.listener.BaseScoreboardListener;
import ru.stonlex.bukkit.test.TestCommand;
import ru.stonlex.bukkit.utility.actionitem.ActionItemListener;
import ru.stonlex.bukkit.vault.VaultService;
import ru.stonlex.bukkit.vault.VaultServiceManager;

import java.util.logging.Level;

@Getter
public final class StonlexBukkitApiPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        // protocollib.
        registerFakeEntityClicker();

        // events.
        getServer().getPluginManager().registerEvents(new BaseInventoryListener(), this);
        getServer().getPluginManager().registerEvents(new BaseScoreboardListener(), this);
        getServer().getPluginManager().registerEvents(new ActionItemListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        getServer().getPluginManager().registerEvents(ProtocolTeam.TEAM_LISTENER, this);
        getServer().getPluginManager().registerEvents(StonlexBukkitApi.HOLOGRAPHIC_MANAGER, this);

        // vault services.
        if (getServer().getPluginManager().getPlugin("Vault") != null) {

            for (VaultService<?> vaultService : VaultServiceManager.REGISTRY.getVaultServices()) {
                Object instance = vaultService.getCurrentService(getServer());

                if (instance == null) {
                    getLogger().log(Level.WARNING, ChatColor.RED + "[StonlexApi][Vault]: Service `" + vaultService.getProviderType() + "` is`nt registered!");

                } else {

                    getLogger().log(Level.WARNING, ChatColor.GREEN + "[StonlexApi][Vault]: Service `" + vaultService.getProviderType() + "` was success registered!");
                }
            }
        }

        // inventories.
        StonlexBukkitApi.INVENTORY_MANAGER.startInventoryUpdateTask(this);

        // messaging.
        //StonlexBukkitApi.MESSAGING_MANAGER.registerIncomingListener(this, new BungeeInventoryIncomingListener(this));

        // test
        StonlexBukkitApi.registerCommand(new TestCommand());
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
