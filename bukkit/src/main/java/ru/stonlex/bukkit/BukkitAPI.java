package ru.stonlex.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.stonlex.bukkit.board.manager.SidebarManager;
import ru.stonlex.bukkit.holographic.manager.ProtocolHolographicManager;
import ru.stonlex.bukkit.test.TestCommand;
import ru.stonlex.bukkit.command.manager.CommandManager;
import ru.stonlex.bukkit.event.EventRegisterManager;
import ru.stonlex.bukkit.game.GameManager;
import ru.stonlex.bukkit.inventory.listener.StonlexInventoryListener;
import ru.stonlex.bukkit.inventory.manager.BukkitInventoryManager;
import ru.stonlex.bukkit.module.protocol.entity.listener.FakeEntityClickListener;
import ru.stonlex.bukkit.module.vault.manager.VaultManager;
import ru.stonlex.bukkit.tab.listener.TagListener;
import ru.stonlex.bukkit.tab.manager.TagManager;

import java.util.HashMap;
import java.util.Map;

public final class BukkitAPI extends JavaPlugin {

    @Getter
    private final CommandManager commandManager = new CommandManager();

    @Getter
    private final SidebarManager sidebarManager = new SidebarManager();

    @Getter
    private final ProtocolHolographicManager holographicManager = new ProtocolHolographicManager();

    @Getter
    private final EventRegisterManager eventRegisterManager = new EventRegisterManager();

    @Getter
    private final BukkitInventoryManager inventoryManager = new BukkitInventoryManager();

    @Getter
    private final GameManager gameManager = new GameManager();

    @Getter
    private final TagManager tagManager = new TagManager();

    @Getter
    private VaultManager vaultManager = null;


    @Getter
    private static BukkitAPI instance; {
        instance = this;
    }


    @Getter
    private final Map<String, Integer> serverOnlineMap = new HashMap<>();


    @Override
    public void onEnable() {
        registerFakeEntityClicker();

        getServer().getPluginManager().registerEvents(new StonlexInventoryListener(), this);
        getServer().getPluginManager().registerEvents(new TagListener(), this);

        vaultManager = new VaultManager();

        //test
        commandManager.registerCommand(this, new TestCommand(), "test");
    }


    /**
     * Регистрация листенера для фейковых Entity
     * (иначе говоря, пакетных, созданных на основе ProtocolLib)
     */
    private void registerFakeEntityClicker() {
        FakeEntityClickListener entityClickListener = new FakeEntityClickListener(this);

        ProtocolLibrary.getProtocolManager().addPacketListener(entityClickListener);
    }


    public int getServerOnline(String serverName) {
        return serverOnlineMap.get(serverName.toLowerCase());
    }

    public int getGlobalOnline() {
        return serverOnlineMap.get("GLOBAL");
    }

}
