package ru.stonlex.bukkit.game.player;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.game.item.GameItem;
import ru.stonlex.bukkit.module.vault.player.VaultPlayer;

import java.util.List;

public class GamePlayer {

    @Getter
    private final String name;

    @Getter
    private final Player player;


    @Getter
    private TIntObjectMap<List<GameItem>> boughtItemMap = new TIntObjectHashMap<>();




    @Getter
    private boolean spectate = false;


    public GamePlayer(String playerName) {
        this.name = playerName;
        this.player = Bukkit.getPlayer(playerName);
    }


    /**
     * Установить игрока наблюдателем игры
     */
    public void setSpectate() {
        this.spectate = true;

        if (player == null) {
            return;
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 3));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));

        player.setAllowFlight(true);
        player.setFlying(true);

        player.getInventory().clear();
    }

    /**
     * Создать из данного игрока объект получения
     * данных Vault для этого игрока
     */
    public VaultPlayer getVaultPlayer() {
        return BukkitAPI.getInstance().getVaultManager().getVaultPlayer(name);
    }

    /**
     * Если предмет куплен у игрока
     *
     * @param gameItem - предмет
     */
    public boolean isItemBought(GameItem gameItem) {
        return boughtItemMap.valueCollection().stream().anyMatch(itemList -> itemList.contains(gameItem));
    }

    /**
     * Отправить сообщение игроку
     *
     * @param chatMessageType - куда отправить
     * @param messageText - сообщение
     */
    public void sendMessage(ChatMessageType chatMessageType, String messageText) {
        if (player == null) {
            return;
        }

        player.spigot().sendMessage(chatMessageType, new TextComponent(messageText).duplicate());
    }

    /**
     * Отправить сообщение игроку в ActionBar
     *
     * @param messageText - сообщение
     */
    public void sendMessage(String messageText) {
        sendMessage(ChatMessageType.ACTION_BAR, messageText);
    }


}
