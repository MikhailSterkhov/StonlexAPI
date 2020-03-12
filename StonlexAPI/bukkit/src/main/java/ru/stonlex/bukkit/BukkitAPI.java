package ru.stonlex.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import ru.stonlex.bukkit.board.manager.SidebarManager;
import ru.stonlex.bukkit.command.factory.CommandFactory;
import ru.stonlex.bukkit.event.EventRegisterManager;
import ru.stonlex.bukkit.game.GameManager;
import ru.stonlex.bukkit.hologram.manager.HologramManager;
import ru.stonlex.bukkit.menu.listener.InventoryListener;
import ru.stonlex.bukkit.protocol.entity.listener.FakeEntityClickListener;
import ru.stonlex.bukkit.tab.manager.TagManager;
import ru.stonlex.bukkit.vault.manager.VaultManager;

public final class BukkitAPI extends JavaPlugin implements PluginMessageListener {

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


    public static final String PLUGIN_MESSAGE_TAG = "StonlexChannel";



    @Override
    public void onEnable() {
        registerFakeEntityClicker();

        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, PLUGIN_MESSAGE_TAG);
        getServer().getMessenger().registerIncomingPluginChannel(this, PLUGIN_MESSAGE_TAG, this);

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

    @Override
    public void onPluginMessageReceived(String messageTag, Player player, byte[] bytes) {
        ByteArrayDataInput dataInput = ByteStreams.newDataInput( bytes );

        if ( !messageTag.equals(PLUGIN_MESSAGE_TAG) ) {
            return;
        }

        String channel = dataInput.readLine();

        switch (channel) {
            //todo
        }
    }

}
