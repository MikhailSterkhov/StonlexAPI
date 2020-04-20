package ru.stonlex.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.stonlex.bukkit.board.manager.SidebarManager;
import ru.stonlex.bukkit.command.factory.CommandFactory;
import ru.stonlex.bukkit.event.EventRegisterManager;
import ru.stonlex.bukkit.game.GameManager;
import ru.stonlex.bukkit.game.factory.AbstractGameFactory;
import ru.stonlex.bukkit.game.factory.AbstractGameTimer;
import ru.stonlex.bukkit.game.listener.GameFactoryListener;
import ru.stonlex.bukkit.game.setup.SetupManager;
import ru.stonlex.bukkit.game.setup.builder.SetupBuilder;
import ru.stonlex.bukkit.hologram.manager.HologramManager;
import ru.stonlex.bukkit.menu.listener.InventoryListener;
import ru.stonlex.bukkit.module.protocol.entity.listener.FakeEntityClickListener;
import ru.stonlex.bukkit.module.vault.manager.VaultManager;
import ru.stonlex.bukkit.tab.listener.TagListener;
import ru.stonlex.bukkit.tab.manager.TagManager;

public final class BukkitAPI extends JavaPlugin {

    @Getter
    private static final CommandFactory commandFactory = new CommandFactory();

    @Getter
    private static final SidebarManager sidebarManager = new SidebarManager();

    @Getter
    private static final HologramManager hologramManager = new HologramManager();

    @Getter
    private static final EventRegisterManager eventRegisterManager = new EventRegisterManager();

    @Getter
    private static final GameManager gameManager = new GameManager();

    @Getter
    private static final TagManager tagManager = new TagManager();

    @Getter
    private static VaultManager vaultManager = null;


    @Getter
    private static BukkitAPI instance; {
        instance = this;
    }


    @Override
    public void onEnable() {
        registerFakeEntityClicker();
        registerGameApi();

        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new TagListener(), this);

        vaultManager = new VaultManager();
    }


    /**
     * Регистрация листенера для фейковых Entity
     * (иначе говоря, пакетных, созданных на основе ProtocolLib)
     */
    private void registerFakeEntityClicker() {
        FakeEntityClickListener entityClickListener = new FakeEntityClickListener(this);

        ProtocolLibrary.getProtocolManager().addPacketListener(entityClickListener);
    }

    /**
     * Инициализация и регистрация фабрик игрового АПИ
     */
    private void registerGameApi() {
        SetupBuilder setupBuilder = gameManager.getSetupManager().getSetupBuilder();

        if (setupBuilder != null && setupBuilder.isSetupMode()) {
            return;
        }

        new GameFactoryListener();
    }

}
