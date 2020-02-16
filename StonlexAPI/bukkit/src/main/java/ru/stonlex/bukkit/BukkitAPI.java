package ru.stonlex.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import lombok.Getter;
import ru.stonlex.bukkit.board.manager.SidebarManager;
import ru.stonlex.bukkit.command.factory.CommandFactory;
import ru.stonlex.bukkit.event.EventRegisterManager;
import ru.stonlex.bukkit.hologram.HologramManager;
import ru.stonlex.bukkit.menu.listener.InventoryListener;
import ru.stonlex.bukkit.protocol.entity.listener.FakeEntityClickListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BukkitAPI extends JavaPlugin {

    @Getter
    private static final CommandFactory commandFactory = new CommandFactory();

    @Getter
    private static final SidebarManager sidebarManager = new SidebarManager();

    @Getter
    private static final HologramManager hologramManager = new HologramManager();

    @Getter
    private static final EventRegisterManager eventRegisterManager = new EventRegisterManager();


    public static final String PLUGIN_MESSAGE_CHANNEL = "StonlexChannel";



    @Override
    public void onEnable() {
        registerFakeEntityClicker();

        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
    }


    /**
     * Регистрация листенера для фейковых Entity
     * (иначе говоря, пакетных, созданных на основе ProtocolLib)
     */
    private void registerFakeEntityClicker() {
        FakeEntityClickListener entityClickListener = new FakeEntityClickListener(this);

        ProtocolLibrary.getProtocolManager().addPacketListener(entityClickListener);
    }

}
